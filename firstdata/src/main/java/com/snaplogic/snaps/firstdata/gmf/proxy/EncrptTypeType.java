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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ENCRPT_TYPE_TYPE_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.RSA2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.VERIFONE2;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = ENCRPT_TYPE_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum EncrptTypeType {
    RSA(RSA2), @XmlEnumValue(VERIFONE2)
    VERIFONE(VERIFONE2);

    private final String value;

    EncrptTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static EncrptTypeType fromValue(String v) {
        for (EncrptTypeType c : EncrptTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}