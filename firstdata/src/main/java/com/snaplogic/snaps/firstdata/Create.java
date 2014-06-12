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

import static com.snaplogic.snaps.firstdata.Constants.DOCNUM_TAG;
import static com.snaplogic.snaps.firstdata.Constants.ERROR_TAG;
import static com.snaplogic.snaps.firstdata.Constants.FD_PROXY_PKG_PREFIX;
import static com.snaplogic.snaps.firstdata.Constants.FD_REQ_TAG;
import static com.snaplogic.snaps.firstdata.Constants.GET_REGEX;
import static com.snaplogic.snaps.firstdata.Constants.IS_REGEX;
import static com.snaplogic.snaps.firstdata.Constants.MESSAGE_TAG;
import static com.snaplogic.snaps.firstdata.Constants.REASON_TAG;
import static com.snaplogic.snaps.firstdata.Constants.REGEX_SET;
import static com.snaplogic.snaps.firstdata.Constants.RESOLUTION_TAG;
import static com.snaplogic.snaps.firstdata.Constants.RESOUCE_LIST;
import static com.snaplogic.snaps.firstdata.Messages.COUNTER_DESCRIPTION;
import static com.snaplogic.snaps.firstdata.Messages.COUNTER_UNIT;
import static com.snaplogic.snaps.firstdata.Messages.CREATE_LABEL;
import static com.snaplogic.snaps.firstdata.Messages.DOCUMENT_COUNTER;
import static com.snaplogic.snaps.firstdata.Messages.ERRORMSG;
import static com.snaplogic.snaps.firstdata.Messages.ERROR_RESOLUTION;
import static com.snaplogic.snaps.firstdata.Messages.RESOURCE_DESC;
import static com.snaplogic.snaps.firstdata.Messages.RESOURCE_LABEL;
import static com.snaplogic.snaps.firstdata.Messages.RESOURCE_PROP;
import static com.snaplogic.snaps.firstdata.Messages.SNAP_DESC;

