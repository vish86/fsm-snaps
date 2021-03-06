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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CARD_TAG;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CARD_TAG_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CARD_TXN_AMT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CARD_TXN_AMT_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.HOST_TOT_DET_GRP_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TXN_CT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TXN_CT_ELT;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = HOST_TOT_DET_GRP_NAME, namespace = NAMESPACE, propOrder = { CARD_TAG, TXN_CT,
        CARD_TXN_AMT })
public class HostTotDetGrp {
    @XmlElement(name = CARD_TAG_ELT, namespace = NAMESPACE)
    protected String cardTag;
    @XmlElement(name = TXN_CT_ELT, namespace = NAMESPACE)
    protected String txnCt;
    @XmlElement(name = CARD_TXN_AMT_ELT, namespace = NAMESPACE)
    protected String cardTxnAmt;

    public String getCardTag() {
        return cardTag;
    }

    public void setCardTag(String value) {
        this.cardTag = value;
    }

    public String getTxnCt() {
        return txnCt;
    }

    public void setTxnCt(String value) {
        this.txnCt = value;
    }

    public String getCardTxnAmt() {
        return cardTxnAmt;
    }

    public void setCardTxnAmt(String value) {
        this.cardTxnAmt = value;
    }
}