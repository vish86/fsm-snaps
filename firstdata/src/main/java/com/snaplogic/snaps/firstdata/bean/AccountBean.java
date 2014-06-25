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
    private String datawireId;
    private String groupId;
    private String terminalId;
    private String authString;
    private int timeOut;

    public int getTimeOut() {
        return timeOut;
    }

    public AccountBean setTimeOut(int timeOut) {
        this.timeOut = timeOut;
        return this;
    }

    public String getAuthString() {
        return authString;
    }

    public AccountBean setAuthString(String authString) {
        this.authString = authString;
        return this;
    }

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

    public String getDatawireId() {
        return datawireId;
    }

    public AccountBean setDatawireId(String datawireId) {
        this.datawireId = datawireId;
        return this;
    }

    public String getGroupId() {
        return groupId;
    }

    public AccountBean setGroupId(String groupId) {
        this.groupId = groupId;
        return this;
    }

    public String getTerminalId() {
        return terminalId;
    }

    public AccountBean setTerminalId(String terminalId) {
        this.terminalId = terminalId;
        return this;
    }
}