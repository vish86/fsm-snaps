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
@XmlType(name = SECR_TXN_GRP_NAME, namespace = NAMESPACE, propOrder = { VISA_XID, VISA_SECR_TXN_AD,
        CAVV_RESULT_CODE, AMEX_XID, AMEX_SECR_AD, SAFEKEY, UCAF_COLLECT_IND, MC_SECR_AD })
public class SecrTxnGrp {
    @XmlElement(name = VISA_XID_ELT, namespace = NAMESPACE)
    protected String visaXID;
    @XmlElement(name = VISA_SECR_TXN_AD_ELT, namespace = NAMESPACE)
    protected String visaSecrTxnAD;
    @XmlElement(name = CAVV_RESULT_CODE_ELT, namespace = NAMESPACE)
    protected String cavvResultCode;
    @XmlElement(name = AMEX_XID_ELT, namespace = NAMESPACE)
    protected String amexXID;
    @XmlElement(name = AMEX_SECR_AD_ELT, namespace = NAMESPACE)
    protected String amexSecrAD;
    @XmlElement(name = SAFEKEY_ELT, namespace = NAMESPACE)
    protected String safekey;
    @XmlElement(name = UCAF_COLLECT_IND_ELT, namespace = NAMESPACE)
    protected String ucafCollectInd;
    @XmlElement(name = MC_SECR_AD_ELT, namespace = NAMESPACE)
    protected String mcSecrAD;

    public String getVisaXID() {
        return visaXID;
    }

    public void setVisaXID(String value) {
        this.visaXID = value;
    }

    public String getVisaSecrTxnAD() {
        return visaSecrTxnAD;
    }

    public void setVisaSecrTxnAD(String value) {
        this.visaSecrTxnAD = value;
    }

    public String getCAVVResultCode() {
        return cavvResultCode;
    }

    public void setCAVVResultCode(String value) {
        this.cavvResultCode = value;
    }

    public String getAmexXID() {
        return amexXID;
    }

    public void setAmexXID(String value) {
        this.amexXID = value;
    }

    public String getAmexSecrAD() {
        return amexSecrAD;
    }

    public void setAmexSecrAD(String value) {
        this.amexSecrAD = value;
    }

    public String getSafekey() {
        return safekey;
    }

    public void setSafekey(String value) {
        this.safekey = value;
    }

    public String getUCAFCollectInd() {
        return ucafCollectInd;
    }

    public void setUCAFCollectInd(String value) {
        this.ucafCollectInd = value;
    }

    public String getMCSecrAD() {
        return mcSecrAD;
    }

    public void setMCSecrAD(String value) {
        this.mcSecrAD = value;
    }
}