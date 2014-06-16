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

/**
 * This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
 * Implementation. Any modifications to this file will be lost upon recompilation of the source
 * schema.
 * 
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "VoidTOReversalResponseDetails", namespace = "com/firstdata/Merchant/gmfV2.08",
        propOrder = { "commonGrp", "cardGrp", "addtlAmtGrp", "emvGrp", "taGrp", "visaGrp", "mcGrp",
                "dsGrp", "amexGrp", "debitGrp", "respGrp", "origAuthGrp" })
public class VoidTOReversalResponseDetails {

    @XmlElement(name = "CommonGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected CommonGrp commonGrp;
    @XmlElement(name = "CardGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected CardGrp cardGrp;
    @XmlElement(name = "AddtlAmtGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected List<AddtlAmtGrp> addtlAmtGrp;
    @XmlElement(name = "EMVGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected EMVGrp emvGrp;
    @XmlElement(name = "TAGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected TAGrp taGrp;
    @XmlElement(name = "VisaGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected VisaGrp visaGrp;
    @XmlElement(name = "MCGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected MCGrp mcGrp;
    @XmlElement(name = "DSGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected DSGrp dsGrp;
    @XmlElement(name = "AmexGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected AmexGrp amexGrp;
    @XmlElement(name = "DebitGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected DebitGrp debitGrp;
    @XmlElement(name = "RespGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected RespGrp respGrp;
    @XmlElement(name = "OrigAuthGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
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
