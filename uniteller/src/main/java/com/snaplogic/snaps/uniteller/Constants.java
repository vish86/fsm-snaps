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
    static final String FORMAT_STRING = "{}:{}";
    static final String DATETIME_FORMAT = "yyyy-MM-dd\'T\'HH:mm:ss";
    static final String DATE_FORMAT = "yyyy-MM-dd";
    static final String INT = "int";
    static final String PROXY_BENE_ID_NUM = "1111111";
    static final String PROXY_BENE_ID_TYPE = "003";
    static final String PROXY_NOTIF_REF_NUM2 = "7";
    static final String PROXY_PAYING_AGENT_OPT = "OP1001";
    static final String PROXY_PAYING_AGENT_BR_CODE = "BANORTE-00001";
    static final String PROXY_PAYING_AGENT = "BANORTE";
    static final String PROXY_NOTIF_TYPE2 = "002";
    static final String PROXY_NOTIF_TYPE1 = "010";
    static final String PROXY_DESC = "DEMO NOTIFICATION ITEM";
    static final String PROXY_NOTIF_REF_NUM1 = "4";
    static final String PROXY_MSG_TEXT_PAID = "DEMO NOTIFICATION ITEM - Transaction paid";
    static final String PROXY_MSG_TEXT_OFAC_UNHOLD = "DEMO NOTIFICATION ITEM - Transaction OFAC unhold";
    static final String PROXY_MSG_TEXT = "DEMO NOTIFICATION ITEM - Transaction OFAC hold";
    static final String PROXY_NOTIF_REF_NUM = "008";
    static final String PROXY_SENDER_REF_NUM = "22222";
    static final String PROXY_SENDER_POSTAL_CODE = "12345";
    static final String PROXY_SENDER_PHONE = "9876543210";
    static final String PROXY_SENDER_FNAME = "JOHN";
    static final String PROXY_SENDER_EMAIL = "john.doe@email.com";
    static final String PROXY_SENDER_CITY = "ROCHELLE PARK";
    static final String PROXY_SENDER_ADDR = "321 BROADWAY";
    static final String PROXY_PAYMENT_TYPE = "001";
    static final String PROXY_LOCATION = "LOCATION X";
    static final String PROXY_BENE_STATE = "NLE";
    static final String PROXY_BENE_REF_NUM = "11111";
    static final String PROXY_POSTAL_CODE = "00000";
    static final String PROXY_PHONE = "1234567890";
    static final String PROXY_BENE_LNAME = "DOE";
    static final String PROXY_BENE_FNAME = "JANE";
    static final String PROXY_BENE_EMAIL = "jane.doe@email.com";
    static final String PROXY_BENE_COUNTRY = "MX";
    static final String PROXY_BENE_CITY = "MONTERREY";
    static final String PROXY_BENE_ADDR1 = "123 MAIN STREET";
    static final String PROXY_SENDER_STATE = "NJ";
    static final String PROXY_CURRECTRY_CODE = "US";
    static final String PROXY_TX_STATIS_PAYABLE = "PAYABLE";
    static final String PROXY_NOTIFICATION_NUM = "1";
    static final String PROXY_TX_STATUS = "PENDING";
    static final String PROXY_PRIMARY_CURRECNCY = "MXN";
    static final String PROXY_CURRENTCY = "USD";
    static final String PROXY_DISCLAIMER = "Demo";
    static final String PROXY_MODE = "DEMO MODE";
    static final String PROXY_SUCCESS_STATUS = "Success - DEMO MODE";
    static final String PROXY_SUCCESS_RESPONSE = "00000000";
    static final String PROXY_TX_IDENTIFIER = "9999999999";
    static final int PROXY_COUNT = 9999;
    static final String PROXY_PAYMENT_LOCATION = "DEMO";
    static final String DS_ALG = "MD5";
    static final String ENC_ALG = "SHA1PRNG";
    static final String NONCE = "ItSihNsYzB495QQ6J5MXr0/O1rI=";
    static final String FOLIO_CREATION_API_URL_SUFFIX = "FOLIO_CREATION_API_URL_SUFFIX";
    static final String TX_CONTEXT_PREFIX = "UFC";
    static final String DEFAULT_ORGANIZATION_ID = "DEFAULT_ORGANIZATION_ID";
    static final String DEFAULT_COMPANY_ID = "DEFAULT_COMPANY_ID";
    static final String DEFAULT_MACHINE_ID = "DEFAULT_MACHINE_ID";
    static final String CHANGE_PASSWORD_ERROR_CODE = "19905001";
    static final String WARNING_INDICATOR = "02";
    static final String BLANK = "";
    static final String DUMMY_PASS = "DUMMY_PASS";
    static final int MIN_PASSWORD_LENGTH = 8;
    static final int MAX_PASSWORD_LENGTH = 16;
    static final String RIMED_OUT_ERROR = "29806002";
    static final String COMM_SWITCH_ERROR = "29806001";
    static final String MACHINE_ID_ERROR = "29908003";
    static final String CONFIG_PARAM_MISSING_ERROR = "29908002";
    static final String CONFIG_PARAM_FORMAT_ERROR = "29908001";
    static final String SECURITY_MGR_ERROR = "29906004";
    static final String CONFIG_MGR_ERROR = "29906003";
    static final String GENERAL_SYSTEM_ERROR = "29906001";
    static final String REGEX_PATTERN_PROTOCOL = "^sldb:///";
    static final Pattern PATTERN = Pattern.compile(REGEX_PATTERN_PROTOCOL);
    static final String CHANGE_PASSWORD = "ChangePassword";
    static final String IS_REGEX = "^is[A-Z].*";
    static final String GET_REGEX = "^get[A-Z].*";
    static final String FILE_PROTOCOL = "file:///%s";
    static final String REGEX_SET = "^set[A-Z].*";
    static final String DOCNUM_TAG = "DocInfo";
    static final String ERROR_TAG = "Error";
    static final String STATUS_CODE_TAG = "statusCode";
    static final String RESOLUTION_TAG = "Resolution";
    static final String REASON_TAG = "Reason";
    static final String MESSAGE_TAG = "Message";
    static final String UFS_FOLIO_CREATION_CLIENT_PKG_URI = "com.snaplogic.snaps.uniteller.CustomUFSFolioCreationClient";
    static final String UNITELLER_PKG_PREFIX = "com.uniteller.support.foliocreationclient.UFS";
    static final String UNITELLER_CHANGE_PASS_PKG = "com.uniteller.support.common.ChangePassword";
    static final String UNITELLER_REQ_TAG = "Req";
    static final String UNITELLER_RESP_TAG = "Resp";

    static enum SnapsModel {
        Create("Create"), Update("Update"), Read("Read"), Delete("Delete");
        private final String resource;

        private SnapsModel(String resource) {
            this.resource = resource;
        }

        @Override
        public String toString() {
            return resource;
        }
    }

    static final HashMap<String, String> EXCEPTION_ERROR_MAP = new HashMap<String, String>() {
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
            put(GENERAL_SYSTEM_ERROR, "General client side system error");
            put(CONFIG_MGR_ERROR, "Error using config manager");
            put(SECURITY_MGR_ERROR, "Error using cecurity manager");
            put(CONFIG_PARAM_FORMAT_ERROR, "Invalid value or format for a configuration parameter");
            put(CONFIG_PARAM_MISSING_ERROR, "Missing configuration parameter");
            put(MACHINE_ID_ERROR, "Machine ID not defined in security");
            put(COMM_SWITCH_ERROR, "Problem communicating with the switch");
            put(RIMED_OUT_ERROR, "Request timed out");
        }
    };

    static final ImmutableMap<String, ImmutableSet<String>> RESOUCE_LIST = ImmutableMap.of(
            SnapsModel.Create.toString(), ImmutableSet.of("CreateTx", "CreateSCTx",
                    "ConfirmSCTx", "NotificationConfirm", "QuickQuote"), SnapsModel.Read
                    .toString(), ImmutableSet.of("Notification", "GetTxDetails"),
            SnapsModel.Update.toString(), ImmutableSet.of("InfoModify", CHANGE_PASSWORD),
            SnapsModel.Delete.toString(), ImmutableSet.of("CancelTx"));
}