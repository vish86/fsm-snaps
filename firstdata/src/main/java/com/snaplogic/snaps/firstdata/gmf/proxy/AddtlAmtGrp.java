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
@XmlType(name = ADDTL_AMT_GRP_NAME, namespace = NAMESPACE, propOrder = { ADD_AMT, ADD_AMT_CRNCY,
        ADD_AMT_TYPE, ADD_AMT_ACCT_TYPE, HOLD_INFO, PART_AUTHRZTN_APPRVL_CAPABLT })
public class AddtlAmtGrp {
    @XmlElement(name = ADD_AMT_ELT, namespace = NAMESPACE)
    protected String addAmt;
    @XmlElement(name = ADD_AMT_CRNCY_ELT, namespace = NAMESPACE)
    protected String addAmtCrncy;
    @XmlElement(name = ADD_AMT_TYPE_ELT, namespace = NAMESPACE)
    protected AddAmtTypeType addAmtType;
    @XmlElement(name = ADD_AMT_ACCT_TYPE_ELT, namespace = NAMESPACE)
    protected String addAmtAcctType;
    @XmlElement(name = HOLD_INFO_ELT, namespace = NAMESPACE)
    protected String holdInfo;
    @XmlElement(name = PART_AUTHRZTN_APPRVL_CAPABLT_ELT, namespace = NAMESPACE)
    protected String partAuthrztnApprvlCapablt;

    public String getAddAmt() {
        return addAmt;
    }

    public void setAddAmt(String value) {
        this.addAmt = value;
    }

    public String getAddAmtCrncy() {
        return addAmtCrncy;
    }

    public void setAddAmtCrncy(String value) {
        this.addAmtCrncy = value;
    }

    public AddAmtTypeType getAddAmtType() {
        return addAmtType;
    }

    public void setAddAmtType(AddAmtTypeType value) {
        this.addAmtType = value;
    }

    public String getAddAmtAcctType() {
        return addAmtAcctType;
    }

    public void setAddAmtAcctType(String value) {
        this.addAmtAcctType = value;
    }

    public String getHoldInfo() {
        return holdInfo;
    }

    public void setHoldInfo(String value) {
        this.holdInfo = value;
    }

    public String getPartAuthrztnApprvlCapablt() {
        return partAuthrztnApprvlCapablt;
    }

    public void setPartAuthrztnApprvlCapablt(String value) {
        this.partAuthrztnApprvlCapablt = value;
    }
}