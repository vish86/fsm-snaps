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
@XmlType(name = ADD_AMT_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum AddAmtTypeType {
    @XmlEnumValue(CASHBACK_ELT)
    CASHBACK(CASHBACK_ELT), @XmlEnumValue(SURCHRG_ELT)
    SURCHRG(SURCHRG_ELT), @XmlEnumValue(FIRST_AUTH_AMT_ELT)
    FIRST_AUTH_AMT(FIRST_AUTH_AMT_ELT), @XmlEnumValue(PRE_AUTH_AMT_ELT)
    PRE_AUTH_AMT(PRE_AUTH_AMT_ELT), @XmlEnumValue(TOTAL_AUTH_AMT_ELT)
    TOTAL_AUTH_AMT(TOTAL_AUTH_AMT_ELT), @XmlEnumValue(TAX_ELT)
    TAX(TAX_ELT), @XmlEnumValue(BEG_BAL_ELT)
    BEG_BAL(BEG_BAL_ELT), @XmlEnumValue(ENDING_BAL_ELT)
    ENDING_BAL(ENDING_BAL_ELT), @XmlEnumValue(AVAIL_BAL_ELT)
    AVAIL_BAL(AVAIL_BAL_ELT), @XmlEnumValue(LEDGER_BAL_ELT)
    LEDGER_BAL(LEDGER_BAL_ELT), @XmlEnumValue(HOLD_BAL_ELT)
    HOLD_BAL(HOLD_BAL_ELT), @XmlEnumValue(ORIG_REQ_AMT_ELT)
    ORIG_REQ_AMT(ORIG_REQ_AMT_ELT), @XmlEnumValue(OPEN_TO_BUY_ELT)
    OPEN_TO_BUY(OPEN_TO_BUY_ELT);

    private final String value;

    AddAmtTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static AddAmtTypeType fromValue(String v) {
        for (AddAmtTypeType c : AddAmtTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}