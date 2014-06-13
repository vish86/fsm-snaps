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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

@WebServiceClient(name = "rcService", targetNamespace = "http://securetransport.dw/rcservice/soap",
        wsdlLocation = "META-INF/wsdl/rcservice.wsdl")
public class RcService extends Service {
    private static URL RCSERVICE_WSDL_LOCATION;
    private final static Logger logger = LoggerFactory.getLogger(RcService.class);

    public RcService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RcService() {
        super(RCSERVICE_WSDL_LOCATION, new QName("http://securetransport.dw/rcservice/soap",
                "rcService"));
    }

    public static void setWsdlURL(URL url) {
        RCSERVICE_WSDL_LOCATION = url;
    }

    /**
     * @return returns RcPortType
     */
    @WebEndpoint(name = "rcServicePort")
    public RcPortType getRcServicePort() {
        return super.getPort(
                new QName("http://securetransport.dw/rcservice/soap", "rcServicePort"),
                RcPortType.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.
     *            Supported features not in the <code>features</code> parameter will have their
     *            default values.
     * @return returns RcPortType
     */
    @WebEndpoint(name = "rcServicePort")
    public RcPortType getRcServicePort(WebServiceFeature... features) {
        return super.getPort(
                new QName("http://securetransport.dw/rcservice/soap", "rcServicePort"),
                RcPortType.class, features);
    }
}
