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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.B2B_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.BILL_PAYMENT_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ECOM_AGG_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.MRKT_SPECIFIC_DATA_IND_TYPE_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = MRKT_SPECIFIC_DATA_IND_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum MrktSpecificDataIndType {
    @XmlEnumValue(BILL_PAYMENT_ELT)
    BILL_PAYMENT(BILL_PAYMENT_ELT), @XmlEnumValue(ECOM_AGG_ELT)
    ECOM_AGG(ECOM_AGG_ELT), @XmlEnumValue(B2B_ELT)
    B_2_B(B2B_ELT);
    private final String value;

    MrktSpecificDataIndType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static MrktSpecificDataIndType fromValue(String v) {
        for (MrktSpecificDataIndType c : MrktSpecificDataIndType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}