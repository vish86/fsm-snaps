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

import com.snaplogic.account.api.AccountType;
import com.snaplogic.account.api.capabilities.AccountCategory;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.common.properties.SnapProperty;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.account.basic.BasicAuthAccount;
import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snaps.uniteller.bean.AccountBean;

import static com.snaplogic.snaps.uniteller.Messages.*;

/**
 * Provides a basic auth account for the Lunex snap.
 * 
 * @author svatada
 */
@General(title = BASIC_AUTH_ACCOUNT_TITLE)
@Version(snap = 1)
@AccountCategory(type = AccountType.BASIC_AUTH)
public class UniTellerBasicAuthAccount extends BasicAuthAccount<AccountBean> {
	private String configFilePath;
	private String securityFilePath;

	@Override
	public void defineProperties(PropertyBuilder propertyBuilder) {
		propertyBuilder
				.describe(CONFIG_FILEPATH_PROP, CONFIG_FILEPATH_LABEL,
						CONFIG_FILEPATH_DESC).fileBrowsing().add();
		propertyBuilder
				.describe(SECURITY_FILEPATH_PROP, SECURITY_FILEPATH_LABEL,
						SECURITY_FILEPATH_DESC).fileBrowsing().add();
	}

	@Override
	public void configure(PropertyValues propertyValues) {
		configFilePath = propertyValues.get(CONFIG_FILEPATH_PROP);
		securityFilePath = propertyValues.get(SECURITY_FILEPATH_PROP);
	}

	@Override
	public AccountBean connect() throws ExecutionException {
		return new AccountBean().setUsername(username).setPassword(password)
				.setConfigFilePath(configFilePath)
				.setSecurityFilePath(securityFilePath);
	}

	@Override
	public void disconnect() throws ExecutionException {
	}
}