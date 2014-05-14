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
import com.snaplogic.snaps.lunex.Constants.CResource;
import com.snaplogic.snaps.lunex.Constants.DResource;
import com.snaplogic.snaps.lunex.Constants.RResource;
import com.snaplogic.snaps.lunex.Constants.UResource;

/**
 * This class holds all the resource specific Lunex URI information.
 *
 * @author svatada
 */

public class ServiceURIInfo {
    /*
     * POST/Create Resource Operation supported URL list
     */
    final static ImmutableMap<String, String> CR_URI_LIST = new ImmutableMap.Builder<String, String>()
            .put(CResource.NewOrder.toString(),
                    "/pos/sellers/{SELLER}/orders/{CID}?sku={SKU}&phone={PHONE}"
                            + "&promo_phone={PROMOPHONE}&amount={AMOUNT}&quantity={QUANTITY}"
                            + "&phone_type={PHONETYPE}&lang={LANG}&notify={NOTIFY}"
                            + "&merchantname={MERCHANTNAME}&merchantid={MERCHANTID}")
            .put(CResource.Account.toString(),
                    "/pos/sellers/{SELLER}/pins/?phone_type={PHONETYPE}&lang="
                            + "{LANG}&notify={NOTIFY}")
            .put(CResource.VoidTx.toString(), "/pos/sellers/{SELLER}/trans/{TXID}?notify={NOTIFY}")
            .put(CResource.PreOrder.toString(),
                    "/pos/sellers/{SELLER}/preorders/{CID}?sku={SKU}&phone="
                            + "{PHONE}&amount={AMOUNT}").build();
    /*
     * GET/Read Resource operation supported resource list
     */
    final static ImmutableMap<String, String> RR_URI_LIST = new ImmutableMap.Builder<String, String>()
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
    final static ImmutableMap<String, String> UR_URI_LIST = new ImmutableMap.Builder<String, String>()
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
    final static ImmutableMap<String, String> DR_URI_LIST = new ImmutableMap.Builder<String, String>()
            .put(DResource.DeleteRegisterPhone.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}/registerphones/{REGISTERPHONE}")
            .put(DResource.DeleteSpeedDial.toString(),
                    "/pos/sellers/{SELLER}/pins/{PIN}/speeddials/{SPEEDDIAL}")
            .put(DResource.RemoveCustomer.toString(),
                    "/SetupService/sellers/{SELLER}/sku/{SKU}/phone/{PHONE}").build();

}