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

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.*;


/**
 * <p>
 * Java class for ReqClientIDType complex type.
 * </p>
 * 
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = REQ_CLIENT_ID_TYPE, namespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP,
        propOrder = { DID3, APP3, AUTH3, CLIENT_REF2 })
public class ReqClientIDType implements Serializable {
    @XmlElement(name = DID2, required = true)
    protected String did;
    @XmlElement(name = APP2, required = true)
    protected String app;
    @XmlElement(name = AUTH2, required = true)
    protected String auth;
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
     * Gets the value of the app property.
     */
    public String getApp() {
        return app;
    }

    /**
     * Sets the value of the app property.
     */
    public void setApp(String value) {
        this.app = value;
    }

    /**
     * Gets the value of the auth property.
     */
    public String getAuth() {
        return auth;
    }

    /**
     * Sets the value of the auth property.
     */
    public void setAuth(String value) {
        this.auth = value;
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
