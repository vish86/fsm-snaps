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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.STATUS_CODE;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.STATUS_TYPE;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.VALUE;


/**
 * <p>
 * Java class for StatusType complex type.
 * 
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = STATUS_TYPE, namespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP,
        propOrder = { VALUE })
public class StatusType implements Serializable {
    @XmlValue
    protected String value;
    @XmlAttribute(name = STATUS_CODE, required = true)
    protected String statusCode;

    /**
     * Gets the value of the value property.
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the statusCode property.
     */
    public String getStatusCode() {
        return statusCode;
    }

    /**
     * Sets the value of the statusCode property.
     */
    public void setStatusCode(String value) {
        this.statusCode = value;
    }
}