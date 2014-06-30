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

import com.snaplogic.common.properties.Suggestions;
import com.snaplogic.common.properties.builders.SuggestionBuilder;
import com.snaplogic.common.services.ws.IntrospectionService;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.soap.HttpContextProvider;
import com.snaplogic.snap.api.soap.HttpRequestAuthProvider;

import org.apache.http.Header;

import java.util.ArrayList;
import java.util.List;

import static com.snaplogic.snaps.firstdata.Messages.WSDL_MUST_BE_DEFINED_FIRST;
import static com.snaplogic.snaps.firstdata.Transaction.PROP_ENDPOINT;
import static com.snaplogic.snaps.firstdata.Transaction.PROP_SERVICE;
import static com.snaplogic.snaps.firstdata.Transaction.PROP_WSDL_URL;

/**
 * AbstractSuggestions is the suggestions implementation that is the base class for all the other
 * suggestion classes.
 *
 * @author svatada
 */
public abstract class AbstractSuggestions implements Suggestions {
    protected final IntrospectionService introspectionService;
    protected final String nodeKey;

    @SuppressWarnings("javadoc")
    public AbstractSuggestions(final IntrospectionService introspectionService,
            final String nodeKey) {
        this.introspectionService = introspectionService;
        this.nodeKey = nodeKey;
    }

    @Override
    public void suggest(SuggestionBuilder suggestionBuilder, PropertyValues propertyValues) {
        Object wsdlUrlObject = propertyValues.get(PROP_WSDL_URL);
        Object serviceNameObject = propertyValues.get(PROP_SERVICE);
        Object portNameObject = propertyValues.get(PROP_ENDPOINT);
        List<Header> headerList = new ArrayList<>();

        if (wsdlUrlObject != null && wsdlUrlObject instanceof String) {
            if (validateSettings(wsdlUrlObject, serviceNameObject, portNameObject,
                    suggestionBuilder)) {
                String wsdlUrl = (String) wsdlUrlObject;
                String serviceName = (String) serviceNameObject;
                String portName = (String) portNameObject;
                // build Headers from configuration, default accept type to text/xml
                Header[] suggestHeaders = headerList.toArray(new Header[0]);
                getSuggestion(suggestionBuilder, wsdlUrl, serviceName, portName, suggestHeaders);
            }
        } else {
            suggestionBuilder.node(nodeKey).suggestions(WSDL_MUST_BE_DEFINED_FIRST);
        }
    }

    /**
     * Get suggestions for the given parameters.
     *
     * @param suggestionBuilder
     * @param wsdlUrl
     * @param serviceName
     * @param portName
     */
    protected abstract void getSuggestion(SuggestionBuilder suggestionBuilder, String wsdlUrl,
            String serviceName, String portName, Header[] headers);

    /**
     * Checks if the given arguments are all valid.
     *
     * @param wsdlUrlObject
     * @param serviceNameObject
     * @param portNameObject
     * @param suggestionBuilder
     *
     * @return true if the arguments are valid
     */
    protected abstract boolean validateSettings(Object wsdlUrlObject, Object serviceNameObject,
            Object portNameObject, SuggestionBuilder suggestionBuilder);

    protected HttpContextProvider getHttpContextProvider(final Header[] headers) {
        HttpContextProvider httpContextProvider = new HttpContextProvider() {
            @Override
            public Header[] getHttpHeaders() {
                return headers;
            }

            @Override
            public HttpRequestAuthProvider getHttpRequestAuthProvider() {
                    return null;
            }
        };
        return httpContextProvider;
    }
}