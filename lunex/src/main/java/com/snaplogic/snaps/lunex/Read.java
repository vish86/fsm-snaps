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

import static com.snaplogic.snaps.lunex.Messages.LUNEX_READ_DESC;
import static com.snaplogic.snaps.lunex.Messages.LUNEX_READ_LABEL;

/**
 * Performs read operation in Lunex making use of Lunex REST API calls.
 *
 * @author svatada
 **/
@General(title = LUNEX_READ_LABEL, purpose = LUNEX_READ_DESC)
public class Read extends BaseService {
    public Read() {
        super(LunexSnaps.Read, HttpMethodNames.GET);
    }
}
