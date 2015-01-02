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

import com.google.inject.Inject;
import com.snaplogic.api.ConfigurationException;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.common.services.ws.ClientBuilder;
import com.snaplogic.common.services.ws.IntrospectionService;
import com.snaplogic.snap.api.EditorSuggestionProvider;
import com.snaplogic.snap.api.ExpressionProperty;
import com.snaplogic.snap.api.soap.HttpContextProvider;
import com.snaplogic.snap.api.soap.SoapEditorSuggestionsProviderImpl;
import com.snaplogic.snap.api.soap.node.visit.SOAPMessageTemplatizer;
import com.snaplogic.snap.api.soap.node.visit.SOAPMessageTemplatizerImpl;
import com.snaplogic.snap.api.soap.node.visit.TemplatizerConstants;
import com.snaplogic.snap.api.soap.util.InputFactory;
import com.snaplogic.snap.api.util.DOMUtil;
import com.snaplogic.snap.api.xsd.Types;
import com.snaplogic.snap.api.xsd.TypesFactory;
import com.snaplogic.snap.api.xsd.schema.SchemaConstants;
import com.snaplogic.snap.api.xsd.schema.SchemaVisitor;
import com.snaplogic.snap.api.xsd.schema.SchemaVisitorImpl;
import com.snaplogic.util.HttpHeaderUtils;

import org.apache.commons.lang3.tuple.Pair;
import org.apache.cxf.interceptor.Interceptor;
import org.apache.cxf.message.Message;
import org.apache.http.Header;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.Part;
import org.w3c.dom.Document;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;
import javax.inject.Singleton;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;

import static com.snaplogic.snaps.checkfree.Messages.ERROR_WHILE_READING_OPERATION_SCHEMA;
import static com.snaplogic.snaps.checkfree.Messages.OPERATION_BINDING_NOT_SUPPORTED;
import static com.snaplogic.snaps.checkfree.Messages.PLEASE_SPECIFY_REQUEST_SCHEMA;
import static com.snaplogic.snaps.checkfree.Messages.REQUEST_SCHEMA_NOT_AVAILABLE;
import static com.snaplogic.snaps.checkfree.Messages.WSDL_VERSION_NOT_SUPPORTED;
import static com.snaplogic.snaps.checkfree.Messages.WSDL_VERSION_NOT_SUPPORTED_REASON;
import static com.snaplogic.snaps.checkfree.Messages.WSDL_VERSION_NOT_SUPPORTED_RES;

/**
 * Utility class for SOAP snaps
 *
 * @author svatada
 */
@Singleton
public class SoapUtils {
    private static final String BINDING = "binding";
    private static final String SOAP_11_NS = "http://schemas.xmlsoap.org/wsdl/soap/";
    private static final String SOAP_12_NS = "http://schemas.xmlsoap.org/wsdl/soap12/";
    private static final String CXF_SCOPES = "org.apache.cxf.jaxws.context" +
            ".WrappedMessageContext.CXF_SCOPES";

    @Inject
    private EditorSuggestionProvider editorSuggestionProvider;
    @Inject
    private IntrospectionService introspectionService;
    @Inject
    private DOMUtil domUtil;
    @Inject
    private TypesFactory typesFactory;
    @Inject
    private InputFactory inputFactory;

