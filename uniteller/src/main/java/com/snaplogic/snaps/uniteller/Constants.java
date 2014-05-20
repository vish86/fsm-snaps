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
package com.snaplogic.snaps.uniteller;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * This class holds all the static final variables and enum types.
 * 
 * @author svatada
 */
public class Constants {
    static final String ERROR_TAG = "Error";
    static final String STATUS_CODE_TAG = "statusCode";
    static final String RESOLUTION_TAG = "Resolution";
    static final String REASON_TAG = "Reason";
    static final String MESSAGE_TAG = "Message";
    static final String UNITELLER_PKG_PREFIX = "com.uniteller.support.foliocreationclient.UFS";
    static final String UNITELLER_REQ_TAG = "Req";
    static final String UNITELLER_RESP_TAG = "Resp";

    static enum SnapType {
        Create("Create"), Update("Update"), Read("Read"), Delete("Delete");
        private final String resource;

        private SnapType(String resource) {
            this.resource = resource;
        }

        @Override
        public String toString() {
            return resource;
        }
    }

    static final ImmutableMap<String, ImmutableSet<String>> RESOUCE_LIST = ImmutableMap.of(
            SnapType.Create.toString(),
            ImmutableSet.of("CreateSCTx", "ConfirmSCTx", "NotificationConfirm", "QuickQuote"),
            SnapType.Read.toString(), ImmutableSet.of("GetNotification", "GetTxDetails"),
            SnapType.Update.toString(), ImmutableSet.of("InfoModify", "ChangePassword"),
            SnapType.Delete.toString(), ImmutableSet.of("CancelTx"));
}