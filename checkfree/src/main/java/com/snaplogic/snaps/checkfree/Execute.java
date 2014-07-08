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
import com.snaplogic.snap.api.soap.InvocationServiceImpl;
import com.snaplogic.snap.api.soap.ResponseInterceptor;
import com.snaplogic.snap.api.soap.SOAPTemplateGenerator;
import com.snaplogic.snap.api.soap.SoapEditorSuggestionsProviderImpl;
import com.snaplogic.snap.api.soap.WsdlIntrospectionService;
import com.snaplogic.snap.api.xml.XmlUtils;
import com.snaplogic.snap.api.xml.XmlUtilsImpl;
import com.snaplogic.snap.schema.api.SchemaBuilder;
import com.snaplogic.snap.schema.api.SchemaProvider;
import com.snaplogic.snaps.checkfree.soap.suggestions.EndpointSuggestions;
import com.snaplogic.snaps.checkfree.soap.suggestions.OperationSuggestions;
import com.snaplogic.snaps.checkfree.soap.suggestions.ServiceSuggestions;
import com.snaplogic.snaps.checkfree.soap.suggestions.TemplateSuggestionsExecuteImpl;

import org.apache.cxf.Bus;
import org.apache.cxf.BusFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.StringReader;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.xml.namespace.QName;
import javax.xml.parsers.DocumentBuilderFactory;
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

import static com.snaplogic.snaps.checkfree.Messages.*;

/**
 * Snap that makes CheckFree calls and writes out response in JSON format.
 *
 * @author svatada
 * @since 2014 July
 */
@Version(snap = 1)
@General(title = Messages.SOAP_EXECUTE_LABEL, purpose = Messages.SOAP_REQUEST_DESC)
@Inputs(min = 0, max = 1, accepts = { ViewType.DOCUMENT })
@Outputs(min = 0, max = 1, offers = { ViewType.DOCUMENT })
@Errors(min = 1, max = 1, offers = { ViewType.DOCUMENT })
@Category(snap = SnapCategory.WRITE)
public class Execute extends SimpleSnap implements MetricsProvider, DependencyManager,
        InputSchemaProvider, ViewProvider {
    private static final Logger LOGGER = LoggerFactory.getLogger(Execute.class);
    public static final String PROP_WSDL_URL = "wsdl_url";
    public static final String PROP_SERVICE = "serviceName";
    public static final String PROP_ENDPOINT = "portName";
    public static final String PROP_OPERATION = "operation";
    private static final String PROP_TIMEOUT = "timeout";
    private static final String PROP_DEFAULT_VALUE = "defaultValue";
    private static final String PROP_USE_DEFAULT_VALUE = "useDefaultValue";
    private final static String DEFAULT_INPUT_VIEW_0 = "input0";
    private final static String DEFAULT_OUTPUT_VIEW_0 = "output0";
    private static final String KEY_ERROR = "error";
    private static final String KEY_ENVELOPE = "envelope";
    private static final String KEY_STRIPPED_ENVELOPE = "stripped_envelope";
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

    private Integer timeout;
    private EditorProperty editorProperty;
    private Counter documentCounter;
    private ClientBuilder clientBuilder;
    private boolean useDefaultValueChecked;

    @Override
    public void defineViews(final ViewBuilder viewBuilder) {
        viewBuilder.describe(DEFAULT_INPUT_VIEW_0).type(ViewType.DOCUMENT).add(ViewCategory.INPUT);
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
        propBuilder.describe(PROP_TIMEOUT, LBL_TIMEOUT, DESC_TIMEOUT)
                .type(SnapType.INTEGER)
                .withMinValue(0)
                .defaultValue(30)
                .required()
                .add();
        Suggestions templateSuggestions = new TemplateSuggestionsExecuteImpl(soapUtils,
                invocationService);
        tablePropertyFactory.addEditorProperty(propBuilder, templateSuggestions,
                SnapProperty.EditorType.XML, CUSTOMIZE_ENVELOPE, CUSTOMIZE_ENVELOPE_DESC);
    }

    @Override
    public void defineMetrics(final MetricsBuilder builder) {
        documentCounter = builder.describe(SOAP_DOC_PROCESSED, XML_WRITTEN_DESCRIPTION)
                .measuredIn(DOCUMENTS)
                .counter();
    }

    @Override
    public void configure(final PropertyValues propertyValues) throws ConfigurationException {
        String wsdlUrl = propertyValues.get(PROP_WSDL_URL);
        String serviceName = propertyValues.get(PROP_SERVICE);
        String portName = propertyValues.get(PROP_ENDPOINT);
        String operation = propertyValues.get(PROP_OPERATION);

        timeout = ((BigInteger) propertyValues.get(PROP_TIMEOUT)).intValue();
        QName serviceQName = QName.valueOf(serviceName);
        QName portQName = QName.valueOf(portName);
        QName operationQName = QName.valueOf(operation);
        clientBuilder = invocationService.createClientBuilderFor(wsdlUrl, serviceQName, portQName,
                operationQName, null);
        configureDispatch(clientBuilder.getDispatchClient(), clientBuilder.getSoapAction());
        editorProperty = propertyValues.getEditorProperty(
                soapEditorContentProvider,
                ((SOAPExecuteTemplateEvaluatorImpl) templateEvaluator).withDefaultValue(XmlUtilsImpl.NO_DATA),
                soapUtils.initializeSuggestionProvider(wsdlUrl, serviceQName, portQName,
                        operationQName, clientBuilder, null));

        Bus bus = BusFactory.getDefaultBus(true);
        bus.getInInterceptors().add(new ResponseInterceptor());
    }

    @Override
    protected void process(final Document document, final String inputViewName) {
        String envelope = null;
        String strippedEnvelope = null;
        try {
            envelope = editorProperty.eval(document);
            if (!useDefaultValueChecked) {
                strippedEnvelope = xmlUtils.stripNodeAndAttributeNodes(envelope,
                        documentBuilderFactory);
                envelope = strippedEnvelope;
            }
            Object documentData = document != null ? document.get() : null;
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
            throw new ExecutionException(e, XML_SERIALIZATION_FAILED).withResolutionAsDefect();
        } catch (Exception e) {
            Throwable rootCause = Throwables.getRootCause(e);
            String reason = rootCause.getMessage();
            Map<String, Object> errorMap = Maps.newHashMap();
            errorMap.put(KEY_ERROR, reason);
            SnapDataException dataException = new SnapDataException(
                    documentUtility.newDocument(errorMap), EXCEPTION_OCCURRED).withReason(reason)
                    .withResolution(SOAP_EXCEPTION_RESOLUTION);
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
        // do nothing
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
            throw new ExecutionException(e, ERROR_PARSING_XML).withReason(e.getMessage())
                    .withResolution(VERIFY_THE_RETURNED_XML_IS_PARSEABLE);
        }
    }

    private QName getServiceName(String wsdlUrl, String serviceName) {
        QName[] services = introspectionService.getServicesFrom(wsdlUrl, null);
        if (services != null) {
            Set<String> serviceNames = new HashSet<>();
            for (QName serviceQName : services) {
                if (serviceName.equals(serviceQName.getLocalPart())) {
                    return serviceQName;
                } else {
                    continue;
                }
            }
        }
        return null;
    }
}
