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
@XmlType(name = RESP_GRP_NAME, namespace = NAMESPACE, propOrder = { RESP_CODE, AUTH_ID,
        ADDTL_RESP_DATA, STTLM_DATE, ATH_NTWK_ID, ERROR_DATA })
public class RespGrp {
    @XmlElement(name = RESP_CODE_ELT, namespace = NAMESPACE)
    protected String respCode;
    @XmlElement(name = AUTH_ID_ELT, namespace = NAMESPACE)
    protected String authID;
    @XmlElement(name = ADDTL_RESP_DATA_ELT, namespace = NAMESPACE)
    protected String addtlRespData;
    @XmlElement(name = STTLM_DATE_ELT, namespace = NAMESPACE)
    protected String sttlmDate;
    @XmlElement(name = ATH_NTWK_ID_ELT, namespace = NAMESPACE)
    protected String athNtwkID;
    @XmlElement(name = ERROR_DATA_ELT, namespace = NAMESPACE)
    protected String errorData;

    public String getRespCode() {
        return respCode;
    }

    public void setRespCode(String value) {
        this.respCode = value;
    }

    public String getAuthID() {
        return authID;
    }

    public void setAuthID(String value) {
        this.authID = value;
    }

    public String getAddtlRespData() {
        return addtlRespData;
    }

    public void setAddtlRespData(String value) {
        this.addtlRespData = value;
    }

    public String getSttlmDate() {
        return sttlmDate;
    }

    public void setSttlmDate(String value) {
        this.sttlmDate = value;
    }

    public String getAthNtwkID() {
        return athNtwkID;
    }

    public void setAthNtwkID(String value) {
        this.athNtwkID = value;
    }

    public String getErrorData() {
        return errorData;
    }

    public void setErrorData(String value) {
        this.errorData = value;
    }
}