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
@XmlType(name = CHECK_GRP_NAME, namespace = NAMESPACE, propOrder = { MICR, DRV_LIC, STATE_CODE,
        DL_DATE_OF_BIRTH, CHK_SVC_PVDR, CHK_ENTRY_METHOD })
public class CheckGrp {
    @XmlElement(name = MICR_ELT, namespace = NAMESPACE)
    protected String micr;
    @XmlElement(name = DRV_LIC_ELT, namespace = NAMESPACE)
    protected String drvLic;
    @XmlElement(name = STATE_CODE_ELT, namespace = NAMESPACE)
    protected String stateCode;
    @XmlElement(name = DL_DATE_OF_BIRTH_ELT, namespace = NAMESPACE)
    protected String dlDateOfBirth;
    @XmlElement(name = CHK_SVC_PVDR_ELT, namespace = NAMESPACE)
    protected ChkSvcPvdrType chkSvcPvdr;
    @XmlElement(name = CHK_ENTRY_METHOD_ELT, namespace = NAMESPACE)
    protected String chkEntryMethod;

    public String getMICR() {
        return micr;
    }

    public void setMICR(String value) {
        this.micr = value;
    }

    public String getDrvLic() {
        return drvLic;
    }

    public void setDrvLic(String value) {
        this.drvLic = value;
    }

    public String getStateCode() {
        return stateCode;
    }

    public void setStateCode(String value) {
        this.stateCode = value;
    }

    public String getDLDateOfBirth() {
        return dlDateOfBirth;
    }

    public void setDLDateOfBirth(String value) {
        this.dlDateOfBirth = value;
    }

    public ChkSvcPvdrType getChkSvcPvdr() {
        return chkSvcPvdr;
    }

    public void setChkSvcPvdr(ChkSvcPvdrType value) {
        this.chkSvcPvdr = value;
    }

    public String getChkEntryMethod() {
        return chkEntryMethod;
    }

    public void setChkEntryMethod(String value) {
        this.chkEntryMethod = value;
    }
}