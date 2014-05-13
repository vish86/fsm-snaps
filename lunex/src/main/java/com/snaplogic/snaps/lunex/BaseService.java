/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc. All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.lunex;

import com.fasterxml.jackson.core.JsonParseException;
import com.google.common.collect.Lists;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.jayway.jsonpath.JsonPath;
import com.snaplogic.account.api.Account;
import com.snaplogic.account.api.capabilities.Accounts;
import com.snaplogic.account.api.capabilities.MultiAccountBinding;
import com.snaplogic.api.ConfigurationException;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.common.SnapType;
import com.snaplogic.common.properties.SnapProperty;
import com.snaplogic.common.properties.Suggestions;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.common.properties.builders.SuggestionBuilder;
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.api.ExpressionProperty;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.SimpleSnap;
import com.snaplogic.snap.api.SnapCategory;
import com.snaplogic.snap.api.SnapDataException;
import com.snaplogic.snap.api.capabilities.Category;
import com.snaplogic.snap.api.capabilities.Inputs;
import com.snaplogic.snap.api.capabilities.Outputs;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snap.api.capabilities.ViewType;
import com.snaplogic.snaps.lunex.Constants.CResource;
import com.snaplogic.snaps.lunex.Constants.HttpMethodNames;
import com.snaplogic.snaps.lunex.Constants.LunexSnaps;
import com.snaplogic.snaps.lunex.bean.AccountBean;
import com.snaplogic.util.JsonPathBuilder;
import com.snaplogic.util.JsonPathUtil;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.snaplogic.snaps.lunex.Messages.*;

import static com.snaplogic.snaps.lunex.Constants.*;


/**
 * Base class for Lunex snap pack which contains common snap properties, authentication and
 * request handling.
 *
 * @author svatada
 */
