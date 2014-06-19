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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CCV_IND_TYPE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ILLEGIBLE2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NTPRVD2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NT_ON_CRD2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PRVDED2;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = CCV_IND_TYPE, namespace = NAMESPACE)
@XmlEnum
public enum CCVIndType {
    @XmlEnumValue(NTPRVD2)
    NTPRVD(NTPRVD2), @XmlEnumValue(PRVDED2)
    PRVDED(PRVDED2), @XmlEnumValue(ILLEGIBLE2)
    ILLEGIBLE(ILLEGIBLE2), @XmlEnumValue(NT_ON_CRD2)
    NT_ON_CRD(NT_ON_CRD2);

    private final String value;

    CCVIndType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CCVIndType fromValue(String v) {
        for (CCVIndType c : CCVIndType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}