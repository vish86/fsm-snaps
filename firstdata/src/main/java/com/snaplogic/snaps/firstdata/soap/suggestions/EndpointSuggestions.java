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

import com.snaplogic.common.properties.builders.SuggestionBuilder;
import com.snaplogic.common.services.ws.IntrospectionService;

import org.apache.http.Header;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import static com.snaplogic.snaps.firstdata.Messages.SERVICE_NAME_MUST_BE_DEFINED_FIRST;
import static com.snaplogic.snaps.firstdata.Transaction.PROP_ENDPOINT;

/**
 * EndpointSuggestions is the suggestions implementation that provides Endpoint suggestion for the
 * selected service name.
 *
 * @author svatada
 */
public class EndpointSuggestions extends AbstractSuggestions {

    @SuppressWarnings("javadoc")
    public EndpointSuggestions(final IntrospectionService introspectionService,
            final String nodeKey) {
        super(introspectionService, nodeKey);
    }

    @Override
    protected boolean validateSettings(Object wsdlUrlObject, Object serviceNameObject,
            Object portNameObject, SuggestionBuilder suggestionBuilder) {
        boolean isValid = serviceNameObject != null && serviceNameObject instanceof String;
        if (!isValid) {
            suggestionBuilder.node(nodeKey).suggestions(SERVICE_NAME_MUST_BE_DEFINED_FIRST);
        }
        return isValid;
    }

    @Override
    protected void getSuggestion(SuggestionBuilder suggestionBuilder, String wsdlUrl,
            String serviceName, String portName, Header[] headers) {
        Set<String> endpoints = new HashSet<>();
        QName[] endpointQNames = introspectionService.getEndpointsFrom(wsdlUrl,
                QName.valueOf(serviceName), getHttpContextProvider(headers));
        for (QName endpointQName : endpointQNames) {
            endpoints.add(endpointQName.getLocalPart());
        }
        suggestionBuilder.node(PROP_ENDPOINT).suggestions(endpoints.toArray(new String[0]));
    }
}
