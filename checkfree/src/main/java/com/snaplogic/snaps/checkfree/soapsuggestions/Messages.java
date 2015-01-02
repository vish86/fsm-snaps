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

/**
 * This class holds the externalizable messages for all the classes in this package.
 *
 * @author svatada
 */
class Messages {
    static final String INVALID_SETTINGS = "Either %s or %s or %s or %s is not set. Please set " +
            "all correctly before suggesting the template.";
    static final String WSDL_MUST_BE_DEFINED_FIRST = "WSDL Url must be defined first.";
    static final String SERVICE_NAME_MUST_BE_DEFINED_FIRST = "Service Name must be defined first.";
    static final String SERVICE_NAME_AND_OR_ENDPOINT_MUST_BE_DEFINED_FIRST = "Service Name " +
            "and/or Endpoint must be defined first.";
}
