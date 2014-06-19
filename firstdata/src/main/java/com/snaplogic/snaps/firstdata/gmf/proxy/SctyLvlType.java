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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ENCRPT_TKNIZATN2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.SCTY_LVL_TYPE_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TKNIZATN2;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = SCTY_LVL_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum SctyLvlType {
    @XmlEnumValue(TKNIZATN2)
    TKNIZATN(TKNIZATN2), @XmlEnumValue(ENCRPT_TKNIZATN2)
    ENCRPT_TKNIZATN(ENCRPT_TKNIZATN2);

    final String value;

    SctyLvlType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static SctyLvlType fromValue(String v) {
        for (SctyLvlType c : SctyLvlType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}