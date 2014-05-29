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
package com.snaplogic.snaps.uniteller.bean;

/**
 * Simple java bean class to hold account information.
 *
 * @author svatada
 */
public class AccountBean {
    private String configFilePath;
    private String securityPermFilePath;

    /**
     * @return configFilePath
     */
    public String getConfigFilePath() {
        return configFilePath;
    }

    /**
     * @return securityPermFilePath
     */
    public String getSecurityPermFilePath() {
        return securityPermFilePath;
    }

    /**
     * @param configFilePath
     * @return AccountBean
     */
    public AccountBean setConfigFilePath(String configFilePath) {
        this.configFilePath = configFilePath;
        return this;
    }

    /**
     * @param securityFilePath
     * @return AccountBean
     */
    public AccountBean setSecurityFilePath(String securityFilePath) {
        this.securityPermFilePath = securityFilePath;
        return this;
    }
}