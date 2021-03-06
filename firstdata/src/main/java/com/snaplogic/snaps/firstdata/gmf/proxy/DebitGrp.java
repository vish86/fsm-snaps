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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.*;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = DEBIT_GRP_NAME, namespace = NAMESPACE, propOrder = { PAYEE_PHONE_NUM,
        PAYEE_ACCT_NUM, PAYEE_ID, BILLING_IND, FPI })
public class DebitGrp {
    @XmlElement(name = PAYEE_PHONE_NUM_ELT, namespace = NAMESPACE)
    protected String payeePhoneNum;
    @XmlElement(name = PAYEE_ACCT_NUM_ELT, namespace = NAMESPACE)
    protected String payeeAcctNum;
    @XmlElement(name = PAYEE_ID2, namespace = NAMESPACE)
    protected String payeeID;
    @XmlElement(name = BILLING_IND_ELT, namespace = NAMESPACE)
    protected String billingInd;
    @XmlElement(name = FPI_ELT, namespace = NAMESPACE)
    protected String fpi;

    public String getPayeePhoneNum() {
        return payeePhoneNum;
    }

    public void setPayeePhoneNum(String value) {
        this.payeePhoneNum = value;
    }

    public String getPayeeAcctNum() {
        return payeeAcctNum;
    }

    public void setPayeeAcctNum(String value) {
        this.payeeAcctNum = value;
    }

    public String getPayeeID() {
        return payeeID;
    }

    public void setPayeeID(String value) {
        this.payeeID = value;
    }

    public String getBillingInd() {
        return billingInd;
    }

    public void setBillingInd(String value) {
        this.billingInd = value;
    }

    public String getFPI() {
        return fpi;
    }

    public void setFPI(String value) {
        this.fpi = value;
    }
}