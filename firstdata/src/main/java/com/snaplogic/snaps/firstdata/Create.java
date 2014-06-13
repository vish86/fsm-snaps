/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc. All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.firstdata;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.snaplogic.account.api.capabilities.Accounts;
import com.snaplogic.api.ConfigurationException;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.api.InputSchemaProvider;
import com.snaplogic.api.MetricsProvider;
import com.snaplogic.common.SnapType;
import com.snaplogic.common.metrics.Counter;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.api.MetricsBuilder;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.SimpleSnap;
import com.snaplogic.snap.api.SnapCategory;
import com.snaplogic.snap.api.SnapDataException;
import com.snaplogic.snap.api.capabilities.Category;
import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snap.api.capabilities.Inputs;
import com.snaplogic.snap.api.capabilities.Outputs;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snap.api.capabilities.ViewType;
import com.snaplogic.snap.schema.api.ObjectSchema;
import com.snaplogic.snap.schema.api.SchemaBuilder;
import com.snaplogic.snap.schema.api.SchemaProvider;
import com.snaplogic.snaps.firstdata.bean.AccountBean;
import com.snaplogic.snaps.firstdata.dw.rcservice.PayloadType;
import com.snaplogic.snaps.firstdata.dw.rcservice.RcPortType;
import com.snaplogic.snaps.firstdata.dw.rcservice.RcService;
import com.snaplogic.snaps.firstdata.dw.rcservice.ReqClientIDType;
import com.snaplogic.snaps.firstdata.dw.rcservice.RequestType;
import com.snaplogic.snaps.firstdata.dw.rcservice.ResponseType;
import com.snaplogic.snaps.firstdata.dw.rcservice.StatusType;
import com.snaplogic.snaps.firstdata.dw.rcservice.TransactionResponseType;
import com.snaplogic.snaps.firstdata.dw.rcservice.TransactionType;
import com.snaplogic.snaps.firstdata.gmf.proxy.CommonGrp;
import com.snaplogic.snaps.firstdata.gmf.proxy.GMFMessageVariants;

import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import static com.snaplogic.snaps.firstdata.Constants.*;
import static com.snaplogic.snaps.firstdata.Messages.*;

/**
 * Performs create operation in FirstData making use of FirstData SOAP API.
 * 
 * @author svatada
 **/
@General(title = CREATE_LABEL, purpose = SNAP_DESC)
@Inputs(min = 1, max = 1, accepts = { ViewType.DOCUMENT })
@Outputs(min = 1, max = 1, offers = { ViewType.DOCUMENT })
@Version(snap = 1)
@Category(snap = SnapCategory.TRANSFORM)
@Accounts(provides = FirstDataCustomAccount.class, optional = false)
public class Create extends SimpleSnap implements MetricsProvider, InputSchemaProvider {
    @Inject
    private FirstDataCustomAccount account;
    private String resourceType = null;
    private Counter counter;
    private static final String INT = "int";
    private static final Logger log = LoggerFactory.getLogger(Create.class);

    @Override
    public void configure(PropertyValues propertyValues) throws ConfigurationException {
        resourceType = propertyValues.get(RESOURCE_PROP);
    }

    @Override
    public void defineProperties(PropertyBuilder propertyBuilder) {
        propertyBuilder.describe(RESOURCE_PROP, RESOURCE_LABEL, RESOURCE_DESC)
                .withAllowedValues(RESOUCE_LIST).required().add();
    }

    @Override
    public void defineMetrics(final MetricsBuilder metricsBuilder) {
        counter = metricsBuilder.describe(DOCUMENT_COUNTER, COUNTER_DESCRIPTION)
                .measuredIn(COUNTER_UNIT).counter();
    }

