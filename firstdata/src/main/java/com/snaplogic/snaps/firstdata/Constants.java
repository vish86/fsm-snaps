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

import com.google.common.collect.ImmutableSet;

/**
 * This class holds all the static final variables and enum types.
 * 
 * @author svatada
 */
public class Constants {
    static final String DOCNUM_TAG = "DocInfo";
    static final String ERROR_TAG = "Error";
    static final String STATUS_CODE_TAG = "statusCode";
    static final String RESOLUTION_TAG = "Resolution";
    static final String REASON_TAG = "Reason";
    static final String MESSAGE_TAG = "Message";
    static final String IS_REGEX = "^is[A-Z].*";
    static final String GET_REGEX = "^get[A-Z].*";
    static final String REGEX_SET = "^set[A-Z].*";
    static final String FD_PROXY_PKG_PREFIX = "com.snaplogic.snaps.firstdata.gmf.proxy.";
    static final String FD_REQ_TAG = "Details";
    static final ImmutableSet<String> RESOUCE_LIST = ImmutableSet.of("CheckRequest", "CreditRequest", "DebitRequest",
            "EBTRequest", "PinlessDebitRequest", "PrepaidRequest");
}