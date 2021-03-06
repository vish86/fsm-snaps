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
@XmlType(name = VISA_GRP_NAME, namespace = NAMESPACE, propOrder = { ACI, MRKT_SPECIFIC_DATA_IND,
        EXISTING_DEBT_IND, CARD_LEVEL_RESULT, SOURCE_REASON_CODE, TRANS_ID, VISA_BID, VISA_AUAR,
        TAX_AMT_CAPABLT })
public class VisaGrp {
    @XmlElement(name = ACI_ELT, namespace = NAMESPACE)
    protected ACIType aci;
    @XmlElement(name = MRKT_SPECIFIC_DATA_IND_ELT, namespace = NAMESPACE)
    protected MrktSpecificDataIndType mrktSpecificDataInd;
    @XmlElement(name = EXISTING_DEBT_IND_ELT, namespace = NAMESPACE)
    protected String existingDebtInd;
    @XmlElement(name = CARD_LEVEL_RESULT_ELT, namespace = NAMESPACE)
    protected String cardLevelResult;
    @XmlElement(name = SOURCE_REASON_CODE_ELT, namespace = NAMESPACE)
    protected String sourceReasonCode;
    @XmlElement(name = TRANS_ID_ELT, namespace = NAMESPACE)
    protected String transID;
    @XmlElement(name = VISA_BID_ELT, namespace = NAMESPACE)
    protected List<String> visaBID;
    @XmlElement(name = VISA_AUAR_ELT, namespace = NAMESPACE)
    protected List<String> visaAUAR;
    @XmlElement(name = TAX_AMT_CAPABLT_ELT, namespace = NAMESPACE)
    protected String taxAmtCapablt;

    public ACIType getACI() {
        return aci;
    }

    public void setACI(ACIType value) {
        this.aci = value;
    }

    public MrktSpecificDataIndType getMrktSpecificDataInd() {
        return mrktSpecificDataInd;
    }

    public void setMrktSpecificDataInd(MrktSpecificDataIndType value) {
        this.mrktSpecificDataInd = value;
    }

    public String getExistingDebtInd() {
        return existingDebtInd;
    }

    public void setExistingDebtInd(String value) {
        this.existingDebtInd = value;
    }

    public String getCardLevelResult() {
        return cardLevelResult;
    }

    public void setCardLevelResult(String value) {
        this.cardLevelResult = value;
    }

    public String getSourceReasonCode() {
        return sourceReasonCode;
    }

    public void setSourceReasonCode(String value) {
        this.sourceReasonCode = value;
    }

    public String getTransID() {
        return transID;
    }

    public void setTransID(String value) {
        this.transID = value;
    }

    public List<String> getVisaBID() {
        if (visaBID == null) {
            visaBID = new ArrayList<String>();
        }
        return this.visaBID;
    }

    public List<String> getVisaAUAR() {
        if (visaAUAR == null) {
            visaAUAR = new ArrayList<String>();
        }
        return this.visaAUAR;
    }

    public String getTaxAmtCapablt() {
        return taxAmtCapablt;
    }

    public void setTaxAmtCapablt(String value) {
        this.taxAmtCapablt = value;
    }
}