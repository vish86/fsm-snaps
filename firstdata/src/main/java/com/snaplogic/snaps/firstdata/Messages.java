/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc. All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.firstdata;

/**
 * Messages is the container for all the externalized messages.
 *
 * @author svatada
 */
class Messages {
    // Metric
    static final String COUNTER_DESCRIPTION = "A metric that indicates the number of "
            + "successfully written records.";
    static final String COUNTER_UNIT = "Documents";
    static final String DOCUMENT_COUNTER = "Document Counter";
    // Snap label for Account
    static final String CUSTOM_ACCOUNT_TITLE = "FirstData custom account";
    static final String APP_ID = "Application_ID";
    static final String APP_ID_LABEL = "Application ID";
    static final String APP_ID_DESC = "FirstData application ID";
    static final String SERVICE_ID = "Sevice_ID";
    static final String SERVICE_ID_LABEL = "Sevice ID";
    static final String SERVICE_ID_DESC = "FirstData sevice ID";
    static final String SERVICE_URL = "EndPoint_URL";
    static final String SERVICE_URL_LABEL = "EndPoint URL";
    static final String SERVICE_URL_DESC = "FirstData EndPoint url";
    static final String WSDL_URL = "WSDL_URL";
    static final String WSDL_URL_LABEL = "WSDL URL";
    static final String WSDL_URL_DESC = "FirstData WSDL url";
    static final String GROUP_ID = "GROUP_ID";
    static final String GROUP_ID_LABEL = "Group ID";
    static final String GROUP_ID_DESC = "FirstData Group ID";
    static final String TERMINAL_ID = "TERMINAL_ID";
    static final String TERMINAL_ID_LABEL = "Terminal ID";
    static final String TERMINAL_ID_DESC = "FirstData Terminal ID";
    static final String DATAWIRE_ID = "DATAWIRE_ID";
    static final String DATAWIRE_ID_LABEL = "Datawire ID";
    static final String DATAWIRE_ID_DESC = "FirstData Datawire ID";
    static final String AUTH_STRING = "AUTH_STRING";
    static final String AUTH_STRING_LABEL = "Auth string";
    static final String AUTH_STRING_DESC = "AuthString will be assigned by FirstData."
            + "General format for AuthString is <Group ID><Merchant ID>|<Terminal ID>";
    static final String TIMEOUT = "TIME_OUT";
    static final String TIMEOUT_LABEL = "Timeout";
    static final String TIMEOUT_DESC = "Time out value";
    // Snap labels and descriptions
    static final String CREATE_LABEL = "FirstData - Create";
    static final String SNAP_DESC = "Create snap";
    static final String RESOURCE_LABEL = "Payment type";
    static final String RESOURCE_PROP = "Resource";
    static final String RESOURCE_DESC = "Available FirstData resources";
    // Error msgs
    static final String NULL_TRANSACTION_RESPONSE = "Null transaction response";
    static final String NULL_OBJECT = "NULL Object";
    static final String INVALID_RESPONSE = "Invalid response";
    static final String IMPROPER_INPUT = "improper input";
    static final String EMPTY_RESPONSE = "Caught empty response";
    static final String VALIDATE_INPUT_DATA = "Validate your input data";
    static final String NO_DATA_ERRMSG = "Unable to process the input document";
    static final String NO_DATA_WARNING = "Empty input document";
    static final String NO_DATA_REASON = "No data coming form the connected snap.";
    static final String ERROR_RESOLUTION = "Validate input data.";
    static final String ERROR_REASON = "Check the values passed.";
    static final String ERRORMSG = "Technical error has occured while processing the input.";
    static final String NO_DATA_RESOLUTION = "Verify the connected snap and validate the data.";
    static final String ILLEGAL_ARGS_EXE = "Indicates that, the input has been passed an illegal "
            + "or inappropriate value to the method %s.";
    static final String INVOCATION_TARGET_EXE = "Trying to execute the unknow function call or "
            + "method parameter types were wrong.";
    static final String ILLIGAL_ACCESS_EXE = "Trying to access the private resources %s";
    static final String ILLIGAL_ACCESS_EXE_RES = "Suppress Java language access checking in order to "
            + "reflectively invoke a private method";
    static final String SECURITY_EXE_RES = "all-permissions - requires access to the resources.";
    static final String SECURITY_EXE = "permissio denied";
    static final String NOSUCH_RESOURCE_EXE = "Trying to access the unknow resources";
}