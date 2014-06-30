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
package com.snaplogic.snaps.firstdata.soap.suggestions;

import com.snaplogic.snap.api.soap.HttpContextProvider;
import com.snaplogic.snap.api.soap.HttpRequestAuthProvider;

import org.apache.http.Header;

/**
 * Http Context Provider for use with Http requests
 *
 * @author svatada
 */
public class SoapHttpContextProvider implements HttpContextProvider {

    @SuppressWarnings("javadoc")
    public SoapHttpContextProvider() {

    }

    @Override
    public Header[] getHttpHeaders() {

        return new Header[0];

    }

    @Override
    public HttpRequestAuthProvider getHttpRequestAuthProvider() {
        return null;
    }
}
