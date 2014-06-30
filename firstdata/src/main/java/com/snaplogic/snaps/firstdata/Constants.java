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

import java.util.HashMap;
import java.util.Map;

/**
 * This class holds all the static final variables and enum types.
 *
 * @author svatada
 */
public class Constants {
    static final String PROP_SOAP_BINDING = "allowedBindings";
    static final String PROP_TIMEOUT = "timeout";
    static final String PROP_DEFAULT_VALUE = "defaultValue";
    static final String PROP_USE_DEFAULT_VALUE = "useDefaultValue";
    final static String DEFAULT_INPUT_VIEW_0 = "input0";
    final static String DEFAULT_INPUT_VIEW_1 = "input1";
    final static String DEFAULT_OUTPUT_VIEW_0 = "output0";
    static final String KEY_ERROR = "error";
    static final String KEY_ENVELOPE = "envelope";
    static final String KEY_STRIPPED_ENVELOPE = "stripped_envelope";
    static final String NAMESPACE_S2 = "S:";
    static final String NAMESPACE_S1 = ":S";
    static final String XML_ENCODING = "UTF-8";
    static final String NAMESPACE_NS2 = "ns2:";
    static final String NAMESPACE_NS1 = ":ns2";
    static final String W3C_NAMESPACE = "https://com/firstdata/Merchant/gmfV2.08";
    static final String FD_NAMESPACE = "com/firstdata/Merchant/gmfV2.08";
    static final String GMF_TAG = "GMF";
    static final String GMF_MESSAGE_VARIANTS_TAG = "gmfMessageVariants";
    static final String PROD_CODE_DET_GRP = "ProdCodeDetGrp";
    static final String ADDTL_AMT_GRP = "AddtlAmtGrp";
    static final String GETTER = "get%s";
    static final String INT = "int";
    static final String CLIENT_REF_PREFIX = "00";
    static final String DELIMITER = "|";
    static final String GET_COMMON_GRP = "getCommonGrp";
    static final String SETTER = "set%s";
    static final String GMF_MESSAGE_VARIANTS = "com.snaplogic.snaps.firstdata.gmf.proxy.GMFMessageVariants";
    static final String TYPES_METHOD = "fromValue";
    static final String AMP_SYM = "&";
    static final String AMP = "&amp;";
    static final String LT_SYM = "<";
    static final String LT = "&lt;";
    static final String GT_SYM = ">";
    static final String GT = "&gt;";
    static final String XML_ESCAPE = "xml_escape";
    static final String STATUS_OK = "OK";
    static final String VERSION = "3";
    static final String CDATA = "cdata";
    static final String TIME_OUT = "30";
    static final String TYPE = "Type";
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
    static final ImmutableSet<String> RESOUCE_LIST = ImmutableSet.of("AdminRequest",
            "CheckRequest", "CreditRequest", "DebitRequest", "EBTRequest", "GenPrepaidRequest",
            "PinlessDebitRequest", "PrepaidRequest", "PrivateLabelRequest", "TARequest",
            "VoidTOReversalRequest");
    static final Map<String, String> GMF_VARIENTS = new HashMap<String, String>() {
        {
            put("TARequest", "TransArmorRequest");
            put("VoidTOReversalRequest", "ReversalRequest");
        }
    };
}