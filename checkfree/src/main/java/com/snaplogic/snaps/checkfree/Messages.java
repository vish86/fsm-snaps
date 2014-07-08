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

/**
 * This class holds the externalizable messages for all the classes in this package.
 *
 * @author svatada
 * @since 2014 July
 */
public class Messages {
    // Labels & descriptions
    public static final String LBL_SERVICE_NAME = "Service Name";
    static final String DESC_SERVICE_NAME = "Service is the system function that has " +
            "been exposed to the Web-based protocols";
    public static final String LBL_ENDPOINT = "Endpoint";
    static final String DESC_ENDPOINT = "Endpoint (in Wsdl 2.0 terminology) / Port name (in " +
            "Wsdl 1.1 terminology)";
    public static final String LBL_OPERATION = "Operation";
    static final String DESC_OPERATION = "Operation to invoke";
    public static final String LBL_WSDL_URL = "WSDL Url";
    static final String DESC_URL_FOR_THE_WSDL = "Url for the WSDL";
    static final String LBL_PROP_SOAP_BINDING = "SOAP binding type";
    static final String DESC_PROP_SOAP_BINDING = "Defines the SOAP binding in case it can not " +
            "be determined via introspection";
    static final String DESC_TIMEOUT = "Timeout for the web service call (in seconds). O " +
            "indicates no timeout";
    static final String LBL_TIMEOUT = "Timeout";
    static final String LBL_DEFAULT_VALUE = "Default value for substitution";
    static final String DESC_DEFAULT_VALUE = "This value will be used for substitution for the " +
            "leaf elements which are not objects and/or not enclosed in an array. If nothing is " +
            "specified and 'Use default value for substitution' is checked, " +
            "an empty string will be used for substitution.";
    static final String LBL_USE_DEFAULT_VALUE = "Use default value for substitution";
    static final String DESC_USE_DEFAULT_VALUE = "If checked, the value provided for the 'Default" +
            " value for substitution' will be used for substitution if the elements don't exist " +
            "in the incoming documents. Otherwise, the elements for which the incoming documents " +
            "don't have any values will be deleted from the SOAP request.";
    static final String DOCUMENTS = "Documents";
    static final String SOAP_DOC_PROCESSED = "SoapDocProcessed";
    static final String SOAP_EXECUTE_LABEL = "CheckFree - Execute";
    static final String SOAP_REQUEST_DESC = "Calls the CheckFree endpoint and writes out response.";
    static final String XML_WRITTEN_DESCRIPTION = "Number of XML documents successfully written";
    static final String SOAP_BASIC_AUTH_ACCOUNT_TITLE = "SOAP basic auth account";
    static final String SOAP_HEADER_ACCOUNT_TITLE = "SOAP header account";
    static final String SOAP_HEADER_LABEL = "SOAP Header";
    static final String SOAP_HEADER_DESCRIPTION = "Allows to define the SOAP header as plain XML." +
            " The XML can be validated if needed if an XSD is provided.";
    static final String SOAP_EXCEPTION_RESOLUTION = "Please verify the template is correct, " +
            "the substituted values are valid and the provided envelope matches the namespaces  " +
            "that are required by the targeted service";
    static final String SOAP_EXCEPTION = "SOAP Exception";
    static final String SOAP_EXCEPTION_REASON = "SOAP request failed";
    static final String XML_SERIALIZATION_FAILED = "XML Serialization failed";
    static final String IO_ERROR = "IO Error";
    static final String IO_ERROR_REASON = "Provided header cannot be read";
    static final String SOAP_HEADER_EXCEPTION_RESOLUTION = "Please verify the provided header is " +
            "correct, and the defined values are valid. Further please ensure the " +
            "provided envelope matches the namespaces that are required by the " +
            "targeted service";
    static final String CUSTOMIZE_ENVELOPE = "Customize Envelope";
    static final String CUSTOMIZE_ENVELOPE_DESC = "Allows to edit the template. " +
            "If no template is stored in the snap, then it will suggest one based on the " +
            "selection of the port, otherwise it will load the existing one.";
    static final String ERROR_WHILE_READING_OPERATION_SCHEMA = "An error occurred while reading " +
            "the operation schema";
    static final String REQUEST_SCHEMA_NOT_AVAILABLE = "The request schema for the given " +
            "operation is not available";
    static final String PLEASE_SPECIFY_REQUEST_SCHEMA = "Please make sure that given wsdl " +
            "contains the request schema for the given operation";
    static final String EXCEPTION_OCCURRED = "An exception occurred while executing the SOAP " +
            "request";
    static final String DISPATCHING_SOAP_REQUEST = "Dispatching a SOAP request:\n{}";
    static final String ERROR_PARSING_XML = "Error parsing XML";
    static final String VERIFY_THE_RETURNED_XML_IS_PARSEABLE = "Verify the returned XML is " +
            "parseable";
}
