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
import com.uniteller.support.common.UFSConfigMgrException;
import com.uniteller.support.common.UFSSecurityMgrException;
import com.uniteller.support.common.UFSUnknownMachineIdException;
import com.uniteller.support.communication.UFSCommunicationException;
import com.uniteller.support.communication.UFSRequestTimedOut;
import com.uniteller.support.util.UFSGeneralException;
import com.uniteller.support.util.UFSInvalidValueForConfigParam;
import com.uniteller.support.util.UFSMissingConfigParam;
import com.uniteller.support.util.UFSSystemError;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.regex.Pattern;

/**
 * This class holds all the static final variables and enum types.
 * 
 * @author svatada
 */
public class Constants {
    static final String RIMED_OUT_ERROR = "29806002";
    static final String COMM_SWITCH_ERROR = "29806001";
    static final String MACHINE_ID_ERROR = "29908003";
    static final String CONFIG_PARAM_MISSING_ERROR = "29908002";
    static final String CONFIG_PARAM_FORMAT_ERROR = "29908001";
    static final String SECURITY_MGR_ERROR = "29906004";
    static final String CONFIG_MGR_ERROR = "29906003";
    static final String GENERAL_SYSTEM_ERROR = "29906001";
    static final String REGEX_PATTERN_PROTOCOL = "^sldb:///"
            + "^ftps://|^hdfs://|^file:///";
    static final Pattern PATTERN = Pattern.compile(REGEX_PATTERN_PROTOCOL);
    static final String CHANGE_PASSWORD = "ChangePassword";
    static final String IS_REGEX = "^is[A-Z].*";
    static final String GET_REGEX = "^get[A-Z].*";
    static final String UFS_FOLIO_CREATION_CLIENT_PKG_URI = "com.snaplogic.snaps.uniteller.CustomUFSFolioCreationClient";
    static final String FILE_PROTOCOL = "file:///%s";
    static final String REGEX_SET = "^set[A-Z].*";
    static final String ERROR_TAG = "Error";
    static final String STATUS_CODE_TAG = "statusCode";
    static final String RESOLUTION_TAG = "Resolution";
    static final String REASON_TAG = "Reason";
    static final String MESSAGE_TAG = "Message";
    static final String UNITELLER_PKG_PREFIX = "com.uniteller.support.foliocreationclient.UFS";
    static final String UNITELLER_CHANGE_PASS_PKG = "com.uniteller.support.common.ChangePassword";
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

    static final HashMap<String, String> exceptionErrorMap = new HashMap() {
        {
            put(UFSGeneralException.class.getName(), GENERAL_SYSTEM_ERROR);
            put(UFSSystemError.class.getName(), GENERAL_SYSTEM_ERROR);
            put(UFSInvalidValueForConfigParam.class.getName(), CONFIG_PARAM_FORMAT_ERROR);
            put(UFSMissingConfigParam.class.getName(), CONFIG_PARAM_MISSING_ERROR);
            put(UFSUnknownMachineIdException.class.getName(), MACHINE_ID_ERROR);
            put(UFSCommunicationException.class.getName(), COMM_SWITCH_ERROR);
            put(UFSRequestTimedOut.class.getName(), RIMED_OUT_ERROR);
            put(UFSConfigMgrException.class.getName(), CONFIG_MGR_ERROR);
            put(UFSSecurityMgrException.class.getName(), SECURITY_MGR_ERROR);
        }
    };

    static final Hashtable<String, String> UFS_ERROR_MSG_LIST = new Hashtable<String, String>() {
        {
            put(GENERAL_SYSTEM_ERROR, "General Client Side System Error");
            put(CONFIG_MGR_ERROR, "Error using Config Manager");
            put(SECURITY_MGR_ERROR, "Error using Security Manager");
            put(CONFIG_PARAM_FORMAT_ERROR, "Invalid value or format for a configuration parameter");
            put(CONFIG_PARAM_MISSING_ERROR, "Missing configuration parameter");
            put(MACHINE_ID_ERROR, "Machine ID not defined in Security");
            put(COMM_SWITCH_ERROR, "Problem communicating with the Switch");
            put(RIMED_OUT_ERROR, "Request Timed Out");
        }
    };

    static final ImmutableMap<String, ImmutableSet<String>> RESOUCE_LIST = ImmutableMap.of(
            SnapType.Create.toString(), ImmutableSet.of("CreateTx", "CreateSCTx", "ConfirmSCTx",
                    "NotificationConfirm", "QuickQuote"), SnapType.Read.toString(), ImmutableSet
                    .of("Notification", "GetTxDetails"), SnapType.Update.toString(), ImmutableSet
                    .of("InfoModify", CHANGE_PASSWORD), SnapType.Delete.toString(), ImmutableSet
                    .of("CancelTx"));
}