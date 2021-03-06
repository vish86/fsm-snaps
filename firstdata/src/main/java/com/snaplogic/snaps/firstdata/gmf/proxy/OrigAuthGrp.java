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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.*;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = ORIG_AUTH_GRP_NAME, namespace = NAMESPACE, propOrder = { ORIG_AUTH_ID,
        ORIG_LOCAL_DATE_TIME, ORIG_TRAN_DATE_TIME, ORIG_STAN, ORIG_RESP_CODE })
public class OrigAuthGrp {
    @XmlElement(name = ORIG_AUTH_ID_ELT, namespace = NAMESPACE)
    protected String origAuthID;
    @XmlElement(name = ORIG_LOCAL_DATE_TIME_ELT, namespace = NAMESPACE)
    protected String origLocalDateTime;
    @XmlElement(name = ORIG_TRAN_DATE_TIME_ELT, namespace = NAMESPACE)
    protected String origTranDateTime;
    @XmlElement(name = ORIG_STAN_ELT, namespace = NAMESPACE)
    protected String origSTAN;
    @XmlElement(name = ORIG_RESP_CODE_ELT, namespace = NAMESPACE)
    protected String origRespCode;

    public String getOrigAuthID() {
        return origAuthID;
    }

    public void setOrigAuthID(String value) {
        this.origAuthID = value;
    }

    public String getOrigLocalDateTime() {
        return origLocalDateTime;
    }

    public void setOrigLocalDateTime(String value) {
        this.origLocalDateTime = value;
    }

    public String getOrigTranDateTime() {
        return origTranDateTime;
    }

    public void setOrigTranDateTime(String value) {
        this.origTranDateTime = value;
    }

    public String getOrigSTAN() {
        return origSTAN;
    }

    public void setOrigSTAN(String value) {
        this.origSTAN = value;
    }

    public String getOrigRespCode() {
        return origRespCode;
    }

    public void setOrigRespCode(String value) {
        this.origRespCode = value;
    }
}