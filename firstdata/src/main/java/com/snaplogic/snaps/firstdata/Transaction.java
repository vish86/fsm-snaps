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

package com.snaplogic.snaps.firstdata;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Throwables;
import com.google.common.collect.Maps;
import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.snaplogic.api.ConfigurationException;
import com.snaplogic.api.DependencyManager;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.api.InputSchemaProvider;
import com.snaplogic.api.MetricsProvider;
import com.snaplogic.api.Snap;
import com.snaplogic.api.ViewProvider;
import com.snaplogic.common.SnapType;
import com.snaplogic.common.metrics.Counter;
import com.snaplogic.common.properties.SnapProperty;
import com.snaplogic.common.properties.Suggestions;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.common.properties.builders.ViewBuilder;
import com.snaplogic.common.services.ws.ClientBuilder;
import com.snaplogic.common.services.ws.ClientBuilderFactory;
import com.snaplogic.common.services.ws.IntrospectionService;
import com.snaplogic.common.services.ws.InvocationService;
import com.snaplogic.snap.api.*;
import com.snaplogic.snap.api.capabilities.Category;
import com.snaplogic.snap.api.capabilities.Errors;
import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snap.api.capabilities.Inputs;
import com.snaplogic.snap.api.capabilities.Outputs;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snap.api.capabilities.ViewType;
import com.snaplogic.snap.api.editor.EditorPropertyFactory;
import com.snaplogic.snap.api.editor.XMLEditorContentProviderImpl;
import com.snaplogic.snap.api.soap.ClientBuilderFactoryImpl;
import com.snaplogic.snap.api.soap.DefaultSOAPTemplateGenerator;
import com.snaplogic.snap.api.soap.HttpContextProvider;
import com.snaplogic.snap.api.soap.InvocationServiceImpl;
import com.snaplogic.snap.api.soap.ResponseInterceptor;
import com.snaplogic.snap.api.soap.SOAPTemplateGenerator;
import com.snaplogic.snap.api.soap.SoapEditorSuggestionsProviderImpl;
import com.snaplogic.snap.api.soap.WsdlIntrospectionService;
import com.snaplogic.snap.api.xml.XmlUtils;
import com.snaplogic.snap.api.xml.XmlUtilsImpl;
import com.snaplogic.snap.schema.api.ObjectSchema;
import com.snaplogic.snap.schema.api.SchemaBuilder;
import com.snaplogic.snap.schema.api.SchemaProvider;
import com.snaplogic.snap.view.InputView;
import com.snaplogic.snaps.firstdata.gmf.proxy.CommonGrp;
import com.snaplogic.snaps.firstdata.gmf.proxy.GMFMessageVariants;
import com.snaplogic.snaps.firstdata.soap.suggestions.EndpointSuggestions;
import com.snaplogic.snaps.firstdata.soap.suggestions.OperationSuggestions;
import com.snaplogic.snaps.firstdata.soap.suggestions.SOAPExecuteTemplateEvaluatorImpl;
import com.snaplogic.snaps.firstdata.soap.suggestions.ServiceSuggestions;
import com.snaplogic.snaps.firstdata.soap.suggestions.SoapHttpContextProvider;
import com.snaplogic.snaps.firstdata.soap.suggestions.SoapUtils;
import com.snaplogic.snaps.firstdata.soap.suggestions.TemplateSuggestionsExecuteImpl;
import com.snaplogic.util.PeekingDocumentIterator;

import org.apache.commons.lang.StringUtils;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;

import net.sf.json.JSON;
import net.sf.json.xml.XMLSerializer;

import static com.snaplogic.snaps.firstdata.Constants.*;
import static com.snaplogic.snaps.firstdata.Messages.*;

/**
 * Snap that makes SOAP calls and writes out SOAP response in JSON format.
 *
 * @author mklumpp
 */