    @Override
    public void defineInputSchema(SchemaProvider provider) {
        Class<?> classType = null;
        log.debug("define schema");
        try {
            classType = Class.forName(getGMFReqClassType());
        } catch (ClassNotFoundException e) {
            log.error(e.getMessage(), e);
            throw new ExecutionException(e.getMessage());
        }
        log.debug(classType.getName());
        for (String viewName : provider.getRegisteredViewNames()) {
            log.debug(viewName);
            try {
                SchemaBuilder schemaBuilder = provider.getSchemaBuilder(viewName);
                schemaBuilder.withChildSchema(getSchema(provider, classType));
                schemaBuilder.build();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        }
    }

    @Override
    protected void process(Document document, String viewname) {
        Object requestObj;
        Object data;
        if (document != null && (data = document.get()) != null) {
            /*
             * Process the input data if and only if it is of Map
             */
            if (data instanceof Map) {
                String claszPath = getGMFReqClassType();
                Object map = Map.class.cast(data).get(getGMFReqClassName());
                requestObj = getObject(claszPath, Map.class.cast(map));
                String xmlData;
                try {
                    /* Using Reflection and JAXB to prepare SOAP request XML */
                    Class<?> gmfmv = Class.forName(GMF_MESSAGE_VARIANTS);
                    Object gmfmvObj = gmfmv.newInstance();
                    Method gmfMethod = gmfmv.getMethod(String.format(SETTER, resourceType),
                            getClassType(claszPath));
                    gmfMethod.invoke(gmfmvObj, requestObj);
                    /* converting simple java objects into XML format using JAXB */
                    xmlData = getGMFXMLRequestData((GMFMessageVariants) gmfmvObj);
                    log.debug(xmlData);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    throw new SnapDataException(e, e.getMessage());
                }
                AccountBean bean = account.connect();
                try {
                    /*
                     * Create the instance of the RequestType that is a class generated from the
                     * Rapid connect Transaction Service WSDL file [rc.wsdl]
                     */
                    RequestType requestType = new RequestType();
                    /* Set client timeout value of request object. */
                    requestType.setClientTimeout(new BigInteger(String.valueOf(bean.getTimeOut())));
                    /*
                     * Create the instance of the ReqClientIDType that is a class generated from the
                     * Rapid connect Transaction Service WSDL file [rc.wsdl]
                     */
                    ReqClientIDType reqClientIDType = new ReqClientIDType();
                    reqClientIDType.setApp(bean.getAppID());
                    reqClientIDType.setAuth(bean.getAuthString());
                    String clientRef = getClientRef(requestObj, claszPath);
                    reqClientIDType.setClientRef(clientRef);
                    reqClientIDType.setDID(bean.getDatawireId());
                    /* Assign ReqClientID value to the requesttype object */
                    requestType.setReqClientID(reqClientIDType);
                    /*
                     * Create the instance of the TransactionType that is a class generated from the
                     * Rapid connect Transaction Service WSDL file [rc.wsdl]
                     */
                    TransactionType transactionType = new TransactionType();
                    /*
                     * Create the instance of the PayloadType that is a class generated from the
                     * Rapid connect Transaction Service WSDL file [rc.wsdl]
                     */
                    PayloadType payloadType = new PayloadType();
                    payloadType.setEncoding(CDATA);
                    payloadType.setValue(xmlData); // Set payload - actual xml request
                    transactionType.setPayload(payloadType);
                    transactionType.setServiceID(String.valueOf(bean.getServiceID()));
                    /* Set transaction type */
                    requestType.setTransaction(transactionType);
                    /* Set version number of the request */
                    requestType.setVersion(VERSION);
                    /* The response to be returned. */
                    String gmfResponse = null;
                    /*
                     * Create the instance of the RcService that is a class generated from the Rapid
                     * connect Transaction Service WSDL file [rc.wsdl]
                     */
                    URL wsdlUrl = new URL(bean.getServiceWSDLURL());
                    RcService.setWsdlURL(wsdlUrl);
                    RcService rcService = new RcService();
                    /*
                     * Create the instance of the RcPortType that is a class generated from the
                     * Rapid connect Transaction Service WSDL file [rc.wsdl]
                     */
                    RcPortType port = rcService.getRcServicePort();
                    /* The URL that will receive the XML request data. */
                    /* Bind the URL using Binding Provider */
                    ((BindingProvider) port).getRequestContext().put(
                            BindingProvider.ENDPOINT_ADDRESS_PROPERTY, bean.getServiceURL());
                    /* Perform actual send operation for the request. */
                    ResponseType responseType = port.rcTransaction(requestType);
                    /* Parse the response received from the URL */
                    StatusType statusType;
                    if (responseType != null && (statusType = responseType.getStatus()) != null) {
                        String statuCode = statusType.getStatusCode();
                        if (statuCode != null && statuCode.equals(STATUS_OK)) {
                            TransactionResponseType trType = responseType.getTransactionResponse();
                            PayloadType pType;
                            if (trType != null && (pType = trType.getPayload()) != null) {
                                String encoding = pType.getEncoding();
                                if (encoding != null && encoding.equals(CDATA)) {
                                    /*
                                     * Extract pay load - the response from data wire for cdata
                                     * encoding.
                                     */
                                    gmfResponse = pType.getValue();
                                } else if (encoding.equalsIgnoreCase(XML_ESCAPE)) {
                                    /*
                                     * Extract pay load - the response from data wire for xml_escape
                                     * encoding.
                                     */
                                    gmfResponse = pType.getValue().replaceAll(GT, GT_SYM)
                                            .replaceAll(LT, LT_SYM).replaceAll(AMP, AMP_SYM);
                                }
                                outputViews.write(documentUtility
                                        .newDocument(getJsonFromXML(gmfResponse)));
                                counter.inc();
                            } else {
                                writeToErrorView(NULL_TRANSACTION_RESPONSE, responseType
                                        .getStatus().getStatusCode(), VALIDATE_INPUT_DATA,
                                        responseType.getStatus().getValue());
                            }
                        } else {
                            writeToErrorView(INVALID_RESPONSE, responseType.getStatus()
                                    .getStatusCode(), VALIDATE_INPUT_DATA, responseType.getStatus()
                                    .getValue());
                        }
                    } else {
                        writeToErrorView(EMPTY_RESPONSE, IMPROPER_INPUT, VALIDATE_INPUT_DATA,
                                NULL_OBJECT);
                    }
                } catch (Exception ex) {
                    writeToErrorView(ERRORMSG, ex.getMessage(), ERROR_RESOLUTION, ex);
                }
            } else {
                writeToErrorView(NO_DATA_ERRMSG, NO_DATA_WARNING, NO_DATA_REASON,
                        NO_DATA_RESOLUTION);
            }
        } else {
            writeToErrorView(NO_DATA_ERRMSG, NO_DATA_WARNING, NO_DATA_REASON, NO_DATA_RESOLUTION);
        }
    }

    private ObjectSchema getSchema(SchemaProvider provider, Class<?> classType) {
        ArrayList<Method> setterMethods = findSetters(classType);
        ObjectSchema schema = null;
        if (!setterMethods.isEmpty()) {
            schema = provider.createSchema(SnapType.STRING, classType.getSimpleName());
        }
        String methodName;
        for (Method method : setterMethods) {
            try {
                String paramType = method.getParameterTypes()[0].getName();
                if (paramType.startsWith(FD_PROXY_PKG_PREFIX) && !paramType.endsWith(TYPE)) {
                    Class<?> subClass = null;
                    try {
                        subClass = Class.forName(paramType);
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage(), e);
                        throw new ExecutionException(e.getMessage());
                    }
                    ObjectSchema subSchema = getSchema(provider, subClass);
                    if (subSchema != null) {
                        schema.addChild(subSchema);
                    }
                } else {
                    methodName = getFieldName(method.getName());
                    schema.addChild(provider.createSchema(getDataTypes(paramType), methodName));
                }
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return null;
            }
        }
        return schema;
    }

