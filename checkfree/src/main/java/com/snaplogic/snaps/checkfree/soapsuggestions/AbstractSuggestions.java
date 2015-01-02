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

package com.snaplogic.snaps.checkfree.soapsuggestions;

import com.snaplogic.common.properties.Suggestions;
import com.snaplogic.common.properties.builders.SuggestionBuilder;
import com.snaplogic.common.services.ws.IntrospectionService;
import com.snaplogic.snap.api.ExpressionProperty;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.soap.HttpContextProvider;
import com.snaplogic.snap.api.soap.HttpRequestAuthProvider;
import com.snaplogic.snap.api.soap.SoapAccount;
import com.snaplogic.snaps.checkfree.PropertiesTemplate;
import com.snaplogic.snaps.checkfree.CheckfreeExecute;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.snaplogic.snaps.checkfree.CheckfreeExecute.PROP_ENDPOINT;
import static com.snaplogic.snaps.checkfree.CheckfreeExecute.PROP_SERVICE;
import static com.snaplogic.snaps.checkfree.CheckfreeExecute.PROP_WSDL_URL;
import static com.snaplogic.snaps.checkfree.soapsuggestions.Messages.WSDL_MUST_BE_DEFINED_FIRST;

/**
 * AbstractSuggestions is the suggestions implementation that is the base class for all the other
 * suggestion classes.
 *
 * @author svatada
 */
public abstract class AbstractSuggestions implements Suggestions {
    private static final String VALUE = "value";
    private final SoapAccount<?> account;
    protected final IntrospectionService introspectionService;
    protected final String nodeKey;
    protected boolean isTrustAll;

    public AbstractSuggestions(IntrospectionService introspectionService, SoapAccount<?> account,
            String nodeKey) {
        this.account = account;
        this.introspectionService = introspectionService;
        this.nodeKey = nodeKey;
    }

    @Override
    public void suggest(SuggestionBuilder suggestionBuilder, PropertyValues propertyValues) {
        Object wsdlUrlObject = propertyValues.get(PROP_WSDL_URL);
        Object serviceNameObject = propertyValues.get(PROP_SERVICE);
        Object portNameObject = propertyValues.get(PROP_ENDPOINT);
        isTrustAll = Boolean.TRUE.equals(propertyValues.get(CheckfreeExecute.TRUST_ALL_CERTS_PROP));
        List<Map<String, Object>> httpHeader = propertyValues.get(PropertiesTemplate.HTTP_HEADER_PROP);
        List<Pair<ExpressionProperty, ExpressionProperty>> httpHeaders = new ArrayList<>();
        List<Header> headerList = new ArrayList<>();
        if (httpHeader != null) {
            for (Map<String, Object> item : httpHeader) {
                ExpressionProperty keyProp = propertyValues.getExpressionPropertyFor(item,
                        PropertiesTemplate.HEADER_KEY_PROP);
                ExpressionProperty valueProp = propertyValues.getExpressionPropertyFor(item,
                        PropertiesTemplate.HEADER_VALUE_PROP);
                httpHeaders.add(Pair.of(keyProp, valueProp));
                Map<String, Object> keyMap = (Map<String, Object>) item.get(PropertiesTemplate
                        .HEADER_KEY_PROP);
                Map<String, Object> valueMap = (Map<String, Object>) item.get(PropertiesTemplate
                        .HEADER_VALUE_PROP);
                String headerKey = (String) keyMap.get(VALUE);
                String headerValue = (String) valueMap.get(VALUE);
                if (!StringUtils.isEmpty(headerKey) && !StringUtils.isEmpty(headerValue)) {
                    headerList.add(new BasicHeader(headerKey, headerValue));
                }
            }
        }
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

    protected HttpContextProvider getHttpContextProvider(final Header[] headers,
            final boolean isTrustAll) {
        return new HttpContextProvider() {
            @Override
            public Header[] getHttpHeaders() {
                return headers;
            }

            @Override
            public HttpRequestAuthProvider getHttpRequestAuthProvider() {
                if (account instanceof HttpRequestAuthProvider) {
                    return (HttpRequestAuthProvider) account;
                } else {
                    return null;
                }
            }
            
            public boolean isTrustAll() {
        		return isTrustAll;
        	}

        };
    }
}
