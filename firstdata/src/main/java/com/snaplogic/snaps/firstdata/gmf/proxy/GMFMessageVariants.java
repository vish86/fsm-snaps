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
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import static com.snaplogic.snaps.firstdata.gmf.proxy.Constants.*;

/**
 * This class was generated based on UMF Schema V2.10a.6V5.xsd
 *
 * @author svatada
 */
@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = GMF_MESSAGE_VARIANTS_NAME, namespace = NAMESPACE, propOrder = { CREDIT_REQUEST,
        DEBIT_REQUEST, PINLESS_DEBIT_REQUEST, CHECK_REQUEST, EBT_REQUEST, PREPAID_REQUEST,
        GEN_PREPAID_REQUEST, PRIVATE_LABEL_REQUEST, TRANS_ARMOR_REQUEST, ADMIN_REQUEST,
        REVERSAL_REQUEST, CREDIT_RESPONSE, DEBIT_RESPONSE, PINLESS_DEBIT_RESPONSE, CHECK_RESPONSE,
        EBT_RESPONSE, PREPAID_RESPONSE, GEN_PREPAID_RESPONSE, PRIVATE_LABEL_RESPONSE,
        TRANS_ARMOR_RESPONSE, ADMIN_RESPONSE, REJECT_RESPONSE, REVERSAL_RESPONSE })
public class GMFMessageVariants {
    @XmlElement(name = CREDIT_REQUEST_ELT, namespace = NAMESPACE)
    protected CreditRequestDetails creditRequest;
    @XmlElement(name = DEBIT_REQUEST_ELT, namespace = NAMESPACE)
    protected DebitRequestDetails debitRequest;
    @XmlElement(name = PINLESS_DEBIT_REQUEST_ELT, namespace = NAMESPACE)
    protected PinlessDebitRequestDetails pinlessDebitRequest;
    @XmlElement(name = CHECK_REQUEST_ELT, namespace = NAMESPACE)
    protected CheckRequestDetails checkRequest;
    @XmlElement(name = EBT_REQUEST_ELT, namespace = NAMESPACE)
    protected EBTRequestDetails ebtRequest;
    @XmlElement(name = PREPAID_REQUEST_ELT, namespace = NAMESPACE)
    protected PrepaidRequestDetails prepaidRequest;
    @XmlElement(name = GEN_PREPAID_REQUEST_ELT, namespace = NAMESPACE)
    protected GenPrepaidRequestDetails genPrepaidRequest;
    @XmlElement(name = PRIVATE_LABEL_REQUEST_ELT, namespace = NAMESPACE)
    protected PrivateLabelRequestDetails privateLabelRequest;
    @XmlElement(name = TRANS_ARMOR_REQUEST_ELT, namespace = NAMESPACE)
    protected TARequestDetails transArmorRequest;
    @XmlElement(name = ADMIN_REQUEST_ELT, namespace = NAMESPACE)
    protected AdminRequestDetails adminRequest;
    @XmlElement(name = REVERSAL_REQUEST_ELT, namespace = NAMESPACE)
    protected VoidTOReversalRequestDetails reversalRequest;
    @XmlElement(name = CREDIT_RESPONSE_ELT, namespace = NAMESPACE)
    protected CreditResponseDetails creditResponse;
    @XmlElement(name = DEBIT_RESPONSE_ELT, namespace = NAMESPACE)
    protected DebitResponseDetails debitResponse;
    @XmlElement(name = PINLESS_DEBIT_RESPONSE_ELT, namespace = NAMESPACE)
    protected PinlessDebitResponseDetails pinlessDebitResponse;
    @XmlElement(name = CHECK_RESPONSE_ELT, namespace = NAMESPACE)
    protected CheckResponseDetails checkResponse;
    @XmlElement(name = EBT_RESPONSE_ELT, namespace = NAMESPACE)
    protected EBTResponseDetails ebtResponse;
    @XmlElement(name = PREPAID_RESPONSE_ELT, namespace = NAMESPACE)
    protected PrepaidResponseDetails prepaidResponse;
    @XmlElement(name = GEN_PREPAID_RESPONSE_ELT, namespace = NAMESPACE)
    protected GenPrepaidResponseDetails genPrepaidResponse;
    @XmlElement(name = PRIVATE_LABEL_RESPONSE_ELT, namespace = NAMESPACE)
    protected PrivateLabelResponseDetails privateLabelResponse;
    @XmlElement(name = TRANS_ARMOR_RESPONSE_ELT, namespace = NAMESPACE)
    protected TAResponseDetails transArmorResponse;
    @XmlElement(name = ADMIN_RESPONSE_ELT, namespace = NAMESPACE)
    protected AdminResponseDetails adminResponse;
    @XmlElement(name = REJECT_RESPONSE_ELT, namespace = NAMESPACE)
    protected RejectResponseDetails rejectResponse;
    @XmlElement(name = REVERSAL_RESPONSE_ELT, namespace = NAMESPACE)
    protected VoidTOReversalResponseDetails reversalResponse;

