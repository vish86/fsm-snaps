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

import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snaps.lunex.Constants.HttpMethodNames;
import com.snaplogic.snaps.lunex.Constants.LunexSnaps;

import static com.snaplogic.snaps.lunex.Messages.LUNEX_CREATE_DESC;
import static com.snaplogic.snaps.lunex.Messages.LUNEX_CREATE_LABEL;

/**
 * Performs create operation in Lunex making use of Lunex REST API calls.
 *
 * @author svatada
 **/
@General(title = LUNEX_CREATE_LABEL, purpose = LUNEX_CREATE_DESC)
public class Create extends BaseService {
    public Create() {
        super(LunexSnaps.Create, HttpMethodNames.POST);
    }
}
