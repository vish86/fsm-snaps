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

/**
 * This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
 * Implementation. Any modifications to this file will be lost upon recompilation of the source
 * schema.
 * 
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HostTotDetGrp", namespace = "com/firstdata/Merchant/gmfV2.08", propOrder = {
        "cardTag", "txnCt", "cardTxnAmt" })
public class HostTotDetGrp {

    @XmlElement(name = "CardTag", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected String cardTag;
    @XmlElement(name = "TxnCt", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected String txnCt;
    @XmlElement(name = "CardTxnAmt", namespace = "com/firstdata/Merchant/gmfV2.08")
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