    /*
     * Returns absolute class type for UFS request object
     */
    private String getGMFReqClassType() {
        return new StringBuilder().append(FD_PROXY_PKG_PREFIX).append(resourceType)
                .append(FD_REQ_TAG).toString();
    }

    /*
     * Returns absolute class name for UFS request object
     */
    private String getGMFReqClassName() {
        return new StringBuilder().append(resourceType).append(FD_REQ_TAG).toString();
    }

    /* Writes the exception records to error view */
    private void writeToErrorView(final String errMsg, final String errReason,
            final String errResoulution, Exception ex) {
        log.error(ex.getMessage(), ex);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DOCNUM_TAG, counter.getStats());
        map.put(ERROR_TAG, errMsg);
        map.put(MESSAGE_TAG, ex.getLocalizedMessage());
        map.put(REASON_TAG, errReason);
        map.put(RESOLUTION_TAG, errResoulution);
        SnapDataException snapException = new SnapDataException(documentUtility.newDocument(map),
                ex.getMessage()).withReason(errReason).withResolution(errResoulution);
        errorViews.write(snapException);
    }

    /* Writes the error records to error view */
    private void writeToErrorView(final String errMsg, final String errReason,
            final String errResoulution, final String errResponse) {
        Map<String, Object> map = new HashMap<String, Object>();
        map.put(DOCNUM_TAG, counter.getStats());
        map.put(ERROR_TAG, errMsg);
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
            String methodName = method.getName();
            if (methodName.matches(GET_REGEX) && !method.getReturnType().equals(void.class))
                return true;
            if (methodName.matches(IS_REGEX) && method.getReturnType().equals(boolean.class))
                return true;
        }
        return false;
    }

    /**
     * @param method
     * @return SnapType
     */
    private SnapType getDataTypes(String type) {
        if (StringUtils.contains(type, INT)) {
            return SnapType.INTEGER;
        } else if (StringUtils.containsIgnoreCase(type, Float.class.getSimpleName())
                || StringUtils.containsIgnoreCase(type, Long.class.getSimpleName())
                || StringUtils.containsIgnoreCase(type, Double.class.getSimpleName())) {
            return SnapType.NUMBER;
        } else if (StringUtils.containsIgnoreCase(type, Calendar.class.getName())) {
            return SnapType.DATETIME;
        } else
            return SnapType.STRING;
    }

    private Map<String, Object> getJsonFromXML(String xml) {
        Map<String, Object> json2Map = new HashMap<String, Object>();
        try {
            JSON objJson = new XMLSerializer().read(xml);
            ObjectMapper objectMapper = new ObjectMapper();
            json2Map = objectMapper.readValue(objJson.toString(),
                    new TypeReference<HashMap<String, Object>>() {
                    });
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SnapDataException(e, e.getMessage());
        }
        return json2Map;
    }

    private Class<?> getClassType(String path) {
        Class<?> clasz = null;
        try {
            clasz = Class.forName(path);
            return clasz;
        } catch (ClassNotFoundException ex) {
            log.error(ex.getMessage(), ex);
            throw new SnapDataException(ex, ex.getMessage());
        }
    }

    private Object getInstance(Class<?> classType) {
        Object obj;
        try {
            obj = classType.newInstance();
            return obj;
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            throw new SnapDataException(e, e.getMessage());
        }
    }

    private Object getObject(String path, Map<String, Object> map) {
        boolean isMethodInvokedAtOnce = false;
        Class<?> classType = getClassType(path);
        Object subClazInstance = getInstance(classType);
        String inputFieldName;
        String returnClazType;
        String returnClazName;
        for (Method method : findSetters(classType)) {
            inputFieldName = getFieldName(method.getName());
            if (!map.containsKey(inputFieldName)) {
                continue;
            }
            returnClazType = method.getParameterTypes()[0].getName();
            try {
                Object paramObj = null;
                if (returnClazType.startsWith(FD_PROXY_PKG_PREFIX)
                        && !returnClazType.endsWith(TYPE)) {
                    returnClazName = method.getParameterTypes()[0].getSimpleName();
                    Object subMap = map.get(returnClazName);
                    paramObj = getObject(returnClazType, Map.class.cast(subMap));
                } else if (returnClazType.endsWith(TYPE)) {
                    paramObj = getTypesInstance(returnClazType, (String) map.get(inputFieldName));
                } else {
                    paramObj = map.get(inputFieldName);
                }
                if (paramObj != null) {
                    method.invoke(subClazInstance, paramObj);
                    isMethodInvokedAtOnce = true;
                }
            } catch (SecurityException e) {
                log.error(e.getMessage(), e);
                writeToErrorView(SECURITY_EXE, e.getMessage(), SECURITY_EXE_RES, e);
            } catch (IllegalArgumentException e) {
                log.error(e.getMessage(), e);
                writeToErrorView(String.format(ILLEGAL_ARGS_EXE, method.getName()), e.getMessage(),
                        ERROR_RESOLUTION, e);
            } catch (InvocationTargetException e) {
                log.error(e.getMessage(), e);
                writeToErrorView(e.getTargetException().getMessage(), e.getMessage(),
                        ERROR_RESOLUTION, e);
            } catch (IllegalAccessException e) {
                log.error(e.getMessage(), e);
                writeToErrorView(String.format(ILLIGAL_ACCESS_EXE, method.getName()),
                        e.getMessage(), ILLIGAL_ACCESS_EXE_RES, e);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                writeToErrorView(ERRORMSG, e.getMessage(), ERROR_RESOLUTION, e);
            }
        }
        return (isMethodInvokedAtOnce) ? subClazInstance : null;
    }

    /*
     * The below method will transform the transaction request object to an XML string in UTF-8
     * encoding. It will convert gmfmv object into serialized XML data which will be sent to Data
     * wire.
     */
    private String getGMFXMLRequestData(
            com.snaplogic.snaps.firstdata.gmf.proxy.GMFMessageVariants gmfmv) {
        StringWriter stringWriter = new StringWriter();
        String returnValue = "";
        try {
            JAXBContext context = null;
            Marshaller marshaller = null;
            context = JAXBContext.newInstance(GMFMessageVariants.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(gmfmv, stringWriter);
            returnValue = stringWriter.toString();
        } catch (JAXBException jaxe) {
            log.error(jaxe.getMessage(), jaxe);
            throw new SnapDataException(jaxe, jaxe.getMessage());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SnapDataException(e, e.getMessage());
        }
        return returnValue;
    }

    private Object getTypesInstance(String classType, String value) {
        Method method = null;
        try {
            Class<?> types = getClassType(classType);
            method = types.getMethod(TYPES_METHOD, String.class);
            Object typesObj = method.invoke(null, value);
            return typesObj;
        } catch (NoSuchMethodException e) {
            log.error(e.getMessage(), e);
            writeToErrorView(NOSUCH_METHOD_EXE, e.getMessage(), ERROR_RESOLUTION, e);
        } catch (SecurityException e) {
            log.error(e.getMessage(), e);
            writeToErrorView(SECURITY_EXE, e.getMessage(), SECURITY_EXE_RES, e);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
            writeToErrorView(String.format(ILLEGAL_ARGS_EXE, method.getName()), e.getMessage(),
                    ERROR_RESOLUTION, e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
            writeToErrorView(e.getTargetException().getMessage(), e.getMessage(), ERROR_RESOLUTION,
                    e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
            writeToErrorView(String.format(ILLIGAL_ACCESS_EXE, method.getName()), e.getMessage(),
                    ILLIGAL_ACCESS_EXE_RES, e);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            writeToErrorView(ERRORMSG, e.getMessage(), ERROR_RESOLUTION, e);
        }
        return null;
    }

    private String getFieldName(String methodName) {
        return methodName.substring(3);
    }

    private String getClientRef(Object obj, String Clazpath) {
        String clientRef = null;
        try {
            Class<?> paymentType = getClassType(Clazpath);
            Method getCommonGrp = paymentType.getMethod(GET_COMMON_GRP);
            CommonGrp cGrp = (CommonGrp) getCommonGrp.invoke(obj);
            clientRef = cGrp.getSTAN() + DELIMITER + cGrp.getTPPID();
            clientRef = CLIENT_REF_PREFIX + clientRef;
        } catch (NoSuchMethodException | SecurityException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            log.error(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            log.error(e.getMessage(), e);
        }
        return clientRef;
    }
}
