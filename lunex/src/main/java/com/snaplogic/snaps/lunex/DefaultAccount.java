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
package com.snaplogic.snaps.lunex;

import com.snaplogic.account.api.Account;
import com.snaplogic.account.api.AccountType;
import com.snaplogic.account.api.capabilities.AccountCategory;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.common.properties.builders.PropertyBuilder;
import com.snaplogic.snap.api.PropertyValues;
import com.snaplogic.snaps.lunex.bean.AccountBean;

/**
 * Represent a default account which provides an null return for account.
 *
 * @author svatada
 */
@AccountCategory(type = AccountType.NONE)
public class DefaultAccount implements Account<AccountBean> {
    @Override
    public void configure(final PropertyValues propertyValues) {
        // NO OP
    }

    @Override
    public AccountBean connect() throws ExecutionException {
        return null;
    }

    @Override
    public void defineProperties(final PropertyBuilder propertyBuilder) {
        // NO OP
    }

    @Override
    public void disconnect() throws ExecutionException {
        // NO OP
    }
}