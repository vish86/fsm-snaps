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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.AVS_BILLING_ADDR;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.AVS_BILLING_ADDR_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.AVS_BILLING_POSTAL_CODE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.AVS_BILLING_POSTAL_CODE_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CUST_INFO_GRP_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = CUST_INFO_GRP_NAME, namespace = NAMESPACE, propOrder = { AVS_BILLING_ADDR,
        AVS_BILLING_POSTAL_CODE })
public class CustInfoGrp {
    @XmlElement(name = AVS_BILLING_ADDR_ELT, namespace = NAMESPACE)
    protected String avsBillingAddr;
    @XmlElement(name = AVS_BILLING_POSTAL_CODE_ELT, namespace = NAMESPACE)
    protected String avsBillingPostalCode;

    public String getAVSBillingAddr() {
        return avsBillingAddr;
    }

    public void setAVSBillingAddr(String value) {
        this.avsBillingAddr = value;
    }

    public String getAVSBillingPostalCode() {
        return avsBillingPostalCode;
    }

    public void setAVSBillingPostalCode(String value) {
        this.avsBillingPostalCode = value;
    }
}