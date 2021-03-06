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
@XmlType(name = PINLESS_DEBIT_REQUEST_DETAILS_NAME, namespace = NAMESPACE, propOrder = {
        COMMON_GRP, BILL_PAY_GRP, ALT_MERCH_NAME_AND_ADDR_GRP, CARD_GRP, TA_GRP, OFFER_GRP,
        ECOMM_GRP, VISA_GRP, DEBIT_GRP })
public class PinlessDebitRequestDetails {
    @XmlElement(name = COMMON_GRP_ELT, namespace = NAMESPACE)
    protected CommonGrp commonGrp;
    @XmlElement(name = BILL_PAY_GRP_ELT, namespace = NAMESPACE)
    protected BillPayGrp billPayGrp;
    @XmlElement(name = ALT_MERCH_NAME_AND_ADDR_GRP_ELT, namespace = NAMESPACE)
    protected AltMerchNameAndAddrGrp altMerchNameAndAddrGrp;
    @XmlElement(name = CARD_GRP_ELT, namespace = NAMESPACE)
    protected CardGrp cardGrp;
    @XmlElement(name = TA_GRP_ELT, namespace = NAMESPACE)
    protected TAGrp taGrp;
    @XmlElement(name = OFFER_GRP_ELT, namespace = NAMESPACE)
    protected OfferGrp offerGrp;
    @XmlElement(name = ECOMM_GRP_ELT, namespace = NAMESPACE)
    protected EcommGrp ecommGrp;
    @XmlElement(name = VISA_GRP_ELT, namespace = NAMESPACE)
    protected VisaGrp visaGrp;
    @XmlElement(name = DEBIT_GRP_ELT, namespace = NAMESPACE)
    protected DebitGrp debitGrp;

    public CommonGrp getCommonGrp() {
        return commonGrp;
    }

    public void setCommonGrp(CommonGrp value) {
        this.commonGrp = value;
    }

    public BillPayGrp getBillPayGrp() {
        return billPayGrp;
    }

    public void setBillPayGrp(BillPayGrp value) {
        this.billPayGrp = value;
    }

    public AltMerchNameAndAddrGrp getAltMerchNameAndAddrGrp() {
        return altMerchNameAndAddrGrp;
    }

    public void setAltMerchNameAndAddrGrp(AltMerchNameAndAddrGrp value) {
        this.altMerchNameAndAddrGrp = value;
    }

    public CardGrp getCardGrp() {
        return cardGrp;
    }

    public void setCardGrp(CardGrp value) {
        this.cardGrp = value;
    }

    public TAGrp getTAGrp() {
        return taGrp;
    }

    public void setTAGrp(TAGrp value) {
        this.taGrp = value;
    }

    public OfferGrp getOfferGrp() {
        return offerGrp;
    }

    public void setOfferGrp(OfferGrp value) {
        this.offerGrp = value;
    }

    public EcommGrp getEcommGrp() {
        return ecommGrp;
    }

    public void setEcommGrp(EcommGrp value) {
        this.ecommGrp = value;
    }

    public VisaGrp getVisaGrp() {
        return visaGrp;
    }

    public void setVisaGrp(VisaGrp value) {
        this.visaGrp = value;
    }

    public DebitGrp getDebitGrp() {
        return debitGrp;
    }

    public void setDebitGrp(DebitGrp value) {
        this.debitGrp = value;
    }
}