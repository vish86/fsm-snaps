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
@XmlType(name = VOID_TO_REVERSAL_RESPONSE_DETAILS_NAME, namespace = NAMESPACE, propOrder = {
        COMMON_GRP, CARD_GRP, ADDTL_AMT_GRP, EMV_GRP, TA_GRP, VISA_GRP, MC_GRP, DS_GRP, AMEX_GRP,
        DEBIT_GRP, RESP_GRP, ORIG_AUTH_GRP })
public class VoidTOReversalResponseDetails {
    @XmlElement(name = COMMON_GRP_ELT, namespace = NAMESPACE)
    protected CommonGrp commonGrp;
    @XmlElement(name = CARD_GRP_ELT, namespace = NAMESPACE)
    protected CardGrp cardGrp;
    @XmlElement(name = ADDTL_AMT_GRP_ELT, namespace = NAMESPACE)
    protected List<AddtlAmtGrp> addtlAmtGrp;
    @XmlElement(name = EMV_GRP_ELT, namespace = NAMESPACE)
    protected EMVGrp emvGrp;
    @XmlElement(name = TA_GRP_ELT, namespace = NAMESPACE)
    protected TAGrp taGrp;
    @XmlElement(name = VISA_GRP_ELT, namespace = NAMESPACE)
    protected VisaGrp visaGrp;
    @XmlElement(name = MC_GRP_ELT, namespace = NAMESPACE)
    protected MCGrp mcGrp;
    @XmlElement(name = DS_GRP_ELT, namespace = NAMESPACE)
    protected DSGrp dsGrp;
    @XmlElement(name = AMEX_GRP_ELT, namespace = NAMESPACE)
    protected AmexGrp amexGrp;
    @XmlElement(name = DEBIT_GRP_ELT, namespace = NAMESPACE)
    protected DebitGrp debitGrp;
    @XmlElement(name = RESP_GRP_ELT, namespace = NAMESPACE)
    protected RespGrp respGrp;
    @XmlElement(name = ORIG_AUTH_GRP_ELT, namespace = NAMESPACE)
    protected OrigAuthGrp origAuthGrp;

    public CommonGrp getCommonGrp() {
        return commonGrp;
    }

    public void setCommonGrp(CommonGrp value) {
        this.commonGrp = value;
    }

    public CardGrp getCardGrp() {
        return cardGrp;
    }

    public void setCardGrp(CardGrp value) {
        this.cardGrp = value;
    }

    public List<AddtlAmtGrp> getAddtlAmtGrp() {
        if (addtlAmtGrp == null) {
            addtlAmtGrp = new ArrayList<AddtlAmtGrp>();
        }
        return this.addtlAmtGrp;
    }

    public EMVGrp getEMVGrp() {
        return emvGrp;
    }

    public void setEMVGrp(EMVGrp value) {
        this.emvGrp = value;
    }

    public TAGrp getTAGrp() {
        return taGrp;
    }

    public void setTAGrp(TAGrp value) {
        this.taGrp = value;
    }

    public VisaGrp getVisaGrp() {
        return visaGrp;
    }

    public void setVisaGrp(VisaGrp value) {
        this.visaGrp = value;
    }

    public MCGrp getMCGrp() {
        return mcGrp;
    }

    public void setMCGrp(MCGrp value) {
        this.mcGrp = value;
    }

    public DSGrp getDSGrp() {
        return dsGrp;
    }

    public void setDSGrp(DSGrp value) {
        this.dsGrp = value;
    }

    public AmexGrp getAmexGrp() {
        return amexGrp;
    }

    public void setAmexGrp(AmexGrp value) {
        this.amexGrp = value;
    }

    public DebitGrp getDebitGrp() {
        return debitGrp;
    }

    public void setDebitGrp(DebitGrp value) {
        this.debitGrp = value;
    }

    public RespGrp getRespGrp() {
        return respGrp;
    }

    public void setRespGrp(RespGrp value) {
        this.respGrp = value;
    }

    public OrigAuthGrp getOrigAuthGrp() {
        return origAuthGrp;
    }

    public void setOrigAuthGrp(OrigAuthGrp value) {
        this.origAuthGrp = value;
    }
}