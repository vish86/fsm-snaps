/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2013, SnapLogic, Inc.  All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.soap;

/**
 * TestMessages
 *
 * @author mklumpp
 */
class TestMessages {
    static final String ERR_URL_CONNECT = "Attempted to open a connection, but returned a null " +
            "connection for the path: %s";
    static final String REASON_URL_CONNECT = "Cannot connect to file path";
    static final String RESOLUTION_URL_CONNECT = "Make sure file path syntax is correct";
    static final String TEMPLATE_READ_FAIL = "Failed to read the template file: %s";
    static final String EMPTY_TEMPLATE = "The template is empty";
    static final String CHECK_TEMPLATE_CONTENTS = "Check that the template is valid";
    static final String ERROR_READING_TEMPLATE = "Error in reading the template file";
}