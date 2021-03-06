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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.HOST_TOT_GRP_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NET_SETTLE_AMT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NET_SETTLE_AMT_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PASSWORD;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PASSWORD_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TOT_REQ_DATE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TOT_REQ_DATE_ELT;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = HOST_TOT_GRP_NAME, namespace = NAMESPACE, propOrder = { TOT_REQ_DATE, PASSWORD,
        NET_SETTLE_AMT })
public class HostTotGrp {
    @XmlElement(name = TOT_REQ_DATE_ELT, namespace = NAMESPACE)
    protected String totReqDate;
    @XmlElement(name = PASSWORD_ELT, namespace = NAMESPACE)
    protected String password;
    @XmlElement(name = NET_SETTLE_AMT_ELT, namespace = NAMESPACE)
    protected String netSettleAmt;

    public String getTotReqDate() {
        return totReqDate;
    }

    public void setTotReqDate(String value) {
        this.totReqDate = value;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String value) {
        this.password = value;
    }

    public String getNetSettleAmt() {
        return netSettleAmt;
    }

    public void setNetSettleAmt(String value) {
        this.netSettleAmt = value;
    }
}