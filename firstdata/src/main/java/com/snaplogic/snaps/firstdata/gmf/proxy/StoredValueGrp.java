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
@XmlType(name = STORED_VALUE_GRP_NAME, namespace = NAMESPACE, propOrder = { ESCHTBL_TXN,
        CARD_CLASS, CARD_COST, FA_CODE })
public class StoredValueGrp {
    @XmlElement(name = ESCHTBL_TXN_ELT, namespace = NAMESPACE)
    protected EschtblTxnType eschtblTxn;
    @XmlElement(name = CARD_CLASS_ELT, namespace = NAMESPACE)
    protected String cardClass;
    @XmlElement(name = CARD_COST_ELT, namespace = NAMESPACE)
    protected String cardCost;
    @XmlElement(name = FA_CODE_ELT, namespace = NAMESPACE)
    protected String faCode;

    public EschtblTxnType getEschtblTxn() {
        return eschtblTxn;
    }

    public void setEschtblTxn(EschtblTxnType value) {
        this.eschtblTxn = value;
    }

    public String getCardClass() {
        return cardClass;
    }

    public void setCardClass(String value) {
        this.cardClass = value;
    }

    public String getCardCost() {
        return cardCost;
    }

    public void setCardCost(String value) {
        this.cardCost = value;
    }

    public String getFACode() {
        return faCode;
    }

    public void setFACode(String value) {
        this.faCode = value;
    }
}