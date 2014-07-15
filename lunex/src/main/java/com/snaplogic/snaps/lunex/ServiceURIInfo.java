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

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.snaplogic.snaps.lunex.Constants.CResource;
import com.snaplogic.snaps.lunex.Constants.DResource;
import com.snaplogic.snaps.lunex.Constants.RResource;
import com.snaplogic.snaps.lunex.Constants.UResource;

import static com.snaplogic.snaps.lunex.Constants.*;

/**
 * This class holds all the resource information and resource specific Lunex API  URI information.
 *
 * @author svatada
 */
public class ServiceURIInfo {
    static final ImmutableMap<String, Integer> REQ_BODY_PARAM_INFO = new ImmutableMap.Builder<String, Integer>()
            .put("AutoRefillUpdatedBy", 0).put("AccessPhone", 0).put("AccessPhoneInt", 0)
            .put("Balance", 1).put(CITY, 0).put("Created", 0).put("CreatedBy", 0)
            .put("Currency", 0).put("Description", 0).put("Distributor", 0).put("Email", 0)
            .put("ExpiredDate", 0).put(FIRST_NAME, 0).put("FirstPurchaseDate", 0)
            .put("FirstUseDate", 0).put(LAST_NAME, 0).put("LastPurchaseDate", 0)
            .put("LastUseDate", 0).put("MinBalance", 1).put("Minutes", 1).put("Num", 1)
            .put("Notes", 0).put(PHONE2, 0).put("Pin", 1).put("Region", 0).put("RedeemDate", 0)
            .put("ReferralName", 0).put("ReferralPhone", 0).put("RefillAmt", 1).put("Reseller", 0)
            .put("RegionInt", 0).put("Sku", 0).put(STATE, 0).put("StatementOption", 0)
            .put("Status", 0).put(STREET, 0).put("Updated", 0).put("UpdatedBy", 0).put(ZIPCODE, 0)
            .put(SUITE, 0).put(COUNTRY, 0).build();
    /*
     * POST/Create operation supported resource list
     */
    static final ImmutableMap<String, ImmutableSet<String>> CR_PARAM_LIST = ImmutableMap.of(
            CResource.NewOrder.toString(), ImmutableSet.of(SELLER, CID, SKU, PHONE, PROMO_PHONE,
                    AMOUNT, QUANTITY, PHONETYPE, LANG, NOTIFY, MERCHANTNAME, MERCHANTID),
            CResource.Account.toString(), ImmutableSet.of(SELLER, PHONETYPE, LANG, NOTIFY,PIN),
            CResource.VoidTx.toString(), ImmutableSet.of(SELLER, TXID, NOTIFY), CResource.PreOrder
                    .toString(), ImmutableSet.of(SELLER, CID, SKU, PHONE, AMOUNT));