    /**
     * Initializes the suggestion provider. Will create the templatizer,
     * extract the operation element and set the http context provider to be able to suggest the
     * template later on.
     *
     * @param wsdlUrl             the WSDL url
     * @param serviceQName        the service QName
     * @param endpointQName       the endpoint QName
     * @param operationQName      the operation QName
     * @param shouldEncodeAttr    the flag to encode the attributes
     * @param clientBuilder       the client builder
     * @param httpContextProvider the auth contedxt
     *
     * @return the editor suggestion provider
     */
    public EditorSuggestionProvider initializeSuggestionProvider(String wsdlUrl,
            QName serviceQName, QName endpointQName, QName operationQName,
            Boolean shouldEncodeAttr, ClientBuilder clientBuilder,
            HttpContextProvider httpContextProvider) {
        URL wsdlURL = introspectionService.urlObjectFor(wsdlUrl);
        Description description = introspectionService.parseWsdl(wsdlURL, httpContextProvider);
        org.ow2.easywsdl.wsdl.api.Binding operationBinding = description.getService(serviceQName)
                .getEndpoint(endpointQName.getLocalPart())
                .getBinding();
        Operation operation = operationBinding
                .getBindingOperation(operationQName.getLocalPart())
                .getOperation();
        Input input = operation.getInput();
        List<SOAPMessageTemplatizer.Input> inputs;
        switch (description.getVersion()) {
            case WSDL11:
                List<Part> parts = input.getParts();
                if (parts == null || parts.isEmpty()) {
                    throw new ConfigurationException(ERROR_WHILE_READING_OPERATION_SCHEMA)
                            .withReason(REQUEST_SCHEMA_NOT_AVAILABLE)
                            .withResolution(PLEASE_SPECIFY_REQUEST_SCHEMA);
                }
                inputs = inputFactory.createTemplatizerInputListFrom(operationQName, parts);
                break;
            case WSDL20:
                // NOTE: The wsdl 2.0 grammar says that there can be 0 or more inputs in an
                // operation but there doesn't seem to be any methods which can return all of them
                inputs = new ArrayList<>(1);
                inputs.add(inputFactory.createTemplatizerInputFrom(operationQName,
                        input.getElement()));
                break;
            default:
                throw new ConfigurationException(WSDL_VERSION_NOT_SUPPORTED)
                        .withReason(WSDL_VERSION_NOT_SUPPORTED_REASON)
                        .withResolution(WSDL_VERSION_NOT_SUPPORTED_RES);
        }


        /*Part part = parts.get(0);
        Element element = part.getElement(); // XXX: Hack - don't know what to do with other parts
        QName elemQName;
        QName typeQName;
        if (element == null) {
            // no element means the part does not define the XSD schema as an element with a
            // proper qname, instead we attempt to get the type
            Type type = part.getType();
            if (type != null) {
                elemQName = type.getQName();
            } else {
                // no type means some crappy defined WSDL, we attempt to get the type through the
                // part qname
                elemQName = part.getPartQName();
            }
            typeQName = elemQName;
        } else {
            // well defined WSDL, we have an element and a type of the element
            elemQName = element.getQName();
            typeQName = element.getType().getQName();
            if (typeQName == null) {
                typeQName = element.getQName();
            }
        }
        ((SoapEditorSuggestionsProviderImpl) editorSuggestionProvider)
                .withClientBuilder(clientBuilder)
                .withElementTypeQName(typeQName)
                .withTemplatizer(templatizer)
                .withHttpContextProvider(httpContextProvider);*/

        SOAPMessageTemplatizer.SoapProtocol soapProtocol = null;
        try {
            switch (operationBinding.getTypeOfBinding()) {
                case SOAP_BINDING4WSDL20:
                case SOAP11_BINDING4WSDL11:
                    soapProtocol = SOAPMessageTemplatizer.SoapProtocol.SOAP_11;
                    break;
                case SOAP12_BINDING4WSDL11:
                    soapProtocol = SOAPMessageTemplatizer.SoapProtocol.SOAP_12;
                    break;
                default:
                    throw new ExecutionException(OPERATION_BINDING_NOT_SUPPORTED)
                            .formatWith(operationBinding.getTypeOfBinding());
            }
        } catch (ClassCastException e) {
            // XXX: Hack
            switch (description.getVersion()) {
                case WSDL11:
                    org.ow2.easywsdl.wsdl.impl.wsdl11.BindingImpl bindingImpl11 = (org.ow2
                            .easywsdl.wsdl.impl.wsdl11.BindingImpl) operationBinding;
                    List<Object> anyList = bindingImpl11.getModel().getAny();
                    for (Object any : anyList) {
                        if (any instanceof JAXBElement && (BINDING.equals(((JAXBElement) any)
                                .getName().getLocalPart())) && (SOAP_11_NS.equals(
                                ((JAXBElement) any).getName().getNamespaceURI()))) {
                            soapProtocol = SOAPMessageTemplatizer.SoapProtocol.SOAP_11;
                            break;
                        } else if (any instanceof JAXBElement && (BINDING.equals(((JAXBElement) any)
                                .getName().getLocalPart())) && (SOAP_12_NS.equals((
                                (JAXBElement) any).getName().getNamespaceURI()))) {
                            soapProtocol = SOAPMessageTemplatizer.SoapProtocol.SOAP_12;
                            break;
                        }
                    }
                    break;
                case WSDL20:
                    org.ow2.easywsdl.wsdl.impl.wsdl20.BindingImpl binding20 = (org.ow2.easywsdl
                            .wsdl.impl.wsdl20.BindingImpl) operationBinding;
                    anyList = binding20.getModel().getOperationOrFaultOrAny();
                    for (Object any : anyList) {
                        if (any instanceof JAXBElement && (BINDING.equals(((JAXBElement) any)
                                .getName().getLocalPart())) && (SOAP_11_NS.equals(
                                ((JAXBElement) any).getName().getLocalPart()))) {
                            soapProtocol = SOAPMessageTemplatizer.SoapProtocol.SOAP_11;
                            break;
                        } else if (any instanceof JAXBElement && (BINDING.equals(((JAXBElement) any)
                                .getName().getLocalPart())) && (SOAP_12_NS.equals((
                                (JAXBElement) any).getName().getLocalPart()))) {
                            soapProtocol = SOAPMessageTemplatizer.SoapProtocol.SOAP_12;
                            break;
                        }
                    }
                    break;
                default:
                    throw new IllegalArgumentException();
            }
        }
        Document wsdlDocument = domUtil.getResourceAsDocument(wsdlURL, httpContextProvider);
        Types types = typesFactory.generateTypes(wsdlDocument);
        SchemaVisitor schemaVisitor = new SchemaVisitorImpl();
        schemaVisitor.setProperty(SchemaConstants.ENCODE_ATTRIBUTE, shouldEncodeAttr);
        SOAPMessageTemplatizerImpl templatizer = new SOAPMessageTemplatizerImpl(types,
                schemaVisitor);
        templatizer.setProperty(TemplatizerConstants.ENCODE_ATTRIBUTE, shouldEncodeAttr);
        ((SoapEditorSuggestionsProviderImpl) editorSuggestionProvider)
                .withClientBuilder(clientBuilder)
                .withInputs(inputs)
                .withSoapProtocol(soapProtocol == null ? SOAPMessageTemplatizer.SoapProtocol
                        .SOAP_11 : soapProtocol)
                .shouldEncodeAttr(shouldEncodeAttr)
                .withTemplatizer(templatizer)
                .withHttpContextProvider(httpContextProvider);
        return editorSuggestionProvider;
    }