import com.snaplogic.api.ExecutionException;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.inject.Inject;
import com.snaplogic.account.api.capabilities.Accounts;
import com.snaplogic.api.ConfigurationException;
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
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.ws.BindingProvider;
import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
    private static final String UNSUPPORTED_OPT_RESOLUTION = "Verify your FirstData library version";
    private static final String UNSUPPORTED_OPT = "Selected Payment type was not found";
    private static final String INVALID_RESPONSE = "Invalid response";
    private static final String IMPROPER_INPUT = "improper input";
    private static final String EMPTY_RESPONSE = "Caught empty response";
    private static final String VALIDATE_INPUT_DATA = "Validate your input data";
    private static final String AMP_SYM = "&";
    private static final String AMP = "&amp;";
    private static final String LT_SYM = "<";
    private static final String LT = "&lt;";
    private static final String GT_SYM = ">";
    private static final String GT = "&gt;";
    private static final String XML_ESCAPE = "xml_escape";
    private static final String STATUS_OK = "OK";
    private static final String VERSION = "3";
    private static final String CDATA = "cdata";
    private static final String TIME_OUT = "30";
    private static final String TYPE = "Type";
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
        String clientRef = null;
        String gmfrequest = null;
        log.debug(document.getMetadata().toString());
        Class<?> classType = null;
        Object paymentOpt = null;
        try {
            classType = Class.forName(getGMFReqClassType());
            paymentOpt = classType.newInstance();
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException ex) {
            log.error(ex.getMessage(), ex);
            writeToErrorView(UNSUPPORTED_OPT, ex.getMessage(), UNSUPPORTED_OPT_RESOLUTION, ex);
        }
        Object data;
        if (document != null && (data = document.get()) != null) {
            if (data instanceof Map) {
                try {
                    Map<String, Object> map = (Map<String, Object>) data;
                    for (Method method : findGetters(classType)) {
                        String paramType = method.getReturnType().getName();
                        if (paramType.startsWith(FD_PROXY_PKG_PREFIX) && !paramType.endsWith(TYPE)) {
                            Class<?> subClass = null;
                            try {
                                subClass = Class.forName(paramType);
                            } catch (ClassNotFoundException ex) {
                                log.error(ex.getMessage(), ex);
                                writeToErrorView(UNSUPPORTED_OPT, ex.getMessage(),
                                        UNSUPPORTED_OPT_RESOLUTION, ex);
                            }
                            try {
                                method.invoke(
                                        paymentOpt,
                                        getObject(subClass, (Map<String, Object>) map.get(subClass
                                                .getSimpleName())));
                            } catch (IllegalAccessException | IllegalArgumentException
                                    | InvocationTargetException e) {
                                log.error(e.getMessage(), e);
                            }
                        } else if (paramType.endsWith(TYPE)) {
                            try {
                                Object typesObj = getTypesInstance(paramType,
                                        (String) map.get(method.getName().substring(3)));
                                method.invoke(paymentOpt, typesObj);
                            } catch (SecurityException e) {
                                // TODO Auto-generated catch block
                                log.error(e.getMessage(), e);
                            } catch (IllegalArgumentException e) {
                                // TODO Auto-generated catch block
                                log.error(e.getMessage(), e);
                            } catch (InvocationTargetException e) {
                                // TODO Auto-generated catch block
                                log.error(e.getMessage(), e);
                            } catch (IllegalAccessException e) {
                                // TODO Auto-generated catch block
                                log.error(e.getMessage(), e);
                            }
                        } else {
                            method.invoke(paymentOpt, map.get(method.getName().substring(3)));
                        }
                    }
                } catch (IllegalArgumentException e) {
                    log.error(e.getMessage(), e);
                } catch (InvocationTargetException e) {
                    log.error(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
        try {
            /* This class is generated from UMF_Schema_V1.1.14.xsd. */
            Class<?> gmfmv = Class
                    .forName("com.snaplogic.snaps.firstdata.gmf.proxy.GMFMessageVariants");
            Object gmfmvObj = gmfmv.newInstance();
            Method gmfMethod = gmfmv.getMethod("set" + resourceType, classType);
            gmfMethod.invoke(gmfmvObj, paymentOpt);
            String xmlData = getGMFXMLRequestData((com.snaplogic.snaps.firstdata.gmf.proxy.GMFMessageVariants) gmfmvObj);
            log.debug(xmlData);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new SnapDataException(e.getMessage());
        }
        AccountBean bean = account.connect();
        try {
            /*
             * Create the instance of the RequestType that is a class generated from the Rapid
             * connect Transaction Service WSDL file [rc.wsdl]
             */
            RequestType requestType = new RequestType();
            /* Set client timeout value of request object. */
            requestType.setClientTimeout(new BigInteger(TIME_OUT));
            /*
             * Create the instance of the ReqClientIDType that is a class generated from the Rapid
             * connect Transaction Service WSDL file [rc.wsdl]
             */
            ReqClientIDType reqClientIDType = new ReqClientIDType();
            /* Set App parameter value. */
            reqClientIDType.setApp(bean.getAppID());
            /* Set Auth parameter value. */
            reqClientIDType.setAuth("XXXXX0000000000|00000000");
            /* Set clientRef parameter value. */
            reqClientIDType.setClientRef(clientRef);
            /* Set DID parameter value. */
            reqClientIDType.setDID(bean.getDatawireId());
            /* Assign ReqClientID value to the requesttype object */
            requestType.setReqClientID(reqClientIDType);
            /*
             * Create the instance of the TransactionType that is a class generated from the Rapid
             * connect Transaction Service WSDL file [rc.wsdl]
             */
            TransactionType transactionType = new TransactionType();
            /*
             * Create the instance of the PayloadType that is a class generated from the Rapid
             * connect Transaction Service WSDL file [rc.wsdl]
             */
            PayloadType payloadType = new PayloadType();
            /* Set encoding of the pay load data- the request XML data */
            payloadType.setEncoding(CDATA);
            /* Set the XML request value as pay load */
            payloadType.setValue(gmfrequest); // Set payload - actual xml request
            /* Set the pay load type */
            transactionType.setPayload(payloadType);
            /* Set service ID */
            transactionType.setServiceID(String.valueOf(bean.getServiceID()));
            /* Set transaction type */
            requestType.setTransaction(transactionType);
            /* Set version number of the request */
            requestType.setVersion(VERSION);

            /* The response to be returned. */
            String gmfResponse = null;
            /*
             * Create the instance of the RcService that is a class generated from the Rapid connect
             * Transaction Service WSDL file [rc.wsdl]
             */
            RcService rcService = new RcService();
            /*
             * Create the instance of the RcPortType that is a class generated from the Rapid
             * connect Transaction Service WSDL file [rc.wsdl]
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
                            /* Extract pay load - the response from data wire for cdata encoding. */
                            gmfResponse = pType.getValue();
                        } else if (encoding.equalsIgnoreCase(XML_ESCAPE)) {
                            /*
                             * Extract pay load - the response from data wire for xml_escape
                             * encoding.
                             */
                            gmfResponse = pType.getValue().replaceAll(GT, GT_SYM)
                                    .replaceAll(LT, LT_SYM).replaceAll(AMP, AMP_SYM);
                        }
                        outputViews.write(documentUtility.newDocument(getJsonFromXML(gmfResponse)));
                    } else {
                        writeToErrorView(responseType.getTransactionResponse().getPayload()
                                .getValue(), responseType.getStatus().getStatusCode(),
                                VALIDATE_INPUT_DATA, responseType.getStatus().getValue());
                    }
                } else {
                    writeToErrorView(INVALID_RESPONSE, responseType.getStatus().getStatusCode(),
                            VALIDATE_INPUT_DATA, responseType.getStatus().getValue());
                }
            } else {
                writeToErrorView(EMPTY_RESPONSE, IMPROPER_INPUT, VALIDATE_INPUT_DATA, "NULL");
            }
        } catch (Exception ex) {
            writeToErrorView(ERRORMSG, ex.getMessage(), ERROR_RESOLUTION, ex);
        }
    }

    private ObjectSchema getSchema(SchemaProvider provider, Class<?> classType) {
        ArrayList<Method> setterMethods = findSetters(classType);
        ObjectSchema schema = null;
        if (!setterMethods.isEmpty()) {
            schema = provider.createSchema(SnapType.STRING, classType.getSimpleName());
        }
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
                    schema.addChild(provider.createSchema(getDataTypes(paramType), method.getName()
                            .substring(3)));
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

    /**
     * finds the declared getter methods in the given classtype
     * 
     * @param c
     * @return ArrayList<Method>
     */
    private static ArrayList<Method> findGetters(Class<?> c) {
        ArrayList<Method> list = new ArrayList<Method>();
        Method[] methods = c.getDeclaredMethods();
        for (Method method : methods) {
            if (isGetter(method)) {
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
        }
        return json2Map;
    }

    private Object getObject(Class<?> classType, Map<String, Object> map) {
        Object obj;
        try {
            obj = classType.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            log.error(e.getMessage(), e);
            return null;
        }
        for (Method method : findGetters(classType)) {
            String paramType = method.getReturnType().getName();
            if (paramType.startsWith(FD_PROXY_PKG_PREFIX) && !paramType.endsWith(TYPE)) {
                Class<?> subClass = null;
                try {
                    subClass = Class.forName(paramType);
                } catch (ClassNotFoundException ex) {
                    log.error(ex.getMessage(), ex);
                    writeToErrorView(UNSUPPORTED_OPT, ex.getMessage(), UNSUPPORTED_OPT_RESOLUTION,
                            ex);
                }
                try {
                    method.invoke(
                            obj,
                            getObject(subClass,
                                    (Map<String, Object>) map.get(subClass.getSimpleName())));
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    log.error(e.getMessage(), e);
                }
            } else if (paramType.endsWith(TYPE)) {
                try {
                    Object typesObj = getTypesInstance(paramType,
                            (String) map.get(method.getName().substring(3)));
                    method.invoke(obj, typesObj);
                } catch (SecurityException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(), e);
                } catch (IllegalArgumentException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(), e);
                } catch (InvocationTargetException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(), e);
                } catch (IllegalAccessException e) {
                    // TODO Auto-generated catch block
                    log.error(e.getMessage(), e);
                }
            } else {
                try {
                    method.invoke(obj, map.get(method.getName().substring(3)));
                } catch (IllegalAccessException | IllegalArgumentException
                        | InvocationTargetException e) {
                    log.error(e.getMessage(), e);
                }
            }
        }

        return obj;
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
            context = JAXBContext
                    .newInstance(com.snaplogic.snaps.firstdata.gmf.proxy.GMFMessageVariants.class);
            marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");
            marshaller.marshal(gmfmv, stringWriter);
            returnValue = stringWriter.toString();
        } catch (JAXBException jaxe) {
            log.error(jaxe.getMessage(), jaxe);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return returnValue;
    }

    private Object getTypesInstance(String classType, String value) {
        try {
            Class<?> types = Class.forName(classType);
            Method typesMethod = types.getMethod("fromValue", String.class);
            Object typesObj = typesMethod.invoke(null, value);
            return typesObj;
        } catch (ClassNotFoundException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(), e);
        } catch (NoSuchMethodException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(), e);
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(), e);
        } catch (IllegalArgumentException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(), e);
        } catch (InvocationTargetException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(), e);
        } catch (IllegalAccessException e) {
            // TODO Auto-generated catch block
            log.error(e.getMessage(), e);
        }
        return null;
    }
}
