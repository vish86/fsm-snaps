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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.IN_COMM2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PREPAID_CLOSED_LOOP2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PROGRAM_ID_TYPE_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.STORED_VALUE_SYSTEMS2;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = PROGRAM_ID_TYPE_NAME, namespace = NAMESPACE)
@XmlEnum
public enum ProgramIDType {
    @XmlEnumValue(PREPAID_CLOSED_LOOP2)
    PREPAID_CLOSED_LOOP(PREPAID_CLOSED_LOOP2), @XmlEnumValue(IN_COMM2)
    IN_COMM(IN_COMM2), @XmlEnumValue(STORED_VALUE_SYSTEMS2)
    STORED_VALUE_SYSTEMS(STORED_VALUE_SYSTEMS2);

    private final String value;

    ProgramIDType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static ProgramIDType fromValue(String v) {
        for (ProgramIDType c : ProgramIDType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}