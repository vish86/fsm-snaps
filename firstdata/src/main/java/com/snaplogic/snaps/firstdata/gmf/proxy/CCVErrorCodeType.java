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
import javax.xml.bind.annotation.XmlType;

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CCV_ERROR_CODE_TYPE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlType(name = CCV_ERROR_CODE_TYPE, namespace = NAMESPACE)
@XmlEnum
public enum CCVErrorCodeType {
    Y;

    public String value() {
        return name();
    }

    public static CCVErrorCodeType fromValue(String v) {
        return valueOf(v);
    }
}