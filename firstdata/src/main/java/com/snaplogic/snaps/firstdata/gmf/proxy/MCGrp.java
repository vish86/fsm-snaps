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
@XmlType(name = MC_GRP_NAME, namespace = NAMESPACE, propOrder = { BANKNET_DATA, MCMSDI,
        CCV_ERROR_CODE, POS_ENTRY_MODE_CHG, TRAN_EDIT_ERR_CODE, MCPOS_DATA, DEV_TYPE_IND })
public class MCGrp {
    @XmlElement(name = BANKNET_DATA_ELT, namespace = NAMESPACE)
    protected String banknetData;
    @XmlElement(name = MCMSDI_ELT, namespace = NAMESPACE)
    protected MCMSDIType mcmsdi;
    @XmlElement(name = CCV_ERROR_CODE_ELT, namespace = NAMESPACE)
    protected CCVErrorCodeType ccvErrorCode;
    @XmlElement(name = POS_ENTRY_MODE_CHG_ELT, namespace = NAMESPACE)
    protected POSEntryModeChgType posEntryModeChg;
    @XmlElement(name = TRAN_EDIT_ERR_CODE_ELT, namespace = NAMESPACE)
    protected String tranEditErrCode;
    @XmlElement(name = MCPOS_DATA_ELT, namespace = NAMESPACE)
    protected String mcposData;
    @XmlElement(name = DEV_TYPE_IND_ELT, namespace = NAMESPACE)
    protected String devTypeInd;

    public String getBanknetData() {
        return banknetData;
    }

    public void setBanknetData(String value) {
        this.banknetData = value;
    }

    public MCMSDIType getMCMSDI() {
        return mcmsdi;
    }

    public void setMCMSDI(MCMSDIType value) {
        this.mcmsdi = value;
    }

    public CCVErrorCodeType getCCVErrorCode() {
        return ccvErrorCode;
    }

    public void setCCVErrorCode(CCVErrorCodeType value) {
        this.ccvErrorCode = value;
    }

    public POSEntryModeChgType getPOSEntryModeChg() {
        return posEntryModeChg;
    }

    public void setPOSEntryModeChg(POSEntryModeChgType value) {
        this.posEntryModeChg = value;
    }

    public String getTranEditErrCode() {
        return tranEditErrCode;
    }

    public void setTranEditErrCode(String value) {
        this.tranEditErrCode = value;
    }

    public String getMCPOSData() {
        return mcposData;
    }

    public void setMCPOSData(String value) {
        this.mcposData = value;
    }

    public String getDevTypeInd() {
        return devTypeInd;
    }

    public void setDevTypeInd(String value) {
        this.devTypeInd = value;
    }
}