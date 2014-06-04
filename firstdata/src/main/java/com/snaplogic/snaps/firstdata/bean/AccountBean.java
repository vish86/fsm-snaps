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
package com.snaplogic.snaps.firstdata.bean;

/**
 * Simple java bean class to hold account information.
 * 
 * @author svatada
 */
public class AccountBean {
	private String serviceURL;
	private String serviceWSDLURL;
	private String appID;
	private int serviceID;

	public String getServiceURL() {
		return serviceURL;
	}

	public AccountBean setServiceURL(String serviceURL) {
		this.serviceURL = serviceURL;
		return this;
	}

	public String getServiceWSDLURL() {
		return serviceWSDLURL;
	}

	public AccountBean setServiceWSDLURL(String serviceWSDLURL) {
		this.serviceWSDLURL = serviceWSDLURL;
		return this;
	}

	public String getAppID() {
		return appID;
	}

	public AccountBean setAppID(String appID) {
		this.appID = appID;
		return this;
	}

	public int getServiceID() {
		return serviceID;
	}

	public AccountBean setServiceID(int serviceID) {
		this.serviceID = serviceID;
		return this;
	}
}