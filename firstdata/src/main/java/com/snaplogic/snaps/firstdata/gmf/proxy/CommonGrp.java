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
@XmlType(name = COMMON_GRP_NAME, namespace = NAMESPACE, propOrder = { PYMT_TYPE, REVERSAL_IND,
        TXN_TYPE, LOCAL_DATE_TIME, TRNMSN_DATE_TIME, STAN, REF_NUM, ORDER_NUM, TPPID, TERM_ID,
        MERCH_ID, ALT_MERCH_ID, MERCH_CAT_CODE, POS_ENTRY_MODE, POS_COND_CODE, TERM_CAT_CODE,
        TERM_ENTRY_CAPABLT, TXN_AMT, TXN_CRNCY, TERM_LOC_IND, CARD_CAPT_CAP, PROGRAM_ID, GROUP_ID,
        POSID, SETTLE_IND })
public class CommonGrp {
    @XmlElement(name = PYMT_TYPE_ELT, namespace = NAMESPACE)
    protected PymtTypeType pymtType;
    @XmlElement(name = REVERSAL_IND_ELT, namespace = NAMESPACE)
    protected ReversalIndType reversalInd;
    @XmlElement(name = TXN_TYPE_ELT, namespace = NAMESPACE)
    protected TxnTypeType txnType;
    @XmlElement(name = LOCAL_DATE_TIME_ELT, namespace = NAMESPACE)
    protected String localDateTime;
    @XmlElement(name = TRNMSN_DATE_TIME_ELT, namespace = NAMESPACE)
    protected String trnmsnDateTime;
    @XmlElement(name = STAN_ELT, namespace = NAMESPACE)
    protected String stan;
    @XmlElement(name = REF_NUM_ELT, namespace = NAMESPACE)
    protected String refNum;
    @XmlElement(name = ORDER_NUM_ELT, namespace = NAMESPACE)
    protected String orderNum;
    @XmlElement(name = TPPID_ELT, namespace = NAMESPACE)
    protected String tppid;
    @XmlElement(name = TERM_ID_ELT, namespace = NAMESPACE)
    protected String termID;
    @XmlElement(name = MERCH_ID_ELT, namespace = NAMESPACE)
    protected String merchID;
    @XmlElement(name = ALT_MERCH_ID_ELT, namespace = NAMESPACE)
    protected String altMerchID;
    @XmlElement(name = MERCH_CAT_CODE_ELT, namespace = NAMESPACE)
    protected String merchCatCode;
    @XmlElement(name = POS_ENTRY_MODE_ELT, namespace = NAMESPACE)
    protected String posEntryMode;
    @XmlElement(name = POS_COND_CODE_ELT, namespace = NAMESPACE)
    protected String posCondCode;
    @XmlElement(name = TERM_CAT_CODE_ELT, namespace = NAMESPACE)
    protected String termCatCode;
    @XmlElement(name = TERM_ENTRY_CAPABLT_ELT, namespace = NAMESPACE)
    protected String termEntryCapablt;
    @XmlElement(name = TXN_AMT_ELT, namespace = NAMESPACE)
    protected String txnAmt;
    @XmlElement(name = TXN_CRNCY_ELT, namespace = NAMESPACE)
    protected String txnCrncy;
    @XmlElement(name = TERM_LOC_IND_ELT, namespace = NAMESPACE)
    protected String termLocInd;
    @XmlElement(name = CARD_CAPT_CAP_ELT, namespace = NAMESPACE)
    protected String cardCaptCap;
    @XmlElement(name = PROGRAM_ID_ELT, namespace = NAMESPACE)
    protected ProgramIDType programID;
    @XmlElement(name = GROUP_ID_ELT, namespace = NAMESPACE)
    protected String groupID;
    @XmlElement(name = POSID_ELT, namespace = NAMESPACE)
    protected String posid;
    @XmlElement(name = SETTLE_IND_ELT, namespace = NAMESPACE)
    protected String settleInd;

    public PymtTypeType getPymtType() {
        return pymtType;
    }

    public void setPymtType(PymtTypeType value) {
        this.pymtType = value;
    }

    public ReversalIndType getReversalInd() {
        return reversalInd;
    }

    public void setReversalInd(ReversalIndType value) {
        this.reversalInd = value;
    }

    public TxnTypeType getTxnType() {
        return txnType;
    }

    public void setTxnType(TxnTypeType value) {
        this.txnType = value;
    }

    public String getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(String value) {
        this.localDateTime = value;
    }

    public String getTrnmsnDateTime() {
        return trnmsnDateTime;
    }

    public void setTrnmsnDateTime(String value) {
        this.trnmsnDateTime = value;
    }

    public String getSTAN() {
        return stan;
    }

    public void setSTAN(String value) {
        this.stan = value;
    }

    public String getRefNum() {
        return refNum;
    }

    public void setRefNum(String value) {
        this.refNum = value;
    }

    public String getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(String value) {
        this.orderNum = value;
    }

    public String getTPPID() {
        return tppid;
    }

    public void setTPPID(String value) {
        this.tppid = value;
    }

    public String getTermID() {
        return termID;
    }

    public void setTermID(String value) {
        this.termID = value;
    }

    public String getMerchID() {
        return merchID;
    }

    public void setMerchID(String value) {
        this.merchID = value;
    }

    public String getAltMerchID() {
        return altMerchID;
    }

    public void setAltMerchID(String value) {
        this.altMerchID = value;
    }

    public String getMerchCatCode() {
        return merchCatCode;
    }

    public void setMerchCatCode(String value) {
        this.merchCatCode = value;
    }

    public String getPOSEntryMode() {
        return posEntryMode;
    }

    public void setPOSEntryMode(String value) {
        this.posEntryMode = value;
    }

    public String getPOSCondCode() {
        return posCondCode;
    }

    public void setPOSCondCode(String value) {
        this.posCondCode = value;
    }

    public String getTermCatCode() {
        return termCatCode;
    }

    public void setTermCatCode(String value) {
        this.termCatCode = value;
    }

    public String getTermEntryCapablt() {
        return termEntryCapablt;
    }

    public void setTermEntryCapablt(String value) {
        this.termEntryCapablt = value;
    }

    public String getTxnAmt() {
        return txnAmt;
    }

    public void setTxnAmt(String value) {
        this.txnAmt = value;
    }

    public String getTxnCrncy() {
        return txnCrncy;
    }

    public void setTxnCrncy(String value) {
        this.txnCrncy = value;
    }

    public String getTermLocInd() {
        return termLocInd;
    }

    public void setTermLocInd(String value) {
        this.termLocInd = value;
    }

    public String getCardCaptCap() {
        return cardCaptCap;
    }

    public void setCardCaptCap(String value) {
        this.cardCaptCap = value;
    }

    public ProgramIDType getProgramID() {
        return programID;
    }

    public void setProgramID(ProgramIDType value) {
        this.programID = value;
    }

    public String getGroupID() {
        return groupID;
    }

    public void setGroupID(String value) {
        this.groupID = value;
    }

    public String getPOSID() {
        return posid;
    }

    public void setPOSID(String value) {
        this.posid = value;
    }

    public String getSettleInd() {
        return settleInd;
    }

    public void setSettleInd(String value) {
        this.settleInd = value;
    }
}