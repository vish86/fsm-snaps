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
@XmlType(name = EMV_GRP_NAME, namespace = NAMESPACE, propOrder = { EMV_DATA, CARD_SEQ_NUM,
        X_CODE_RESP, SERV_CODE, APP_EXP_DATE, CARC, PROC_IND, PROC_INFO })
public class EMVGrp {
    @XmlElement(name = EMV_DATA_ELT, namespace = NAMESPACE)
    protected String emvData;
    @XmlElement(name = CARD_SEQ_NUM_ELT, namespace = NAMESPACE)
    protected String cardSeqNum;
    @XmlElement(name = X_CODE_RESP_ELT, namespace = NAMESPACE)
    protected String xCodeResp;
    @XmlElement(name = SERV_CODE_ELT, namespace = NAMESPACE)
    protected String servCode;
    @XmlElement(name = APP_EXP_DATE_ELT, namespace = NAMESPACE)
    protected String appExpDate;
    @XmlElement(name = CARC_ELT, namespace = NAMESPACE)
    protected String carc;
    @XmlElement(name = PROC_IND_ELT, namespace = NAMESPACE)
    protected String procInd;
    @XmlElement(name = PROC_INFO_ELT, namespace = NAMESPACE)
    protected String procInfo;

    public String getEMVData() {
        return emvData;
    }

    public void setEMVData(String value) {
        this.emvData = value;
    }

    public String getCardSeqNum() {
        return cardSeqNum;
    }

    public void setCardSeqNum(String value) {
        this.cardSeqNum = value;
    }

    public String getXCodeResp() {
        return xCodeResp;
    }

    public void setXCodeResp(String value) {
        this.xCodeResp = value;
    }

    public String getServCode() {
        return servCode;
    }

    public void setServCode(String value) {
        this.servCode = value;
    }

    public String getAppExpDate() {
        return appExpDate;
    }

    public void setAppExpDate(String value) {
        this.appExpDate = value;
    }

    public String getCARC() {
        return carc;
    }

    public void setCARC(String value) {
        this.carc = value;
    }

    public String getProcInd() {
        return procInd;
    }

    public void setProcInd(String value) {
        this.procInd = value;
    }

    public String getProcInfo() {
        return procInfo;
    }

    public void setProcInfo(String value) {
        this.procInfo = value;
    }
}