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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.KEY_SERIAL_NUM_DATA;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.KEY_SERIAL_NUM_DATA_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PIN_DATA;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PIN_DATA_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PIN_GRP_NAME;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PIN_GRP_NAME, namespace = NAMESPACE, propOrder = { PIN_DATA, KEY_SERIAL_NUM_DATA })
public class PINGrp {
    @XmlElement(name = PIN_DATA_ELT, namespace = NAMESPACE)
    protected String pinData;
    @XmlElement(name = KEY_SERIAL_NUM_DATA_ELT, namespace = NAMESPACE)
    protected String keySerialNumData;

    public String getPINData() {
        return pinData;
    }

    public void setPINData(String value) {
        this.pinData = value;
    }

    public String getKeySerialNumData() {
        return keySerialNumData;
    }

    public void setKeySerialNumData(String value) {
        this.keySerialNumData = value;
    }
}