    /**
     * Creates the header list.
     *
     * @param httpHeaders the http headers property
     *
     * @return the list of http  headers
     */
    protected List<Header> buildHeaderList(List<Pair<ExpressionProperty,
            ExpressionProperty>> httpHeaders) {
        return buildHeaderList(null, null, httpHeaders);
    }

    /**
     * Creates the header list.
     *
     * @param document     the document if any
     * @param documentData the data of the document if any
     * @param httpHeaders  the http headers property
     *
     * @return the list of http  headers
     */
    protected List<Header> buildHeaderList(@Nullable com.snaplogic.snap.api.Document document,
            @Nullable Object documentData, List<Pair<ExpressionProperty,
            ExpressionProperty>> httpHeaders) {
        return HttpHeaderUtils.buildHeaders(httpHeaders, document, documentData, null);
    }

    /**
     * Ensure we can serialize headers during preview
     *
     * @param headers           the headers to be serialized
     * @param serializedHeaders the serialized headers which can be written as part of the error
     *                          document
     */
    @SuppressWarnings("unchecked")
    public void serializeHeader(Map<?, Object> headers, Map<String,
            Object> serializedHeaders) {
        for (Map.Entry<?, Object> items : headers.entrySet()) {
            String name = String.valueOf(items.getKey());
            // scope headers are not good for consumption,
            // some internal crap being stored for cxf.
            if (name != null && !name.equalsIgnoreCase(CXF_SCOPES)) {
                Object value = items.getValue();
                if (value instanceof Map) {
                    serializeHeader((Map<?, Object>) value, serializedHeaders);
                } else if (value instanceof Collection && !((Collection) value).isEmpty()) {
                    // lets take the first entry to keep it simple,
                    // all headers encountered really only have one value in the list.
                    value = ((Collection) value).iterator().next();
                    serializedHeaders.put(name, String.valueOf(value));
                } else {
                    serializedHeaders.put(name, String.valueOf(value));
                }
            }
        }
    }

    /**
     * Resets the interceptors for the given class on the bus. The bus is static and would
     * accumulate the interceptors for the given class everytime they are added.
     *
     * @param interceptors the list of interceptors, either in or out.
     * @param klass        the class of interceptor being reset
     */
    public void resetInterceptors(List<Interceptor<? extends Message>> interceptors,
            Class klass) {
        int i = 0;
        for (Interceptor<? extends Message> interceptor : interceptors) {
            if (interceptor.getClass().equals(klass)) {
                interceptors.remove(i);
            }
            i++;
        }
    }
}