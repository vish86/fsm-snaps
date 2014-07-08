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

package com.snaplogic.snaps.checkfree.soap.suggestions;

import com.snaplogic.common.properties.Suggestions;
import com.snaplogic.common.properties.builders.SuggestionBuilder;
import com.snaplogic.common.services.ws.IntrospectionService;

import org.apache.http.Header;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import static com.snaplogic.snaps.checkfree.Execute.PROP_SERVICE;

/**
 * ServiceSuggestions is the implementation of {@link Suggestions} that uses the wsdl url to list
 * all the services available.
 *
 * @author svatada
 * @since 2014
 */
public class ServiceSuggestions extends AbstractSuggestions {

    public ServiceSuggestions(final IntrospectionService introspectionService,             final String nodeKey) {
        super(introspectionService,  nodeKey);
    }

    @Override
    protected boolean validateSettings(Object wsdlUrlObject, Object serviceNameObject,
            Object portNameObject, SuggestionBuilder suggestionBuilder) {
        return true;
    }

    @Override
    protected void getSuggestion(SuggestionBuilder suggestionBuilder, String wsdlUrl,
            String serviceName, String portName, Header[] headers) {
        QName[] services = introspectionService.getServicesFrom(wsdlUrl, null);
        if (services != null) {
            Set<String> serviceNames = new HashSet<>();
            for (QName serviceQName : services) {
                serviceNames.add(serviceQName.toString());
            }
            suggestionBuilder.node(PROP_SERVICE).suggestions(serviceNames.toArray(new String[0]));
        }
    }
}