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
package com.snaplogic.snaps.uniteller;

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
    static final String SNAP_DESC = "Provides the integration machanisum with UniTeller Web "
            + "Services for the correspondent resources.";
    // Common properties labels and descriptions
    static final String RESOURCE_LABEL = "Resource type";
    static final String RESOURCE_PROP = "Resource";
    static final String RESOURCE_DESC = "Available UniTeller resources";
    // Error messages
    static final String ERR_PASSWORD_EMPTY = "Password is null or empty";
    static final String NO_DATA_ERRMSG = "Unable to process the input document";
    static final String NO_DATA_WARNING = "Empty input document";
    static final String NO_DATA_REASON = "No data coming form the connected snap.";
    static final String NO_DATA_RESOLUTION = "Verify the connected snap and validate the data.";
    static final String NO_PASSWORD_ERROR = "Password for machine ID %s was blank or corrupt, "
            + "using dummy password";
    static final String CHANGE_PASS_ERR = "Error while doing Change Password";
    static final String NO_PASSWORD_FOR_MACHINE_ID = "No password for machine id %s ";
    static final String NULLREQUEST = "%s was Null";
    static final String NORESPONSEIOOBJECT = "No Response ioObject in %s";
    static final String NULLEXCEPTION = "Null Exception!!";
    static final String ERR_MALFORMED_URL_RESOLUTION = "Please enter a valid file name.";
    static final String ERR_URI_SYNTAX = "URI Syntax error: %s, detail: %s";
    static final String ERR_URL_CONNECT = "Attempted to open an connection, but returned a null"
            + " connection object for URL: %s";
    static final String ERROR_RESOLUTION = "Validate input data.";
    static final String ERROR_REASON = "Check the values passed.";
    static final String ERRORMSG = "Technical error has occured while processing the input.";
    static final String ILLEGAL_ARGS_EXE = "Indicates that, the input has been passed an illegal "
            + "or inappropriate value.";
    static final String INVOCATION_TARGET_EXE = "Trying to execute the unknow function call or "
            + "method parameter types were wrong.";
}