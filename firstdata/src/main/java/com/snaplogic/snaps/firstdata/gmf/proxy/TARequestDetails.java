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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CARD_GRP;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CARD_GRP_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.COMMON_GRP;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.COMMON_GRP_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TA_GRP;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TA_GRP_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.TA_REQUEST_DETAILS_NAME;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TA_REQUEST_DETAILS_NAME, namespace = NAMESPACE, propOrder = { COMMON_GRP, CARD_GRP,
        TA_GRP })
public class TARequestDetails {
    @XmlElement(name = COMMON_GRP_ELT, namespace = NAMESPACE)
    protected CommonGrp commonGrp;
    @XmlElement(name = CARD_GRP_ELT, namespace = NAMESPACE)
    protected CardGrp cardGrp;
    @XmlElement(name = TA_GRP_ELT, namespace = NAMESPACE)
    protected TAGrp taGrp;

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

    public TAGrp getTAGrp() {
        return taGrp;
    }

    public void setTAGrp(TAGrp value) {
        this.taGrp = value;
    }
}