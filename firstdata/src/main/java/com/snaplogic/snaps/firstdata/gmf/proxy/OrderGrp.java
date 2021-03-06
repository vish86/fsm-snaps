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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ORDER_DATE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ORDER_DATE_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ORDER_GRP_NAME;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ORDER_GRP_NAME, namespace = NAMESPACE, propOrder = { ORDER_DATE })
public class OrderGrp {
    @XmlElement(name = ORDER_DATE_ELT, namespace = NAMESPACE)
    protected String orderDate;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String value) {
        this.orderDate = value;
    }
}