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

import java.util.ArrayList;
import java.util.List;

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
@XmlType(name = PURCH_CARDLVL2_GRP_NAME, namespace = NAMESPACE, propOrder = { TAX_AMT, TAX_IND,
        PURCH_IDFR, PC_ORDER_NUM, DISCNT_AMT, FRGHT_AMT, DUTY_AMT, DEST_POSTAL_CODE,
        SHIP_FROM_POSTAL_CODE, DEST_CTRY_CODE, MERCH_TAX_ID, PROD_DESC })
public class PurchCardlvl2Grp {
    @XmlElement(name = TAX_AMT_ELT, namespace = NAMESPACE)
    protected String taxAmt;
    @XmlElement(name = TAX_IND_ELT, namespace = NAMESPACE)
    protected String taxInd;
    @XmlElement(name = PURCH_IDFR_ELT, namespace = NAMESPACE)
    protected String purchIdfr;
    @XmlElement(name = PC_ORDER_NUM_ELT, namespace = NAMESPACE)
    protected String pcOrderNum;
    @XmlElement(name = DISCNT_AMT_ELT, namespace = NAMESPACE)
    protected String discntAmt;
    @XmlElement(name = FRGHT_AMT_ELT, namespace = NAMESPACE)
    protected String frghtAmt;
    @XmlElement(name = DUTY_AMT_ELT, namespace = NAMESPACE)
    protected String dutyAmt;
    @XmlElement(name = DEST_POSTAL_CODE_ELT, namespace = NAMESPACE)
    protected String destPostalCode;
    @XmlElement(name = SHIP_FROM_POSTAL_CODE_ELT, namespace = NAMESPACE)
    protected String shipFromPostalCode;
    @XmlElement(name = DEST_CTRY_CODE_ELT, namespace = NAMESPACE)
    protected String destCtryCode;
    @XmlElement(name = MERCH_TAX_ID_ELT, namespace = NAMESPACE)
    protected String merchTaxID;
    @XmlElement(name = PROD_DESC_ELT, namespace = NAMESPACE)
    protected List<String> prodDesc;

    public String getTaxAmt() {
        return taxAmt;
    }

    public void setTaxAmt(String value) {
        this.taxAmt = value;
    }

    public String getTaxInd() {
        return taxInd;
    }

    public void setTaxInd(String value) {
        this.taxInd = value;
    }

    public String getPurchIdfr() {
        return purchIdfr;
    }

    public void setPurchIdfr(String value) {
        this.purchIdfr = value;
    }

    public String getPCOrderNum() {
        return pcOrderNum;
    }

    public void setPCOrderNum(String value) {
        this.pcOrderNum = value;
    }

    public String getDiscntAmt() {
        return discntAmt;
    }

    public void setDiscntAmt(String value) {
        this.discntAmt = value;
    }

    public String getFrghtAmt() {
        return frghtAmt;
    }

    public void setFrghtAmt(String value) {
        this.frghtAmt = value;
    }

    public String getDutyAmt() {
        return dutyAmt;
    }

    public void setDutyAmt(String value) {
        this.dutyAmt = value;
    }

    public String getDestPostalCode() {
        return destPostalCode;
    }

    public void setDestPostalCode(String value) {
        this.destPostalCode = value;
    }

    public String getShipFromPostalCode() {
        return shipFromPostalCode;
    }

    public void setShipFromPostalCode(String value) {
        this.shipFromPostalCode = value;
    }

    public String getDestCtryCode() {
        return destCtryCode;
    }

    public void setDestCtryCode(String value) {
        this.destCtryCode = value;
    }

    public String getMerchTaxID() {
        return merchTaxID;
    }

    public void setMerchTaxID(String value) {
        this.merchTaxID = value;
    }

    public List<String> getProdDesc() {
        if (prodDesc == null) {
            prodDesc = new ArrayList<String>();
        }
        return this.prodDesc;
    }
}