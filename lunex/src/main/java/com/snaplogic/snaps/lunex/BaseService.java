/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc.  All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.lunex;

import static com.snaplogic.snaps.lunex.constants.Constants.CLOSETAG;
import static com.snaplogic.snaps.lunex.constants.Constants.CR_PARAM_LIST;
import static com.snaplogic.snaps.lunex.constants.Constants.DR_PARAM_LIST;
import static com.snaplogic.snaps.lunex.constants.Constants.ADDRESS_JSONOBJ;
import static com.snaplogic.snaps.lunex.constants.Constants.OPENTAG;
import static com.snaplogic.snaps.lunex.constants.Constants.REQ_BODY_PARAM_INFO;
import static com.snaplogic.snaps.lunex.constants.Constants.RR_PARAM_LIST;
import static com.snaplogic.snaps.lunex.constants.Constants.UR_PARAM_LIST;
import static com.snaplogic.snaps.lunex.constants.Messages.AUTH_ERROR;
import static com.snaplogic.snaps.lunex.constants.Messages.AUTH_ERROR_REASON;
import static com.snaplogic.snaps.lunex.constants.Messages.AUTH_ERROR_RESOLUTION;
import static com.snaplogic.snaps.lunex.constants.Messages.CONTENT_STREAM_ERROR;
import static com.snaplogic.snaps.lunex.constants.Messages.ERROR_REASON;
import static com.snaplogic.snaps.lunex.constants.Messages.ERROR_RESOLUTION;
import static com.snaplogic.snaps.lunex.constants.Messages.FIELD_NAME_DESC;
import static com.snaplogic.snaps.lunex.constants.Messages.FIELD_NAME_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.FIELD_NAME_PROP;
import static com.snaplogic.snaps.lunex.constants.Messages.FIELD_PATH_DESC;
import static com.snaplogic.snaps.lunex.constants.Messages.FIELD_PATH_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.FIELD_PATH_PROP;
import static com.snaplogic.snaps.lunex.constants.Messages.FIELD_TABLE_MAPPINGS_DESC;
import static com.snaplogic.snaps.lunex.constants.Messages.FIELD_TABLE_MAPPINGS_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.FIELD_TABLE_MAPPINGS_PROP;
import static com.snaplogic.snaps.lunex.constants.Messages.HEADER_KEY_DESCRIPTION;
import static com.snaplogic.snaps.lunex.constants.Messages.HEADER_KEY_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.HEADER_VALUE_DESCRIPTION;
import static com.snaplogic.snaps.lunex.constants.Messages.HEADER_VALUE_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.HTTP_HEADER_DESCRIPTION;
import static com.snaplogic.snaps.lunex.constants.Messages.HTTP_HEADER_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.ILLEGAL_STATE_REASON;
import static com.snaplogic.snaps.lunex.constants.Messages.ILLEGAL_STATE_RESOLUTION;
import static com.snaplogic.snaps.lunex.constants.Messages.IO_ERROR;
import static com.snaplogic.snaps.lunex.constants.Messages.IO_ERROR_REASON;
import static com.snaplogic.snaps.lunex.constants.Messages.IO_ERROR_RESOLUTION;
import static com.snaplogic.snaps.lunex.constants.Messages.JSON_PARSING_ERROR;
import static com.snaplogic.snaps.lunex.constants.Messages.JSON_PARSING_REASON;
import static com.snaplogic.snaps.lunex.constants.Messages.JSON_PARSING_RESOLUTION;
import static com.snaplogic.snaps.lunex.constants.Messages.PARAM_NAME_DESC;
import static com.snaplogic.snaps.lunex.constants.Messages.PARAM_NAME_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.PARAM_NAME_PROP;
import static com.snaplogic.snaps.lunex.constants.Messages.PARAM_PATH_DESC;
import static com.snaplogic.snaps.lunex.constants.Messages.PARAM_PATH_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.PARAM_PATH_PROP;
import static com.snaplogic.snaps.lunex.constants.Messages.PARAM_TABLE_MAPPINGS_DESC;
import static com.snaplogic.snaps.lunex.constants.Messages.PARAM_TABLE_MAPPINGS_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.PARAM_TABLE_MAPPINGS_PROP;
import static com.snaplogic.snaps.lunex.constants.Messages.RESOURCE_DESC;
import static com.snaplogic.snaps.lunex.constants.Messages.RESOURCE_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.RESOURCE_PROP;
import static com.snaplogic.snaps.lunex.constants.Messages.SERVICE_DOMAIN_DESCRIPTION;
import static com.snaplogic.snaps.lunex.constants.Messages.SERVICE_DOMAIN_LABEL;
import static com.snaplogic.snaps.lunex.constants.Messages.*;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import com.snaplogic.snap.api.capabilities.Errors;
import com.snaplogic.snap.api.capabilities.Inputs;
import com.snaplogic.snap.api.capabilities.Outputs;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snap.api.capabilities.ViewType;
import com.snaplogic.snaps.lunex.bean.AccountBean;
import com.snaplogic.snaps.lunex.constants.Constants.CResource;
import com.snaplogic.snaps.lunex.constants.Constants.LunexSnaps;
import com.snaplogic.snaps.lunex.http.handler.RequestBuilder;
import com.snaplogic.snaps.lunex.http.handler.RequestProcessor;
import com.snaplogic.util.JsonPathBuilder;
import com.snaplogic.util.JsonPathUtil;
/**
 * This will takes care of common snap properties, authentication and request
 * handling.
 * 
 * @author svatada
 */
