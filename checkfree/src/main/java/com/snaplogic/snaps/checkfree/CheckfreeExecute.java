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

package com.snaplogic.snaps.checkfree;

import static com.snaplogic.snaps.checkfree.Messages.CUSTOMIZE_ENVELOPE;
import static com.snaplogic.snaps.checkfree.Messages.CUSTOMIZE_ENVELOPE_DESC;
import static com.snaplogic.snaps.checkfree.Messages.DESC_DEFAULT_VALUE;
import static com.snaplogic.snaps.checkfree.Messages.DESC_ENCODE_ATTRIBUTE;
import static com.snaplogic.snaps.checkfree.Messages.DESC_ENDPOINT;
import static com.snaplogic.snaps.checkfree.Messages.DESC_OPERATION;
import static com.snaplogic.snaps.checkfree.Messages.DESC_SERVICE_NAME;
import static com.snaplogic.snaps.checkfree.Messages.DESC_TIMEOUT;
import static com.snaplogic.snaps.checkfree.Messages.DESC_URL_FOR_THE_WSDL;
import static com.snaplogic.snaps.checkfree.Messages.DESC_URL_FOR_THE_KEYSTORE;
import static com.snaplogic.snaps.checkfree.Messages.DESC_PASS_FOR_THE_KEYSTORE;
import static com.snaplogic.snaps.checkfree.Messages.DESC_USE_DEFAULT_VALUE;
import static com.snaplogic.snaps.checkfree.Messages.DISPATCHING_SOAP_REQUEST;
import static com.snaplogic.snaps.checkfree.Messages.DOCUMENTS;
import static com.snaplogic.snaps.checkfree.Messages.ERROR_PARSING_XML;
import static com.snaplogic.snaps.checkfree.Messages.EXCEPTION_OCCURRED;
import static com.snaplogic.snaps.checkfree.Messages.LBL_DEFAULT_VALUE;
import static com.snaplogic.snaps.checkfree.Messages.LBL_ENCODE_ATTRIBUTE;
import static com.snaplogic.snaps.checkfree.Messages.LBL_ENDPOINT;
import static com.snaplogic.snaps.checkfree.Messages.LBL_OPERATION;
import static com.snaplogic.snaps.checkfree.Messages.LBL_SERVICE_NAME;
import static com.snaplogic.snaps.checkfree.Messages.LBL_TIMEOUT;
import static com.snaplogic.snaps.checkfree.Messages.LBL_USE_DEFAULT_VALUE;
import static com.snaplogic.snaps.checkfree.Messages.LBL_WSDL_URL;
import static com.snaplogic.snaps.checkfree.Messages.LBL_KEYSTORE_URL;
import static com.snaplogic.snaps.checkfree.Messages.LBL_KEYSTORE_PASS;
import static com.snaplogic.snaps.checkfree.Messages.SOAP_DOC_PROCESSED;
import static com.snaplogic.snaps.checkfree.Messages.SOAP_EXCEPTION_RESOLUTION;
import static com.snaplogic.snaps.checkfree.Messages.SOAP_RESPONSES_WRITTEN_DESCRIPTION;
import static com.snaplogic.snaps.checkfree.Messages.TRUST_ALL_CERTS_DESCRIPTION;
import static com.snaplogic.snaps.checkfree.Messages.TRUST_ALL_CERTS_LABEL;
import static com.snaplogic.snaps.checkfree.Messages.VERIFY_THE_RETURNED_XML_IS_PARSEABLE;
import static com.snaplogic.snaps.checkfree.Messages.XML_SERIALIZATION_FAILED;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringReader;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.http.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.api.EditorContentProvider;
import com.snaplogic.snap.api.EditorProperty;
import com.snaplogic.snap.api.EditorSuggestionProvider;
import com.snaplogic.snap.api.ExpressionProperty;
import com.snaplogic.snap.api.MetricsBuilder;
import com.snaplogic.snap.api.OutputViews;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.SimpleSnap;
import com.snaplogic.snap.api.SnapCategory;
import com.snaplogic.snap.api.SnapDataException;
import com.snaplogic.snap.api.TemplateEvaluator;
import com.snaplogic.snap.api.ViewCategory;
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
import com.snaplogic.snap.api.xsd.TypesFactory;
import com.snaplogic.snap.api.xsd.TypesFactoryImpl;
import com.snaplogic.snap.schema.api.SchemaBuilder;
import com.snaplogic.snap.schema.api.SchemaProvider;
import com.snaplogic.snaps.soap.suggestions.EndpointSuggestions;
import com.snaplogic.snaps.soap.suggestions.OperationSuggestions;
import com.snaplogic.snaps.soap.suggestions.ServiceSuggestions;
import com.snaplogic.snaps.soap.suggestions.TemplateSuggestionsExecuteImpl;

