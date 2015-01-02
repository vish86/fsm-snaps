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

package com.snaplogic.snaps.soap.suggestions;

import com.snaplogic.common.properties.builders.SuggestionBuilder;
import com.snaplogic.common.services.ws.IntrospectionService;
import com.snaplogic.snap.api.soap.SoapAccount;

import org.apache.http.Header;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import static com.snaplogic.snaps.checkfree.CheckfreeExecute.PROP_ENDPOINT;

import static com.snaplogic.snaps.soap.suggestions.Messages.SERVICE_NAME_MUST_BE_DEFINED_FIRST;

/**
 * EndpointSuggestions is the suggestions implementation that provides Endpoint suggestion
 * for the selected service name.
 *
 * @author svatada
 */
public class EndpointSuggestions extends AbstractSuggestions {

    public EndpointSuggestions(IntrospectionService introspectionService,
            SoapAccount<?> account, String nodeKey) {
        super(introspectionService, account, nodeKey);
    }

    //--------------------------------- Implemented methods ------------------------------------//

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
                QName.valueOf(serviceName), getHttpContextProvider(headers, isTrustAll));
        for (QName endpointQName : endpointQNames) {
            endpoints.add(endpointQName.toString());
        }
        suggestionBuilder.node(PROP_ENDPOINT).suggestions(endpoints.toArray(new String[0]));
    }
}
