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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.*;

/**
 * Java class for ResponseType complex type.
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = RESPONSE_TYPE, namespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP, propOrder = {
        RESP_CLIENT_ID, STATUS_S, TRANSACTION_RESPONSE_S })
public class ResponseType implements Serializable {
    @XmlElement(name = RESP_CLIENT_ID_C, required = true)
    protected RespClientIDType respClientID;
    @XmlElement(name = STATUS, required = true)
    protected StatusType status;
    @XmlElement(name = TRANSACTION_RESPONSE)
    protected TransactionResponseType transactionResponse;
    @XmlAttribute(name = VERSION, required = true)
    protected String version;

    /**
     * Gets the value of the respClientID property.
     */
    public RespClientIDType getRespClientID() {
        return respClientID;
    }

    /**
     * Sets the value of the respClientID property.
     */
    public void setRespClientID(RespClientIDType value) {
        this.respClientID = value;
    }

    /**
     * Gets the value of the status property.
     */
    public StatusType getStatus() {
        return status;
    }

    /**
     * Sets the value of the status property.
     */
    public void setStatus(StatusType value) {
        this.status = value;
    }

    /**
     * Gets the value of the transactionResponse property.
     */
    public TransactionResponseType getTransactionResponse() {
        return transactionResponse;
    }

    /**
     * Sets the value of the transactionResponse property.
     */
    public void setTransactionResponse(TransactionResponseType value) {
        this.transactionResponse = value;
    }

    /**
     * Gets the value of the version property.
     */
    public String getVersion() {
        if (version == null) {
            return VERSION_NUM;
        } else {
            return version;
        }
    }

    /**
     * Sets the value of the version property.
     */
    public void setVersion(String value) {
        this.version = value;
    }
}