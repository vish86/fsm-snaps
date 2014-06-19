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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.BILL_PYMT_TXN_IND_TYPE_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.DEFERRED_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.INSTALLMENT_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.RECURRING_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.SINGLE_ELT;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = BILL_PYMT_TXN_IND_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum BillPymtTxnIndType {
    @XmlEnumValue(SINGLE_ELT)
    SINGLE(SINGLE_ELT), @XmlEnumValue(RECURRING_ELT)
    RECURRING(RECURRING_ELT), @XmlEnumValue(INSTALLMENT_ELT)
    INSTALLMENT(INSTALLMENT_ELT), @XmlEnumValue(DEFERRED_ELT)
    DEFERRED(DEFERRED_ELT);
    private final String value;

    BillPymtTxnIndType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static BillPymtTxnIndType fromValue(String v) {
        for (BillPymtTxnIndType c : BillPymtTxnIndType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}