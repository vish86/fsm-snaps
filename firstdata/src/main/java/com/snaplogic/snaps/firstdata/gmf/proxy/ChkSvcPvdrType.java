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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CHK_SVC_PVDR_TYPE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TELE_CHECK2;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = CHK_SVC_PVDR_TYPE, namespace = NAMESPACE)
@XmlEnum
public enum ChkSvcPvdrType {
    @XmlEnumValue(TELE_CHECK2)
    TELE_CHECK(TELE_CHECK2);
    private final String value;

    ChkSvcPvdrType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ChkSvcPvdrType fromValue(String v) {
        for (ChkSvcPvdrType c : ChkSvcPvdrType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}