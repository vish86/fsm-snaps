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
@XmlType(name = FILE_DL_GRP_NAME, namespace = NAMESPACE, propOrder = { FILE_TYPE, FUN_CODE,
        CURR_FILE_CREATION_DT, FILE_SIZE, FILE_CRC16, FB_SEQ, REQ_FB_MAX_SIZE, REQ_FILE_OFFSET,
        NEXT_FILE_DL_OFFSET, FB_DATA })
public class FileDLGrp {
    @XmlElement(name = FILE_TYPE_ELT, namespace = NAMESPACE)
    protected FileTypeType fileType;
    @XmlElement(name = FUN_CODE_ELT, namespace = NAMESPACE)
    protected FunCodeType funCode;
    @XmlElement(name = CURR_FILE_CREATION_DT_ELT, namespace = NAMESPACE)
    protected String currFileCreationDt;
    @XmlElement(name = FILE_SIZE_ELT, namespace = NAMESPACE)
    protected String fileSize;
    @XmlElement(name = FILE_CRC16_ELT, namespace = NAMESPACE)
    protected String fileCRC16;
    @XmlElement(name = FB_SEQ_ELT, namespace = NAMESPACE)
    protected String fbSeq;
    @XmlElement(name = REQ_FB_MAX_SIZE_ELT, namespace = NAMESPACE)
    protected String reqFBMaxSize;
    @XmlElement(name = REQ_FILE_OFFSET_ELT, namespace = NAMESPACE)
    protected String reqFileOffset;
    @XmlElement(name = NEXT_FILE_DL_OFFSET_ELT, namespace = NAMESPACE)
    protected String nextFileDLOffset;
    @XmlElement(name = FB_DATA_ELT, namespace = NAMESPACE)
    protected String fbData;

    public FileTypeType getFileType() {
        return fileType;
    }

    public void setFileType(FileTypeType value) {
        this.fileType = value;
    }

    public FunCodeType getFunCode() {
        return funCode;
    }

    public void setFunCode(FunCodeType value) {
        this.funCode = value;
    }

    public String getCurrFileCreationDt() {
        return currFileCreationDt;
    }

    public void setCurrFileCreationDt(String value) {
        this.currFileCreationDt = value;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String value) {
        this.fileSize = value;
    }

    public String getFileCRC16() {
        return fileCRC16;
    }

    public void setFileCRC16(String value) {
        this.fileCRC16 = value;
    }

    public String getFBSeq() {
        return fbSeq;
    }

    public void setFBSeq(String value) {
        this.fbSeq = value;
    }

    public String getReqFBMaxSize() {
        return reqFBMaxSize;
    }

    public void setReqFBMaxSize(String value) {
        this.reqFBMaxSize = value;
    }

    public String getReqFileOffset() {
        return reqFileOffset;
    }

    public void setReqFileOffset(String value) {
        this.reqFileOffset = value;
    }

    public String getNextFileDLOffset() {
        return nextFileDLOffset;
    }

    public void setNextFileDLOffset(String value) {
        this.nextFileDLOffset = value;
    }

    public String getFBData() {
        return fbData;
    }

    public void setFBData(String value) {
        this.fbData = value;
    }
}