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
package com.snaplogic.snaps.lunex.constants;

import java.util.Map;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

/**
 * This class holds all the constants and enum types
 * 
 * @author svatada
 */
public class Constants {
    public static final String RESOLUTION_TAG = "Resolution";
    public static final String REASON_TAG = "Reason";
    public static final String MESSAGE_TAG = "Message";
    public static final String COMMA = ",";
    public static final String COLON = ":";
    public static final String QUOTE = "\"";
    public static final String ADDRESS = "Address";
    public static final String ERROR_TAG = "Error";
    public static final String APPLICATION_JSON = "application/json";
    public static final String CONTENT_LENGTH = "Content-length";
    public static final String ACCEPT = "Accept";
    public static final String CONTENT_TYPE = "Content-type";
    public static final String BASIC = "Basic ";
    public static final String AUTHORIZATION = "Authorization";
    public static final TypeReference<Map<String, Object>> MAP_TYPE_REFERENCE = new TypeReference<Map<String, Object>>() {
    };
    public static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    public static final String ZIPCODE = "Zipcode";
    public static final String SUITE = "Suite";
    public static final String HTTP = "http";
    public static final String DOUBLE_SLASH = "//";
    public static final String ESCAPECHAR = "\\";
    public static final String CLOSETAG = "}";
    public static final String OPENTAG = "{";
    private static final String STREET = "Street";
    private static final String STATE = "State";
    private static final String COUNTRY = "Country";
    private static final String CITY = "City";
    private static final String PHONE2 = "Phone";
    private static final String LAST_NAME = "LastName";
    private static final String FIRST_NAME = "FirstName";
    private static final String TYPE = "TYPE";
    private static final String ORDER = "ORDER";
    private static final String TXID = "TXID";
    private static final String MERCHANTID = "MERCHANTID";
    private static final String MERCHANTNAME = "MERCHANTNAME";
    private static final String NOTIFY = "NOTIFY";
    private static final String LANG = "LANG";
    private static final String PHONETYPE = "PHONETYPE";
    private static final String QUANTITY = "QUANTITY";
    private static final String AMOUNT = "AMOUNT";
    private static final String PROMO_PHONE = "PROMOPHONE";
    private static final String PHONE = "PHONE";
    private static final String CID = "CID";
    private static final String SELLER = "SELLER";
    private static final String SKU = "SKU";

    public static enum HttpMethodNames {
        POST("POST"), PUT("PUT"), GET("GET"), DELETE("DELETE");
        private final String methodName;

        private HttpMethodNames(String resource) {
            this.methodName = resource;
        }

        public String toString() {
            return methodName;
        }
    }

    public static enum LunexSnaps {
        Create("Create"), Update("Update"), Read("Read"), Delete("Delete");
        private final String resource;

        private LunexSnaps(String resource) {
            this.resource = resource;
        }

        public String toString() {
            return resource;
        }
    }

    public static enum CResource {
        Account("Account"), NewOrder("NewOrder"), PreOrder("PreOrder"), VoidTx("VoidTx");
        private final String cresource;

        private CResource(String resource) {
            cresource = resource;
        }

        public String toString() {
            return cresource;
        }
    }

    public static enum DResource {
        DeleteSpeedDial("DeleteSpeedDial"), DeleteRegisterPhone("DeleteRegisterPhone"), RemoveCustomer(
                "RemoveCustomer");
        private final String dresource;

        private DResource(String resource) {
            dresource = resource;
        }

        public String toString() {
            return dresource;
        }
    }

    public static enum UResource {
        UpdateAccount("UpdateAccount"), UpdateSpeedDial("UpdateSpeedDial"), UpdateRegisterPhone(
                "UpdateRegisterPhone"), PostOrder("PostOrder");
        private final String uresource;

        private UResource(String resource) {
            uresource = resource;
        }

        public String toString() {
            return uresource;
        }
    }

    public static enum RResource {
        GetAccount("GetAccount"), ListAccount("ListAccount"), ListSpeedDial("ListSpeedDial"), ListRegisterPhone(
                "ListRegisterPhone"), GetOrderStatus("GetOrderStatus"), ListProduct("ListProduct"), GetSeller(
                "GetSeller"), ListTransactions("ListTransactions"), ListDidByState("ListDidByState"), GetAutoITU(
                "GetAutoITU"), TopUpOrder("TopupOrder"), GetTime("GetTime");
        private final String rresource;

        private RResource(String resource) {
            rresource = resource;
        }

        public String toString() {
            return rresource;
        }
    }

