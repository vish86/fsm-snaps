/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2013, SnapLogic, Inc. All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.lunex;

/**
 * Messages is the container for all the externalized messages.
 *
 * @author svatada
 */
public class Messages {
    // Snap labels and descriptions
    static final String LUNEX_CREATE_LABEL = "Lunex - Create";
    static final String LUNEX_CREATE_DESC = "Issues an HTTP Postto "
            + "Lunex API service endpoint.";
    static final String LUNEX_UPDATE_LABEL = "Lunex - Update";
    static final String LUNEX_UPDATE_DESC = "Issues an HTTP Put to "
            + "Lunex API service endpoint.";
    static final String LUNEX_DELETE_LABEL = "Lunex - Delete";
    static final String LUNEX_DELETE_DESC = "Issues an HTTP Delete to"
            + " Lunex API service endpoint.";
    static final String LUNEX_READ_LABEL = "Lunex - Read";
    static final String LUNEX_READ_DESC = "Issues an HTTP GET to "
            + "Lunex API service endpoint.";
    // Common properties labels and descriptions
    static final String SERVICE_DOMAIN_PROP = "endpoint";
    static final String SERVICE_DOMAIN_LABEL = "Endpoint IP";
    static final String SERVICE_DOMAIN_DESCRIPTION = "Please enter "
            + "the Lunex service endpoint domain address";
    static final String USERNAME_PROP = "username";
    static final String USERNAME_LABEL = "Username";
    static final String USERNAME_DESCRIPTION = "Please enter "
            + "the username for Lunex service endpoint";
    static final String PASSWORD_PROP = "passward";
    static final String PASSWORD_LABEL = "Password";
    static final String PASSWORD_DESCRIPTION = "Please enter "
            + "the passcode for Lunex service endpoint";
    static final String RESOURCE_LABEL = "Resource type";
    static final String RESOURCE_PROP = "Resource";
    static final String RESOURCE_DESC = "Available Lunex resources";
    static final String PARAM_PATH_PROP = "ParamPath";
    static final String PARAM_PATH_LABEL = "Parameter path";
    static final String PARAM_PATH_DESC = "Lunex resource param JSON" + "path";
    static final String PARAM_NAME_PROP = "ParamName";
    static final String PARAM_NAME_LABEL = "Parameter name";
    static final String PARAM_NAME_DESC = "Lunex resource parameter name"
            + " to be used in the output map data. If left blank, the Field path"
            + " will be used instead.";
    static final String PARAM_TABLE_MAPPINGS_PROP = "ParamMappings";
    static final String PARAM_TABLE_MAPPINGS_LABEL = "Request parameters";
    static final String PARAM_TABLE_MAPPINGS_DESC = "Please enter Param "
            + "Mappings field paths and names.If left empty, Snap will not produce "
            + "any Lunex request to the specified resources.";
    static final String FIELD_PATH_PROP = "FieldPath";
    static final String FIELD_PATH_LABEL = "Field path";
    static final String FIELD_PATH_DESC = "Lunex resource field JSON path";
    static final String FIELD_NAME_PROP = "FieldName";
    static final String FIELD_NAME_LABEL = "Field name";
    static final String FIELD_NAME_DESC = "Lunex resource field name to be "
            + "used in the output map data.If left blank, the Field path will be "
            + "used instead.";
    static final String FIELD_TABLE_MAPPINGS_PROP = "fieldMappings";
    static final String FIELD_TABLE_MAPPINGS_LABEL = "Request body";
    static final String FIELD_TABLE_MAPPINGS_DESC = "Please enter field"
            + " Mappings field paths and names.If left empty, Snap will not produce"
            + " any Lunex request to the specified resources.";
    static final String HEADER_KEY_PROP = "headerKey";
    static final String HEADER_VALUE_PROP = "headerValue";
    static final String HTTP_HEADER_PROP = "header";
    static final String HEADER_KEY_LABEL = "Key";
    static final String HEADER_KEY_DESCRIPTION = "Please enter HTTP header key.";
    static final String HEADER_VALUE_LABEL = "Value";
    static final String HEADER_VALUE_DESCRIPTION = "Please enter HTTP header value.";
    static final String HTTP_HEADER_LABEL = "Custom HTTP headers";
    static final String HTTP_HEADER_DESCRIPTION = "Please enter HTTP header "
            + "key-value pairs.";
    static final String LUNEX_BASIC_AUTH_ACCOUNT_TITLE = "LUNEX basic auth account";
    // Error messages
    static final String AUTH_ERROR_RESOLUTION = "Please provide valid "
            + "Basic auth account or Authorization header.";
    static final String AUTH_ERROR_REASON = "Missing Account or Authorization"
            + " header in API request.";
    static final String AUTH_ERROR = "Authentication error occured.";
    static final String IO_ERROR_RESOLUTION = "IO Error occured, please check "
            + "the values provided.";
    static final String IO_ERROR_REASON = "Check the values passed.";
    static final String IO_ERROR = "An io error occured while reading data.";
    static final String INPUT_STREAM_ERROR = "IO Error occured while opening the input stream %s";
    static final String CONTENT_STREAM_ERROR = "The content stream for the"
            + " response could not be created.";
    static final String ILLEGAL_STATE_REASON = "Unable to process the response.";
    static final String ILLEGAL_STATE_RESOLUTION = "Check that input data is "
            + "available for processing.";
    static final String URL_SYNTAX_ERROR = "Error while constructing uri %s";
    static final String ERROR_RESOLUTION = "Corrections are required in input data.";
    static final String ERROR_REASON = "Check the values passed.";
    static final String ERRORMSG = "Internal error has occured while reading data.";
    static final String INVALID_URI = "The provided URI is invalid: %s";
    static final String INVALID_URI_RESOLUTION = "Please verify the provided URI "
            + "and parameters are syntactically correct";
    static final String JSON_PARSING_ERROR = "An error occurred while parsing "
            + "the Json reponse.";
    static final String JSON_PARSING_REASON = "Error occurred while parsing the "
            + "unstructured json object.";
    static final String JSON_PARSING_RESOLUTION = "Please check reponse returned "
            + "from the service";
    static final String MALFORMEDURL_ERROR_REASON = "The URL string is not parseable or contains an unsupported protocol";
    static final String MALFORMEDURL_ERROR_RESOLUTION = "Possible common mistakes includes specifying an invalid protocol,leaving out the protocal and colun character.";
    static final String HTTP_ERROR_REASON = "Some thing went wrong while building the HTTP request or provided input data has invalid information";
    static final String HTTP_ERROR_RESOLUTION = "Corrections are required on input data";
}