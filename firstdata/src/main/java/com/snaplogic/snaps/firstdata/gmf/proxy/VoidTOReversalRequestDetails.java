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
@XmlType(name = VOID_TO_REVERSAL_REQUEST_DETAILS_NAME, namespace = NAMESPACE, propOrder = {
        COMMON_GRP, BILL_PAY_GRP, ALT_MERCH_NAME_AND_ADDR_GRP, CARD_GRP, PIN_GRP, ADDTL_AMT_GRP,
        EMV_GRP, TA_GRP, OFFER_GRP, ECOMM_GRP, SECR_TXN_GRP, VISA_GRP, MC_GRP, DS_GRP, AMEX_GRP,
        EBT_GRP, DEBIT_GRP, PURCH_CARDLVL2_GRP, CUST_INFO_GRP, ORDER_GRP, STORED_VALUE_GRP,
        ORIG_AUTH_GRP, PROD_CODE_GRP, PROD_CODE_DET_GRP })
public class VoidTOReversalRequestDetails {
    @XmlElement(name = COMMON__GRP_ELT, namespace = NAMESPACE)
    protected CommonGrp commonGrp;
    @XmlElement(name = BILL_PAY__GRP_ELT, namespace = NAMESPACE)
    protected BillPayGrp billPayGrp;
    @XmlElement(name = ALT_MERCH_NAME_AND_ADDR__GRP_ELT, namespace = NAMESPACE)
    protected AltMerchNameAndAddrGrp altMerchNameAndAddrGrp;
    @XmlElement(name = CARD__GRP_ELT, namespace = NAMESPACE)
    protected CardGrp cardGrp;
    @XmlElement(name = PIN__GRP_ELT, namespace = NAMESPACE)
    protected PINGrp pinGrp;
    @XmlElement(name = ADDTL_AMT__GRP_ELT, namespace = NAMESPACE)
    protected List<AddtlAmtGrp> addtlAmtGrp;
    @XmlElement(name = EMV__GRP_ELT, namespace = NAMESPACE)
    protected EMVGrp emvGrp;
    @XmlElement(name = TA__GRP_ELT, namespace = NAMESPACE)
    protected TAGrp taGrp;
    @XmlElement(name = OFFER__GRP_ELT, namespace = NAMESPACE)
    protected OfferGrp offerGrp;
    @XmlElement(name = ECOMM__GRP_ELT, namespace = NAMESPACE)
    protected EcommGrp ecommGrp;
    @XmlElement(name = SECR_TXN__GRP_ELT, namespace = NAMESPACE)
    protected SecrTxnGrp secrTxnGrp;
    @XmlElement(name = VISA__GRP_ELT, namespace = NAMESPACE)
    protected VisaGrp visaGrp;
    @XmlElement(name = MC__GRP_ELT, namespace = NAMESPACE)
    protected MCGrp mcGrp;
    @XmlElement(name = DS__GRP_ELT, namespace = NAMESPACE)
    protected DSGrp dsGrp;
    @XmlElement(name = AMEX__GRP_ELT, namespace = NAMESPACE)
    protected AmexGrp amexGrp;
    @XmlElement(name = EBT__GRP_ELT, namespace = NAMESPACE)
    protected EbtGrp ebtGrp;
    @XmlElement(name = DEBIT__GRP_ELT, namespace = NAMESPACE)
    protected DebitGrp debitGrp;
    @XmlElement(name = PURCH_CARDLVL2__GRP_ELT, namespace = NAMESPACE)
    protected PurchCardlvl2Grp purchCardlvl2Grp;
    @XmlElement(name = CUST_INFO__GRP_ELT, namespace = NAMESPACE)
    protected CustInfoGrp custInfoGrp;
    @XmlElement(name = ORDER__GRP_ELT, namespace = NAMESPACE)
    protected OrderGrp orderGrp;
    @XmlElement(name = STORED_VALUE__GRP_ELT, namespace = NAMESPACE)
    protected StoredValueGrp storedValueGrp;
    @XmlElement(name = ORIG_AUTH__GRP_ELT, namespace = NAMESPACE)
    protected OrigAuthGrp origAuthGrp;
    @XmlElement(name = PROD_CODE__GRP_ELT, namespace = NAMESPACE)
    protected ProdCodeGrp prodCodeGrp;
    @XmlElement(name = PROD_CODE_DET__GRP_ELT, namespace = NAMESPACE)
    protected List<ProdCodeDetGrp> prodCodeDetGrp;

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

    public PINGrp getPINGrp() {
        return pinGrp;
    }

    public void setPINGrp(PINGrp value) {
        this.pinGrp = value;
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

    public SecrTxnGrp getSecrTxnGrp() {
        return secrTxnGrp;
    }

    public void setSecrTxnGrp(SecrTxnGrp value) {
        this.secrTxnGrp = value;
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

    public EbtGrp getEbtGrp() {
        return ebtGrp;
    }

    public void setEbtGrp(EbtGrp value) {
        this.ebtGrp = value;
    }

    public DebitGrp getDebitGrp() {
        return debitGrp;
    }

    public void setDebitGrp(DebitGrp value) {
        this.debitGrp = value;
    }

    public PurchCardlvl2Grp getPurchCardlvl2Grp() {
        return purchCardlvl2Grp;
    }

    public void setPurchCardlvl2Grp(PurchCardlvl2Grp value) {
        this.purchCardlvl2Grp = value;
    }

    public CustInfoGrp getCustInfoGrp() {
        return custInfoGrp;
    }

    public void setCustInfoGrp(CustInfoGrp value) {
        this.custInfoGrp = value;
    }

    public OrderGrp getOrderGrp() {
        return orderGrp;
    }

    public void setOrderGrp(OrderGrp value) {
        this.orderGrp = value;
    }

    public StoredValueGrp getStoredValueGrp() {
        return storedValueGrp;
    }

    public void setStoredValueGrp(StoredValueGrp value) {
        this.storedValueGrp = value;
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