    public CreditRequestDetails getCreditRequest() {
        return creditRequest;
    }

    public void setCreditRequest(CreditRequestDetails value) {
        this.creditRequest = value;
    }

    public DebitRequestDetails getDebitRequest() {
        return debitRequest;
    }

    public void setDebitRequest(DebitRequestDetails value) {
        this.debitRequest = value;
    }

    public PinlessDebitRequestDetails getPinlessDebitRequest() {
        return pinlessDebitRequest;
    }

    public void setPinlessDebitRequest(PinlessDebitRequestDetails value) {
        this.pinlessDebitRequest = value;
    }

    public CheckRequestDetails getCheckRequest() {
        return checkRequest;
    }

    public void setCheckRequest(CheckRequestDetails value) {
        this.checkRequest = value;
    }

    public EBTRequestDetails getEBTRequest() {
        return ebtRequest;
    }

    public void setEBTRequest(EBTRequestDetails value) {
        this.ebtRequest = value;
    }

    public PrepaidRequestDetails getPrepaidRequest() {
        return prepaidRequest;
    }

    public void setPrepaidRequest(PrepaidRequestDetails value) {
        this.prepaidRequest = value;
    }

    public GenPrepaidRequestDetails getGenPrepaidRequest() {
        return genPrepaidRequest;
    }

    public void setGenPrepaidRequest(GenPrepaidRequestDetails value) {
        this.genPrepaidRequest = value;
    }

    public PrivateLabelRequestDetails getPrivateLabelRequest() {
        return privateLabelRequest;
    }

    public void setPrivateLabelRequest(PrivateLabelRequestDetails value) {
        this.privateLabelRequest = value;
    }

    public TARequestDetails getTransArmorRequest() {
        return transArmorRequest;
    }

    public void setTransArmorRequest(TARequestDetails value) {
        this.transArmorRequest = value;
    }

    public AdminRequestDetails getAdminRequest() {
        return adminRequest;
    }

    public void setAdminRequest(AdminRequestDetails value) {
        this.adminRequest = value;
    }

    public VoidTOReversalRequestDetails getReversalRequest() {
        return reversalRequest;
    }

    public void setReversalRequest(VoidTOReversalRequestDetails value) {
        this.reversalRequest = value;
    }

    public CreditResponseDetails getCreditResponse() {
        return creditResponse;
    }

    public void setCreditResponse(CreditResponseDetails value) {
        this.creditResponse = value;
    }

    public DebitResponseDetails getDebitResponse() {
        return debitResponse;
    }

    public void setDebitResponse(DebitResponseDetails value) {
        this.debitResponse = value;
    }

    public PinlessDebitResponseDetails getPinlessDebitResponse() {
        return pinlessDebitResponse;
    }

    public void setPinlessDebitResponse(PinlessDebitResponseDetails value) {
        this.pinlessDebitResponse = value;
    }

    public CheckResponseDetails getCheckResponse() {
        return checkResponse;
    }

    public void setCheckResponse(CheckResponseDetails value) {
        this.checkResponse = value;
    }

    public EBTResponseDetails getEBTResponse() {
        return ebtResponse;
    }

    public void setEBTResponse(EBTResponseDetails value) {
        this.ebtResponse = value;
    }

    public PrepaidResponseDetails getPrepaidResponse() {
        return prepaidResponse;
    }

    public void setPrepaidResponse(PrepaidResponseDetails value) {
        this.prepaidResponse = value;
    }

    public GenPrepaidResponseDetails getGenPrepaidResponse() {
        return genPrepaidResponse;
    }

    public void setGenPrepaidResponse(GenPrepaidResponseDetails value) {
        this.genPrepaidResponse = value;
    }

    public PrivateLabelResponseDetails getPrivateLabelResponse() {
        return privateLabelResponse;
    }

    public void setPrivateLabelResponse(PrivateLabelResponseDetails value) {
        this.privateLabelResponse = value;
    }

    public TAResponseDetails getTransArmorResponse() {
        return transArmorResponse;
    }

    public void setTransArmorResponse(TAResponseDetails value) {
        this.transArmorResponse = value;
    }

    public AdminResponseDetails getAdminResponse() {
        return adminResponse;
    }

    public void setAdminResponse(AdminResponseDetails value) {
        this.adminResponse = value;
    }

    public RejectResponseDetails getRejectResponse() {
        return rejectResponse;
    }

    public void setRejectResponse(RejectResponseDetails value) {
        this.rejectResponse = value;
    }

    public VoidTOReversalResponseDetails getReversalResponse() {
        return reversalResponse;
    }

    public void setReversalResponse(VoidTOReversalResponseDetails value) {
        this.reversalResponse = value;
    }
}