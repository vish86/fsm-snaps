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
@XmlType(name = "PrepaidRequestDetails", namespace = "com/firstdata/Merchant/gmfV2.08",
        propOrder = { "commonGrp", "altMerchNameAndAddrGrp", "cardGrp", "addtlAmtGrp",
                "storedValueGrp" })
public class PrepaidRequestDetails {

    @XmlElement(name = "CommonGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected CommonGrp commonGrp;
    @XmlElement(name = "AltMerchNameAndAddrGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected AltMerchNameAndAddrGrp altMerchNameAndAddrGrp;
    @XmlElement(name = "CardGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected CardGrp cardGrp;
    @XmlElement(name = "AddtlAmtGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected List<AddtlAmtGrp> addtlAmtGrp;
    @XmlElement(name = "StoredValueGrp", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected StoredValueGrp storedValueGrp;

    public CommonGrp getCommonGrp() {
        return commonGrp;
    }

    public void setCommonGrp(CommonGrp value) {
        this.commonGrp = value;
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

    public List<AddtlAmtGrp> getAddtlAmtGrp() {
        if (addtlAmtGrp == null) {
            addtlAmtGrp = new ArrayList<AddtlAmtGrp>();
        }
        return this.addtlAmtGrp;
    }

    public StoredValueGrp getStoredValueGrp() {
        return storedValueGrp;
    }

    public void setStoredValueGrp(StoredValueGrp value) {
        this.storedValueGrp = value;
    }

}
