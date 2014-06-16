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

/**
 * This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
 * Implementation. Any modifications to this file will be lost upon recompilation of the source
 * schema.
 * 
 * @author svatada
 */
@XmlType(name = "TxnTypeType", namespace = "com/firstdata/Merchant/gmfV2.08")
@XmlEnum
public enum TxnTypeType {

    @XmlEnumValue("Activation")
    ACTIVATION("Activation"), @XmlEnumValue("Authorization")
    AUTHORIZATION("Authorization"), @XmlEnumValue("BalanceInquiry")
    BALANCE_INQUIRY("BalanceInquiry"), @XmlEnumValue("BalanceLock")
    BALANCE_LOCK("BalanceLock"), @XmlEnumValue("Cashout")
    CASHOUT("Cashout"), @XmlEnumValue("CashoutActiveStatus")
    CASHOUT_ACTIVE_STATUS("CashoutActiveStatus"), @XmlEnumValue("Completion")
    COMPLETION("Completion"), @XmlEnumValue("FileDownload")
    FILE_DOWNLOAD("FileDownload"), @XmlEnumValue("HostTotals")
    HOST_TOTALS("HostTotals"), @XmlEnumValue("Load")
    LOAD("Load"), @XmlEnumValue("Redemption")
    REDEMPTION("Redemption"), @XmlEnumValue("RedemptionUnlock")
    REDEMPTION_UNLOCK("RedemptionUnlock"), @XmlEnumValue("Refund")
    REFUND("Refund"), @XmlEnumValue("Reload")
    RELOAD("Reload"), @XmlEnumValue("Sale")
    SALE("Sale"), @XmlEnumValue("TAKeyRequest")
    TA_KEY_REQUEST("TAKeyRequest"), @XmlEnumValue("TATokenRequest")
    TA_TOKEN_REQUEST("TATokenRequest"), @XmlEnumValue("Verification")
    VERIFICATION("Verification");
    private final String value;

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
