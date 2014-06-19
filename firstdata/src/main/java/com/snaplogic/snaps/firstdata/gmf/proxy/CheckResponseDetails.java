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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ADDTL_AMT_GRP;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.ADDTL_AMT_GRP_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.CHECK_RESPONSE_DETAILS;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.COMMON_GRP;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.COMMON_GRP_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.RESP_GRP;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.RESP_GRP_ELT;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = CHECK_RESPONSE_DETAILS, namespace = NAMESPACE, propOrder = { COMMON_GRP,
        ADDTL_AMT_GRP, RESP_GRP })
public class CheckResponseDetails {
    @XmlElement(name = COMMON_GRP_ELT, namespace = NAMESPACE)
    protected CommonGrp commonGrp;
    @XmlElement(name = ADDTL_AMT_GRP_ELT, namespace = NAMESPACE)
    protected List<AddtlAmtGrp> addtlAmtGrp;
    @XmlElement(name = RESP_GRP_ELT, namespace = NAMESPACE)
    protected RespGrp respGrp;

    public CommonGrp getCommonGrp() {
        return commonGrp;
    }

    public void setCommonGrp(CommonGrp value) {
        this.commonGrp = value;
    }

    public List<AddtlAmtGrp> getAddtlAmtGrp() {
        if (addtlAmtGrp == null) {
            addtlAmtGrp = new ArrayList<AddtlAmtGrp>();
        }
        return this.addtlAmtGrp;
    }

    public RespGrp getRespGrp() {
        return respGrp;
    }

    public void setRespGrp(RespGrp value) {
        this.respGrp = value;
    }
}