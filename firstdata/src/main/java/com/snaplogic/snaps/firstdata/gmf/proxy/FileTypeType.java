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

/**
 * This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
 * Implementation. Any modifications to this file will be lost upon recompilation of the source
 * schema.
 * 
 * @author svatada
 */
@XmlType(name = "FileTypeType", namespace = "com/firstdata/Merchant/gmfV2.08")
@XmlEnum
public enum FileTypeType {

    @XmlEnumValue("EMV2KEY")
    EMV_2_KEY("EMV2KEY");
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
