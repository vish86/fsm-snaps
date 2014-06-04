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

import com.snaplogic.account.api.Account;
import com.snaplogic.account.api.AccountType;
import com.snaplogic.account.api.capabilities.AccountCategory;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snaps.firstdata.bean.AccountBean;

import static com.snaplogic.snaps.firstdata.Messages.*;

/**
 * custom account for the FirstData snap.
 *
 * @author svatada
 */
@General(title = CUSTOM_ACCOUNT_TITLE)
@Version(snap = 1)
@AccountCategory(type = AccountType.CUSTOM)
public class FirstDataCustomAccount implements Account<AccountBean> {
	private String serviceURL;
	private String serviceWSDLURL;
	private String appID;
	private int serviceID;

	@Override
	public void defineProperties(PropertyBuilder propertyBuilder) {
		propertyBuilder.describe(APP_ID, APP_ID_LABEL, APP_ID_DESC).required()
				.add();
		propertyBuilder.describe(SERVICE_ID, SERVICE_ID_LABEL, SERVICE_ID_DESC)
				.required().add();
		propertyBuilder
				.describe(SERVICE_URL, SERVICE_URL_LABEL, SERVICE_URL_DESC)
				.required().add();
		propertyBuilder.describe(WSDL_URL, WSDL_URL_LABEL, WSDL_URL_DESC)
				.required().add();
	}

	@Override
	public void configure(PropertyValues propertyValues) {
		appID = propertyValues.get(APP_ID);
		serviceID = propertyValues.get(SERVICE_ID);
		serviceURL = propertyValues.get(SERVICE_URL);
		serviceWSDLURL = propertyValues.get(WSDL_URL);
	}

	@Override
	public AccountBean connect() throws ExecutionException {
		return new AccountBean().setAppID(appID).setServiceID(serviceID)
				.setServiceURL(serviceURL).setServiceWSDLURL(serviceWSDLURL);
	}

	@Override
	public void disconnect() throws ExecutionException {
		// NOOP
	}
}