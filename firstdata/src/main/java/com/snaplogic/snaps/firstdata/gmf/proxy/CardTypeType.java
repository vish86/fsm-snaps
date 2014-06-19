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
@XmlType(name = CARD_TYPE_TYPE, namespace = NAMESPACE)
@XmlEnum
public enum CardTypeType {
    @XmlEnumValue(AMEX2)
    AMEX(AMEX2), @XmlEnumValue(DINERS2)
    DINERS(DINERS2), @XmlEnumValue(DISCOVER2)
    DISCOVER(DISCOVER2), JCB("JCB"), @XmlEnumValue(MASTER_CARD2)
    MASTER_CARD(MASTER_CARD2), @XmlEnumValue(P_PAY_CL2)
    P_PAY_CL(P_PAY_CL2), @XmlEnumValue(VISA2)
    VISA(VISA2), @XmlEnumValue(GIFT_CARD2)
    GIFT_CARD(GIFT_CARD2), @XmlEnumValue(EXXON2)
    EXXON(EXXON2), @XmlEnumValue(CAR_CARE_ONE2)
    CAR_CARE_ONE(CAR_CARE_ONE2);

    private final String value;

    CardTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CardTypeType fromValue(String v) {
        for (CardTypeType c : CardTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}