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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PARTIAL2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.REVERSAL_IND_TYPE_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TIMEOUT2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TOR_VOID2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.VOID2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.VOID_FR2;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = REVERSAL_IND_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum ReversalIndType {
    @XmlEnumValue(TIMEOUT2)
    TIMEOUT(TIMEOUT2), @XmlEnumValue(VOID2)
    VOID(VOID2), @XmlEnumValue(VOID_FR2)
    VOID_FR(VOID_FR2), @XmlEnumValue(TOR_VOID2)
    TOR_VOID(TOR_VOID2), @XmlEnumValue(PARTIAL2)
    PARTIAL(PARTIAL2);

    final String value;

    ReversalIndType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ReversalIndType fromValue(String v) {
        for (ReversalIndType c : ReversalIndType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}