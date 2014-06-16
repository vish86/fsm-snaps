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

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.transform.Source;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Dispatch;
import javax.xml.ws.Service;
import javax.xml.ws.soap.SOAPBinding;

import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP;
import static com.snaplogic.snaps.firstdata.dw.rcservice.RCServiceConstants.RC_SERVICE_PORT;


/**
 * RcServicePortProxy
 * 
 * @author svatada
 */
public class RcServicePortProxy {
    protected Descriptor descriptor;

    public class Descriptor {
        private com.snaplogic.snaps.firstdata.dw.rcservice.RcService service = null;
        private com.snaplogic.snaps.firstdata.dw.rcservice.RcPortType proxy = null;
        private Dispatch<Source> dispatch = null;

        public Descriptor() {
            init();
        }

        public Descriptor(URL wsdlLocation, QName serviceName) {
            service = new com.snaplogic.snaps.firstdata.dw.rcservice.RcService(wsdlLocation,
                    serviceName);
            initCommon();
        }

        public void init() {
            service = null;
            proxy = null;
            dispatch = null;
            service = new com.snaplogic.snaps.firstdata.dw.rcservice.RcService();
            initCommon();
        }

        private void initCommon() {
            proxy = service.getRcServicePort();
        }

        public com.snaplogic.snaps.firstdata.dw.rcservice.RcPortType getProxy() {
            return proxy;
        }

        public Dispatch<Source> getDispatch() {
            if (dispatch == null) {
                QName portQName = new QName(HTTP_SECURETRANSPORT_DW_RCSERVICE_SOAP, RC_SERVICE_PORT);
                dispatch = service.createDispatch(portQName, Source.class, Service.Mode.MESSAGE);

                String proxyEndpointUrl = getEndpoint();
                BindingProvider bp = (BindingProvider) dispatch;
                String dispatchEndpointUrl = (String) bp.getRequestContext().get(
                        BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
                if (!dispatchEndpointUrl.equals(proxyEndpointUrl))
                    bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
                            proxyEndpointUrl);
            }
            return dispatch;
        }

        public String getEndpoint() {
            BindingProvider bp = (BindingProvider) proxy;
            return (String) bp.getRequestContext().get(BindingProvider.ENDPOINT_ADDRESS_PROPERTY);
        }

        public void setEndpoint(String endpointUrl) {
            BindingProvider bp = (BindingProvider) proxy;
            bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);

            if (dispatch != null) {
                bp = (BindingProvider) dispatch;
                bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointUrl);
            }
        }

        public void setMTOMEnabled(boolean enable) {
            SOAPBinding binding = (SOAPBinding) ((BindingProvider) proxy).getBinding();
            binding.setMTOMEnabled(enable);
        }
    }

    public RcServicePortProxy() {
        descriptor = new Descriptor();
        descriptor.setMTOMEnabled(true);
    }

    public RcServicePortProxy(URL wsdlLocation, QName serviceName) {
        descriptor = new Descriptor(wsdlLocation, serviceName);
        descriptor.setMTOMEnabled(true);
    }

    public Descriptor _getDescriptor() {
        return descriptor;
    }

    public ResponseType rcTransaction(RequestType body) {
        return _getDescriptor().getProxy().rcTransaction(body);
    }

}