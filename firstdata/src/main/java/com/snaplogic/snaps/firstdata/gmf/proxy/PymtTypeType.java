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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CHECK2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CREDIT2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.DEBIT2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.EBT2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PL_DEBIT2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PREPAID2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PVT_LABL2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PYMT_TYPE_TYPE_NAME;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = PYMT_TYPE_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum PymtTypeType {
    @XmlEnumValue(CREDIT2)
    CREDIT(CREDIT2), @XmlEnumValue(DEBIT2)
    DEBIT(DEBIT2), @XmlEnumValue(PL_DEBIT2)
    PL_DEBIT(PL_DEBIT2), @XmlEnumValue(EBT2)
    EBT(EBT2), @XmlEnumValue(CHECK2)
    CHECK(CHECK2), @XmlEnumValue(PREPAID2)
    PREPAID(PREPAID2), @XmlEnumValue(PVT_LABL2)
    PVT_LABL(PVT_LABL2);

    final String value;

    PymtTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static PymtTypeType fromValue(String v) {
        for (PymtTypeType c : PymtTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}