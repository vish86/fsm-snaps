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

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.CLIENT_REF;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.CLIENT_REF2;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.DID2;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.DID3;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.RESP_CLIENT_ID_TYPE;

/**
 * Java class for RespClientIDType complex type.
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = RESP_CLIENT_ID_TYPE, namespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP,
propOrder = { DID3, CLIENT_REF2 })
public class RespClientIDType implements Serializable {
    @XmlElement(name = DID2, required = true)
    protected String did;
    @XmlElement(name = CLIENT_REF, required = true)
    protected String clientRef;

    /**
     * Gets the value of the did property.
     */
    public String getDID() {
        return did;
    }

    /**
     * Sets the value of the did property.
     */
    public void setDID(String value) {
        this.did = value;
    }

    /**
     * Gets the value of the clientRef property.
     */
    public String getClientRef() {
        return clientRef;
    }

    /**
     * Sets the value of the clientRef property.
     */
    public void setClientRef(String value) {
        this.clientRef = value;
    }
}