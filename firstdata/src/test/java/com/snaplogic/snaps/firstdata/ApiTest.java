/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc.  All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.firstdata;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.snaplogic.account.api.Account;
import com.snaplogic.snap.api.DocumentUtility;
import com.snaplogic.snap.api.impl.DocumentUtilityImpl;
import com.snaplogic.snaps.firstdata.bean.AccountBean;

import org.junit.Before;

/**
 * FirstData Snap unit test setup
 *
 * @author svatada
 */
public abstract class ApiTest {
    protected Injector injector;
    protected Account<AccountBean> account = new MockAccount();
    protected Account<AccountBean> invalidAccount = new MockInvalidAccount();

    protected static class MockAccount extends FirstDataCustomAccount {
        MockAccount() {
            serviceURL = "https://prod.dw.us.fdcnet.biz/rc";
            serviceWSDLURL = "https://prod.dw.us.fdcnet.biz/rc.wsdl";
            appID = "RAPIDCONNECTVXN";
            serviceID = 160;
            datawireId = "00010892579160084007";
            groupId = "10001";
            terminalId = "RNO001";
            authString = "X0000001000010001|00000001";
            timeOut = 30;
        }
    }

    protected static class MockInvalidAccount extends FirstDataCustomAccount {
        MockInvalidAccount() {
            serviceURL = "https://prod.dw.us.fdcnet.biz/rc";
            serviceWSDLURL = "https://prod.dw.us.fdcnet.biz/rc.wsdl";
            appID = "RAPIDCONNECTVXN";
            serviceID = 160;
            datawireId = "00010892579160084007";
            groupId = "10001";
            terminalId = "RNO001";
            authString = "10001RCTST0000005071|00000001";
            timeOut = 30;
        }
    }

    @Before
    public void setup() {
        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                DocumentUtilityImpl documentUtility = new DocumentUtilityImpl("mockSnap");
                bind(DocumentUtility.class).toInstance(documentUtility);
            }
        });
    }
}