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
package com.snaplogic.snaps.firstdata;

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
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;

/**
 * JUnit tests for FirstData Create Snap.
 *
 * @author svatada
 */
@SuppressWarnings("unchecked")
@RunWith(SnapTestRunner.class)
public class CreateTest extends ApiTest {
    @TestFixture(snap = Create.class, outputs = "out1", errors = "err1",
            input = "data/create/input_invalid_DebitReqData.json",
            properties = "data/create/property_request.json")
    public void testValidateAccount(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(invalidAccount).add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        assertEquals(0, outputRecorder.getDocumentCount());
        OutputRecorder errRecorder = testResult.getErrorViewByName("err1");
        if (errRecorder.getDocumentCount() < 1) {
            assertTrue(false);
        }
    }

    @TestFixture(snap = Create.class, outputs = "out1", errors = "err1",
            input = "data/create/input_valid_map_debitReq.json",
            properties = "data/create/property_request.json")
    public void testCreateREquestValidData(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(account).add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is Create Snap
        Snap snap = testResult.getSnap();
        assertEquals(Create.class, snap.getClass());
        // only 1 document to the output view
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        OutputRecorder errRecorder = testResult.getErrorViewByName("err1");
        assertEquals(1, outputRecorder.getDocumentCount() + errRecorder.getDocumentCount());
        if (outputRecorder.getDocumentCount() > 0) {
            Document document = outputRecorder.getRecordedDocuments().get(0);
            Map<String, Object> data = document.get(Map.class);
            String statusCode = (String) data.get("StatusCode");
            assertEquals("OK", statusCode);
        }
    }

    @TestFixture(snap = Create.class, outputs = "out1", errors = "err1",
            input = "data/create/input_valid_list_debitReq.json",
            properties = "data/create/property_request.json")
    public void testCreateRequestValidDataWithJsonArray(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(account).add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is Create Snap
        Snap snap = testResult.getSnap();
        assertEquals(Create.class, snap.getClass());
        // only 1 document to the output view
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        OutputRecorder errRecorder = testResult.getErrorViewByName("err1");
        assertEquals(1, outputRecorder.getDocumentCount() + errRecorder.getDocumentCount());
        if (outputRecorder.getDocumentCount() > 0) {
            Document document = outputRecorder.getRecordedDocuments().get(0);
            Map<String, Object> data = document.get(Map.class);
            String statusCode = (String) data.get("StatusCode");
            assertEquals("OK", statusCode);
        }
    }

    @TestFixture(snap = Create.class, outputs = "out1", errors = "err1",
            input = "data/create/input_invalid_DebitReqData.json",
            properties = "data/create/property_request.json")
    public void testCreateRequestInvalidData(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(account).add();
        TestResult testResult = setup.test();
        assertNull(testResult.getException());
        // check if it is Create Snap
        Snap snap = testResult.getSnap();
        assertEquals(Create.class, snap.getClass());
        // only 1 document to the output view
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        assertEquals(0, outputRecorder.getDocumentCount());
        OutputRecorder errRecorder = testResult.getErrorViewByName("err1");
        if (errRecorder.getDocumentCount() < 1) {
            assertTrue(false);
        }
    }
}