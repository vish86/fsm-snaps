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
@XmlType(name = DS_GRP_NAME, namespace = NAMESPACE, propOrder = { DISC_PROC_CODE, DISC_POS_ENTRY,
        DISC_RESP_CODE, DISC_POS_DATA, DISC_TRANS_QUALIFIER, DISC_NRID })
public class DSGrp {
    @XmlElement(name = DISC_PROC_CODE_ELT, namespace = NAMESPACE)
    protected String discProcCode;
    @XmlElement(name = DISC_POS_ENTRY_ELT, namespace = NAMESPACE)
    protected String discPOSEntry;
    @XmlElement(name = DISC_RESP_CODE_ELT, namespace = NAMESPACE)
    protected String discRespCode;
    @XmlElement(name = DISC_POS_DATA_ELT, namespace = NAMESPACE)
    protected String discPOSData;
    @XmlElement(name = DISC_TRANS_QUALIFIER_ELT, namespace = NAMESPACE)
    protected String discTransQualifier;
    @XmlElement(name = DISC_NRID_ELT, namespace = NAMESPACE)
    protected String discNRID;

    public String getDiscProcCode() {
        return discProcCode;
    }

    public void setDiscProcCode(String value) {
        this.discProcCode = value;
    }

    public String getDiscPOSEntry() {
        return discPOSEntry;
    }

    public void setDiscPOSEntry(String value) {
        this.discPOSEntry = value;
    }

    public String getDiscRespCode() {
        return discRespCode;
    }

    public void setDiscRespCode(String value) {
        this.discRespCode = value;
    }

    public String getDiscPOSData() {
        return discPOSData;
    }

    public void setDiscPOSData(String value) {
        this.discPOSData = value;
    }

    public String getDiscTransQualifier() {
        return discTransQualifier;
    }

    public void setDiscTransQualifier(String value) {
        this.discTransQualifier = value;
    }

    public String getDiscNRID() {
        return discNRID;
    }

    public void setDiscNRID(String value) {
        this.discNRID = value;
    }
}