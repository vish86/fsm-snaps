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

import com.snaplogic.account.api.Account;
import com.snaplogic.snap.api.soap.HttpContextProvider;
import com.snaplogic.snap.api.soap.HttpRequestAuthProvider;

import org.apache.http.Header;

import java.util.List;

/**
 * Http Context Provider for use with Http requests
 *
 * @author svatada
 */
public class SoapHttpContextProvider implements HttpContextProvider {

    private final List<Header> httpHeader;
    private final boolean isTrustAll;

    public SoapHttpContextProvider(final List<Header> httpHeader, Account account) {
        this(httpHeader, account, false);
    }

    public SoapHttpContextProvider(final List<Header> httpHeader, Account account,
            boolean isTrustAll) {
        this.httpHeader = httpHeader;
        this.isTrustAll = isTrustAll;
    }

    @Override
    public Header[] getHttpHeaders() {
        if (httpHeader != null) {
            return httpHeader.toArray(new Header[0]);
        } else {
            return new Header[0];
        }
    }
    
  	@Override
    public HttpRequestAuthProvider getHttpRequestAuthProvider() {
        return null;
    }

	@Override
	public boolean isTrustAll() {
		return isTrustAll;
	}
}
