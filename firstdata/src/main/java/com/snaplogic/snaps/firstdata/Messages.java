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
public class Messages {
    // Metric
    static final String COUNTER_DESCRIPTION = "A metric that indicates the number of "
            + "successfully written records.";
    static final String COUNTER_UNIT = "Documents";
    static final String DOCUMENT_COUNTER = "Document Counter";
    // Snap label for Account
    static final String DESC_ENDPOINT = "Endpoint (in Wsdl 2.0 terminology) / Port name (in "
            + "Wsdl 1.1 terminology)";
    static final String DESC_OPERATION = "Operation to invoke";
    static final String DESC_URL_FOR_THE_WSDL = "Url for the WSDL";
    static final String LBL_PROP_SOAP_BINDING = "SOAP binding type";
    static final String DESC_PROP_SOAP_BINDING = "Defines the SOAP binding in case it can not "
            + "be determined via introspection";
    static final String DESC_TIMEOUT = "Timeout for the web service call (in seconds). O "
            + "indicates no timeout";
    static final String LBL_TIMEOUT = "Timeout";
    static final String CUSTOMIZE_ENVELOPE = "Customize Envelope";
    static final String CUSTOMIZE_ENVELOPE_DESC = "Allows to edit the template. "
            + "If no template is stored in the snap, then it will suggest one based on the "
            + "selection of the port, otherwise it will load the existing one.";
    // ------------------
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
    static final String CREATE1_LABEL = "FirstData - Transaction";
    static final String CREATE_LABEL = "FirstData - Create";
    static final String SNAP_DESC = "Create snap";
    static final String RESOURCE_LABEL = "Payment type";
    static final String RESOURCE_PROP = "Resource";
    static final String RESOURCE_DESC = "Available FirstData resources";
    // Error msgs
    static final String REQUEST_FAILED = "Request preparation failed at initial level";
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
    static final String NOSUCH_METHOD_EXE = "Trying to access the unknow resources";
    public static final String INVALID_SETTINGS = "Either %s or %s or %s or %s is not set. Please set "
            + "all correctly before suggesting the template.";
    public static final String WSDL_MUST_BE_DEFINED_FIRST = "WSDL Url must be defined first.";
    public static final String SERVICE_NAME_MUST_BE_DEFINED_FIRST = "Service Name must be defined first.";
    public static final String SERVICE_NAME_AND_OR_ENDPOINT_MUST_BE_DEFINED_FIRST = "Service Name "
            + "and/or Endpoint must be defined first.";
    public static final String LBL_SERVICE_NAME = "Service Name";
    static final String DESC_SERVICE_NAME = "Service is the system function that has "
            + "been exposed to the Web-based protocols";
    public static final String LBL_ENDPOINT = "Endpoint";
    public static final String LBL_OPERATION = "Operation";
    public static final String LBL_WSDL_URL = "WSDL Url";
    public static final String ERROR_WHILE_READING_OPERATION_SCHEMA = "An error occurred while reading "
            + "the operation schema";
    public static final String REQUEST_SCHEMA_NOT_AVAILABLE = "The request schema for the given "
            + "operation is not available";
    public static final String PLEASE_SPECIFY_REQUEST_SCHEMA = "Please make sure that given wsdl "
            + "contains the request schema for the given operation";
    static final String SOAP_EXCEPTION = "SOAP Exception";
    static final String SOAP_EXCEPTION_REASON = "SOAP request failed";
    static final String SOAP_EXCEPTION_RESOLUTION = "Please verify the template is correct, "
            + "the substituted values are valid and the provided envelope matches the namespaces  "
            + "that are required by the targeted service";
    static final String XML_SERIALIZATION_FAILED = "XML Serialization failed";
    static final String EXCEPTION_OCCURRED = "An exception occurred while executing the SOAP "
            + "request";
    static final String DISPATCHING_SOAP_REQUEST = "Dispatching a SOAP request:\n{}";
    static final String ERROR_PARSING_XML = "Error parsing XML";
    static final String VERIFY_THE_RETURNED_XML_IS_PARSEABLE = "Verify the returned XML is "
            + "parseable";
}