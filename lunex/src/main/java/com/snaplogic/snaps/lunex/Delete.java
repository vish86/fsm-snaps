/*
 * SnapLogic - Data Integration
 *
 * Copyright (C) 2014, SnapLogic, Inc.  All rights reserved.
 *
 * This program is licensed under the terms of
 * the SnapLogic Commercial Subscription agreement.
 *
 * "SnapLogic" is a trademark of SnapLogic, Inc.
 */
package com.snaplogic.snaps.lunex;

import static com.snaplogic.snaps.lunex.constants.Messages.LUNEX_DELETE_DESC;
import static com.snaplogic.snaps.lunex.constants.Messages.LUNEX_DELETE_LABEL;

import com.snaplogic.snap.api.capabilities.General;
import com.snaplogic.snaps.lunex.constants.Constants.LunexSnaps;

/**
 * Performs delete operation in Lunex making use of Lunex REST API calls.
 *
 * @author svatada
 **/
@General(title = LUNEX_DELETE_LABEL, purpose = LUNEX_DELETE_DESC)
public class Delete extends BaseService {
	public Delete() {
		super(LunexSnaps.Delete);
	}
}
