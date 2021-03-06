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
@XmlType(name = PRIVATE_LABEL_REQUEST_DETAILS, namespace = NAMESPACE, propOrder = { COMMON_GRP,
        CARD_GRP, ADDTL_AMT_GRP, OFFER_GRP, CUST_INFO_GRP, ORIG_AUTH_GRP, PROD_CODE_GRP,
        PROD_CODE_DET_GRP })
public class PrivateLabelRequestDetails {
    @XmlElement(name = COMMON_GRP_ELT, namespace = NAMESPACE)
    protected CommonGrp commonGrp;
    @XmlElement(name = CARD_GRP_ELT, namespace = NAMESPACE)
    protected CardGrp cardGrp;
    @XmlElement(name = ADDTL_AMT_GRP_ELT, namespace = NAMESPACE)
    protected List<AddtlAmtGrp> addtlAmtGrp;
    @XmlElement(name = OFFER_GRP_ELT, namespace = NAMESPACE)
    protected OfferGrp offerGrp;
    @XmlElement(name = CUST_INFO_GRP_ELT, namespace = NAMESPACE)
    protected CustInfoGrp custInfoGrp;
    @XmlElement(name = ORIG_AUTH_GRP_ELT, namespace = NAMESPACE)
    protected OrigAuthGrp origAuthGrp;
    @XmlElement(name = PROD_CODE_GRP_ELT, namespace = NAMESPACE)
    protected ProdCodeGrp prodCodeGrp;
    @XmlElement(name = PROD_CODE_DET_GRP_ELT, namespace = NAMESPACE)
    protected List<ProdCodeDetGrp> prodCodeDetGrp;

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

    public OfferGrp getOfferGrp() {
        return offerGrp;
    }

    public void setOfferGrp(OfferGrp value) {
        this.offerGrp = value;
    }

    public CustInfoGrp getCustInfoGrp() {
        return custInfoGrp;
    }

    public void setCustInfoGrp(CustInfoGrp value) {
        this.custInfoGrp = value;
    }

    public OrigAuthGrp getOrigAuthGrp() {
        return origAuthGrp;
    }

    public void setOrigAuthGrp(OrigAuthGrp value) {
        this.origAuthGrp = value;
    }

    public ProdCodeGrp getProdCodeGrp() {
        return prodCodeGrp;
    }

    public void setProdCodeGrp(ProdCodeGrp value) {
        this.prodCodeGrp = value;
    }

    public List<ProdCodeDetGrp> getProdCodeDetGrp() {
        if (prodCodeDetGrp == null) {
            prodCodeDetGrp = new ArrayList<ProdCodeDetGrp>();
        }
        return this.prodCodeDetGrp;
    }
}