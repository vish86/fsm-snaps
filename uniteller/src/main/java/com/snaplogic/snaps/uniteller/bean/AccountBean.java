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
	private String password;
	private String securityPermFilePath;
	private String username;

	public String getConfigFilePath() {
		return configFilePath;
	}

	public String getPassword() {
		return password;
	}

	public String getSecurityPermFilePath() {
		return securityPermFilePath;
	}

	public String getUsername() {
		return username;
	}

	public AccountBean setConfigFilePath(String configFilePath) {
		this.configFilePath = configFilePath;
		return this;
	}

	public AccountBean setPassword(String password) {
		this.password = password;
		return this;
	}

	public AccountBean setSecurityFilePath(String securityFilePath) {
		this.securityPermFilePath = securityFilePath;
		return this;
	}

	public AccountBean setUsername(String username) {
		this.username = username;
		return this;
	}
}