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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.EMV2KEY;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.FILE_TYPE_TYPE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = FILE_TYPE_TYPE, namespace = NAMESPACE)
@XmlEnum
public enum FileTypeType {
    @XmlEnumValue(EMV2KEY)
    EMV_2_KEY(EMV2KEY);

    private final String value;

    FileTypeType(String v) {
        value = v;
    }

    public String value() {
        return value;
    }

    public static FileTypeType fromValue(String v) {
        for (FileTypeType c : FileTypeType.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        throw new IllegalArgumentException(v);
    }
}