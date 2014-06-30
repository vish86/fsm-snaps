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
package com.snaplogic.snaps.firstdata.soap.suggestions;

import com.google.inject.Inject;
import com.snaplogic.api.ConfigurationException;
import com.snaplogic.common.services.ws.ClientBuilder;
import com.snaplogic.common.services.ws.IntrospectionService;
import com.snaplogic.snap.api.EditorSuggestionProvider;
import com.snaplogic.snap.api.soap.HttpContextProvider;
import com.snaplogic.snap.api.soap.SOAPTemplateGenerator;
import com.snaplogic.snap.api.soap.SoapEditorSuggestionsProviderImpl;

import org.ow2.easywsdl.schema.api.Element;
import org.ow2.easywsdl.schema.api.Type;
import org.ow2.easywsdl.wsdl.api.Description;
import org.ow2.easywsdl.wsdl.api.Input;
import org.ow2.easywsdl.wsdl.api.Operation;
import org.ow2.easywsdl.wsdl.api.Part;

import java.net.URL;
import java.util.List;

import javax.inject.Singleton;
import javax.xml.namespace.QName;

import static com.snaplogic.snaps.firstdata.Messages.ERROR_WHILE_READING_OPERATION_SCHEMA;
import static com.snaplogic.snaps.firstdata.Messages.PLEASE_SPECIFY_REQUEST_SCHEMA;
import static com.snaplogic.snaps.firstdata.Messages.REQUEST_SCHEMA_NOT_AVAILABLE;
/**
 * Utility class for SOAP snaps
 *
 * @author svatada
 */
@Singleton
public class SoapUtils {

    @Inject
    private EditorSuggestionProvider editorSuggestionProvider;
    @Inject
    private SOAPTemplateGenerator defaultSOAPTemplateGenerator;
    @Inject
    private IntrospectionService introspectionService;

    /**
     * Initializes the suggestion provider. Will create the templatizer,
     * extract the operation element and set the http context provider to be able to suggest the
     * template later on.
     *
     * @param wsdlUrl             the WSDL url
     * @param serviceQName        the service QName
     * @param endpointQName       the endpoint QName
     * @param operationQName      the operation QName
     * @param clientBuilder       the client builder
     * @param httpContextProvider the auth contedxt
     *
     * @return the editor suggestion provider
     */
    public EditorSuggestionProvider initializeSuggestionProvider(String wsdlUrl,
            QName serviceQName, QName endpointQName, QName operationQName,
            ClientBuilder clientBuilder, HttpContextProvider httpContextProvider) {
        URL wsdlURL = introspectionService.urlObjectFor(wsdlUrl);
        Description description = introspectionService.parseWsdl(wsdlURL, httpContextProvider);
        Operation operation = description.getService(serviceQName)
                .getEndpoint(endpointQName.getLocalPart())
                .getBinding()
                .getBindingOperation(operationQName.getLocalPart())
                .getOperation();
        Input input = operation.getInput();
        List<Part> parts = input.getParts();
        if (parts == null || parts.isEmpty()) {
            throw new ConfigurationException(ERROR_WHILE_READING_OPERATION_SCHEMA)
                    .withReason(REQUEST_SCHEMA_NOT_AVAILABLE)
                    .withResolution(PLEASE_SPECIFY_REQUEST_SCHEMA);
        }
        Part part = parts.get(0);
        Element element = part.getElement();// XXX: Hack - don't know what to do with other parts
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
        SOAPTemplateGenerator.Templatizer templatizer = defaultSOAPTemplateGenerator
                .initializeWith(description, elemQName);
        ((SoapEditorSuggestionsProviderImpl) editorSuggestionProvider)
                .withClientBuilder(clientBuilder)
                .withElementTypeQName(typeQName)
                .withTemplatizer(templatizer)
                .withHttpContextProvider(httpContextProvider);
        return editorSuggestionProvider;
    }
}