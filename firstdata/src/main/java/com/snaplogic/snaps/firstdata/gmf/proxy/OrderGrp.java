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

/**
 * This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference
 * Implementation. Any modifications to this file will be lost upon recompilation of the source
 * schema.
 * 
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrderGrp", namespace = "com/firstdata/Merchant/gmfV2.08",
        propOrder = { "orderDate" })
public class OrderGrp {

    @XmlElement(name = "OrderDate", namespace = "com/firstdata/Merchant/gmfV2.08")
    protected String orderDate;

    public String getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(String value) {
        this.orderDate = value;
    }

}
