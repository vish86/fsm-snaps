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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.EBT_GRP_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.EBT_TYPE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.EBT_TYPE2;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = EBT_GRP_NAME, namespace = NAMESPACE, propOrder = { EBT_TYPE })
public class EbtGrp {
    @XmlElement(name = EBT_TYPE2, namespace = NAMESPACE)
    protected EBTTypeType ebtType;

    public EBTTypeType getEBTType() {
        return ebtType;
    }

    public void setEBTType(EBTTypeType value) {
        this.ebtType = value;
    }
}