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
package com.snaplogic.snaps.uniteller;

import com.snaplogic.api.Snap;
import com.snaplogic.snap.api.Document;
import com.snaplogic.snap.test.harness.OutputRecorder;
import com.snaplogic.snap.test.harness.SnapTestRunner;
import com.snaplogic.snap.test.harness.TestFixture;
import com.snaplogic.snap.test.harness.TestResult;
import com.snaplogic.snap.test.harness.TestSetup;

import org.junit.runner.RunWith;

import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotSame;
import static junit.framework.Assert.assertNull;

/**
 * Unit tests for UniTeller Update snap.
 *
 * @author svatada
 */
@SuppressWarnings("unchecked")
@RunWith(SnapTestRunner.class)
public class UpdateTest extends ApiTest {
    @TestFixture(snap = Update.class, outputs = "out1", errors = "err1",
            input = "data/update/input_valid_InfoModifyData.json",
            properties = "data/update/property_InfoModify.json")
    public void testUpdateValidData(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(account).add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is update Snap
        Snap snap = testResult.getSnap();
        assertEquals(Update.class, snap.getClass());
        // only 1 document to the output view
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        OutputRecorder errRecorder = testResult.getErrorViewByName("err1");
        assertEquals(1, outputRecorder.getDocumentCount() + errRecorder.getDocumentCount());
        if (outputRecorder.getDocumentCount() > 0) {
            Document document = outputRecorder.getRecordedDocuments().get(0);
            Map<String, Object> data = document.get(Map.class);
            int statusCode = (int) data.get("ResponseCode");
            assertNotSame("00000000", statusCode);
        }
    }

    @TestFixture(snap = Update.class, input = "data/update/input_invalid_InfoModifyData.json",
            outputs = "out1", errors = "err1", properties = "data/update/property_InfoModify.json")
    public void testUpdateInvalidData(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(account).add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is update Snap
        Snap snap = testResult.getSnap();
        assertEquals(Update.class, snap.getClass());
        // only one document to the output view
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        assertEquals(0, outputRecorder.getDocumentCount());
        outputRecorder = testResult.getErrorViewByName("err1");
        assertEquals(1, outputRecorder.getDocumentCount());
    }
}
