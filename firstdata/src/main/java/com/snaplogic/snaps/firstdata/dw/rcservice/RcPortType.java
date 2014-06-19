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

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.BODY;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.HTTP_SECURETRANSPORT_DW_RCSERVICE;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.RC_PORT_TYPE;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.REQUEST;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.RESPONSE;

/**
 * RcPortType
 *
 * @author svatada
 */
@WebService(name = RC_PORT_TYPE, targetNamespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP)
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ ObjectFactory.class })
public interface RcPortType {
    /**
     * @param body
     * @return ResponseType
     */
    @WebMethod(action = HTTP_SECURETRANSPORT_DW_RCSERVICE)
    @WebResult(name = RESPONSE, targetNamespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP,
    partName = BODY)
    public ResponseType rcTransaction(
            @WebParam(name = REQUEST, targetNamespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP,
            partName = BODY) RequestType body);
}