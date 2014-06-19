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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.AMEX_GRP_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.AM_EX_POS_DATA;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.AM_EX_POS_DATA_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.AM_EX_TRAN_ID;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.AM_EX_TRAN_ID_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = AMEX_GRP_NAME, namespace = NAMESPACE, propOrder = { AM_EX_POS_DATA, AM_EX_TRAN_ID })
public class AmexGrp {
    @XmlElement(name = AM_EX_POS_DATA_ELT, namespace = NAMESPACE)
    protected String amExPOSData;
    @XmlElement(name = AM_EX_TRAN_ID_ELT, namespace = NAMESPACE)
    protected String amExTranID;

    public String getAmExPOSData() {
        return amExPOSData;
    }

    public void setAmExPOSData(String value) {
        this.amExPOSData = value;
    }

    public String getAmExTranID() {
        return amExTranID;
    }

    public void setAmExTranID(String value) {
        this.amExTranID = value;
    }
}