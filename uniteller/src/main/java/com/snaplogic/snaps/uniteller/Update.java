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
package com.snaplogic.snaps.uniteller;

import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snaps.uniteller.Constants.SnapType;

import static com.snaplogic.snaps.uniteller.Messages.UPDATE_DESC;
import static com.snaplogic.snaps.uniteller.Messages.UPDATE_LABEL;

/**
 * Performs update operation in UniTeller making use of UniTeller client.
 * 
 * @author svatada
 **/
@General(title = UPDATE_LABEL, purpose = UPDATE_DESC)
public class Update extends BaseService {
    public Update() {
        super(SnapType.Update);
    }
}