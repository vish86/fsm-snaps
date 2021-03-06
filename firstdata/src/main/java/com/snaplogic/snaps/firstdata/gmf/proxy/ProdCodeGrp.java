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

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NAMESPACE;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NUM_OF_PRODS;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.NUM_OF_PRODS_ELT;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.PROD_CODE_GRP_NAME;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.SERV_LVL;
import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.SERV_LVL_ELT;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = PROD_CODE_GRP_NAME, namespace = NAMESPACE, propOrder = { SERV_LVL, NUM_OF_PRODS })
public class ProdCodeGrp {
    @XmlElement(name = SERV_LVL_ELT, namespace = NAMESPACE)
    protected String servLvl;
    @XmlElement(name = NUM_OF_PRODS_ELT, namespace = NAMESPACE)
    protected String numOfProds;

    public String getServLvl() {
        return servLvl;
    }

    public void setServLvl(String value) {
        this.servLvl = value;
    }

    public String getNumOfProds() {
        return numOfProds;
    }

    public void setNumOfProds(String value) {
        this.numOfProds = value;
    }
}