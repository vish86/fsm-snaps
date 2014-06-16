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
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceFeature;

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.RC_SERVICE;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.RC_SERVICE_PORT;


/**
 * RcService
 * 
 * @author svatada
 */
@WebServiceClient(name = RC_SERVICE, targetNamespace = HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP)
public class RcService extends Service {
    private static URL RCSERVICE_WSDL_LOCATION;

    public RcService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public RcService() {
        super(RCSERVICE_WSDL_LOCATION,
                new QName(HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP, RC_SERVICE));
    }

    public static void setWsdlURL(URL url) {
        RCSERVICE_WSDL_LOCATION = url;
    }

    /**
     * @return RcPortType
     */
    @WebEndpoint(name = RC_SERVICE_PORT)
    public RcPortType getRcServicePort() {
        return super.getPort(new QName(HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP, RC_SERVICE_PORT),
                RcPortType.class);
    }

    /**
     * @param features A list of {@link javax.xml.ws.WebServiceFeature}
     * @return RcPortType
     */
    @WebEndpoint(name = RC_SERVICE_PORT)
    public RcPortType getRcServicePort(WebServiceFeature... features) {
        return super.getPort(new QName(HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP, RC_SERVICE_PORT),
                RcPortType.class, features);
    }
}