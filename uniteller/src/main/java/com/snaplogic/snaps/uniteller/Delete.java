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
import com.snaplogic.snaps.uniteller.Constants.SnapsModel;

import static com.snaplogic.snaps.uniteller.Messages.DELETE_LABEL;
import static com.snaplogic.snaps.uniteller.Messages.SNAP_DESC;

/**
 * Performs delete operation in UniTeller making use of UniTeller client.
 *
 * @author svatada
 **/
@General(title = DELETE_LABEL, purpose = SNAP_DESC)
public class Delete extends BaseService {
    @Override
    protected SnapsModel getSnapType() {
        return SnapsModel.Delete;
    }
}