import de.odysseus.staxon.json.JsonXMLConfig;
import de.odysseus.staxon.json.JsonXMLConfigBuilder;
import de.odysseus.staxon.json.JsonXMLOutputFactory;

import java.security.GeneralSecurityException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.xml.namespace.QName;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.transport.http.HTTPConduit;


/**
 * Snap that makes Checkfree calls and writes out SOAP response in JSON format.
 *
 * @author svatada
 *
 */
@Version(snap = 1)
@General(title = Messages.SOAP_EXECUTE_LABEL, purpose = Messages.SOAP_REQUEST_DESC)
@Inputs(min = 0, max = 1, accepts = {ViewType.DOCUMENT})
@Outputs(min = 0, max = 1, offers = {ViewType.DOCUMENT})
@Errors(min = 1, max = 1, offers = {ViewType.DOCUMENT})
@Category(snap = SnapCategory.WRITE)
public class CheckfreeExecute extends SimpleSnap implements MetricsProvider,DependencyManager, InputSchemaProvider, ViewProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(CheckfreeExecute.class);
    String wsdlUrl = null;
//    String keystoreUrl = null;
//    String keystorePass = null;
    String serviceName = null;
    String portName = null;
    String operation = null;
    // Public as these constants are used by SuggestionsProviders
    public static final String PROP_WSDL_URL = "wsdl_url";
