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

@WebService(name = "rcPortType", targetNamespace = "http://securetransport.dw/rcservice/soap")
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
@XmlSeeAlso({ ObjectFactory.class })
public interface RcPortType {

    /**
     * @param body
     * @return returns dw.securetransport.rcservice.soap.ResponseType
     */
    @WebMethod(action = "http://securetransport.dw/rcservice")
    @WebResult(name = "Response", targetNamespace = "http://securetransport.dw/rcservice/soap",
            partName = "body")
    public ResponseType rcTransaction(
            @WebParam(name = "Request",
                    targetNamespace = "http://securetransport.dw/rcservice/soap", partName = "body") RequestType body);
}
