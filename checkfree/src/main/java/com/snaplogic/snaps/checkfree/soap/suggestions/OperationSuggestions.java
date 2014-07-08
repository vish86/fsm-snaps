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

import com.snaplogic.common.properties.builders.SuggestionBuilder;
import com.snaplogic.common.services.ws.IntrospectionService;

import org.apache.http.Header;

import java.util.HashSet;
import java.util.Set;

import javax.xml.namespace.QName;

import static com.snaplogic.snaps.checkfree.Execute.PROP_OPERATION;
import static com.snaplogic.snaps.checkfree.soap.suggestions.Messages.SERVICE_NAME_AND_OR_ENDPOINT_MUST_BE_DEFINED_FIRST;

/**
 * OperationSuggestions is the suggestions implementation that provides operations suggestion for
 * the selected binding.
 *
 * @author svatada
 * @since 2014 July
 */
public class OperationSuggestions extends AbstractSuggestions {

    public OperationSuggestions(final IntrospectionService introspectionService,
            final String nodeKey) {
        super(introspectionService, nodeKey);
    }

    @Override
    protected boolean validateSettings(Object wsdlUrlObject, Object serviceNameObject,
            Object portNameObject, SuggestionBuilder suggestionBuilder) {
        boolean isValid = serviceNameObject != null && portNameObject != null
                && serviceNameObject instanceof String && portNameObject instanceof String;
        if (!isValid) {
            suggestionBuilder.node(nodeKey).suggestions(
                    SERVICE_NAME_AND_OR_ENDPOINT_MUST_BE_DEFINED_FIRST);
        }
        return isValid;
    }

    @Override
    protected void getSuggestion(SuggestionBuilder suggestionBuilder, String wsdlUrl,
            String serviceName, String portName, Header[] headers) {
        QName[] operationQNames = introspectionService.getOperationsFrom(wsdlUrl,
                QName.valueOf(serviceName), QName.valueOf(portName),
                getHttpContextProvider(headers));
        Set<String> operations = new HashSet<>();
        for (QName bindingOperation : operationQNames) {
            operations.add(bindingOperation.toString());
        }
        suggestionBuilder.node(PROP_OPERATION).suggestions(operations.toArray(new String[0]));
    }
}