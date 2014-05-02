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
package com.snaplogic.snaps.lunex.constants;

/**
 * Messages is the container for all the externalized messages in this package.
 * 
 * @author svatada
 */
public class Messages {
    // Snap labels and descriptions
    public static final String LUNEX_CREATE_LABEL = "Lunex - Create";
    public static final String LUNEX_CREATE_DESC = "Issues an HTTP Postto "
            + "Lunex API service endpoint.";
    public static final String LUNEX_UPDATE_LABEL = "Lunex - Update";
    public static final String LUNEX_UPDATE_DESC = "Issues an HTTP Put to "
            + "Lunex API service endpoint.";
    public static final String LUNEX_DELETE_LABEL = "Lunex - Delete";
    public static final String LUNEX_DELETE_DESC = "Issues an HTTP Delete to"
            + " Lunex API service endpoint.";
    public static final String LUNEX_READ_LABEL = "Lunex - Read";
    public static final String LUNEX_READ_DESC = "Issues an HTTP GET to "
            + "Lunex API service endpoint.";
    // Common properties labels and descriptions
    public static final String SERVICE_DOMAIN_PROP = "endpoint";
    public static final String SERVICE_DOMAIN_LABEL = "Endpoint IP";
    public static final String SERVICE_DOMAIN_DESCRIPTION = "Please enter "
            + "the Lunex service endpoint domain address";
    public static final String RESOURCE_LABEL = "Resource type";
    public static final String RESOURCE_PROP = "Resource";
    public static final String RESOURCE_DESC = "Available Lunex resources";
    public static final String PARAM_PATH_PROP = "ParamPath";
    public static final String PARAM_PATH_LABEL = "Parameter path";
    public static final String PARAM_PATH_DESC = "Lunex resource param JSON" + "path";
    public static final String PARAM_NAME_PROP = "ParamName";
    public static final String PARAM_NAME_LABEL = "Parameter name";
    public static final String PARAM_NAME_DESC = "Lunex resource parameter name"
            + " to be used in the output map data. If left blank, the Field path"
            + " will be used instead.";
    public static final String PARAM_TABLE_MAPPINGS_PROP = "ParamMappings";
    public static final String PARAM_TABLE_MAPPINGS_LABEL = "Request parameters";
    public static final String PARAM_TABLE_MAPPINGS_DESC = "Please enter Param "
            + "Mappings field paths and names.If left empty, Snap will not produce "
            + "any Lunex request to the specified resources.";
    public static final String FIELD_PATH_PROP = "FieldPath";
    public static final String FIELD_PATH_LABEL = "Field path";
    public static final String FIELD_PATH_DESC = "Lunex resource field JSON path";
    public static final String FIELD_NAME_PROP = "FieldName";
    public static final String FIELD_NAME_LABEL = "Field name";
    public static final String FIELD_NAME_DESC = "Lunex resource field name to be "
            + "used in the output map data.If left blank, the Field path will be "
            + "used instead.";
    public static final String FIELD_TABLE_MAPPINGS_PROP = "fieldMappings";
    public static final String FIELD_TABLE_MAPPINGS_LABEL = "Request body";
    public static final String FIELD_TABLE_MAPPINGS_DESC = "Please enter field"
            + " Mappings field paths and names.If left empty, Snap will not produce"
            + " any Lunex request to the specified resources.";
    public static final String HEADER_KEY_PROP = "headerKey";
    public static final String HEADER_VALUE_PROP = "headerValue";
    public static final String HTTP_HEADER_PROP = "header";
    public static final String HEADER_KEY_LABEL = "Key";
    public static final String HEADER_KEY_DESCRIPTION = "Please enter HTTP header key.";
    public static final String HEADER_VALUE_LABEL = "Value";
    public static final String HEADER_VALUE_DESCRIPTION = "Please enter HTTP header value.";
    public static final String HTTP_HEADER_LABEL = "Custom HTTP headers";
    public static final String HTTP_HEADER_DESCRIPTION = "Please enter HTTP header "
            + "key-value pairs.";
    public static final String LUNEX_BASIC_AUTH_ACCOUNT_TITLE = "LUNEX basic auth account";
    // Error messages
    public static final String AUTH_ERROR_RESOLUTION = "Please provide valid "
            + "Basic auth account or Authorization header.";
    public static final String AUTH_ERROR_REASON = "Missing Account or Authorization"
            + " header in API request.";
    public static final String AUTH_ERROR = "Authentication error occured.";
    public static final String IO_ERROR_RESOLUTION = "IO Error occured, please check "
            + "the values provided.";
    public static final String IO_ERROR_REASON = "Check the values passed.";
    public static final String IO_ERROR = "An io error occured while reading data.";
    public static final String CONTENT_STREAM_ERROR = "The content stream for the"
            + " response could not be created.";
    public static final String ILLEGAL_STATE_REASON = "Unable to process the response.";
    public static final String ILLEGAL_STATE_RESOLUTION = "Check that input data is "
            + "available for processing.";
    public static final String URL_SYNTAX_ERROR = "Error while constructing uri %s";
    public static final String ERROR_RESOLUTION = "Corrections are required in input data.";
    public static final String ERROR_REASON = "Check the values passed.";
    public static final String ERRORMSG = "Internal error has occured while reading data.";
    public static final String INVALID_URI = "The provided URI is invalid: %s";
    public static final String INVALID_URI_RESOLUTION = "Please verify the provided URI "
            + "and parameters are syntactically correct";
    public static final String JSON_PARSING_ERROR = "An error occurred while parsing "
            + "the Json reponse.";
    public static final String JSON_PARSING_REASON = "Error occurred while parsing the "
            + "unstructured json object.";
    public static final String JSON_PARSING_RESOLUTION = "Please check reponse returned "
            + "from the service";
    public static final String MALFORMEDURL_ERROR_REASON = "The URL string is not parseable or contains an unsupported protocol";
    public static final String MALFORMEDURL_ERROR_RESOLUTION = "Possible common mistakes includes specifying an invalid protocol,leaving out the protocal and colun character.";
    public static final String HTTP_ERROR_REASON = "Some thing went wrong while building the HTTP request or provided input data has invalid information";
    public static final String HTTP_ERROR_RESOLUTION = "Corrections are required on input data";
}