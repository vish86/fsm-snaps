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
package com.snaplogic.snaps.firstdata.dw.rcservice;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;

/**
 * This object contains factory methods for each Java content interface and Java element interface
 * generated in the dw.securetransport.rcservice.soap package.
 * <p>
 * An ObjectFactory allows you to programatically construct new instances of the Java representation
 * for XML content. The Java representation of XML content can consist of schema derived interfaces
 * and classes representing the binding of schema type definitions, element declarations and model
 * groups. Factory methods for each of these are provided in this class.
 * 
 * @author svatada
 */
@XmlRegistry
public class ObjectFactory {
    private final static QName _Response_QNAME = new QName(
            "http://securetransport.dw/rcservice/soap", "Response");
    private final static QName _Transaction_QNAME = new QName(
            "http://securetransport.dw/rcservice/soap", "Transaction");
    private final static QName _TransactionResponse_QNAME = new QName(
            "http://securetransport.dw/rcservice/soap", "TransactionResponse");
    private final static QName _Request_QNAME = new QName(
            "http://securetransport.dw/rcservice/soap", "Request");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes
     * for package: dw.securetransport.rcservice.soap
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link PayloadType }
     */
    public PayloadType createPayloadType() {
        return new PayloadType();
    }

    /**
     * Create an instance of {@link StatusType }
     */
    public StatusType createStatusType() {
        return new StatusType();
    }

    /**
     * Create an instance of {@link RespClientIDType }
     */
    public RespClientIDType createRespClientIDType() {
        return new RespClientIDType();
    }

    /**
     * Create an instance of {@link RequestType }
     */
    public RequestType createRequestType() {
        return new RequestType();
    }

    /**
     * Create an instance of {@link ReqClientIDType }
     */
    public ReqClientIDType createReqClientIDType() {
        return new ReqClientIDType();
    }

    /**
     * Create an instance of {@link TransactionResponseType }
     */
    public TransactionResponseType createTransactionResponseType() {
        return new TransactionResponseType();
    }

    /**
     * Create an instance of {@link TransactionType }
     */
    public TransactionType createTransactionType() {
        return new TransactionType();
    }

    /**
     * Create an instance of {@link ResponseType }
     */
    public ResponseType createResponseType() {
        return new ResponseType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType } {@code >}
     */
    @XmlElementDecl(namespace = "http://securetransport.dw/rcservice/soap", name = "Response")
    public JAXBElement<ResponseType> createResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_Response_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link TransactionType } {@code >}
     */
    @XmlElementDecl(namespace = "http://securetransport.dw/rcservice/soap", name = "Transaction")
    public JAXBElement<TransactionType> createTransaction(TransactionType value) {
        return new JAXBElement<TransactionType>(_Transaction_QNAME, TransactionType.class, null,
                value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <} {@link TransactionResponseType }{@code >}
     */
    @XmlElementDecl(namespace = "http://securetransport.dw/rcservice/soap",
            name = "TransactionResponse")
    public JAXBElement<TransactionResponseType> createTransactionResponse(
            TransactionResponseType value) {
        return new JAXBElement<TransactionResponseType>(_TransactionResponse_QNAME,
                TransactionResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link RequestType } {@code >}
     */
    @XmlElementDecl(namespace = "http://securetransport.dw/rcservice/soap", name = "Request")
    public JAXBElement<RequestType> createRequest(RequestType value) {
        return new JAXBElement<RequestType>(_Request_QNAME, RequestType.class, null, value);
    }
}
