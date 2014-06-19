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
@XmlType(name = ALT_MERCH_NAME_AND_ADDR_GRP_NAME, namespace = NAMESPACE,
propOrder = { MERCHANT_ID, MERCH_NAME, MERCH_ADDR, MERCH_CITY, MERCH_STATE, MERCH_CNTY,
        MERCH_POSTAL_CODE, MERCH_CTRY })
public class AltMerchNameAndAddrGrp {
    @XmlElement(name = MERCHANT_ID_ELT, namespace = NAMESPACE)
    protected String merchantID;
    @XmlElement(name = MERCH_NAME_ELT, namespace = NAMESPACE)
    protected String merchName;
    @XmlElement(name = MERCH_ADDR_ELT, namespace = NAMESPACE)
    protected String merchAddr;
    @XmlElement(name = MERCH_CITYELT, namespace = NAMESPACE)
    protected String merchCity;
    @XmlElement(name = MERCH_STATEELT, namespace = NAMESPACE)
    protected String merchState;
    @XmlElement(name = MERCH_CNTY_ELT, namespace = NAMESPACE)
    protected String merchCnty;
    @XmlElement(name = MERCH_POSTAL_CODE_ELT, namespace = NAMESPACE)
    protected String merchPostalCode;
    @XmlElement(name = MERCH_CTRY_ELT, namespace = NAMESPACE)
    protected String merchCtry;

    public String getMerchantID() {
        return merchantID;
    }

    public void setMerchantID(String value) {
        this.merchantID = value;
    }

    public String getMerchName() {
        return merchName;
    }

    public void setMerchName(String value) {
        this.merchName = value;
    }

    public String getMerchAddr() {
        return merchAddr;
    }

    public void setMerchAddr(String value) {
        this.merchAddr = value;
    }

    public String getMerchCity() {
        return merchCity;
    }

    public void setMerchCity(String value) {
        this.merchCity = value;
    }

    public String getMerchState() {
        return merchState;
    }

    public void setMerchState(String value) {
        this.merchState = value;
    }

    public String getMerchCnty() {
        return merchCnty;
    }

    public void setMerchCnty(String value) {
        this.merchCnty = value;
    }

    public String getMerchPostalCode() {
        return merchPostalCode;
    }

    public void setMerchPostalCode(String value) {
        this.merchPostalCode = value;
    }

    public String getMerchCtry() {
        return merchCtry;
    }

    public void setMerchCtry(String value) {
        this.merchCtry = value;
    }
}