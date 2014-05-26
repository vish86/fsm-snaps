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
import com.snaplogic.snaps.uniteller.Constants.SnapType;
import com.snaplogic.snaps.uniteller.bean.AccountBean;
import com.uniteller.support.common.IUFSConfigMgr;
import com.uniteller.support.common.IUFSSecurityMgr;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.snaplogic.snaps.uniteller.Constants.*;
import static com.snaplogic.snaps.uniteller.Messages.COUNTER_DESCRIPTION;
import static com.snaplogic.snaps.uniteller.Messages.COUNTER_UNIT;
import static com.snaplogic.snaps.uniteller.Messages.DOCUMENT_COUNTER;
import static com.snaplogic.snaps.uniteller.Messages.ERRORMSG;
import static com.snaplogic.snaps.uniteller.Messages.ERROR_REASON;
import static com.snaplogic.snaps.uniteller.Messages.ERROR_RESOLUTION;
import static com.snaplogic.snaps.uniteller.Messages.RESOURCE_DESC;
import static com.snaplogic.snaps.uniteller.Messages.RESOURCE_LABEL;
import static com.snaplogic.snaps.uniteller.Messages.RESOURCE_PROP;

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
    private SnapType snapsType;
    private Counter counter;
    private String resourceType;
    @Inject
    private UniTellerBasicAuthAccount account;

    @Inject
    private URLEncoder urlEncoder;
    private static final Logger log = LoggerFactory.getLogger(BaseService.class);

    protected SnapType getSnapType() {
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
            Class classType = null;
            try {
                classType = Class.forName(getUFSReqClassType());
            } catch (ClassNotFoundException e) {
                log.error(e.getMessage(), e);
            }
            SchemaBuilder schemaBuilder = provider.getSchemaBuilder(viewName);
            for (String field : findSetters(classType)) {
                schemaBuilder.withChildSchema(provider.createSchema(
                        com.snaplogic.common.SnapType.STRING, field));
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
            log.debug(UFSConfigFilePath + " " + UFSSecurityFilePath);
            /* instantiating USFCreationClient */
            Class CustomUSFCreationClient = Class.forName(UFS_FOLIO_CREATION_CLIENT_PKG_URI);
            Constructor constructor = CustomUSFCreationClient.getDeclaredConstructor(new Class[] {
                    IUFSConfigMgr.class, IUFSSecurityMgr.class });
            Object USFCreationClientObj = constructor.newInstance(
                    CustomUFSConfigMgr.getInstance(UFSConfigFilePath),
                    CustomUFSSecurityMgr.getInstance(UFSSecurityFilePath));
            /* Preparing the request for USF */
            Map<String, Object> map;
            Object data;
            if (document != null && (data = document.get()) != null) {
                if (data instanceof Map) {
                    map = (Map<String, Object>) data;
                    Class UFSRequest = Class.forName(getUFSReqClassType());
                    Object UFSRequestObj = UFSRequest.newInstance();
                    Method[] declaredMethods = UFSRequest.getDeclaredMethods();
                    for (Method method : declaredMethods) {
                        if (isSetter(method)) {
                            method.invoke(UFSRequestObj, map.get(method.getName().substring(3)));
                        }
                    }
                    /* Invoking the request over USFCreationClient */
                    Method creationClientMethod = CustomUSFCreationClient.getMethod(resourceType,
                            UFSRequest);
                    Object UFSResponseObj = creationClientMethod.invoke(USFCreationClientObj,
                            UFSRequestObj);
                    outputViews.write(documentUtility
                            .newDocument(processResponseObj(UFSResponseObj)));
                    counter.inc();
                }
            }
        } catch (Exception ex) {
            writeToErrorView(ERRORMSG, ERROR_REASON, ERROR_RESOLUTION, ex);
        }
    }

    @Override
    public void cleanup() throws ExecutionException {
    }

    public Map<String, Object> processResponseObj(Object UFSResponseObj)
            throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
        Map<String, Object> map = new HashMap<String, Object>();
        /* Preparing the map to writing the values to output document */
        Class UFSResponse = UFSResponseObj.getClass();
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
    private String getUFSRespClassType() {
        return new StringBuilder().append(UNITELLER_PKG_PREFIX).append(resourceType)
                .append(UNITELLER_RESP_TAG).toString();
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
    static ArrayList<String> findSetters(Class<?> classType) {
        ArrayList<String> list = new ArrayList<String>();
        Method[] methods = classType.getDeclaredMethods();
        for (Method method : methods)
            if (isSetter(method))
                list.add(method.getName().substring(3));
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