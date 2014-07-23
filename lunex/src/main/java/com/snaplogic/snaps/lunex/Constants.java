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
package com.snaplogic.snaps.lunex;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

/**
 * This class holds all the static final variables and enum types.
 *
 * @author svatada
 */
public class Constants {
    static final String DEFAULT_CONTENT_LENGTH = "0";
    static final String LUNEX_HTTP_INFO = "[{\"ResourceType\": \"%s\"}, {\"Lunex URL\":\"%s\"}, {\"Header\": \"%s\"}]";
    static final String LUNEX_HTTP_REQ_INFO = "Lunex Request Body: %s";
    static final String HTTP_STATUS = "Lunex HTTPStatus: %s ";
    static final String REGEX = "[^\\p{L}\\p{Nd}]";
    static final String TIME_STAMP_TAG = "TimeStamp";
    static final String DELETE_RESPONSE_FLAG = "DeletionStatus";
    static final String REGISTERPHONE = "REGISTERPHONE";
    static final String SPEEDDIAL = "SPEEDDIAL";
    static final String STATE2 = "STATE";
    static final String TODATE = "TODATE";
    static final String FROMDATE = "FROMDATE";
    static final String SUB = "SUB";
    static final String STATUS_CODE_TAG = "statusCode";
    static final String RESOLUTION_TAG = "Resolution";
    static final String REASON_TAG = "Reason";
    static final String MESSAGE_TAG = "Message";
    static final String COMMA = ",";
    static final String COLON = ":";
    static final String QUOTE = "\"";
    static final String ADDRESS = "Address";
    static final String ERROR_TAG = "Error";
    static final String APPLICATION_JSON = "application/json";
    static final String CONTENT_LENGTH = "Content-length";
    static final String ACCEPT = "Accept";
    static final String CONTENT_TYPE = "Content-type";
    static final String BASIC = "Basic ";
    static final String AUTHORIZATION = "Authorization";
    static final TypeReference<List<Map<String, Object>>> MAP_TYPE_REFERENCE = new TypeReference<List<Map<String, Object>>>() {
    };
    static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    static final String ZIPCODE = "Zipcode";
    static final String SUITE = "Suite";
    static final String HTTP = "http";
    static final String DOUBLE_SLASH = "//";
    static final String ESCAPECHAR = "\\";
    static final String CLOSETAG = "}";
    static final String OPENTAG = "{";
    static final String OPENBRACKET = "[";
    static final String CLOSEBRACKET = "]";
    static final String STREET = "Street";
    static final String STATE = "State";
    static final String COUNTRY = "Country";
    static final String CITY = "City";
    static final String PHONE2 = "Phone";
    static final String LAST_NAME = "LastName";
    static final String FIRST_NAME = "FirstName";
    static final String TYPE = "TYPE";
    static final String ORDER = "ORDER";
    static final String TXID = "TXID";
    static final String MERCHANTID = "MERCHANTID";
    static final String MERCHANTNAME = "MERCHANTNAME";
    static final String NOTIFY = "NOTIFY";
    static final String LANG = "LANG";
    static final String PHONETYPE = "PHONETYPE";
    static final String QUANTITY = "QUANTITY";
    static final String AMOUNT = "AMOUNT";
    static final String PROMO_PHONE = "PROMOPHONE";
    static final String PHONE = "PHONE";
    static final String CID = "CID";
    static final String SELLER = "SELLER";
    static final String SKU = "SKU";
    static final String PIN = "PIN";
    static final String PASSCODE = "PASSCODE";

    static enum HttpMethodNames {
        POST("POST"), PUT("PUT"), GET("GET"), DELETE("DELETE");
        private final String methodName;

        private HttpMethodNames(String resource) {
            this.methodName = resource;
        }

        @Override
        public String toString() {
            return methodName;
        }
    }

    static enum LunexSnaps {
        Create("Create"), Update("Update"), Read("Read"), Delete("Delete");
        private final String resource;

        private LunexSnaps(String resource) {
            this.resource = resource;
        }

        @Override
        public String toString() {
            return resource;
        }
    }

    static enum CResource {
        Account("Account"), NewOrder("NewOrder"), PreOrder("PreOrder"), VoidTx("VoidTx");
        private final String cresource;

        private CResource(String resource) {
            cresource = resource;
        }

        @Override
        public String toString() {
            return cresource;
        }
    }

    static enum DResource {
        DeleteSpeedDial("DeleteSpeedDial"), DeleteRegisterPhone("DeleteRegisterPhone"), RemoveCustomer(
                "RemoveCustomer");
        private final String dresource;

        private DResource(String resource) {
            dresource = resource;
        }

        @Override
        public String toString() {
            return dresource;
        }
    }

    static enum UResource {
        UpdateAccount("UpdateAccount"), UpdateSpeedDial("UpdateSpeedDial"), UpdateRegisterPhone(
                "UpdateRegisterPhone"), PostOrder("PostOrder");
        private final String uresource;

        private UResource(String resource) {
            uresource = resource;
        }

        @Override
        public String toString() {
            return uresource;
        }
    }

    static enum RResource {
        GetAccount("GetAccount"), GetTime("GetTime"), ListAccount("ListAccount"), ListSpeedDial(
                "ListSpeedDial"), ListRegisterPhone("ListRegisterPhone"), GetOrderStatus(
                "GetOrderStatus"), ListProduct("ListProduct"), GetSeller("GetSeller"), ListTransactions(
                "ListTransactions"), ListDidByState("ListDidByState"), GetAutoITU("GetAutoITU"), TopUpOrder(
                "TopupOrder");
        private final String rresource;

        private RResource(String resource) {
            rresource = resource;
        }

        @Override
        public String toString() {
            return rresource;
        }
    }
}