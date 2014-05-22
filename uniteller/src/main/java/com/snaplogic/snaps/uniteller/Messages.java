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
    // Metric
    static final String COUNTER_DESCRIPTION = "A metric that indicates the number of "
            + "successfully written records into NetSuite";
    static final String COUNTER_UNIT = "Documents";
    static final String DOCUMENT_COUNTER = "Document Counter";
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
    static final String UPDATE_LABEL = "UniTeller - Update";
    static final String DELETE_LABEL = "UniTeller - Delete";
    static final String READ_LABEL = "UniTeller - Read";
    static final String SNAP_DESC = "Provides the integration machanisum with UniTeller Web Services for the correspondent resources.";
    // Common properties labels and descriptions
    static final String RESOURCE_LABEL = "Resource type";
    static final String RESOURCE_PROP = "Resource";
    static final String RESOURCE_DESC = "Available UniTeller resources";
    // Error messages
    static final String RESOLUTION_CUSTOMER_SUPPORT = "Please report to customer support for"
            + " further investigation";
    static final String ERR_CLOSE_FILESYSTEM = "Cannot close file system for %s";
    static final String ERR_CLOSE_FILESYSTEM_REASON = "Failed to close the file system due to an"
            + " unknown reason";
    static final String ERR_DIFFERENT_SCHEMES = "Different schemes in the same reader are not"
            + " allowed: %s, %s";
    static final String RESOLUTION_DIFFERENT_SCHEMES = "Please use a secondary reader that uses"
            + " the secondary scheme.";
    static final String ERR_MALFORMED_URL_RESOLUTION = "Please enter a valid file name.";
    static final String ERR_URI_SYNTAX = "URI Syntax error: %s, detail: %s";
    static final String ERR_URL_CONNECT = "Attempted to open an connection, but returned a null"
            + " connection object for URL: %s";
    static final String ERR_DIR_NOT_FOUND = "Directory not found: %s, detail: %s";
    static final String RESOLUTION_DIR_NOT_FOUND = "Please check if the directory exists,"
            + " you have access right and all Snap properties and credentials are valid.";
    static final String ERR_CREATE_FILESYSTEM = "Unable to create filesystem object for %s";
    static final String ERR_CREATE_FILESYSTEM_RESOLUTION = "Please check all properties and"
            + "credentials";
    static final String ERR_FILE_NOT_FOUND = "File not found: %s";
    static final String ERR_FILE_NOT_FOUND_REASON = "Failed to get file attribute, the requested"
            + " file may not exist or you may not have the access privilege";
    static final String ERR_FILE_NOT_FOUND_RESOLUTION = "Please enter a correct file name or"
            + " account.";
    static final String ERR_UNSUPPORTED_FILE_TYPE = "Unsupported file type: %s";
    static final String ERR_UNSUPPORTED_FILE_TYPE_REASON = "File is not a regular file nor a"
            + " directory";
    static final String ERR_UNSUPPORTED_FILE_TYPE_RESOLUTION = "Please enter a correct file name.";

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