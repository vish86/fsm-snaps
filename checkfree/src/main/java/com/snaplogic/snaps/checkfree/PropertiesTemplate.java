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

import com.snaplogic.common.SnapType;
import com.snaplogic.common.properties.SnapProperty;
import com.snaplogic.common.properties.builders.PropertyBuilder;

/**
 * Enumeration of re-useable properties
 *
 * @author svatada
 */
public enum PropertiesTemplate {
    HTTP_HEADERS_PROPERTY;

    static final String HEADER_KEY_LABEL = "Key";
    static final String HEADER_KEY_DESCRIPTION = "Please enter HTTP header key.";
    static final String HEADER_VALUE_LABEL = "Value";
    static final String HEADER_VALUE_DESCRIPTION = "Please enter HTTP header value.";
    static final String HTTP_HEADER_LABEL = "HTTP header";
    static final String HTTP_HEADER_DESCRIPTION = "Please enter HTTP header key-value pairs.";
    public static final String HEADER_KEY_PROP = "headerKey";
    public static final String HEADER_VALUE_PROP = "headerValue";
    public static final String HTTP_HEADER_PROP = "header";

    /**
     * creates predefined PropertyBuilder definition
     * - but does not call PropertyBuilder.build() or PropertyBuilder.add() to enable modification of returned builder
     *
     * @param propertyBuilder
     * @return
     */
    public PropertyBuilder defineUsing(PropertyBuilder propertyBuilder) {
        switch(this) {
            case HTTP_HEADERS_PROPERTY :
                SnapProperty headerKey = propertyBuilder.describe(
                        HEADER_KEY_PROP, HEADER_KEY_LABEL,
                        HEADER_KEY_DESCRIPTION)
                        .expression(SnapProperty.DecoratorType.ACCEPTS_SCHEMA)
                        .build();
                SnapProperty headerValue = propertyBuilder.describe(
                        HEADER_VALUE_PROP, HEADER_VALUE_LABEL,
                        HEADER_VALUE_DESCRIPTION)
                        .expression(SnapProperty.DecoratorType.ACCEPTS_SCHEMA)
                        .build();
                return propertyBuilder.describe(HTTP_HEADER_PROP, HTTP_HEADER_LABEL, HTTP_HEADER_DESCRIPTION)
                        .type(SnapType.TABLE)
                        .withEntry(headerKey)
                        .withEntry(headerValue);
        }
        return null;
    }
}
