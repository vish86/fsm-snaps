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
package com.snaplogic.snaps.firstdata.dw.rcservice;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.PAYLOAD;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.PAYLOAD_S;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.SERVICE_ID;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.SERVICE_ID_C;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.TRANSACTION_TYPE;

/**
 * Java class for TransactionType complex type.
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TRANSACTION_TYPE, namespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP, propOrder = {
        SERVICE_ID, PAYLOAD_S })
public class TransactionType implements Serializable {
    @XmlElement(name = SERVICE_ID_C, required = true)
    protected String serviceID;
    @XmlElement(name = PAYLOAD, required = true)
    protected PayloadType payload;

    /**
     * Gets the value of the serviceID property.
     */
    public String getServiceID() {
        return serviceID;
    }

    /**
     * Sets the value of the serviceID property.
     */
    public void setServiceID(String value) {
        this.serviceID = value;
    }

    /**
     * Gets the value of the payload property.
     */
    public PayloadType getPayload() {
        return payload;
    }

    /**
     * Sets the value of the payload property.
     */
    public void setPayload(PayloadType value) {
        this.payload = value;
    }
}