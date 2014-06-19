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
@XmlType(name = PROD_CODE_DET_GRP_NAME, namespace = NAMESPACE, propOrder = { NACS_PROD_CODE,
        UNIT_OF_MSURE, QNTY, UNIT_PRICE, PROD_AMT })
public class ProdCodeDetGrp {
    @XmlElement(name = NACS_PROD_CODE_ELT, namespace = NAMESPACE)
    protected String nacsProdCode;
    @XmlElement(name = UNIT_OF_MSURE_ELT, namespace = NAMESPACE)
    protected String unitOfMsure;
    @XmlElement(name = QNTY_ELT, namespace = NAMESPACE)
    protected String qnty;
    @XmlElement(name = UNIT_PRICE_ELT, namespace = NAMESPACE)
    protected String unitPrice;
    @XmlElement(name = PROD_AMT_ELT, namespace = NAMESPACE)
    protected String prodAmt;

    public String getNACSProdCode() {
        return nacsProdCode;
    }

    public void setNACSProdCode(String value) {
        this.nacsProdCode = value;
    }

    public String getUnitOfMsure() {
        return unitOfMsure;
    }

    public void setUnitOfMsure(String value) {
        this.unitOfMsure = value;
    }

    public String getQnty() {
        return qnty;
    }

    public void setQnty(String value) {
        this.qnty = value;
    }

    public String getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(String value) {
        this.unitPrice = value;
    }

    public String getProdAmt() {
        return prodAmt;
    }

    public void setProdAmt(String value) {
        this.prodAmt = value;
    }
}