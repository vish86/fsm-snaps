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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ENCRPT_TRGT_TYPE_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PAN2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TRACK1;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TRACK2;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = ENCRPT_TRGT_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum EncrptTrgtType {
    @XmlEnumValue(TRACK1)
    TRACK_1(TRACK1), @XmlEnumValue(TRACK2)
    TRACK_2(TRACK2), @XmlEnumValue(PAN2)
    PAN(PAN2);

    private final String value;

    EncrptTrgtType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EncrptTrgtType fromValue(String v) {
        for (EncrptTrgtType c : EncrptTrgtType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}