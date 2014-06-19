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
import java.math.BigInteger;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.CLIENT_TIMEOUT;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.REQUEST_TYPE;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.REQ_CLIENT_ID;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.REQ_CLIENT_ID2;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.TRANSACTION;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.TRANSACTION3;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.VERSION;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.VERSION_NUM;

/**
 * Java class for RequestType complex type.
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = REQUEST_TYPE, namespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP, propOrder = {
        REQ_CLIENT_ID2, TRANSACTION3 })
public class RequestType implements Serializable {
    @XmlElement(name = REQ_CLIENT_ID, required = true)
    protected ReqClientIDType reqClientID;
    @XmlElement(name = TRANSACTION, required = true)
    protected TransactionType transaction;
    @XmlAttribute(name = VERSION, required = true)
    protected String version;
    @XmlAttribute(name = CLIENT_TIMEOUT)
    protected BigInteger clientTimeout;

    /**
     * Gets the value of the reqClientID property.
     */
    public ReqClientIDType getReqClientID() {
        return reqClientID;
    }

    /**
     * Sets the value of the reqClientID property.
     */
    public void setReqClientID(ReqClientIDType value) {
        this.reqClientID = value;
    }

    /**
     * Gets the value of the transaction property.
     */
    public TransactionType getTransaction() {
        return transaction;
    }

    /**
     * Sets the value of the transaction property.
     */
    public void setTransaction(TransactionType value) {
        this.transaction = value;
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

    /**
     * Gets the value of the clientTimeout property.
     */
    public BigInteger getClientTimeout() {
        return clientTimeout;
    }

    /**
     * Sets the value of the clientTimeout property.
     */
    public void setClientTimeout(BigInteger value) {
        this.clientTimeout = value;
    }
}