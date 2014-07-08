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
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.soap.HttpContextProvider;

import org.apache.http.Header;

import static com.snaplogic.snaps.checkfree.Execute.PROP_ENDPOINT;
import static com.snaplogic.snaps.checkfree.Execute.PROP_SERVICE;
import static com.snaplogic.snaps.checkfree.Execute.PROP_WSDL_URL;
import static com.snaplogic.snaps.checkfree.soap.suggestions.Messages.WSDL_MUST_BE_DEFINED_FIRST;

/**
 * AbstractSuggestions is the suggestions implementation that is the base class for all the other
 * suggestion classes.
 *
 * @author svatada
 * @since 2014 July
 */
public abstract class AbstractSuggestions implements Suggestions {
    protected final IntrospectionService introspectionService;
    protected final String nodeKey;

    public AbstractSuggestions(final IntrospectionService introspectionService, final String nodeKey) {
        this.introspectionService = introspectionService;
        this.nodeKey = nodeKey;
    }

    @Override
    public void suggest(SuggestionBuilder suggestionBuilder, PropertyValues propertyValues) {
        Object wsdlUrlObject = propertyValues.get(PROP_WSDL_URL);
        Object serviceNameObject = propertyValues.get(PROP_SERVICE);
        Object portNameObject = propertyValues.get(PROP_ENDPOINT);

        if (wsdlUrlObject != null && wsdlUrlObject instanceof String) {
            if (validateSettings(wsdlUrlObject, serviceNameObject, portNameObject,
                    suggestionBuilder)) {
                String wsdlUrl = (String) wsdlUrlObject;
                String serviceName = (String) serviceNameObject;
                String portName = (String) portNameObject;
                getSuggestion(suggestionBuilder, wsdlUrl, serviceName, portName, null);
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
        return null;
    }
}
