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
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.RETURN_CODE;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.RETURN_CODE_S;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.TRANSACTION_RESPONSE_TYPE_C;

/**
 * Java class for TransactionResponseType complex type.
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = TRANSACTION_RESPONSE_TYPE_C, namespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP,
propOrder = { RETURN_CODE_S, PAYLOAD_S })
public class TransactionResponseType implements Serializable {
    @XmlElement(name = RETURN_CODE, required = true)
    protected String returnCode;
    @XmlElement(name = PAYLOAD)
    protected PayloadType payload;

    /**
     * Gets the value of the returnCode property.
     */
    public String getReturnCode() {
        return returnCode;
    }

    /**
     * Sets the value of the returnCode property.
     */
    public void setReturnCode(String value) {
        this.returnCode = value;
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