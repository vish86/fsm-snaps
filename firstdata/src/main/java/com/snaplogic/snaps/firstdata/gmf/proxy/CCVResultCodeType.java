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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CCV_RESULT_CODE_TYPE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.MATCH2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NOT_PRC2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NOT_PRT2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NOT_PRV2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NO_MTCH2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.UNKNWN2;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = CCV_RESULT_CODE_TYPE, namespace = NAMESPACE)
@XmlEnum
public enum CCVResultCodeType {
    @XmlEnumValue(MATCH2)
    MATCH(MATCH2), @XmlEnumValue(NO_MTCH2)
    NO_MTCH(NO_MTCH2), @XmlEnumValue(NOT_PRC2)
    NOT_PRC(NOT_PRC2), @XmlEnumValue(NOT_PRV2)
    NOT_PRV(NOT_PRV2), @XmlEnumValue(NOT_PRT2)
    NOT_PRT(NOT_PRT2), @XmlEnumValue(UNKNWN2)
    UNKNWN(UNKNWN2);

    private final String value;

    CCVResultCodeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static CCVResultCodeType fromValue(String v) {
        for (CCVResultCodeType c : CCVResultCodeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}