@Inputs(min = 1, max = 1, accepts = { ViewType.DOCUMENT })
@Outputs(min = 1, max = 1, offers = { ViewType.DOCUMENT })
@Version(snap = 1)
@Category(snap = SnapCategory.TRANSFORM)
@Accounts(provides = { LunexBasicAuthAccount.class }, optional = true)
public abstract class BaseService extends SimpleSnap implements
    MultiAccountBinding<Account<AccountBean>> {
    private static final int INIT_HEADER_SIZE = 4;
    private final String PARAM_NAME_JSON =
        new JsonPathBuilder(PARAM_NAME_PROP).appendValueElement()
                .build();
    private final String FIELD_NAME_JSON =
        new JsonPathBuilder(FIELD_NAME_PROP).appendValueElement()
                .build();
    private final String HEADER_KEY_JSON =
        new JsonPathBuilder(HEADER_KEY_PROP).appendValueElement()
                .build();
    private final String HEADER_VALUE_JSON =
        new JsonPathBuilder(HEADER_VALUE_PROP).appendValueElement()
                .build();
    private List<Pair<String, ExpressionProperty>> queryParams;
    private List<Pair<String, ExpressionProperty>> requestContentInfo;
    private List<Pair<String, String>> headersProperties;
    @Inject
    private Account<AccountBean> account;
    private ExpressionProperty username, password;
    private LunexSnaps snapsType;
    @Inject
    private JsonPathUtil jsonPathUtil;
    private String resourceType;
    private String lunexEnpointHostIP;
    private boolean isAuthHeaderSet;
    private boolean isCredentialsSet;
    private static Logger log;
    private HttpMethodNames httpMethod;

    public BaseService(LunexSnaps snaps, HttpMethodNames httpMethod) {
        this.snapsType = snaps;
        this.httpMethod = httpMethod;
        log = LoggerFactory.getLogger(getClass());
    }

    @Override
    public void cleanup() throws ExecutionException {
        // NO OP
    }

    @Override
    public void configure(final PropertyValues propertyValues) throws ConfigurationException {
        String queryParam, key, value;
        ExpressionProperty queryParamValue;
        lunexEnpointHostIP = propertyValues.get(SERVICE_DOMAIN_PROP);
        resourceType = propertyValues.get(RESOURCE_PROP).toString();
        List<Map<String, String>> params = propertyValues.get(PARAM_TABLE_MAPPINGS_PROP);
        if (CollectionUtils.isNotEmpty(params)) {
            queryParams = Lists.newArrayListWithExpectedSize(params.size());
            for (Map<String, String> param : params) {
                queryParam = JsonPath.read(param, PARAM_NAME_JSON);
                queryParamValue = propertyValues.getExpressionPropertyFor(param, PARAM_PATH_PROP);
                queryParams.add(Pair.of(queryParam, queryParamValue));
            }
        }
        List<Map<String, String>> fieldSet = propertyValues.get(FIELD_TABLE_MAPPINGS_PROP);
        if (CollectionUtils.isNotEmpty(fieldSet)) {
            requestContentInfo = Lists.newArrayListWithExpectedSize(fieldSet.size());
            for (Map<String, String> field : fieldSet) {
                queryParam = JsonPath.read(field, FIELD_NAME_JSON);
                queryParamValue = propertyValues.getExpressionPropertyFor(field, FIELD_PATH_PROP);
                requestContentInfo.add(Pair.of(queryParam, queryParamValue));
            }
        }
        username = propertyValues.getAsExpression(USERNAME_PROP);
        password = propertyValues.getAsExpression(PASSWORD_PROP);
        List<Map<String, Object>> httpHeader = propertyValues.get(HTTP_HEADER_PROP);
        int size = INIT_HEADER_SIZE;
        if (CollectionUtils.isNotEmpty(httpHeader)) {
            size += httpHeader.size();
        }
        /*
         * Snap considers the authentication header based on the following: -> Gives the first
         * priority to Username and Password properties from the snap. -> If they are empty then
         * checks for Account class, -> finally it will checks in header table for Authentication
         * header.
         */
        if (!username.isEmpty() && !password.isEmpty()) {
            isCredentialsSet = true;
        }
        headersProperties = Lists.newArrayListWithExpectedSize(size);
        setDefaultHeaders();
        if (CollectionUtils.isNotEmpty(httpHeader)) {
            for (Map<String, Object> item : httpHeader) {
                if (null != item) {
                    key = jsonPathUtil.nullableRead(HEADER_KEY_JSON, item);
                    if (isAuthHeaderSet && key.equalsIgnoreCase(AUTHORIZATION)) {
                        continue;
                    } else {
                        value = jsonPathUtil.nullableRead(HEADER_VALUE_JSON, item);
                        headersProperties.add(Pair.of(key, value));
                        if (key.equalsIgnoreCase(AUTHORIZATION)) {
                            isAuthHeaderSet = true;
                        }
                    }
                }
            }
        }
        /* No credentials and no auth header */
        if (!isAuthHeaderSet && !isCredentialsSet) {
            throw new ExecutionException(AUTH_ERROR)
                    .withReason(AUTH_ERROR_REASON)
                    .withResolution(AUTH_ERROR_RESOLUTION);
        }
    }

    @Override
    public void defineProperties(final PropertyBuilder propertyBuilder) {
        propertyBuilder.describe(SERVICE_DOMAIN_PROP, SERVICE_DOMAIN_LABEL,
            SERVICE_DOMAIN_DESCRIPTION)
                .required()
                .add();
        propertyBuilder.describe(USERNAME_PROP, USERNAME_LABEL, USERNAME_DESCRIPTION)
                .expression(SnapProperty.DecoratorType.ACCEPTS_SCHEMA)
                .add();
        propertyBuilder.describe(PASSWORD_PROP, PASSWORD_LABEL, PASSWORD_DESCRIPTION)
                .expression(SnapProperty.DecoratorType.ACCEPTS_SCHEMA)
                .add();
        propertyBuilder.describe(RESOURCE_PROP, RESOURCE_LABEL, RESOURCE_DESC);
        switch (snapsType) {
            case Create:
                propertyBuilder.withAllowedValues(CR_PARAM_LIST.keySet())
                        .add();
                break;
            case Read:
                propertyBuilder.withAllowedValues(RR_PARAM_LIST.keySet())
                        .add();
                break;
            case Delete:
                propertyBuilder.withAllowedValues(DR_PARAM_LIST.keySet())
                        .add();
                break;
            case Update:
                propertyBuilder.withAllowedValues(UR_PARAM_LIST.keySet())
                        .add();
                break;
        }
        SnapProperty headerKey =
            propertyBuilder.describe(HEADER_KEY_PROP, HEADER_KEY_LABEL, HEADER_KEY_DESCRIPTION)
                    .build();
        SnapProperty headerValue =
            propertyBuilder.describe(HEADER_VALUE_PROP, HEADER_VALUE_LABEL,
                HEADER_VALUE_DESCRIPTION)
                    .build();
        propertyBuilder.describe(HTTP_HEADER_PROP, HTTP_HEADER_LABEL, HTTP_HEADER_DESCRIPTION)
                .type(SnapType.TABLE)
                .withEntry(headerKey)
                .withEntry(headerValue)
                .add();
        SnapProperty paramPath =
            propertyBuilder.describe(PARAM_PATH_PROP, PARAM_PATH_LABEL, PARAM_PATH_DESC)
                    .expression(SnapProperty.DecoratorType.ACCEPTS_SCHEMA)
                    .build();
        SnapProperty paramName =
            propertyBuilder.describe(PARAM_NAME_PROP, PARAM_NAME_LABEL, PARAM_NAME_DESC)
                    .withSuggestions(new Suggestions() {
                    @Override
                    public void suggest(final SuggestionBuilder suggestionBuilder,
                        final PropertyValues propertyValues) {
                        switch (snapsType) {
                            case Create:
                                suggestionBuilder.node(PARAM_TABLE_MAPPINGS_PROP)
                                        .over(PARAM_NAME_PROP)
                                        .suggestions(
                                            CR_PARAM_LIST.get(propertyValues.get(RESOURCE_PROP))
                                                .toArray(new String[0]));
                                break;
                            case Read:
                                suggestionBuilder.node(PARAM_TABLE_MAPPINGS_PROP)
                                        .over(PARAM_NAME_PROP)
                                        .suggestions(
                                            RR_PARAM_LIST.get(propertyValues.get(RESOURCE_PROP))
                                                .toArray(new String[0]));
                                break;
                            case Delete:
                                suggestionBuilder.node(PARAM_TABLE_MAPPINGS_PROP)
                                        .over(PARAM_NAME_PROP)
                                        .suggestions(
                                            DR_PARAM_LIST.get(propertyValues.get(RESOURCE_PROP))
                                                .toArray(new String[0]));
                                break;
                            case Update:
                                suggestionBuilder.node(PARAM_TABLE_MAPPINGS_PROP)
                                        .over(PARAM_NAME_PROP)
                                        .suggestions(
                                            UR_PARAM_LIST.get(propertyValues.get(RESOURCE_PROP))
                                                .toArray(new String[0]));
                                break;
                        }
                    }
                })
                .build();
        propertyBuilder.describe(PARAM_TABLE_MAPPINGS_PROP, PARAM_TABLE_MAPPINGS_LABEL,
            PARAM_TABLE_MAPPINGS_DESC)
                    .type(SnapType.TABLE)
                    .withEntry(paramPath)
                    .withEntry(paramName)
                    .required()
                    .add();
        if (snapsType != LunexSnaps.Read) {
            SnapProperty fieldPath =
                propertyBuilder.describe(FIELD_PATH_PROP, FIELD_PATH_LABEL, FIELD_PATH_DESC)
                        .expression(SnapProperty.DecoratorType.ACCEPTS_SCHEMA)
                        .build();
            SnapProperty fieldName =
                propertyBuilder.describe(FIELD_NAME_PROP, FIELD_NAME_LABEL, FIELD_NAME_DESC)
                        .withSuggestions(new Suggestions() {
                        @Override
                        public void suggest(final SuggestionBuilder suggestionBuilder,
                            final PropertyValues propertyValues) {
                            suggestionBuilder.node(FIELD_TABLE_MAPPINGS_PROP)
                                    .over(FIELD_NAME_PROP)
                                    .suggestions(REQ_BODY_PARAM_INFO.keySet()
                                        .toArray(new String[0]));
                        }
                    })
                    .build();
            propertyBuilder.describe(FIELD_TABLE_MAPPINGS_PROP, FIELD_TABLE_MAPPINGS_LABEL,
                FIELD_TABLE_MAPPINGS_DESC)
                        .type(SnapType.TABLE)
                        .withEntry(fieldPath)
                        .withEntry(fieldName)
                        .add();
        }
    }

    private StringBuilder getJsonSlice(Pair<String, ExpressionProperty> paramPair, Document document) {
        String key = paramPair.getKey();
        if (REQ_BODY_PARAM_INFO.get(key) == 1) {
            return new StringBuilder().append(QUOTE)
                .append(key)
                .append(QUOTE)
                .append(COLON)
                .append(paramPair.getRight()
                    .eval(document))
                .append(COMMA);
        }
        return new StringBuilder().append(key)
            .append(QUOTE)
            .append(COLON)
            .append(QUOTE)
            .append(paramPair.getRight()
                .eval(document))
            .append(QUOTE)
            .append(COMMA);
    }

    @Override
    public Module getManagedAccountModule(final Account<AccountBean> account) {
        return new AbstractModule() {
            @Override
            protected void configure() {
                if (account == null) {
                    bind(Key.get(new TypeLiteral<Account<AccountBean>>() {
                    })).to(DefaultAccount.class)
                        .in(Scopes.SINGLETON);
                } else {
                    bind(Key.get(new TypeLiteral<Account<AccountBean>>() {
                    })).toInstance(account);
                }
            }
        };
    }

    private StringBuilder prepareJson(List<Pair<String, ExpressionProperty>> requestContent,
        Document document) {
        StringBuilder json = new StringBuilder();
        StringBuilder subJson = new StringBuilder();
        boolean isSubJsonRequired = false, isEmptyJson = true;
        if (requestContent != null) {
            if (resourceType.equals(CResource.NewOrder.toString())
                || resourceType.equals(CResource.PreOrder.toString())) {
                subJson.append(ADDRESS)
                        .append(COLON)
                        .append(OPENTAG);
                isSubJsonRequired = true;
            }
            for (Pair<String, ExpressionProperty> paramPair : requestContent) {
                if (isSubJsonRequired && ADDRESS_JSONOBJ.contains(paramPair.getKey())) {
                    subJson.append(getJsonSlice(paramPair, document));
                    isEmptyJson = false;
                } else {
                    json.append(getJsonSlice(paramPair, document));
                }
            }
            if (!isEmptyJson) {
                subJson.append(CLOSETAG)
                        .append(COMMA);
            }
            return new StringBuilder().append(OPENTAG)
                .append(subJson)
                .append(json.deleteCharAt(json.length() - 1))
                .append(CLOSETAG);
        }
        return new StringBuilder("");
    }

    @Override
    protected void process(Document document, String inputViewName) {
        Map<String, Object> map = null;
        // SnapDataException snapException = null;
        try {
            if (isCredentialsSet) {
                /* Updating Auth header using credentials from input document */
                setAuthHeaderProp(username.eval(document)
                        .toString(), password.eval(document)
                        .toString());
            }
            RequestBuilder rBuilder = new RequestBuilder().addDoc(document)
                .addEndPointIP(lunexEnpointHostIP)
                .addHeaders(headersProperties)
                .addQueryParams(queryParams)
                .addRequestBody(prepareJson(requestContentInfo, document))
                .addResource(resourceType)
                .addSnapTye(snapsType)
                .addMethod(httpMethod);
            String response = RequestProcessor.getInstance()
                .execute(rBuilder);
            if (RequestProcessor.getInstance()
                .getStatusCode() == HttpStatus.SC_OK) {
                map = new HashMap<String, Object>();
                map.put(STATUS_CODE_TAG, HttpStatus.SC_OK);
                map.put(resourceType, OBJECT_MAPPER.readValue(response, MAP_TYPE_REFERENCE));
                outputViews.write(documentUtility.newDocument(map));
            } else {
                writeErrorRecord(RequestProcessor.getInstance()
                    .getStatusCode(), HTTP_ERROR_REASON, HTTP_ERROR_RESOLUTION, response);
            }
        } catch (MalformedURLException ex) {
            writeExceptionRecord(ex.getLocalizedMessage(), MALFORMEDURL_ERROR_REASON,
                MALFORMEDURL_ERROR_RESOLUTION, ex);
        } catch (IllegalStateException ex) {
            writeExceptionRecord(CONTENT_STREAM_ERROR, ILLEGAL_STATE_REASON,
                ILLEGAL_STATE_RESOLUTION, ex);
        } catch (JsonParseException ex) {
            writeExceptionRecord(JSON_PARSING_ERROR, JSON_PARSING_REASON, JSON_PARSING_RESOLUTION,
                ex);
        } catch (IOException ex) {
            writeExceptionRecord(IO_ERROR, IO_ERROR_REASON, IO_ERROR_RESOLUTION, ex);
        } catch (Exception ex) {
            writeExceptionRecord(ERRORMSG, ERROR_REASON, ERROR_RESOLUTION, ex);
        }
    }

    private void writeExceptionRecord(final String errMsg, final String errReason,
        final String errResoulution, Throwable ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_TAG, errMsg);
        map.put(MESSAGE_TAG, ex.getMessage());
        map.put(REASON_TAG, errReason);
        map.put(RESOLUTION_TAG, errResoulution);
        SnapDataException snapException =new SnapDataException(documentUtility.newDocument(map), ex.getMessage())
                .withReason(errReason)
                .withResolution(errResoulution);
        errorViews.write(snapException);
    }

    private void writeErrorRecord(final int httpErrCode, final String errReason,
        final String errResoulution, final String errResponse) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_TAG, httpErrCode);
        map.put(MESSAGE_TAG, errResponse);
        map.put(REASON_TAG, errReason);
        map.put(RESOLUTION_TAG, errResoulution);
        SnapDataException snapException =new SnapDataException(documentUtility.newDocument(map), errResponse)
                .withReason(errReason)
                .withResolution(errResoulution);
        errorViews.write(snapException);
    }

    private void setAuthHeaderProp(String username, String passcode) {
        headersProperties.add(Pair.of(AUTHORIZATION, new StringBuilder().append(BASIC)
            .append(Base64.encodeBase64String((new StringBuilder().append(username)
                .append(COLON)
                .append(passcode).toString()).getBytes()))
            .toString()));
    }

    private void setDefaultHeaders() {
        headersProperties.add(Pair.of(CONTENT_TYPE, APPLICATION_JSON));
        headersProperties.add(Pair.of(ACCEPT, APPLICATION_JSON));
        AccountBean bean = account.connect();
        if (!isCredentialsSet && bean != null) {
            isAuthHeaderSet = true;
            setAuthHeaderProp(bean.getUsername(), bean.getPassword());
        }
    }
}