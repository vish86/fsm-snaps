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

package com.snaplogic.snaps.checkfree;

import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.snaplogic.snap.api.account.soap.SoapRequestContext;

import org.junit.Ignore;

/**
 * A module containing bindings for SOAP tests
 *
 * @author svatada
 */
@Ignore
public class SoapTestModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(SoapRequestContext.class).in(Scopes.SINGLETON);
    }
}
