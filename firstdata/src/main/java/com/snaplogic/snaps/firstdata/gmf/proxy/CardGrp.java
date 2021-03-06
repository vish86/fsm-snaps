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
@XmlType(name = Constants.CARD_GRP_NAME, namespace = NAMESPACE, propOrder = { ACCT_NUM,
        CARD_ACTIV_DATE, CARD_EXPIRY_DATE, TRACK1_DATA, TRACK2_DATA, CARD_TYPE, AVS_RESULT_CODE,
        CCV_IND, CCV_DATA, CCV_RESULT_CODE })
public class CardGrp {
    @XmlElement(name = ACCT_NUM_ELT, namespace = NAMESPACE)
    protected String acctNum;
    @XmlElement(name = CARD_ACTIV_DATE_ELT, namespace = NAMESPACE)
    protected String cardActivDate;
    @XmlElement(name = CARD_EXPIRY_DATE_ELT, namespace = NAMESPACE)
    protected String cardExpiryDate;
    @XmlElement(name = TRACK1_DATA_ELT, namespace = NAMESPACE)
    protected String track1Data;
    @XmlElement(name = TRACK2_DATA_ELT, namespace = NAMESPACE)
    protected String track2Data;
    @XmlElement(name = CARD_TYPE2, namespace = NAMESPACE)
    protected CardTypeType cardType;
    @XmlElement(name = AVS_RESULT_CODE_ELT, namespace = NAMESPACE)
    protected AVSResultCodeType avsResultCode;
    @XmlElement(name = CCV_IND_ELT, namespace = NAMESPACE)
    protected CCVIndType ccvInd;
    @XmlElement(name = CCV_DATA_ELT, namespace = NAMESPACE)
    protected String ccvData;
    @XmlElement(name = Constants.CCV_RESULT_CODE_ELT, namespace = NAMESPACE)
    protected CCVResultCodeType ccvResultCode;

    public String getAcctNum() {
        return acctNum;
    }

    public void setAcctNum(String value) {
        this.acctNum = value;
    }

    public String getCardActivDate() {
        return cardActivDate;
    }

    public void setCardActivDate(String value) {
        this.cardActivDate = value;
    }

    public String getCardExpiryDate() {
        return cardExpiryDate;
    }

    public void setCardExpiryDate(String value) {
        this.cardExpiryDate = value;
    }

    public String getTrack1Data() {
        return track1Data;
    }

    public void setTrack1Data(String value) {
        this.track1Data = value;
    }

    public String getTrack2Data() {
        return track2Data;
    }

    public void setTrack2Data(String value) {
        this.track2Data = value;
    }

    public CardTypeType getCardType() {
        return cardType;
    }

    public void setCardType(CardTypeType value) {
        this.cardType = value;
    }

    public AVSResultCodeType getAVSResultCode() {
        return avsResultCode;
    }

    public void setAVSResultCode(AVSResultCodeType value) {
        this.avsResultCode = value;
    }

    public CCVIndType getCCVInd() {
        return ccvInd;
    }

    public void setCCVInd(CCVIndType value) {
        this.ccvInd = value;
    }

    public String getCCVData() {
        return ccvData;
    }

    public void setCCVData(String value) {
        this.ccvData = value;
    }

    public CCVResultCodeType getCCVResultCode() {
        return ccvResultCode;
    }

    public void setCCVResultCode(CCVResultCodeType value) {
        this.ccvResultCode = value;
    }
}