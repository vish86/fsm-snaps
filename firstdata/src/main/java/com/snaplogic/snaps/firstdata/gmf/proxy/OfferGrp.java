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
@XmlType(name = OFFER_GRP_NAME, namespace = NAMESPACE, propOrder = { POS_OFFER_CAP, OFFER_DESC,
        OFFER_RES_IND, OFFER_ID, OFFER_PROV_NAME, OFFER_AMOUNT, RECEIPT_COPY, VAR_CUST_RCT_TXT,
        OFFER_PROV_ID, OFFER_PUB_ID, OFFER_PUB_NAME })
public class OfferGrp {
    @XmlElement(name = POS_OFFER_CAP_ELT, namespace = NAMESPACE)
    protected String posOfferCap;
    @XmlElement(name = OFFER_DESC_ELT, namespace = NAMESPACE)
    protected String offerDesc;
    @XmlElement(name = OFFER_RES_IND_ELT, namespace = NAMESPACE)
    protected String offerResInd;
    @XmlElement(name = OFFER_ID_ELT, namespace = NAMESPACE)
    protected String offerID;
    @XmlElement(name = OFFER_PROV_NAME_ELT, namespace = NAMESPACE)
    protected String offerProvName;
    @XmlElement(name = OFFER_AMOUNT_ELT, namespace = NAMESPACE)
    protected String offerAmount;
    @XmlElement(name = RECEIPT_COPY_ELT, namespace = NAMESPACE)
    protected String receiptCopy;
    @XmlElement(name = VAR_CUST_RCT_TXT_ELT, namespace = NAMESPACE)
    protected String varCustRctTxt;
    @XmlElement(name = OFFER_PROV_ID_ELT, namespace = NAMESPACE)
    protected String offerProvID;
    @XmlElement(name = OFFER_PUB_ID_ELT, namespace = NAMESPACE)
    protected String offerPubID;
    @XmlElement(name = OFFER_PUB_NAME_ELT, namespace = NAMESPACE)
    protected String offerPubName;

    public String getPOSOfferCap() {
        return posOfferCap;
    }

    public void setPOSOfferCap(String value) {
        this.posOfferCap = value;
    }

    public String getOfferDesc() {
        return offerDesc;
    }

    public void setOfferDesc(String value) {
        this.offerDesc = value;
    }

    public String getOfferResInd() {
        return offerResInd;
    }

    public void setOfferResInd(String value) {
        this.offerResInd = value;
    }

    public String getOfferID() {
        return offerID;
    }

    public void setOfferID(String value) {
        this.offerID = value;
    }

    public String getOfferProvName() {
        return offerProvName;
    }

    public void setOfferProvName(String value) {
        this.offerProvName = value;
    }

    public String getOfferAmount() {
        return offerAmount;
    }

    public void setOfferAmount(String value) {
        this.offerAmount = value;
    }

    public String getReceiptCopy() {
        return receiptCopy;
    }

    public void setReceiptCopy(String value) {
        this.receiptCopy = value;
    }

    public String getVarCustRctTxt() {
        return varCustRctTxt;
    }

    public void setVarCustRctTxt(String value) {
        this.varCustRctTxt = value;
    }

    public String getOfferProvID() {
        return offerProvID;
    }

    public void setOfferProvID(String value) {
        this.offerProvID = value;
    }

    public String getOfferPubID() {
        return offerPubID;
    }

    public void setOfferPubID(String value) {
        this.offerPubID = value;
    }

    public String getOfferPubName() {
        return offerPubName;
    }

    public void setOfferPubName(String value) {
        this.offerPubName = value;
    }
}