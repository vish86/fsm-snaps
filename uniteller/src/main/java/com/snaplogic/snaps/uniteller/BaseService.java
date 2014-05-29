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
package com.snaplogic.snaps.uniteller;

import com.google.inject.Inject;
import com.snaplogic.account.api.capabilities.Accounts;
import com.snaplogic.api.ConfigurationException;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.api.InputSchemaProvider;
import com.snaplogic.api.MetricsProvider;
import com.snaplogic.common.metrics.Counter;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.common.utilities.URLEncoder;
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.api.MetricsBuilder;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.SimpleSnap;
import com.snaplogic.snap.api.SnapCategory;
import com.snaplogic.snap.api.SnapDataException;
import com.snaplogic.snap.api.capabilities.Category;
import com.snaplogic.snap.api.capabilities.Inputs;
import com.snaplogic.snap.api.capabilities.Outputs;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snap.api.capabilities.ViewType;
import com.snaplogic.snap.schema.api.SchemaBuilder;
import com.snaplogic.snap.schema.api.SchemaProvider;
import com.snaplogic.snaps.uniteller.Constants.SnapCatogery;
import com.snaplogic.snaps.uniteller.bean.AccountBean;
import com.uniteller.support.common.IUFSConfigMgr;
import com.uniteller.support.common.IUFSSecurityMgr;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.snaplogic.snaps.uniteller.Constants.*;
import static com.snaplogic.snaps.uniteller.Messages.*;
import static com.snaplogic.snaps.uniteller.util.Utilities.getDataTypes;

/**
 * Abstract base class for UniTeller snap pack which contains common snap properties,
 * authentication.
 *
 * @author svatada
 */
