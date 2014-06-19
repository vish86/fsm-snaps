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
@XmlType(name = TA_GRP_NAME, namespace = NAMESPACE, propOrder = { SCTY_LVL, ENCRPT_TYPE,
        ENCRPT_TRGT, KEY_ID, ENCRPT_BLOCK, TKN_TYPE, TKN, SCTY_KEY_UPD_IND, TA_SCTY_KEY,
        TA_EXP_DATE })
public class TAGrp {
    @XmlElement(name = SCTY_LVL_ELT, namespace = NAMESPACE)
    protected SctyLvlType sctyLvl;
    @XmlElement(name = ENCRPT_TYPE_ELT, namespace = NAMESPACE)
    protected EncrptTypeType encrptType;
    @XmlElement(name = ENCRPT_TRGT_ELT, namespace = NAMESPACE)
    protected EncrptTrgtType encrptTrgt;
    @XmlElement(name = KEY_ID_ELT, namespace = NAMESPACE)
    protected String keyID;
    @XmlElement(name = ENCRPT_BLOCK_ELT, namespace = NAMESPACE)
    protected String encrptBlock;
    @XmlElement(name = TKN_TYPE_ELT, namespace = NAMESPACE)
    protected String tknType;
    @XmlElement(name = TKN_ELT, namespace = NAMESPACE)
    protected String tkn;
    @XmlElement(name = SCTY_KEY_UPD_IND_ELT, namespace = NAMESPACE)
    protected String sctyKeyUpdInd;
    @XmlElement(name = TA_SCTY_KEY_ELT, namespace = NAMESPACE)
    protected String taSctyKey;
    @XmlElement(name = TA_EXP_DATE_ELT, namespace = NAMESPACE)
    protected String taExpDate;

    public SctyLvlType getSctyLvl() {
        return sctyLvl;
    }

    public void setSctyLvl(SctyLvlType value) {
        this.sctyLvl = value;
    }

    public EncrptTypeType getEncrptType() {
        return encrptType;
    }

    public void setEncrptType(EncrptTypeType value) {
        this.encrptType = value;
    }

    public EncrptTrgtType getEncrptTrgt() {
        return encrptTrgt;
    }

    public void setEncrptTrgt(EncrptTrgtType value) {
        this.encrptTrgt = value;
    }

    public String getKeyID() {
        return keyID;
    }

    public void setKeyID(String value) {
        this.keyID = value;
    }

    public String getEncrptBlock() {
        return encrptBlock;
    }

    public void setEncrptBlock(String value) {
        this.encrptBlock = value;
    }

    public String getTknType() {
        return tknType;
    }

    public void setTknType(String value) {
        this.tknType = value;
    }

    public String getTkn() {
        return tkn;
    }

    public void setTkn(String value) {
        this.tkn = value;
    }

    public String getSctyKeyUpdInd() {
        return sctyKeyUpdInd;
    }

    public void setSctyKeyUpdInd(String value) {
        this.sctyKeyUpdInd = value;
    }

    public String getTASctyKey() {
        return taSctyKey;
    }

    public void setTASctyKey(String value) {
        this.taSctyKey = value;
    }

    public String getTAExpDate() {
        return taExpDate;
    }

    public void setTAExpDate(String value) {
        this.taExpDate = value;
    }
}