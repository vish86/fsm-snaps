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
import com.snaplogic.common.SnapType;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snaps.firstdata.bean.AccountBean;

import static com.snaplogic.snaps.firstdata.Messages.*;

/**
 * Custom account for the FirstData snap.
 *
 * @author svatada
 */
@General(title = CUSTOM_ACCOUNT_TITLE)
@Version(snap = 1)
@AccountCategory(type = AccountType.CUSTOM)
public class FirstDataCustomAccount implements Account<AccountBean> {
    protected String serviceURL;
    protected String serviceWSDLURL;
    protected String appID;
    protected int serviceID;
    protected String datawireId;
    protected String groupId;
    protected String terminalId;
    protected String authString;
    protected int timeOut;

    @Override
    public void defineProperties(PropertyBuilder propertyBuilder) {
        propertyBuilder.describe(APP_ID, APP_ID_LABEL, APP_ID_DESC).required().add();
        propertyBuilder.describe(SERVICE_ID, SERVICE_ID_LABEL, SERVICE_ID_DESC)
        .type(SnapType.INTEGER).required().add();
        propertyBuilder.describe(SERVICE_URL, SERVICE_URL_LABEL, SERVICE_URL_DESC).required().add();
        propertyBuilder.describe(WSDL_URL, WSDL_URL_LABEL, WSDL_URL_DESC).required().add();
        propertyBuilder.describe(DATAWIRE_ID, DATAWIRE_ID_LABEL, DATAWIRE_ID_DESC).required().add();
        propertyBuilder.describe(GROUP_ID, GROUP_ID_LABEL, GROUP_ID_DESC).required().add();
        propertyBuilder.describe(TERMINAL_ID, TERMINAL_ID_LABEL, TERMINAL_ID_DESC).required().add();
        propertyBuilder.describe(AUTH_STRING, AUTH_STRING_LABEL, AUTH_STRING_DESC).required().add();
        propertyBuilder.describe(TIMEOUT, TIMEOUT_LABEL, TIMEOUT_DESC).required()
        .type(SnapType.INTEGER).defaultValue(30).add();
    }

    @Override
    public void configure(PropertyValues propertyValues) {
        appID = propertyValues.get(APP_ID);
        serviceID = propertyValues.getInt(SERVICE_ID).intValue();
        serviceURL = propertyValues.get(SERVICE_URL);
        serviceWSDLURL = propertyValues.get(WSDL_URL);
        datawireId = propertyValues.get(DATAWIRE_ID);
        groupId = propertyValues.get(GROUP_ID);
        terminalId = propertyValues.get(TERMINAL_ID);
        authString = propertyValues.get(AUTH_STRING);
        timeOut = propertyValues.getInt(TIMEOUT).intValue();
    }

    @Override
    public AccountBean connect() throws ExecutionException {
        return new AccountBean().setAppID(appID).setServiceID(serviceID).setServiceURL(serviceURL)
                .setServiceWSDLURL(serviceWSDLURL).setDatawireId(datawireId).setGroupId(groupId)
                .setTerminalId(terminalId).setAuthString(authString).setTimeOut(timeOut);
    }

    @Override
    public void disconnect() throws ExecutionException {
        // NOOP
    }
}