@Inputs(min = 1, max = 1, accepts = { ViewType.DOCUMENT })
@Outputs(min = 1, max = 1, offers = { ViewType.DOCUMENT })
@Version(snap = 1)
@Category(snap = SnapCategory.TRANSFORM)
@Accounts(provides = { UniTellerBasicAuthAccount.class }, optional = false)
public abstract class BaseService extends SimpleSnap implements MetricsProvider,
        InputSchemaProvider {
    private SnapCatogery snapsType;
    private Counter counter;
    private String resourceType;
    @Inject
    private UniTellerBasicAuthAccount account;
    @Inject
    private URLEncoder urlEncoder;
    private static final Logger log = LoggerFactory.getLogger(BaseService.class);

    protected SnapCatogery getSnapType() {
        return snapsType;
    }

    @Override
    public void defineProperties(final PropertyBuilder propertyBuilder) {
        propertyBuilder.describe(RESOURCE_PROP, RESOURCE_LABEL, RESOURCE_DESC)
                .withAllowedValues(RESOUCE_LIST.get(getSnapType().toString())).add();
    }

    @Override
    public void defineInputSchema(SchemaProvider provider) {
        for (String viewName : provider.getRegisteredViewNames()) {
            Class<?> classType = null;
            try {
                classType = Class.forName(getUFSReqClassType());
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
            SchemaBuilder schemaBuilder = provider.getSchemaBuilder(viewName);
            for (Method method : findSetters(classType)) {
                schemaBuilder.withChildSchema(provider.createSchema(getDataTypes(method), method
                        .getName().substring(3)));
            }
            schemaBuilder.build();
        }
    }

    @Override
    public void defineMetrics(final MetricsBuilder metricsBuilder) {
        counter = metricsBuilder.describe(DOCUMENT_COUNTER, COUNTER_DESCRIPTION)
                .measuredIn(COUNTER_UNIT).counter();
    }

    @Override
    public void configure(final PropertyValues propertyValues) throws ConfigurationException {
        resourceType = propertyValues.get(RESOURCE_PROP).toString();
    }

    @Override
    protected void process(Document document, String inputViewName) {
        try {
            AccountBean bean = account.connect();
            String UFSConfigFilePath = urlEncoder.validateAndEncodeURI(bean.getConfigFilePath(),
                    PATTERN, null).toString();
            String UFSSecurityFilePath = urlEncoder.validateAndEncodeURI(
                    bean.getSecurityPermFilePath(), PATTERN, null).toString();
            /* instantiating USFCreationClient */
            Class<?> CustomUSFCreationClient = Class.forName(UFS_FOLIO_CREATION_CLIENT_PKG_URI);
            Constructor<?> constructor = CustomUSFCreationClient
                    .getDeclaredConstructor(new Class[] { IUFSConfigMgr.class,
                            IUFSSecurityMgr.class });
            Object USFCreationClientObj = constructor.newInstance(
                    CustomUFSConfigMgr.getInstance(UFSConfigFilePath),
                    CustomUFSSecurityMgr.getInstance(UFSSecurityFilePath));
            /* Preparing the request for USF */
            Object data;
            if (document != null && (data = document.get()) != null) {
                if (data instanceof Map) {
                    Map<String, Object> map = (Map<String, Object>) data;
                    Class<?> UFSRequest = Class.forName(getUFSReqClassType());
                    Object UFSRequestObj = UFSRequest.newInstance();
                    Object inputFieldValue = null;
                    for (Method method : UFSRequest.getDeclaredMethods()) {
                        if (isSetter(method)
                                && (inputFieldValue = map.get(method.getName().substring(3))) != null) {
                            try {
                                if (inputFieldValue instanceof String) {
                                    method.invoke(UFSRequest.cast(UFSRequestObj),
                                            String.valueOf(inputFieldValue));
                                } else if (inputFieldValue instanceof BigDecimal) {
                                    method.invoke(UFSRequest.cast(UFSRequestObj),
                                            ((BigDecimal) inputFieldValue).intValue());
                                } else if (inputFieldValue instanceof BigInteger) {
                                    method.invoke(UFSRequest.cast(UFSRequestObj),
                                            ((BigInteger) inputFieldValue).intValue());
                                } else if (inputFieldValue instanceof Date) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.setTime(((Date) inputFieldValue));
                                    method.invoke(UFSRequest.cast(UFSRequestObj), cal);
                                }
                            } catch (IllegalArgumentException iae) {
                                writeToErrorView(ILLEGAL_ARGS_EXE, iae.getMessage(),
                                        ERROR_RESOLUTION, iae);
                            } catch (InvocationTargetException ite) {
                                writeToErrorView(ite.getTargetException().getMessage(),
                                        ite.getMessage(), ERROR_RESOLUTION, ite);
                            } catch (Exception ex) {
                                writeToErrorView(ERRORMSG, ex.getMessage(), ERROR_RESOLUTION, ex);
                            }
                        }
                    }
                    /* Invoking the request over USFCreationClient */
                    Object UFSResponseObj = null;
                    try {
                        Method creationClientMethod = CustomUSFCreationClient.getMethod(
                                getCamelCaseForMethod(resourceType), UFSRequest);
                        UFSResponseObj = creationClientMethod.invoke(
                                CustomUSFCreationClient.cast(USFCreationClientObj),
                                UFSRequest.cast(UFSRequestObj));
                    } catch (IllegalArgumentException iae) {
                        writeToErrorView(ILLEGAL_ARGS_EXE, iae.getMessage(), ERROR_RESOLUTION, iae);
                    } catch (InvocationTargetException ite) {
                        writeToErrorView(ite.getTargetException().getMessage(), ite.getMessage(),
                                ERROR_RESOLUTION, ite);
                    } catch (Exception ex) {
                        writeToErrorView(ERRORMSG, ex.getMessage(), ERROR_RESOLUTION, ex);
                    }
                    if (null != UFSResponseObj) {
                        outputViews.write(documentUtility
                                .newDocument(processResponseObj(UFSResponseObj)));
                        counter.inc();
                    }
                } else {
                    writeToErrorView(000, NO_DATA_WARNING, NO_DATA_REASON, NO_DATA_RESOLUTION);
                }
            }
        } catch (Exception ex) {
            writeToErrorView(ERRORMSG, ERROR_REASON, ERROR_RESOLUTION, ex);
        }
    }

    @Override
    public void cleanup() throws ExecutionException {
    }

    /**
     * Process all the nested UFS response objects
     *
     * @param UFSResponseObj
     * @return Map
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     */
    public Map<String, Object> processResponseObj(Object UFSResponseObj)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, Object> map = new HashMap<String, Object>();
        /* Preparing the map to writing the values to output document */
        Class<? extends Object> UFSResponse = UFSResponseObj.getClass();
        for (Method method : findGetters(UFSResponse)) {
            if (method.getReturnType().isPrimitive()
                    || method.getReturnType().isAssignableFrom(String.class)
                    || method.getReturnType().isAssignableFrom(Calendar.class)) {
                map.put(method.getName().substring(3), method.invoke(UFSResponseObj, null));
            } else {
                map.put(method.getName().substring(3),
                        processResponseObj(method.invoke(UFSResponseObj, null)));
            }
        }
        return map;
    }

    /*
     * Returns absolute class type for UFS request object
     */
    private String getUFSReqClassType() {
        if (resourceType.equals(CHANGE_PASSWORD)) {
            return UNITELLER_CHANGE_PASS_PKG;
        }
        return new StringBuilder().append(UNITELLER_PKG_PREFIX).append(resourceType)
                .append(UNITELLER_REQ_TAG).toString();
    }

    /*
     * Returns absolute class type for UFS response object
     */
    @SuppressWarnings("unused")
    private String getUFSRespClassType() {
        return new StringBuilder().append(UNITELLER_PKG_PREFIX).append(resourceType)
                .append(UNITELLER_RESP_TAG).toString();
    }

    /*
     * Returns absolute class type for UFS response object
     */
    private String getCamelCaseForMethod(String resourceType) {
        return new StringBuilder().append(StringUtils.substring(resourceType, 0, 1).toLowerCase())
                .append(StringUtils.substring(resourceType, 1)).toString();
    }

    /* Writes the exception records to error view */
    private void writeToErrorView(final String errMsg, final String errReason,
            final String errResoulution, Exception ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_TAG, errMsg);
        map.put(MESSAGE_TAG, ex.getMessage());
        map.put(REASON_TAG, errReason);
        map.put(RESOLUTION_TAG, errResoulution);
        SnapDataException snapException = new SnapDataException(documentUtility.newDocument(map),
                ex.getMessage()).withReason(errReason).withResolution(errResoulution);
        errorViews.write(snapException);
    }

    /* Writes the error records to error view */
    private void writeToErrorView(final int httpErrCode, final String errReason,
            final String errResoulution, final String errResponse) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(ERROR_TAG, httpErrCode);
        map.put(MESSAGE_TAG, errResponse);
        map.put(REASON_TAG, errReason);
        map.put(RESOLUTION_TAG, errResoulution);
        SnapDataException snapException = new SnapDataException(documentUtility.newDocument(map),
                errResponse).withReason(errReason).withResolution(errResoulution);
        errorViews.write(snapException);
    }

    /*
     * finds the declared setter methods in the given classtype
     */
    static ArrayList<Method> findSetters(Class<?> classType) {
        ArrayList<Method> list = new ArrayList<Method>();
        Method[] methods = classType.getDeclaredMethods();
        for (Method method : methods) {
            if (isSetter(method)) {
                list.add(method);
            }
        }
        return list;
    }

    /*
     * finds the declared getter methods in the given classtype
     */
    static ArrayList<Method> findGetters(Class<?> c) {
        ArrayList<Method> list = new ArrayList<Method>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods)
            if (isGetter(method))
                list.add(method);
        return list;
    }

    /*
     * finds whether the given method setter or not
     */
    static boolean isSetter(Method method) {
        return Modifier.isPublic(method.getModifiers())
                && method.getReturnType().equals(void.class)
                && method.getParameterTypes().length == 1 && method.getName().matches(REGEX_SET);
    }

    /*
     * finds whether the given method getter or not
     */
    static boolean isGetter(Method method) {
        if (Modifier.isPublic(method.getModifiers()) && method.getParameterTypes().length == 0) {
            if (method.getName().matches(GET_REGEX) && !method.getReturnType().equals(void.class))
                return true;
            if (method.getName().matches(IS_REGEX) && method.getReturnType().equals(boolean.class))
                return true;
        }
        return false;
    }
}