@Inputs(min = 1, max = 1, accepts = {ViewType.DOCUMENT})
@Outputs(min = 1, max = 1, offers = {ViewType.DOCUMENT})
@Errors(min = 1, max = 1, offers = {ViewType.DOCUMENT})
@Version(snap = 1)
@Category(snap = SnapCategory.TRANSFORM)
@Accounts(provides = {LunexBasicAuthAccount.class}, optional = true)
public abstract class BaseService extends SimpleSnap
        implements
            MultiAccountBinding<Account<AccountBean>> {
	private static final String RESOLUTION_TAG = "Resolution";
	private static final String REASON_TAG = "Reason";
	private static final String MESSAGE_TAG = "Message";
	private static final String COMMA = ",";
	private static final String COLON = ":";
	private static final String QUOTE = "\"";
	private static final String ADDRESS = "Address";
	private static final String ERROR_TAG = "Error";
	private static final String APPLICATION_JSON = "application/json";
	private static final String ACCEPT = "Accept";
	private static final String CONTENT_TYPE = "Content-type";
	private static final String BASIC = "Basic ";
	private static final String AUTHORIZATION = "Authorization";
	private static final String HEADER_KEY_PROP = "headerKey";
	private static final String HEADER_VALUE_PROP = "headerValue";
	private static final String HTTP_HEADER_PROP = "header";
	private static final String SERVICE_DOMAIN_PROP = "Domain";
	private final String PARAM_NAME_JSON = new JsonPathBuilder(PARAM_NAME_PROP)
	        .appendValueElement().build();
	private final String FIELD_NAME_JSON = new JsonPathBuilder(FIELD_NAME_PROP)
	        .appendValueElement().build();

	private final String HEADER_KEY_JSON = new JsonPathBuilder(HEADER_KEY_PROP)
	        .appendValueElement().build();
	private final String HEADER_VALUE_JSON = new JsonPathBuilder(
	        HEADER_VALUE_PROP).appendValueElement().build();
	private List<Pair<String, ExpressionProperty>> queryParams;
	private List<Pair<String, ExpressionProperty>> requestContentInfo;
	private List<Pair<String, String>> additionalHeaders;
	private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	@Inject
	private Account<AccountBean> account = null;
	private LunexSnaps snapsType;
	@Inject
	private JsonPathUtil jsonPathUtil;
	private String resourceType;
	private String lunexEnpointHostIP;
	private boolean isAuthHeaderSet = false;
	private static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {
	};
	private static Logger log;

	public BaseService(LunexSnaps snaps) {
		this.snapsType = snaps;
		log = LoggerFactory.getLogger(getClass());
	}

	@Override
	public Module getManagedAccountModule(final Account<AccountBean> account) {
		return new AbstractModule() {
			@Override
			protected void configure() {
				if (account == null) {
					bind(Key.get(new TypeLiteral<Account<AccountBean>>() {
					})).to(DefaultAccount.class).in(Scopes.SINGLETON);
				} else {
					bind(Key.get(new TypeLiteral<Account<AccountBean>>() {
					})).toInstance(account);
				}
			}
		};
	}

	@Override
	public void defineProperties(final PropertyBuilder propertyBuilder) {
		propertyBuilder
		        .describe(SERVICE_DOMAIN_PROP, SERVICE_DOMAIN_LABEL,
		                SERVICE_DOMAIN_DESCRIPTION).required().add();
		propertyBuilder.describe(RESOURCE_PROP, RESOURCE_LABEL, RESOURCE_DESC);
		switch (snapsType) {
			case Create :
				propertyBuilder.withAllowedValues(CR_PARAM_LIST.keySet()).add();
				break;
			case Read :
				propertyBuilder.withAllowedValues(RR_PARAM_LIST.keySet()).add();
				break;
			case Delete :
				propertyBuilder.withAllowedValues(DR_PARAM_LIST.keySet()).add();
				break;
			case Update :
				propertyBuilder.withAllowedValues(UR_PARAM_LIST.keySet()).add();
				break;
		}
		SnapProperty headerKey = propertyBuilder.describe(HEADER_KEY_PROP,
		        HEADER_KEY_LABEL, HEADER_KEY_DESCRIPTION).build();
		SnapProperty headerValue = propertyBuilder.describe(HEADER_VALUE_PROP,
		        HEADER_VALUE_LABEL, HEADER_VALUE_DESCRIPTION).build();
		propertyBuilder
		        .describe(HTTP_HEADER_PROP, HTTP_HEADER_LABEL,
		                HTTP_HEADER_DESCRIPTION).type(SnapType.TABLE)
		        .withEntry(headerKey).withEntry(headerValue).add();

		SnapProperty paramPath = propertyBuilder
		        .describe(PARAM_PATH_PROP, PARAM_PATH_LABEL, PARAM_PATH_DESC)
		        .schemaAware(SnapProperty.DecoratorType.ACCEPTS_SCHEMA)
		        .expression().build();
		SnapProperty paramName = propertyBuilder
		        .describe(PARAM_NAME_PROP, PARAM_NAME_LABEL, PARAM_NAME_DESC)
		        .withSuggestions(new Suggestions() {
			        @Override
			        public void suggest(
			                final SuggestionBuilder suggestionBuilder,
			                final PropertyValues propertyValues) {
				        switch (snapsType) {
							case Create :
								suggestionBuilder
								        .node(PARAM_TABLE_MAPPINGS_PROP)
								        .over(PARAM_NAME_PROP)
								        .suggestions(
								                CR_PARAM_LIST
								                        .get(propertyValues
								                                .get(RESOURCE_PROP))
								                        .toArray(new String[0]));
								break;
							case Read :
								suggestionBuilder
								        .node(PARAM_TABLE_MAPPINGS_PROP)
								        .over(PARAM_NAME_PROP)
								        .suggestions(
								                RR_PARAM_LIST
								                        .get(propertyValues
								                                .get(RESOURCE_PROP))
								                        .toArray(new String[0]));
								break;
							case Delete :
								suggestionBuilder
								        .node(PARAM_TABLE_MAPPINGS_PROP)
								        .over(PARAM_NAME_PROP)
								        .suggestions(
								                DR_PARAM_LIST
								                        .get(propertyValues
								                                .get(RESOURCE_PROP))
								                        .toArray(new String[0]));
								break;
							case Update :
								suggestionBuilder
								        .node(PARAM_TABLE_MAPPINGS_PROP)
								        .over(PARAM_NAME_PROP)
								        .suggestions(
								                UR_PARAM_LIST
								                        .get(propertyValues
								                                .get(RESOURCE_PROP))
								                        .toArray(new String[0]));
								break;
						}
					}
		        }).build();
		propertyBuilder
		        .describe(PARAM_TABLE_MAPPINGS_PROP,
		                PARAM_TABLE_MAPPINGS_LABEL, PARAM_TABLE_MAPPINGS_DESC)
		        .type(SnapType.TABLE).withEntry(paramPath).withEntry(paramName)
		        .required().add();

		if (snapsType != LunexSnaps.Read) {
			SnapProperty fieldPath = propertyBuilder
			        .describe(FIELD_PATH_PROP, FIELD_PATH_LABEL,
			                FIELD_PATH_DESC)
			        .schemaAware(SnapProperty.DecoratorType.ACCEPTS_SCHEMA)
			        .expression().build();
			SnapProperty fieldName = propertyBuilder
			        .describe(FIELD_NAME_PROP, FIELD_NAME_LABEL,
			                FIELD_NAME_DESC).withSuggestions(new Suggestions() {
				        @Override
				        public void suggest(
				                final SuggestionBuilder suggestionBuilder,
				                final PropertyValues propertyValues) {
					        suggestionBuilder
					                .node(FIELD_TABLE_MAPPINGS_PROP)
					                .over(FIELD_NAME_PROP)
					                .suggestions(
					                        REQ_BODY_PARAM_INFO.keySet()
					                                .toArray(new String[0]));
				        }
			        }).build();

			propertyBuilder
			        .describe(FIELD_TABLE_MAPPINGS_PROP,
			                FIELD_TABLE_MAPPINGS_LABEL,
			                FIELD_TABLE_MAPPINGS_DESC).type(SnapType.TABLE)
			        .withEntry(fieldPath).withEntry(fieldName).required().add();
		}
	}
	@Override
	public void configure(final PropertyValues propertyValues)
	        throws ConfigurationException {
		lunexEnpointHostIP = propertyValues.get(SERVICE_DOMAIN_PROP);
		resourceType = propertyValues.get(RESOURCE_PROP).toString();
		List<Map<String, String>> params = propertyValues
		        .get(PARAM_TABLE_MAPPINGS_PROP);
		if (CollectionUtils.isNotEmpty(params)) {
			queryParams = Lists.newArrayListWithExpectedSize(params.size());
			for (Map<String, String> param : params) {
				String queryParam = JsonPath.read(param, PARAM_NAME_JSON);
				ExpressionProperty queryParamValue = propertyValues
				        .getExpressionPropertyFor(param, PARAM_PATH_PROP);
				queryParams.add(Pair.of(queryParam, queryParamValue));
			}
		}
		List<Map<String, String>> fieldSet = propertyValues
		        .get(FIELD_TABLE_MAPPINGS_PROP);
		if (CollectionUtils.isNotEmpty(fieldSet)) {
			requestContentInfo = Lists.newArrayListWithExpectedSize(fieldSet
			        .size());
			for (Map<String, String> field : fieldSet) {
				String queryParam = JsonPath.read(field, FIELD_NAME_JSON);
				ExpressionProperty queryParamValue = propertyValues
				        .getExpressionPropertyFor(field, FIELD_PATH_PROP);
				requestContentInfo.add(Pair.of(queryParam, queryParamValue));
			}
		}
		List<Map<String, Object>> httpHeader = propertyValues
		        .get(HTTP_HEADER_PROP);
		int size = 3;
		if (CollectionUtils.isNotEmpty(httpHeader)) {
			size = httpHeader.size() + 4;
		}
		additionalHeaders = Lists.newArrayListWithExpectedSize(size);
		setDefaultHeaders();
		for (Map<String, Object> item : httpHeader) {
			String key = jsonPathUtil.nullableRead(HEADER_KEY_JSON, item);
			if (isAuthHeaderSet == true && key.equalsIgnoreCase(AUTHORIZATION)) {
				continue;
			} else {
				String value = jsonPathUtil.nullableRead(HEADER_VALUE_JSON,
				        item);
				additionalHeaders.add(Pair.of(key, value));
				if (key.equalsIgnoreCase(AUTHORIZATION)) {
					isAuthHeaderSet = true;
				}
			}
		}
		if (!isAuthHeaderSet) {
			throw new ExecutionException(AUTH_ERROR).withReason(
			        AUTH_ERROR_REASON).withResolution(AUTH_ERROR_RESOLUTION);
		}
	}

	private void setDefaultHeaders() {
		additionalHeaders.add(Pair.of(CONTENT_TYPE, APPLICATION_JSON));
		additionalHeaders.add(Pair.of(ACCEPT, APPLICATION_JSON));
		AccountBean bean = account.connect();
		if (bean != null) {
			isAuthHeaderSet = true;
			additionalHeaders.add(Pair.of(
			        AUTHORIZATION,
			        BASIC
			                + Base64.encodeBase64String((bean.getUsername()
			                        + COLON + bean.getPassword()).getBytes())));
		}

	}
	@Override
	protected void process(Document document, String inputViewName) {
		Map<String, Object> map = null;
		SnapDataException snapException = null;
		try {
			RequestBuilder rBuilder = new RequestBuilder().addDoc(document)
			        .addEndPointIP(lunexEnpointHostIP)
			        .addHeaders(additionalHeaders).addQueryParams(queryParams)
			        .addRequestBody(prepareJson(requestContentInfo, document))
			        .addResource(resourceType).addSnapTye(snapsType);
			String response = RequestProcessor.getInstance().execute(rBuilder);
			map = OBJECT_MAPPER.readValue(response, MAP_TYPE_REFERENCE);
			outputViews.write(documentUtility.newDocument(map));
		} catch (MalformedURLException ex) {
			log.error(ex.getMessage(), ex);
			map = new HashMap<String, Object>();
			map.put(ERROR_TAG, ex.getLocalizedMessage());
			map.put(MESSAGE_TAG, ex.getMessage());
			map.put(REASON_TAG, MALFORMEDURL_ERROR_REASON);
			map.put(RESOLUTION_TAG, MALFORMEDURL_ERROR_RESOLUTION);
			snapException = new SnapDataException(
			        documentUtility.newDocument(map), ex.getMessage())
			        .withReason(ILLEGAL_STATE_REASON).withResolution(
			                ILLEGAL_STATE_RESOLUTION);
			errorViews.write(snapException);
		} catch (IllegalStateException ex) {
			log.error(ex.getMessage(), ex);
			map = new HashMap<String, Object>();
			map.put(ERROR_TAG, CONTENT_STREAM_ERROR);
			map.put(MESSAGE_TAG, ex.getMessage());
			map.put(REASON_TAG, ILLEGAL_STATE_REASON);
			map.put(RESOLUTION_TAG, ILLEGAL_STATE_RESOLUTION);
			snapException = new SnapDataException(
			        documentUtility.newDocument(map), ex.getMessage())
			        .withReason(ILLEGAL_STATE_REASON).withResolution(
			                ILLEGAL_STATE_RESOLUTION);
			errorViews.write(snapException);
		} catch (JsonParseException ex) {
			log.error(ex.getMessage(), ex);
			map = new HashMap<String, Object>();
			map.put(ERROR_TAG, JSON_PARSING_ERROR);
			map.put(MESSAGE_TAG, ex.getMessage());
			map.put(REASON_TAG, JSON_PARSING_REASON);
			map.put(RESOLUTION_TAG, JSON_PARSING_RESOLUTION);
			snapException = new SnapDataException(
			        documentUtility.newDocument(map), ex.getLocalizedMessage())
			        .withReason(JSON_PARSING_REASON).withResolution(
			                JSON_PARSING_RESOLUTION);
			errorViews.write(snapException);
		} catch (IOException ex) {
			log.error(ex.getMessage(), ex);
			map = new HashMap<String, Object>();
			map.put(ERROR_TAG, IO_ERROR);
			map.put(MESSAGE_TAG, ex.getMessage());
			map.put(REASON_TAG, IO_ERROR_REASON);
			map.put(RESOLUTION_TAG, IO_ERROR_RESOLUTION);
			snapException = new SnapDataException(
			        documentUtility.newDocument(map), ex.getLocalizedMessage())
			        .withReason(IO_ERROR_REASON).withResolution(
			                IO_ERROR_RESOLUTION);
			errorViews.write(snapException);
		} catch (Exception ex) {
			log.error(ex.getMessage(), ex);
			map = new HashMap<String, Object>();
			map.put(ERROR_TAG, ERRORMSG);
			map.put(MESSAGE_TAG, ex.getMessage());
			map.put(REASON_TAG, ERROR_REASON);
			map.put(RESOLUTION_TAG, ERROR_RESOLUTION);
			snapException = new SnapDataException(
			        documentUtility.newDocument(map), ex.getLocalizedMessage())
			        .withReason(ERROR_REASON).withResolution(ERROR_RESOLUTION);
			errorViews.write(snapException);
		}
	}
	private String prepareJson(
	        List<Pair<String, ExpressionProperty>> requestContent,
	        Document document) {
		StringBuffer json = new StringBuffer();
		StringBuffer subJson = new StringBuffer();
		boolean isSubJsonRequired = false, isEmptyJson = true;
		if (requestContent != null) {
			if (resourceType.equals(CResource.NewOrder.toString())
			        || resourceType.equals(CResource.PreOrder.toString())) {
				subJson.append(ADDRESS).append(COLON).append(OPENTAG);
				isSubJsonRequired = true;
			}

			for (Pair<String, ExpressionProperty> paramPair : requestContent) {
				if (isSubJsonRequired
				        && ADDRESS_JSONOBJ.contains(paramPair.getKey())) {
					subJson.append(getJsonSlice(paramPair, document));
					isEmptyJson = false;
				} else {
					json.append(getJsonSlice(paramPair, document));
				}
			}
			if (!isEmptyJson) {
				subJson.append(CLOSETAG).append(COMMA);
			}
			return OPENTAG + subJson
			        + json.deleteCharAt(json.length() - 1).toString()
			        + CLOSETAG;
		}
		return "";
	}
	private String getJsonSlice(Pair<String, ExpressionProperty> paramPair,
	        Document document) {
		String key = paramPair.getKey();
		if (REQ_BODY_PARAM_INFO.get(key) == 1) {
			return QUOTE + key + QUOTE + COLON
			        + (String) paramPair.getRight().eval(document) + COMMA;
		}
		return QUOTE + key + QUOTE + COLON + QUOTE
		        + (String) paramPair.getRight().eval(document) + QUOTE + COMMA;
	}
	@Override
	public void cleanup() throws ExecutionException {
		// NO OP
	}

}