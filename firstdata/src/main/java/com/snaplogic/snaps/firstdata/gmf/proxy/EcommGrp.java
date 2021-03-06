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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CUST_SVC_PHONE_NUMBER;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CUST_SVC_PHONE_NUMBER_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ECOMM_GRP_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ECOMM_TXN_IND;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ECOMM_TXN_IND_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ECOMM_URL;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ECOMM_URL_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ECOMM_GRP_NAME, namespace = NAMESPACE, propOrder = { ECOMM_TXN_IND,
        CUST_SVC_PHONE_NUMBER, ECOMM_URL })
public class EcommGrp {
    @XmlElement(name = ECOMM_TXN_IND_ELT, namespace = NAMESPACE)
    protected String ecommTxnInd;
    @XmlElement(name = CUST_SVC_PHONE_NUMBER_ELT, namespace = NAMESPACE)
    protected String custSvcPhoneNumber;
    @XmlElement(name = ECOMM_URL_ELT, namespace = NAMESPACE)
    protected String ecommURL;

    public String getEcommTxnInd() {
        return ecommTxnInd;
    }

    public void setEcommTxnInd(String value) {
        this.ecommTxnInd = value;
    }

    public String getCustSvcPhoneNumber() {
        return custSvcPhoneNumber;
    }

    public void setCustSvcPhoneNumber(String value) {
        this.custSvcPhoneNumber = value;
    }

    public String getEcommURL() {
        return ecommURL;
    }

    public void setEcommURL(String value) {
        this.ecommURL = value;
    }
}