    public static final ImmutableMap<String, Integer> REQ_BODY_PARAM_INFO = new ImmutableMap.Builder<String, Integer>()
            .put("AutoRefillUpdatedBy", 0).put("AccessPhone", 0).put("AccessPhoneInt", 0)
            .put("Balance", 0).put(CITY, 0).put("Created", 0).put("CreatedBy", 0)
            .put("Currency", 0).put("Description", 0).put("Distributor", 0).put("Email", 0)
            .put("ExpiredDate", 0).put(FIRST_NAME, 0).put("FirstPurchaseDate", 0)
            .put("FirstUseDate", 0).put(LAST_NAME, 0).put("LastPurchaseDate", 0)
            .put("LastUseDate", 0).put("MinBalance", 0).put("Minutes", 1).put("Num", 1)
            .put("Notes", 0).put(PHONE2, 1).put("Pin", 1).put("Region", 0).put("RedeemDate", 0)
            .put("ReferralName", 0).put("ReferralPhone", 1).put("RefillAmt", 0).put("Reseller", 0)
            .put("RegionInt", 1).put("Sku", 1).put(STATE, 0).put("StatementOption", 0)
            .put("Status", 0).put(STREET, 0).put("Updated", 0).put("UpdatedBy", 0).put(ZIPCODE, 0)
            .build();

    /*
     * POST/Create operation supported resource list
     */
    public static final ImmutableMap<String, ImmutableSet<String>> CR_PARAM_LIST = ImmutableMap.of(
            CResource.NewOrder.toString(), ImmutableSet.of(SELLER, CID, SKU, PHONE, PROMO_PHONE,
                    AMOUNT, QUANTITY, PHONETYPE, LANG, NOTIFY, MERCHANTNAME, MERCHANTID),
            CResource.Account.toString(), ImmutableSet.of(SELLER, PHONETYPE, LANG, NOTIFY),
            CResource.VoidTx.toString(), ImmutableSet.of(SELLER, TXID, NOTIFY), CResource.PreOrder
                    .toString(), ImmutableSet.of(SELLER, CID, SKU, PHONE, AMOUNT));

    public static final ImmutableSet ADDRESS_JSONOBJ = ImmutableSet.of(CITY, COUNTRY, STATE,
            STREET, SUITE, ZIPCODE);
    /*
     * GET/Read Resource operation supported resource list
     */
    public static final ImmutableMap<String, ImmutableSet<String>> RR_PARAM_LIST = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put(RResource.GetTime.toString(), new ImmutableSet.Builder<String>().build())
            .put(RResource.GetAccount.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(PROMO_PHONE).add(AMOUNT).add(QUANTITY).add(PHONETYPE).add(LANG)
                            .add(NOTIFY).add(MERCHANTNAME).add(MERCHANTID).build())
            .put(RResource.GetOrderStatus.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PHONETYPE).add(LANG)
                            .add(NOTIFY).build())
            .put(RResource.GetSeller.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(TXID).add(NOTIFY).build())
            .put(RResource.ListAccount.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(AMOUNT).build())
            .put(RResource.ListDidByState.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(AMOUNT).build())
            .put(RResource.ListProduct.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(AMOUNT).build())
            .put(RResource.ListRegisterPhone.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(AMOUNT).build())
            .put(RResource.ListSpeedDial.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(AMOUNT).build())
            .put(RResource.ListTransactions.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(AMOUNT).build())
            .put(RResource.GetAutoITU.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(TYPE).add(PHONE).build())
            .put(RResource.TopUpOrder.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(ORDER).add(SKU).add(PHONE)
                            .add(AMOUNT).build()).build();

    /*
     * PUT/UpdateResource operation supported resource list
     */
    public static final ImmutableMap<String, ImmutableSet<String>> UR_PARAM_LIST = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put(UResource.PostOrder.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(PROMO_PHONE).add(AMOUNT).add(QUANTITY).add(PHONETYPE).add(LANG)
                            .add(NOTIFY).add(MERCHANTNAME).add(MERCHANTID).build())
            .put(UResource.UpdateAccount.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PHONETYPE).add(LANG)
                            .add(NOTIFY).build())
            .put(UResource.UpdateRegisterPhone.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(TXID).add(NOTIFY).build())
            .put(UResource.UpdateSpeedDial.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(AMOUNT).build()).build();
    /*
     * DELETE/Delete Resource operation supported resource list
     */
    public static final ImmutableMap<String, ImmutableSet<String>> DR_PARAM_LIST = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put(DResource.DeleteRegisterPhone.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(SKU).add(PHONE)
                            .add(PROMO_PHONE).add(AMOUNT).add(QUANTITY).add(PHONETYPE).add(LANG)
                            .add(NOTIFY).add(MERCHANTNAME).add(MERCHANTID).build())
            .put(DResource.DeleteSpeedDial.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PHONETYPE).add(LANG)
                            .add(NOTIFY).build())
            .put(DResource.RemoveCustomer.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(TXID).add(NOTIFY).build())
            .build();
}