    static final ImmutableSet<String> ADDRESS_JSONOBJ = ImmutableSet.of(CITY, COUNTRY, STATE,
            STREET, SUITE, ZIPCODE);
    /*
     * GET/Read Resource operation supported resource list
     */
    static final ImmutableMap<String, ImmutableSet<String>> RR_PARAM_LIST = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put(RResource.GetAccount.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PASSCODE).add(PIN).build())
            .put(RResource.GetOrderStatus.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).build())
            .put(RResource.GetSeller.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).build())
            .put(RResource.GetTime.toString(), new ImmutableSet.Builder<String>().build())
            .put(RResource.GetAutoITU.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(TYPE).add(PHONE).build())
            .put(RResource.ListAccount.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(SKU).add(PHONE).build())
            .put(RResource.ListDidByState.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(STATE2).add(LANG).add(TYPE)
                            .build())
            .put(RResource.ListProduct.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).build())
            .put(RResource.ListRegisterPhone.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PIN).add(PASSCODE).build())
            .put(RResource.ListSpeedDial.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PIN).add(PASSCODE).build())
            .put(RResource.ListTransactions.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(SUB).add(TYPE).add(PIN)
                            .add(FROMDATE).add(TODATE).build())
            .put(RResource.TopUpOrder.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(ORDER).add(SKU).add(PHONE)
                            .add(AMOUNT).build()).build();
    /*
     * PUT/UpdateResource operation supported resource list
     */
    static final ImmutableMap<String, ImmutableSet<String>> UR_PARAM_LIST = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put(UResource.PostOrder.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(CID).add(PROMO_PHONE)
                            .build())
            .put(UResource.UpdateAccount.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PIN).build())
            .put(UResource.UpdateRegisterPhone.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PIN).add(NOTIFY).build())
            .put(UResource.UpdateSpeedDial.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PIN).build()).build();
    /*
     * DELETE/Delete Resource operation supported resource list
     */
    static final ImmutableMap<String, ImmutableSet<String>> DR_PARAM_LIST = new ImmutableMap.Builder<String, ImmutableSet<String>>()
            .put(DResource.DeleteRegisterPhone.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PIN).add(REGISTERPHONE)
                            .build())
            .put(DResource.DeleteSpeedDial.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(PIN).add(SPEEDDIAL).build())
            .put(DResource.RemoveCustomer.toString(),
                    new ImmutableSet.Builder<String>().add(SELLER).add(SKU).add(PHONE).build())
            .build();
    /*
     * POST/Create Resource Operation supported URL list
     */
    static final ImmutableMap<String, String> CR_URI_LIST = new ImmutableMap.Builder<String, String>()
            .put(CResource.NewOrder.toString(),
                    "/pos/sellers/{SELLER}/orders/{CID}?sku={SKU}&phone={PHONE}"
                            + "&promo_phone={PROMOPHONE}&amount={AMOUNT}&quantity={QUANTITY}"
                            + "&phone_type={PHONETYPE}&lang={LANG}&notify={NOTIFY}"
                            + "&merchantname={MERCHANTNAME}&merchantid={MERCHANTID}")
            .put(CResource.Account.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}?phone_type={PHONETYPE}&lang="
                            + "{LANG}&notify={NOTIFY}")
            .put(CResource.VoidTx.toString(), "/pos/sellers/{SELLER}/trans/{TXID}?notify={NOTIFY}")
            .put(CResource.PreOrder.toString(),
                    "/pos/sellers/{SELLER}/preorders/{CID}?sku={SKU}&phone="
                            + "{PHONE}&amount={AMOUNT}").build();
    /*
     * GET/Read Resource operation supported resource list
     */
    static final ImmutableMap<String, String> RR_URI_LIST = new ImmutableMap.Builder<String, String>()
            .put(RResource.GetAccount.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}?passcode={PASSCODE}")
            .put(RResource.GetTime.toString(), "/pos/time")
            .put(RResource.GetOrderStatus.toString(), "/pos/sellers/{SELLER}/orderstatus/{CID}")
            .put(RResource.GetSeller.toString(), "/pos/sellers/{SELLER}")
            .put(RResource.GetAutoITU.toString(),
                    "/pos/sellers/{SELLER}/skus?type={TYPE}&phone={PHONE}")
            .put(RResource.ListTransactions.toString(),
                    "/pos/sellers/{SELLER}/trans/?type={TYPE}&sub={SUB}&"
                            + "from_date={FROMDATE}&to_date={TODATE}&pin={PIN}")
            .put(RResource.ListAccount.toString(),
                    "/pos/sellers/{SELLER}/pins/?sku={SKU}&phone={PHONE}")
            .put(RResource.ListDidByState.toString(),
                    "/pos/sellers/{SELLER}/did/?state={STATE}&type={TYPE}" + "&lang={LANG}")
            .put(RResource.ListProduct.toString(), "/pos/sellers/{SELLER}/products/")
            .put(RResource.ListRegisterPhone.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}/registerphones/?" + "passcode={PASSCODE}")
            .put(RResource.ListSpeedDial.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}/speeddials/?passcode" + "={PASSCODE}")
            .put(RResource.TopUpOrder.toString(),
                    "/pos/sellers/{seller}/orders/{ORDER}?sku={SKU}&phone"
                            + "={PHONE}&amount={AMOUNT}").build();
    /*
     * PUT/Update Resource operation supported resource list
     */
    static final ImmutableMap<String, String> UR_URI_LIST = new ImmutableMap.Builder<String, String>()
            .put(UResource.PostOrder.toString(),
                    "/pos/sellers/{SELLER}/orders/{CID}?promo_phone=" + "{PROMOPHONE}")
            .put(UResource.UpdateAccount.toString(), "/pos/sellers/{SELLER}/pins/{PIN}")
            .put(UResource.UpdateRegisterPhone.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}/registerphones/?notify={NOTIFY}")
            .put(UResource.UpdateSpeedDial.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}/speeddials/").build();
    /*
     * DELETE/Delete Resource operation supported resource list
     */
    static final ImmutableMap<String, String> DR_URI_LIST = new ImmutableMap.Builder<String, String>()
            .put(DResource.DeleteRegisterPhone.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}/registerphones/{REGISTERPHONE}")
            .put(DResource.DeleteSpeedDial.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}/speeddials/{SPEEDDIAL}")
            .put(DResource.RemoveCustomer.toString(),
                    "/SetupService/sellers/{SELLER}/sku/{SKU}/phone/{PHONE}").build();
}
