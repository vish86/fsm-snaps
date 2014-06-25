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

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.ENCODING2;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.PAYLOAD_TYPE;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.VALUE;

/**
 * Java class for PayloadType complex type.
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PAYLOAD_TYPE, namespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP, propOrder = { VALUE })
public class PayloadType implements Serializable {
    @XmlValue
    protected String value;
    @XmlAttribute(name = ENCODING2)
    protected String encoding;

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
     * Gets the value of the encoding property.
     */
    public String getEncoding() {
        return encoding;
    }

    /**
     * Sets the value of the encoding property.
     */
    public void setEncoding(String value) {
        this.encoding = value;
    }
}