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

import java.util.ArrayList;
import java.util.List;

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
@XmlType(name = ADMIN_RESPONSE_DETAILS_NAME, namespace = NAMESPACE, propOrder = { COMMON_GRP,
        RESP_GRP, HOST_TOT_GRP, HOST_TOT_DET_GRP, FILE_DL_GRP })
public class AdminResponseDetails {
    @XmlElement(name = COMMON_GRP_ELT, namespace = NAMESPACE)
    protected CommonGrp commonGrp;
    @XmlElement(name = RESP_GRP_ELT, namespace = NAMESPACE)
    protected RespGrp respGrp;
    @XmlElement(name = HOST_TOT_GRP_ELT, namespace = NAMESPACE)
    protected HostTotGrp hostTotGrp;
    @XmlElement(name = HOST_TOT_DET_GRP_ELT, namespace = NAMESPACE)
    protected List<HostTotDetGrp> hostTotDetGrp;
    @XmlElement(name = FILE_DL_GRPELT, namespace = NAMESPACE)
    protected FileDLGrp fileDLGrp;

    public CommonGrp getCommonGrp() {
        return commonGrp;
    }

    public void setCommonGrp(CommonGrp value) {
        this.commonGrp = value;
    }

    public RespGrp getRespGrp() {
        return respGrp;
    }

    public void setRespGrp(RespGrp value) {
        this.respGrp = value;
    }

    public HostTotGrp getHostTotGrp() {
        return hostTotGrp;
    }

    public void setHostTotGrp(HostTotGrp value) {
        this.hostTotGrp = value;
    }

    public List<HostTotDetGrp> getHostTotDetGrp() {
        if (hostTotDetGrp == null) {
            hostTotDetGrp = new ArrayList<HostTotDetGrp>();
        }
        return this.hostTotDetGrp;
    }

    public FileDLGrp getFileDLGrp() {
        return fileDLGrp;
    }

    public void setFileDLGrp(FileDLGrp value) {
        this.fileDLGrp = value;
    }
}