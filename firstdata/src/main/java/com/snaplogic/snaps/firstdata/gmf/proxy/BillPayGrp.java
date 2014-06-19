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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.BILL_PAY_GRP_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.BILL_PYMT_TXN_IND;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.BILL_PYMT_TXN_IND_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.MERCH_ADVICE_CODE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.MERCH_ADVICE_CODE_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = BILL_PAY_GRP_NAME, namespace = NAMESPACE, propOrder = { BILL_PYMT_TXN_IND,
        MERCH_ADVICE_CODE })
public class BillPayGrp {
    @XmlElement(name = BILL_PYMT_TXN_IND_ELT, namespace = NAMESPACE)
    protected BillPymtTxnIndType billPymtTxnInd;
    @XmlElement(name = MERCH_ADVICE_CODE_ELT, namespace = NAMESPACE)
    protected String merchAdviceCode;

    public BillPymtTxnIndType getBillPymtTxnInd() {
        return billPymtTxnInd;
    }

    public void setBillPymtTxnInd(BillPymtTxnIndType value) {
        this.billPymtTxnInd = value;
    }

    public String getMerchAdviceCode() {
        return merchAdviceCode;
    }

    public void setMerchAdviceCode(String value) {
        this.merchAdviceCode = value;
    }
}