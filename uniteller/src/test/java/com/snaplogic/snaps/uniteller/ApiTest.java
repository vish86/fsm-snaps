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
package com.snaplogic.snaps.uniteller;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.snaplogic.account.api.Account;
import com.snaplogic.snap.api.DocumentUtility;
import com.snaplogic.snap.api.impl.DocumentUtilityImpl;
import com.snaplogic.snaps.uniteller.bean.AccountBean;

import org.junit.Before;

/**
 * UniTeller Snap unit test setup
 * 
 * @author svatada
 */
public abstract class ApiTest {
    protected Injector injector;
    protected Account<AccountBean> account = new MockAccount();
    protected Account<AccountBean> invalidAccount = new MockInvalidAccount();

    protected static class MockAccount extends UniTellerBasicAuthAccount {
        MockAccount() {
            configFilePath = "data/UFSFCLConfig.properties";
            securityFilePath = "data/UFSSecurity";
        }
    }

    protected static class MockInvalidAccount extends UniTellerBasicAuthAccount {
        MockInvalidAccount() {
            configFilePath = "data/UFSFCLConfig1.properties";
            securityFilePath = "data/UFSSecurity1";
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
