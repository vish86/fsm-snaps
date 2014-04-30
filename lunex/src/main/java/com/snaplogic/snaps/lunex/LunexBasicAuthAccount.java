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

import static com.snaplogic.snaps.lunex.constants.Messages.LUNEX_BASIC_AUTH_ACCOUNT_TITLE;

import com.snaplogic.account.api.AccountType;
import com.snaplogic.account.api.capabilities.AccountCategory;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.snap.api.account.basic.BasicAuthAccount;
import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snap.api.capabilities.Version;
import com.snaplogic.snaps.lunex.bean.AccountBean;

/**
 * Provides a basic auth account for the Lunex snap.
 * 
 * @author Svatada
 */
@General(title = LUNEX_BASIC_AUTH_ACCOUNT_TITLE)
@Version(snap = 1)
@AccountCategory(type = AccountType.BASIC_AUTH)
public class LunexBasicAuthAccount extends BasicAuthAccount<AccountBean> {

	@Override
	public AccountBean connect() throws ExecutionException {
		return new AccountBean(username, password);
	}

	@Override
	public void disconnect() throws ExecutionException {
		// NO OP
	}
}