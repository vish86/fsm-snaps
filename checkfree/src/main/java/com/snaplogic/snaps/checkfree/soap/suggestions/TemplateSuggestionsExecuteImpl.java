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

import com.snaplogic.api.ConfigurationException;
import com.snaplogic.common.properties.Suggestions;
import com.snaplogic.common.properties.builders.SuggestionBuilder;
import com.snaplogic.common.services.ws.ClientBuilder;
import com.snaplogic.common.services.ws.InvocationService;
import com.snaplogic.snap.api.EditorSuggestionProvider;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.editor.EditorPropertyFactory;
import com.snaplogic.snaps.checkfree.Messages;
import com.snaplogic.snaps.checkfree.SoapUtils;

import javax.xml.namespace.QName;

import static com.snaplogic.snaps.checkfree.Execute.PROP_ENDPOINT;
import static com.snaplogic.snaps.checkfree.Execute.PROP_OPERATION;
import static com.snaplogic.snaps.checkfree.Execute.PROP_SERVICE;
import static com.snaplogic.snaps.checkfree.Execute.PROP_WSDL_URL;
import static com.snaplogic.snaps.checkfree.soap.suggestions.Messages.INVALID_SETTINGS;

/**
 * Provides functionality to suggest a template for a SOAP request during a suggest value call. The
 * template will provide envelope, header and operation. The template will be returned to the
 * caller. The caller can edit the template afterwards if needed or use it directly as part of the
 * request.
 *
 * @author svatada
 * @since 2014 July
 */
public class TemplateSuggestionsExecuteImpl implements Suggestions {
    private final SoapUtils soapUtils;
    private final InvocationService invocationService;

    public TemplateSuggestionsExecuteImpl(SoapUtils soapUtils, InvocationService invocationService) {
        this.soapUtils = soapUtils;
        this.invocationService = invocationService;
    }

    @Override
    public void suggest(final SuggestionBuilder suggestionBuilder,
            final PropertyValues propertyValues) throws ConfigurationException {
        String wsdlUrl = propertyValues.get(PROP_WSDL_URL);
        String serviceName = propertyValues.get(PROP_SERVICE);
        String portName = propertyValues.get(PROP_ENDPOINT);
        String operation = propertyValues.get(PROP_OPERATION);
        if (!isValid(wsdlUrl, serviceName, portName, operation)) {
            suggestionBuilder.node(EditorPropertyFactory.EDITOR_PROP).suggestions(
                    String.format(INVALID_SETTINGS, Messages.LBL_WSDL_URL,
                            Messages.LBL_SERVICE_NAME, Messages.LBL_ENDPOINT,
                            Messages.LBL_OPERATION));
        }
        QName serviceQName = QName.valueOf(serviceName);
        QName portQName = QName.valueOf(portName);
        QName operationQName = QName.valueOf(operation);
        ClientBuilder clientBuilder = invocationService.createClientBuilderFor(wsdlUrl,
                serviceQName, portQName, operationQName, null);
        EditorSuggestionProvider suggestionProvider = soapUtils.initializeSuggestionProvider(
                wsdlUrl, serviceQName, portQName, operationQName, clientBuilder, null);

        String xmlTemplate = suggestionProvider.suggestContent();
        suggestionBuilder.node(EditorPropertyFactory.EDITOR_PROP).suggestions(xmlTemplate);
    }

    private boolean isValid(Object wsdlUrlObject, Object serviceNameObject, Object portNameObject,
            String operation) {
        return (wsdlUrlObject != null && serviceNameObject != null && portNameObject != null && operation != null);
    }
}