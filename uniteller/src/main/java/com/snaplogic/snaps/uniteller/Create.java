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
import static com.snaplogic.snaps.uniteller.Constants.*;
import static com.snaplogic.snaps.uniteller.Messages.*;

/**
 * Performs create operation in UniTeller making use of UniTeller client.
 * 
 * @author svatada
 **/
@General(title = CREATE_LABEL, purpose = CREATE_DESC)
public class Create extends BaseService {
	public Create() {
		super(SnapType.Create);
	}
}
