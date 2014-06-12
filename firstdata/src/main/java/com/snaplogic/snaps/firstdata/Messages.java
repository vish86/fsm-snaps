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
	 // Snap labels and descriptions
    static final String CREATE_LABEL = "FirstData - Create";
    static final String SNAP_DESC = "Create snap";
    static final String RESOURCE_LABEL = "Payment type";
    static final String RESOURCE_PROP = "Resource";
    static final String RESOURCE_DESC = "Available FirstData resources";
    //Error msgs
    static final String ERROR_RESOLUTION = "Validate input data.";
    static final String ERROR_REASON = "Check the values passed.";
    static final String ERRORMSG = "Technical error has occured while processing the input.";
   
}