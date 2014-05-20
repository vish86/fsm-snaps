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
package com.snaplogic.snaps.uniteller;

/**
 * Messages is the container for all the externalized messages.
 * 
 * @author svatada
 */
class Messages {
    // Snap label for Account
    static final String BASIC_AUTH_ACCOUNT_TITLE = "UniTeller basic auth account";
    static final String CONFIG_FILEPATH_PROP = "UFS_Config";
    static final String CONFIG_FILEPATH_LABEL = "Configuration File";
    static final String CONFIG_FILEPATH_DESC = "UniTeller configuration file path";
    static final String SECURITY_FILEPATH_PROP = "UFS_Security";
    static final String SECURITY_FILEPATH_LABEL = "Security File";
    static final String SECURITY_FILEPATH_DESC = "UniTeller security file path";
    // Snap labels and descriptions
    static final String CREATE_LABEL = "UniTeller - Create";
    static final String CREATE_DESC = "Issues an HTTP Postto " + "UniTeller API service endpoint.";
    static final String UPDATE_LABEL = "UniTeller - Update";
    static final String UPDATE_DESC = "Issues an HTTP Put to " + "UniTeller API service endpoint.";
    static final String DELETE_LABEL = "UniTeller - Delete";
    static final String DELETE_DESC = "Issues an HTTP Delete to"
            + " UniTeller API service endpoint.";
    static final String READ_LABEL = "UniTeller - Read";
    static final String READ_DESC = "Issues an HTTP GET to " + "UniTeller API service endpoint.";
    // Common properties labels and descriptions
    static final String RESOURCE_LABEL = "Resource type";
    static final String RESOURCE_PROP = "Resource";
    static final String RESOURCE_DESC = "Available UniTeller resources";
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
    static final String MALFORMEDURL_ERROR_REASON = "The URL string is not parseable or contains an unsupported protocol";
    static final String MALFORMEDURL_ERROR_RESOLUTION = "Possible common mistakes includes specifying an invalid protocol,leaving out the protocal and colun character.";
    static final String HTTP_ERROR_REASON = "Some thing went wrong while building the HTTP request or provided input data has invalid information";
    static final String HTTP_ERROR_RESOLUTION = "Corrections are required on input data";
}