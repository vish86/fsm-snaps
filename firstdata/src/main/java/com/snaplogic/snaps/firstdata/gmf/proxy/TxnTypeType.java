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
package com.snaplogic.snaps.firstdata.gmf.proxy;

import javax.xml.bind.annotation.XmlEnum;
import javax.xml.bind.annotation.XmlEnumValue;
import javax.xml.bind.annotation.XmlType;

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.*;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = TXN_TYPE_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum TxnTypeType {
    @XmlEnumValue(ACTIVATION2)
    ACTIVATION(ACTIVATION2), @XmlEnumValue(AUTHORIZATION2)
    AUTHORIZATION(AUTHORIZATION2), @XmlEnumValue(BALANCE_INQUIRY2)
    BALANCE_INQUIRY(BALANCE_INQUIRY2), @XmlEnumValue(BALANCE_LOCK2)
    BALANCE_LOCK(BALANCE_LOCK2), @XmlEnumValue(CASHOUT2)
    CASHOUT(CASHOUT2), @XmlEnumValue(CASHOUT_ACTIVE_STATUS2)
    CASHOUT_ACTIVE_STATUS(CASHOUT_ACTIVE_STATUS2), @XmlEnumValue(COMPLETION2)
    COMPLETION(COMPLETION2), @XmlEnumValue(FILE_DOWNLOAD2)
    FILE_DOWNLOAD(FILE_DOWNLOAD2), @XmlEnumValue(HOST_TOTALS2)
    HOST_TOTALS(HOST_TOTALS2), @XmlEnumValue(LOAD2)
    LOAD(LOAD2), @XmlEnumValue(REDEMPTION2)
    REDEMPTION(REDEMPTION2), @XmlEnumValue(REDEMPTION_UNLOCK2)
    REDEMPTION_UNLOCK(REDEMPTION_UNLOCK2), @XmlEnumValue(REFUND2)
    REFUND(REFUND2), @XmlEnumValue(RELOAD2)
    RELOAD(RELOAD2), @XmlEnumValue(SALE2)
    SALE(SALE2), @XmlEnumValue(TA_KEY_REQUEST2)
    TA_KEY_REQUEST(TA_KEY_REQUEST2), @XmlEnumValue(TA_TOKEN_REQUEST2)
    TA_TOKEN_REQUEST(TA_TOKEN_REQUEST2), @XmlEnumValue(VERIFICATION2)
    VERIFICATION(VERIFICATION2);

    final String value;

    TxnTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static TxnTypeType fromValue(String v) {
        for (TxnTypeType c : TxnTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}