//    public static final String PROP_KEYSTORE_URL = "keystore_url";
//    public static final String PROP_KEYSTORE_PASS = "keystore_pass";
    public static final String PROP_SERVICE = "serviceName";
    public static final String PROP_ENDPOINT = "portName";
    public static final String PROP_OPERATION = "operation";
    public static final String PROP_ENCODE_ATTRIBUTE = "encodeAttribute";
    private static final String PROP_TIMEOUT = "timeout";
    private static final String PROP_DEFAULT_VALUE = "defaultValue";
    private static final String PROP_USE_DEFAULT_VALUE = "useDefaultValue";
    private final static String DEFAULT_INPUT_VIEW_0 = "input0";
    private final static String DEFAULT_OUTPUT_VIEW_0 = "output0";
    private static final String KEY_ERROR = "error";
    private static final String STACKTRACE = "stacktrace";
    private static final String KEY_ENVELOPE = "envelope";
    private static final String KEY_STRIPPED_ENVELOPE = "stripped_envelope";
    private static final String REQUEST_HEADERS = "request_headers";
    public static final String TRUST_ALL_CERTS_PROP = "trustAllCerts";

    private final JsonXMLConfig config = new JsonXMLConfigBuilder()
            .autoArray(true)
            .multiplePI(true)
            .build();
    private final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    private final JsonXMLOutputFactory jsonOutputFactory = new JsonXMLOutputFactory(config);
    private final ResponseInterceptor responseInterceptor = new ResponseInterceptor();
    @Inject
    private OutputViews outputViews;
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
    @Inject
    private DocumentBuilderFactory documentBuilderFactory;

    private final List<Pair<ExpressionProperty, ExpressionProperty>> httpHeaders = new ArrayList<>();

    private EditorProperty editorProperty;
    private Counter documentCounter;
    private ClientBuilder clientBuilder;
    private boolean useDefaultValueChecked;
    private boolean trustAllCerts;
    private Bus bus;

    @Override
    public void defineViews(final ViewBuilder viewBuilder) {
        viewBuilder.describe(DEFAULT_INPUT_VIEW_0)
                .type(ViewType.DOCUMENT)
                .add(ViewCategory.INPUT);
        viewBuilder.describe(DEFAULT_OUTPUT_VIEW_0)
                .type(ViewType.DOCUMENT)
                .add(ViewCategory.OUTPUT);
    }

    @Override
    public Module getManagedModule() {
        return new AbstractModule() {
            @Override
            protected void configure() {
                bind(ClientBuilderFactory.class).to(ClientBuilderFactoryImpl.class).in(Scopes
                        .SINGLETON);
                bind(IntrospectionService.class).to(WsdlIntrospectionService.class).in(Scopes
                        .SINGLETON);
                bind(InvocationService.class).to(InvocationServiceImpl.class).in(Scopes
                        .SINGLETON);
                bind(EditorContentProvider.class).to(XMLEditorContentProviderImpl.class);
                bind(TemplateEvaluator.class).to(SOAPExecuteTemplateEvaluatorImpl.class);
                bind(EditorSuggestionProvider.class).to(SoapEditorSuggestionsProviderImpl.class);
                bind(XmlUtils.class).to(XmlUtilsImpl.class);
                bind(SOAPTemplateGenerator.class).to(DefaultSOAPTemplateGenerator.class);
                bind(XmlUtils.class).to(XmlUtilsImpl.class);
                bind(TypesFactory.class).to(TypesFactoryImpl.class);
            }
        };
    }

    @Override
    public void defineInputSchema(final SchemaProvider provider) {
        for (String viewName : provider.getRegisteredViewNames()) {
            SchemaBuilder schemaBuilder = provider.getSchemaBuilder(viewName);
            editorProperty.getSchema(schemaBuilder, viewName);
        }
    }

    @Override
    public void defineProperties(final PropertyBuilder propBuilder) {
        propBuilder.describe(PROP_WSDL_URL, LBL_WSDL_URL, DESC_URL_FOR_THE_WSDL)
                .required()
                .fileBrowsing()
                .add();
        propBuilder.describe(PROP_SERVICE, LBL_SERVICE_NAME, DESC_SERVICE_NAME)
                .withSuggestions(new ServiceSuggestions(introspectionService, null,
                        PROP_SERVICE))
                .required()
                .add();
        propBuilder.describe(PROP_ENDPOINT, LBL_ENDPOINT, DESC_ENDPOINT)
                .withSuggestions(new EndpointSuggestions(introspectionService, null,
                        PROP_ENDPOINT))
                .required()
                .add();
        propBuilder.describe(PROP_OPERATION, LBL_OPERATION, DESC_OPERATION)
                .withSuggestions(new OperationSuggestions(introspectionService, null,
                        PROP_OPERATION))
                .required()
                .add();
        propBuilder.describe(PROP_TIMEOUT, LBL_TIMEOUT, DESC_TIMEOUT)
                .type(SnapType.INTEGER)
                .withMinValue(0)
                .defaultValue(0)
                .required()
                .add();
        propBuilder.describe(PROP_ENCODE_ATTRIBUTE, LBL_ENCODE_ATTRIBUTE, DESC_ENCODE_ATTRIBUTE)
                .type(SnapType.BOOLEAN)
                .defaultValue(Boolean.FALSE)
                .add();
        propBuilder.describe(PROP_DEFAULT_VALUE, LBL_DEFAULT_VALUE, DESC_DEFAULT_VALUE)
                .add();
        propBuilder.describe(PROP_USE_DEFAULT_VALUE, LBL_USE_DEFAULT_VALUE, DESC_USE_DEFAULT_VALUE)
                .type(SnapType.BOOLEAN)
                .defaultValue(Boolean.FALSE)
                .add();
        propBuilder.describe(TRUST_ALL_CERTS_PROP, TRUST_ALL_CERTS_LABEL,
                TRUST_ALL_CERTS_DESCRIPTION)
                .type(SnapType.BOOLEAN)
                .defaultValue(false)
                .add();
//        propBuilder.describe(PROP_KEYSTORE_URL, LBL_KEYSTORE_URL, DESC_URL_FOR_THE_KEYSTORE)
//        		.required()
//        		.fileBrowsing()
//        		.add();
//        propBuilder.describe(PROP_KEYSTORE_PASS, LBL_KEYSTORE_PASS, DESC_PASS_FOR_THE_KEYSTORE)
//				.required()
//				.obfuscate()
//				.add();
        Suggestions templateSuggestions = new TemplateSuggestionsExecuteImpl(soapUtils,
                invocationService, null);
        tablePropertyFactory.addEditorProperty(propBuilder, templateSuggestions,
                SnapProperty.EditorType.XML, CUSTOMIZE_ENVELOPE, CUSTOMIZE_ENVELOPE_DESC);

        PropertiesTemplate.HTTP_HEADERS_PROPERTY.defineUsing(propBuilder).add();
    }

    @Override
    public void defineMetrics(final MetricsBuilder builder) {
        documentCounter = builder.describe(SOAP_DOC_PROCESSED, SOAP_RESPONSES_WRITTEN_DESCRIPTION)
                .measuredIn(DOCUMENTS)
                .counter();
    }

    @Override
    public void configure(final PropertyValues propertyValues) throws ConfigurationException {
        wsdlUrl = propertyValues.get(PROP_WSDL_URL);
        serviceName = propertyValues.get(PROP_SERVICE);
//        keystoreUrl = propertyValues.get(PROP_KEYSTORE_URL);
//        keystorePass = propertyValues.get(PROP_KEYSTORE_PASS);
        portName = propertyValues.get(PROP_ENDPOINT);
        operation = propertyValues.get(PROP_OPERATION);
        boolean shouldEncodeAttr = Boolean.TRUE.equals(propertyValues.get(PROP_ENCODE_ATTRIBUTE));
        String defaultValueForSubstitution = propertyValues.get(PROP_DEFAULT_VALUE);
        useDefaultValueChecked = Boolean.TRUE.equals(propertyValues.get(PROP_USE_DEFAULT_VALUE));
        if (useDefaultValueChecked) {
            if (defaultValueForSubstitution == null) {
                defaultValueForSubstitution = StringUtils.EMPTY;
            }
        }
        Integer timeout = ((BigInteger) propertyValues.get(PROP_TIMEOUT)).intValue();
        trustAllCerts = Boolean.TRUE.equals(propertyValues.get(TRUST_ALL_CERTS_PROP));
        QName serviceQName = QName.valueOf(serviceName);
        QName portQName = QName.valueOf(portName);
        QName operationQName = QName.valueOf(operation);
        final List<Map<String, Object>> httpHeader = propertyValues.get(PropertiesTemplate
                .HTTP_HEADER_PROP);
        if (httpHeader != null) {
            for (Map<String, Object> item : httpHeader) {
                ExpressionProperty keyProp = propertyValues.getExpressionPropertyFor(item,
                        PropertiesTemplate.HEADER_KEY_PROP);
                ExpressionProperty valueProp = propertyValues.getExpressionPropertyFor(item,
                        PropertiesTemplate.HEADER_VALUE_PROP);
                httpHeaders.add(Pair.of(keyProp, valueProp));
            }
        }
        // TODO - MK: this is a problem, the headers can be expressions,
        // here we set it up during configure so that the wsdl parsing can access them. That
        // won't work if the header is created dynamically using input variables.
        List<Header> headerList = soapUtils.buildHeaderList(httpHeaders);
        HttpContextProvider httpContextProvider = new SoapHttpContextProvider(headerList,
                null, trustAllCerts);
        clientBuilder = invocationService.createClientBuilderFor(wsdlUrl, serviceQName, portQName,
                operationQName, httpContextProvider);
        configureDispatch(clientBuilder.getDispatchClient(), clientBuilder.getSoapAction());
        ((SOAPExecuteTemplateEvaluatorImpl) templateEvaluator)
                .withDefaultValue(useDefaultValueChecked ? defaultValueForSubstitution :
                        XmlUtilsImpl.NO_DATA);
        if (shouldEncodeAttr) {
            ((SOAPExecuteTemplateEvaluatorImpl) templateEvaluator).attrEncoded();
        }
        EditorSuggestionProvider editorSuggestionProvider = soapUtils.initializeSuggestionProvider(
                wsdlUrl, serviceQName, portQName, operationQName, shouldEncodeAttr,
                clientBuilder, httpContextProvider);
        editorProperty = propertyValues.getEditorProperty(soapEditorContentProvider,
                templateEvaluator, editorSuggestionProvider);

        bus = BusFactory.getDefaultBus(true);
        List<Interceptor<? extends Message>> inInterceptors = bus.getInInterceptors();
        soapUtils.resetInterceptors(inInterceptors, ResponseInterceptor.class);
        inInterceptors.add(responseInterceptor);
//        account.withClientBuilder(clientBuilder)
//                .withTimeout(timeout)
//                .connect();
    }

    @Override
    @SuppressWarnings("ToArrayCallWithZeroLengthArrayArgument")
    protected void process(final Document document, final String inputViewName) {
        String envelope = null;
        String strippedEnvelope = null;
        Object documentData = document != null ? document.get() : null;
        List<Header> headerList = soapUtils.buildHeaderList(document, documentData,
                httpHeaders);
        Header[] headers = headerList.toArray(new Header[0]);
        // remove all previous created ones, its a static registry
        List<Interceptor<? extends Message>> outInterceptors = bus.getOutInterceptors();
        soapUtils.resetInterceptors(outInterceptors, SoapMessageSenderInterceptor.class);

        SoapMessageSenderInterceptor soapMessageSenderInterceptor = new
                SoapMessageSenderInterceptor(headers, trustAllCerts,null,null);
        outInterceptors.add(soapMessageSenderInterceptor);

        try {
            envelope = editorProperty.eval(document);
            if (!useDefaultValueChecked) {
                strippedEnvelope = xmlUtils.stripElementsAndAttributesExceptSOAPHeader(envelope,
                        documentBuilderFactory);
                envelope = strippedEnvelope;
            }


            LOGGER.info(DISPATCHING_SOAP_REQUEST, envelope);
            SOAPMessage soapResponse = invocationService.call(clientBuilder, envelope);
            if (soapResponse == null) {
                return;
            }
            Object data = invocationService.serialize(soapResponse);
            if (data != null) {
                outputViews.write(documentUtility.newDocument(data), document);
                documentCounter.inc();
            }
        } catch (XMLStreamException e) {
            throw new ExecutionException(e, XML_SERIALIZATION_FAILED)
                    .withResolutionAsDefect();
        } catch (Exception e) {
            Throwable rootCause = Throwables.getRootCause(e);
            String reason = rootCause.getMessage();
            Map<String, Object> errorMap = Maps.newHashMap();
            errorMap.put(KEY_ERROR, reason);
            errorMap.put(STACKTRACE, Throwables.getStackTraceAsString(e.getCause() != null ?
                    e.getCause() : e));
            SnapDataException dataException = new SnapDataException(documentUtility
                    .newDocument(errorMap), e, EXCEPTION_OCCURRED)
                    .withReason(reason)
                    .withResolution(SOAP_EXCEPTION_RESOLUTION);
            // serialize headers
            Map<String, Object> soapHeaders = Maps.newHashMap();

            Dispatch<SOAPMessage> dispatchClient = clientBuilder.getDispatchClient();
            Map<?, Object> requestContext = dispatchClient.getRequestContext();
            if (requestContext != null) {
                soapUtils.serializeHeader(requestContext, soapHeaders);
            }

            Message message = soapMessageSenderInterceptor.getMessage();
            if (message != null) {
                // protocol headers are the ones passed into the http/s request.
                soapUtils.serializeHeader((Map<?, Object>) message.get(Message.PROTOCOL_HEADERS),
                        soapHeaders);
            }

            for (Header header : headerList) {
                String name = header.getName();
                if (name != null) {
                    soapHeaders.put(name, header.getValue());
                }
            }
            errorMap.put(REQUEST_HEADERS, soapHeaders);
            // serialize the envelope
            if (envelope != null) {
                errorMap.put(KEY_ENVELOPE, parseXML2JSON(envelope, xmlUtils));
            }
            if (strippedEnvelope != null) {
                errorMap.put(KEY_STRIPPED_ENVELOPE, parseXML2JSON(strippedEnvelope, xmlUtils));
            }
            errorViews.write(dataException, document);
        }
    }

    @Override
    public void cleanup() throws ExecutionException {
    }

    private void configureDispatch(Dispatch<? extends SOAPMessage> dispatch, String action) {
        if (action == null) {
            return;
        }
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_USE_PROPERTY, true);
        // Some services such as MS, do require the action to be set on the request context.
        dispatch.getRequestContext().put(BindingProvider.SOAPACTION_URI_PROPERTY, action);
    }

    private Object parseXML2JSON(String envelope, XmlUtils xmlUtils) {
        Source source = new StreamSource(new StringReader(envelope));
        try {
            return xmlUtils.convertToJson(xmlInputFactory, jsonOutputFactory, source);
        } catch (XMLStreamException e) {
            throw new ExecutionException(e, ERROR_PARSING_XML)
                    .withReason(e.getMessage())
                    .withResolution(VERIFY_THE_RETURNED_XML_IS_PARSEABLE);
        }
    }
}