@Version(snap = 1)
@General(title = CREATE1_LABEL, purpose = SNAP_DESC)
@Inputs(min = 2, max = 2, accepts = { ViewType.DOCUMENT })
@Outputs(min = 0, max = 1, offers = { ViewType.DOCUMENT })
@Errors(min = 1, max = 1, offers = { ViewType.DOCUMENT })
@Category(snap = SnapCategory.WRITE)
public class Transaction implements Snap, MetricsProvider, DependencyManager, InputSchemaProvider,
        ViewProvider {
    private String resourceType = null;
    private static final Logger log = LoggerFactory.getLogger(Transaction.class);
    public static final String PROP_WSDL_URL = "wsdl_url";
    public static final String PROP_SERVICE = "serviceName";
    public static final String PROP_ENDPOINT = "portName";
    public static final String PROP_OPERATION = "operation";
    private final JsonXMLConfig config = new JsonXMLConfigBuilder().autoArray(true)
            .multiplePI(true)
            .build();
    private final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    private final JsonXMLOutputFactory jsonOutputFactory = new JsonXMLOutputFactory(config);
    @Inject
    private InputViews inputViews;
    @Inject
    private OutputViews outputViews;
    @Inject
    private ErrorViews errorViews;
    @Inject
    private DocumentUtility documentUtility;
    @Inject
    private InvocationService invocationService;
    @Inject
    private IntrospectionService introspectionService;
    @Inject
    private EditorPropertyFactory tablePropertyFactory;
    @Inject
    private TemplateEvaluator templateEvaluator;
    @Inject
    private EditorContentProvider soapEditorContentProvider;
    @Inject
    private SoapUtils soapUtils;
    @Inject
    private XmlUtils xmlUtils;
    private EditorProperty editorProperty;
    private Counter counter;
    private ClientBuilder clientBuilder;
    private boolean useDefaultValueChecked;

    @Override
    public void defineViews(final ViewBuilder viewBuilder) {
        viewBuilder.describe(DEFAULT_INPUT_VIEW_0).type(ViewType.DOCUMENT).add(ViewCategory.INPUT);
        viewBuilder.describe(DEFAULT_INPUT_VIEW_1).type(ViewType.DOCUMENT).add(ViewCategory.INPUT);
        viewBuilder.describe(DEFAULT_OUTPUT_VIEW_0)
                .type(ViewType.DOCUMENT)
                .add(ViewCategory.OUTPUT);
    }

    @Override
    public Module getManagedModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(ClientBuilderFactory.class).to(ClientBuilderFactoryImpl.class).in(
                        Scopes.SINGLETON);
                bind(IntrospectionService.class).to(WsdlIntrospectionService.class).in(
                        Scopes.SINGLETON);
                bind(InvocationService.class).to(InvocationServiceImpl.class).in(Scopes.SINGLETON);
                bind(EditorContentProvider.class).to(XMLEditorContentProviderImpl.class);
                bind(TemplateEvaluator.class).to(SOAPExecuteTemplateEvaluatorImpl.class);
                bind(EditorSuggestionProvider.class).to(SoapEditorSuggestionsProviderImpl.class);
                bind(XmlUtils.class).to(XmlUtilsImpl.class);
                bind(SOAPTemplateGenerator.class).to(DefaultSOAPTemplateGenerator.class);
                bind(XmlUtils.class).to(XmlUtilsImpl.class);
            }
        };
    }

    @Override
    public void defineInputSchema(final SchemaProvider provider) {
        for (String viewName : provider.getRegisteredViewNames()) {
            if (viewName.equals(DEFAULT_INPUT_VIEW_0)) {
                SchemaBuilder schemaBuilder = provider.getSchemaBuilder(viewName);
                editorProperty.getSchema(schemaBuilder, viewName);
            } else {
                Class<?> classType = null;
                try {
                    classType = Class.forName(getGMFReqClassType());
                } catch (ClassNotFoundException e) {
                    log.error(e.getMessage(), e);
                    throw new ExecutionException(e.getMessage());
                }
                try {
                    SchemaBuilder schemaBuilder = provider.getSchemaBuilder(viewName);
                    schemaBuilder.withChildSchema(getSchema(provider, classType, SnapType.STRING));
                    schemaBuilder.build();
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                }
            }
        }
    }

    @Override
    public void defineProperties(final PropertyBuilder propBuilder) {
        propBuilder.describe(PROP_WSDL_URL, LBL_WSDL_URL, DESC_URL_FOR_THE_WSDL)
                .required()
                .fileBrowsing()
                .add();
        propBuilder.describe(PROP_SERVICE, LBL_SERVICE_NAME, DESC_SERVICE_NAME)
                .withSuggestions(new ServiceSuggestions(introspectionService, PROP_SERVICE))
                .required()
                .add();
        propBuilder.describe(PROP_ENDPOINT, LBL_ENDPOINT, DESC_ENDPOINT)
                .withSuggestions(new EndpointSuggestions(introspectionService, PROP_ENDPOINT))
                .required()
                .add();
        propBuilder.describe(PROP_OPERATION, LBL_OPERATION, DESC_OPERATION)
                .withSuggestions(new OperationSuggestions(introspectionService, PROP_OPERATION))
                .required()
                .add();
        propBuilder.describe(RESOURCE_PROP, RESOURCE_LABEL, RESOURCE_DESC)
                .withAllowedValues(RESOUCE_LIST)
                .required()
                .add();
        Suggestions templateSuggestions = new TemplateSuggestionsExecuteImpl(soapUtils,
                invocationService);
        tablePropertyFactory.addEditorProperty(propBuilder, templateSuggestions,
                SnapProperty.EditorType.XML, CUSTOMIZE_ENVELOPE, CUSTOMIZE_ENVELOPE_DESC);
    }

    @Override
    public void defineMetrics(final MetricsBuilder metricsBuilder) {
        counter = metricsBuilder.describe(DOCUMENT_COUNTER, COUNTER_DESCRIPTION)
                .measuredIn(COUNTER_UNIT)
                .counter();
    }

    @Override
    public void configure(final PropertyValues propertyValues) throws ConfigurationException {
        resourceType = propertyValues.get(RESOURCE_PROP);
        String wsdlUrl = propertyValues.get(PROP_WSDL_URL);
        String serviceName = propertyValues.get(PROP_SERVICE);
        String portName = propertyValues.get(PROP_ENDPOINT);
        String operation = propertyValues.get(PROP_OPERATION);
        String defaultValueForSubstitution = propertyValues.get(PROP_DEFAULT_VALUE);

        QName serviceQName = QName.valueOf(serviceName);
        QName portQName = QName.valueOf(portName);
        QName operationQName = QName.valueOf(operation);
        HttpContextProvider httpContextProvider = new SoapHttpContextProvider();
        clientBuilder = invocationService.createClientBuilderFor(wsdlUrl, serviceQName, portQName,
                operationQName, httpContextProvider);
        configureDispatch(clientBuilder.getDispatchClient(), clientBuilder.getSoapAction());
        editorProperty = propertyValues.getEditorProperty(
                soapEditorContentProvider,
                ((SOAPExecuteTemplateEvaluatorImpl) templateEvaluator).withDefaultValue(useDefaultValueChecked ? defaultValueForSubstitution
                        : XmlUtilsImpl.NO_DATA),
                soapUtils.initializeSuggestionProvider(wsdlUrl, serviceQName, portQName,
                        operationQName, clientBuilder, httpContextProvider));
        Bus bus = BusFactory.getDefaultBus(true);
        bus.getInInterceptors().add(new ResponseInterceptor());
    }

    @Override
    public void cleanup() throws ExecutionException {
        // do nothing
    }

    private void configureDispatch(Dispatch<? extends SOAPMessage> dispatch, String action) {
        if (action == null) {
            return;
        }
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, true);
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, action);
    }

    private Object parseXML2JSON(String envelope, XmlUtils xmlUtils) {
        Source source = new StreamSource(new StringReader(envelope));
        try {
            return xmlUtils.convertToJson(xmlInputFactory, jsonOutputFactory, source);
        } catch (XMLStreamException e) {
            throw new ExecutionException(e, ERROR_PARSING_XML).withReason(e.getMessage())
                    .withResolution(VERIFY_THE_RETURNED_XML_IS_PARSEABLE);
        }
    }

    @Override
    public void execute() throws ExecutionException {
        Map<String, InputView> inputViewMap = inputViews.getAll();
        InputView payloadInputView = inputViewMap.get(DEFAULT_INPUT_VIEW_1);
        InputView authInputView = inputViewMap.get(DEFAULT_INPUT_VIEW_0);
        PeekingDocumentIterator payloadInputViewIterator = null;
        Document payloadInputDoc = null;
        if (payloadInputView != null) {
            payloadInputViewIterator = new PeekingDocumentIterator(
                    inputViews.getDocumentsFrom(payloadInputView), DEFAULT_INPUT_VIEW_1);
            if (payloadInputViewIterator != null && payloadInputViewIterator.hasNext()) {
                payloadInputDoc = payloadInputViewIterator.next();
            }
        }
        Object requestObj;
        Object inputData;
        if ((inputData = payloadInputDoc.get()) != null) {
            /*
             * Process the input data if and only if it is of Map
             */
            if (inputData instanceof Map) {
                String claszPath = getGMFReqClassType();
                Object map = Map.class.cast(inputData).get(getGMFReqClassName());
                Map<String, Object> inputMap = Map.class.cast(map);
                requestObj = getObject(claszPath, inputMap);
                if (inputMap.containsKey(ADDTL_AMT_GRP)) {
                    try {
                        List<Map<String, Object>> inputList = List.class.cast(inputMap.get(ADDTL_AMT_GRP));
                        if (inputList != null) {
                            requestObj = prepareListObj(requestObj, claszPath, ADDTL_AMT_GRP,
                                    inputList);
                        }
                    } catch (ClassCastException e) {
                        Map<String, Object> inputPair = Map.class.cast(inputMap.get(ADDTL_AMT_GRP));
                        requestObj = prepareListObj(requestObj, claszPath, ADDTL_AMT_GRP, inputPair);
                    }
                }
                if (inputMap.containsKey(PROD_CODE_DET_GRP)) {
                    try {
                        List<Map<String, Object>> inputList = List.class.cast(inputMap.get(PROD_CODE_DET_GRP));
                        if (inputList != null) {
                            requestObj = prepareListObj(requestObj, claszPath, PROD_CODE_DET_GRP,
                                    inputList);
                        }
                    } catch (ClassCastException e) {
                        Map<String, Object> inputPair = Map.class.cast(inputMap.get(PROD_CODE_DET_GRP));
                        requestObj = prepareListObj(requestObj, claszPath, PROD_CODE_DET_GRP,
                                inputPair);
                    }
                }

                String xmlData = null;
                try {
                    /* Using Reflection and JAXB to prepare SOAP request XML */
                    Class<?> gmfmv = Class.forName(GMF_MESSAGE_VARIANTS);
                    Object gmfmvObj = gmfmv.newInstance();
                    Method gmfMethod = gmfmv.getMethod(
                            String.format(SETTER, getGMFVarient(resourceType)),
                            getClassType(claszPath));
                    gmfMethod.invoke(gmfmvObj, requestObj);
                    /* converting simple java objects into XML format using JAXB */
                    xmlData = getGMFXMLRequestData((GMFMessageVariants) gmfmvObj);
                    xmlData = xmlData.replaceAll(GMF_MESSAGE_VARIANTS_TAG, GMF_TAG);
                    xmlData = xmlData.replaceAll(NAMESPACE_NS1, NAMESPACE_S1);
                    xmlData = xmlData.replaceAll(NAMESPACE_NS2, NAMESPACE_S2);
                    log.debug(xmlData);
                } catch (Exception e) {
                    log.error(e.getMessage(), e);
                    writeToErrorView(e.getMessage(), REQUEST_FAILED, ERROR_RESOLUTION, e);
                    return;
                }

                PeekingDocumentIterator authInputViewIterator = null;
                Document document = null;
                Map<String, Object> requestData = null;
                if (authInputView != null) {
                    authInputViewIterator = new PeekingDocumentIterator(
                            inputViews.getDocumentsFrom(authInputView), DEFAULT_INPUT_VIEW_0);
                    if (authInputViewIterator != null && authInputViewIterator.hasNext()) {
                        document = authInputViewIterator.next();
                        if ((requestData = (Map<String, Object>) document.get()) != null) {
                            Map dataSet = new HashMap();
                            dataSet.put("Encoding", "cdata");
                            dataSet.put("value", xmlData);
                            Map payload = new HashMap();
                            payload.put("Payload", dataSet);
                            requestData.put("Transaction", payload);
                        }
                        document.set(requestData);
                    }
                }

                String envelope = null;
                String strippedEnvelope = null;
                try {
                    envelope = editorProperty.eval(document);
                    log.info(DISPATCHING_SOAP_REQUEST, envelope);
                    SOAPMessage soapResponse = invocationService.call(clientBuilder, envelope);
                    if (soapResponse == null) {
                        return;
                    }
                    Object data = invocationService.serialize(soapResponse);
                    if (data != null) {
                        outputViews.write(documentUtility.newDocument(data), document);
                        counter.inc();
                    }
                } catch (XMLStreamException e) {
                    throw new ExecutionException(e, XML_SERIALIZATION_FAILED).withResolutionAsDefect();
                } catch (Exception e) {
                    Throwable rootCause = Throwables.getRootCause(e);
                    String reason = rootCause.getMessage();
                    Map<String, Object> errorMap = Maps.newHashMap();
                    errorMap.put(KEY_ERROR, reason);
                    SnapDataException dataException = new SnapDataException(
                            documentUtility.newDocument(errorMap), EXCEPTION_OCCURRED).withReason(
                            reason).withResolution(SOAP_EXCEPTION_RESOLUTION);
                    if (envelope != null) {
                        errorMap.put(KEY_ENVELOPE, parseXML2JSON(envelope, xmlUtils));
                    }
                    if (strippedEnvelope != null) {
                        errorMap.put(KEY_STRIPPED_ENVELOPE,
                                parseXML2JSON(strippedEnvelope, xmlUtils));
                    }
                    errorViews.write(dataException, document);
                }
            }
        }
    }

    private ObjectSchema getSchema(SchemaProvider provider, Class<?> classType, SnapType snapType) {
        ArrayList<Method> getterMethods = findGetters(classType);
        ObjectSchema schema = null;
        if (!getterMethods.isEmpty()) {
            schema = provider.createSchema(snapType, classType.getSimpleName());
        }
        String name;
        String paramType;
        Class<?> subClass;
        Class<?> fieldTypeParameterType;
        ObjectSchema subSchema;
        ParameterizedType fieldGenericType;
        for (Method method : getterMethods) {
            try {
                paramType = method.getReturnType().getName();
                if (paramType.startsWith(FD_PROXY_PKG_PREFIX) && !paramType.endsWith(TYPE)) {
                    try {
                        subClass = Class.forName(paramType);
                    } catch (ClassNotFoundException e) {
                        log.error(e.getMessage(), e);
                        throw new ExecutionException(e.getMessage());
                    }
                    subSchema = getSchema(provider, subClass, SnapType.STRING);
                    if (subSchema != null) {
                        schema.addChild(subSchema);
                    }
                } else if (paramType.endsWith(List.class.getSimpleName())) {
                    fieldGenericType = (ParameterizedType) method.getGenericReturnType();
                    fieldTypeParameterType = (Class<?>) fieldGenericType.getActualTypeArguments()[0];
                    if (fieldTypeParameterType == String.class) {
                        subSchema = provider.createSchema(SnapType.COMPOSITE,
                                getFieldName(method.getName()));
                    } else {
                        subSchema = getSchema(provider, fieldTypeParameterType, SnapType.COMPOSITE);
                    }
                    if (subSchema != null) {
                        schema.addChild(subSchema);
                    }
                } else {
                    name = getFieldName(method.getName());
                    schema.addChild(provider.createSchema(getDataTypes(paramType), name));
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
        return new StringBuilder().append(FD_PROXY_PKG_PREFIX)
                .append(resourceType)
                .append(FD_REQ_TAG)
                .toString();
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

    /**
     * finds the declared getter methods in the given classtype
     *
     * @param c
     * @return ArrayList<Method>
     */
    public static ArrayList<Method> findGetters(Class<?> c) {
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
            xml = xml.replace(FD_NAMESPACE, W3C_NAMESPACE);
            XMLSerializer xmlSerializer = new XMLSerializer();
            xmlSerializer.setRemoveNamespacePrefixFromElements(true);
            xmlSerializer.setSkipNamespaces(true);
            JSON objJson = xmlSerializer.read(xml);
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
                    if (subMap == null) {
                        continue;
                    }
                    paramObj = getObject(returnClazType, Map.class.cast(subMap));
                    paramObj = setArrayObj(paramObj, returnClazType, Map.class.cast(subMap));
                } else if (returnClazType.endsWith(TYPE)) {
                    paramObj = getTypesInstance(returnClazType, (String) map.get(inputFieldName));
                } else if (returnClazType.endsWith(String.class.getSimpleName())) {
                    paramObj = map.get(inputFieldName);
                }
                if (paramObj != null) {
                    if (method.getParameterTypes()[0].isArray()) {
                        paramObj = paramObj.toString().getBytes();
                    }
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
            marshaller.setProperty(Marshaller.JAXB_ENCODING, XML_ENCODING);
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
            if (cGrp == null) {
                return StringUtils.EMPTY;
            }
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

    private Object prepareListObj(Object requestObj, String claszPath, String method2Invoke,
            Object inputData) {
        if (inputData != null) {
            Class<?> clasz = getClassType(claszPath);
            Method method;
            try {
                method = clasz.getDeclaredMethod(String.format(GETTER, method2Invoke));
                ParameterizedType fieldGenericType = (ParameterizedType) method.getGenericReturnType();
                Class<?> fieldTypeParameterType = (Class<?>) fieldGenericType.getActualTypeArguments()[0];
                List<Object> list = (List<Object>) method.invoke(requestObj);
                if (inputData instanceof List) {
                    List<Map<String, Object>> inputList = List.class.cast(inputData);
                    for (Map<String, Object> mapObj : inputList) {
                        Object obj = getObject(fieldTypeParameterType.getName(), mapObj);
                        list.add(obj);
                    }
                } else if (inputData instanceof Map) {
                    Object obj = getObject(fieldTypeParameterType.getName(),
                            Map.class.cast(inputData));
                    list.add(obj);
                }
            } catch (NoSuchMethodException | SecurityException e) {
                log.warn(e.getMessage(), e);
            } catch (IllegalAccessException e) {
                log.warn(e.getMessage(), e);
            } catch (IllegalArgumentException e) {
                log.warn(e.getMessage(), e);
            } catch (InvocationTargetException e) {
                log.warn(e.getMessage(), e);
            }
        }
        return requestObj;
    }

    private Object setArrayObj(Object requestObj, String claszPath, Map<String, Object> inputData) {
        if (inputData != null) {
            Class<?> clasz = getClassType(claszPath);
            Object obj = null;
            List<Object> list;
            for (Method method : findGetters(clasz)) {
                String declareClasz = method.getReturnType().getName();
                if (declareClasz != List.class.getSimpleName()) {
                    continue;
                }
                try {
                    list = (List<Object>) method.invoke(requestObj);
                    if ((obj = inputData.get(getFieldName(method.getName()))) == null) {
                        continue;
                    }
                    if (obj instanceof List) {
                        for (Object data : List.class.cast(obj)) {
                            list.add(data);
                        }
                    } else if (obj instanceof String) {
                        list.add(obj);
                    }
                } catch (IllegalAccessException e) {
                    log.warn(e.getMessage(), e);
                } catch (IllegalArgumentException e) {
                    log.warn(e.getMessage(), e);
                } catch (InvocationTargetException e) {
                    log.warn(e.getMessage(), e);
                } catch (Exception e) {
                    log.warn(e.getMessage(), e);
                }
            }
        }
        return requestObj;
    }

    private String getGMFVarient(String resourceType) {
        String gmfVarient;
        if ((gmfVarient = GMF_VARIENTS.get(resourceType)) != null)
            return gmfVarient;
        return resourceType;
    }
}
