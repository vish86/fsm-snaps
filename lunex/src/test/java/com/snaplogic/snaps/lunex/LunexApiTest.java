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
package com.snaplogic.snaps.lunex;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.snaplogic.account.api.Account;
import com.snaplogic.snap.api.DocumentUtility;
import com.snaplogic.snap.api.impl.DocumentUtilityImpl;
import com.snaplogic.snaps.lunex.bean.AccountBean;
import com.snaplogic.util.SnapObjectMapper;

import org.junit.Before;

/**
 * Common Lunex API Snap unit test setup
 *
 * @author svatada
 */
public abstract class LunexApiTest {
    private static final SnapObjectMapper MAPPER = new SnapObjectMapper();
    protected Injector injector;
    protected Account<AccountBean> account = new MockAccount();

    protected static class MockAccount extends LunexBasicAuthAccount {
        MockAccount() {
            username = "northgate";
            password = "northgatetest";
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
