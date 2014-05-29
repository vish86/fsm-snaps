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
 * JUnit tests for UniTeller Read snap.
 *
 * @author svatada
 */
@SuppressWarnings("unchecked")
@RunWith(SnapTestRunner.class)
public class ReadTest extends ApiTest {
    @TestFixture(snap = Read.class, outputs = "out1", errors = "err1",
            input = "data/read/input_valid_GetTxDetailsData.json",
            properties = "data/read/property_GetTxDetails.json")
    public void testGetTxDetailsValidData(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(account).add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is read Snap
        Snap snap = testResult.getSnap();
        assertEquals(Read.class, snap.getClass());
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

    @TestFixture(snap = Read.class, input = "data/read/input_invalid_GetTxDetailsData.json",
            outputs = "out1", errors = "err1", properties = "data/read/property_GetTxDetails.json")
    public void testGetTxDetailsInvalidData(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(account).add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is read Snap
        Snap snap = testResult.getSnap();
        assertEquals(Read.class, snap.getClass());
        // only one document to the output view
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        assertEquals(0, outputRecorder.getDocumentCount());
        outputRecorder = testResult.getErrorViewByName("err1");
        assertEquals(1, outputRecorder.getDocumentCount());
    }

    @TestFixture(snap = Read.class, outputs = "out1", errors = "err1",
            input = "data/read/input_valid_GetNotificationsData.json",
            properties = "data/read/property_GetNotifications.json")
    public void testGetNotificationValidData(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(account).add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is read Snap
        Snap snap = testResult.getSnap();
        assertEquals(Read.class, snap.getClass());
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

    @TestFixture(snap = Read.class, input = "data/read/input_invalid_GetNotificationsData.json",
            outputs = "out1", errors = "err1",
            properties = "data/read/property_GetNotifications.json")
    public void testGetNotificationsInvalidData(TestSetup setup) throws Exception {
        setup.inject().fieldName("account").dependency(account).add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is read Snap
        Snap snap = testResult.getSnap();
        assertEquals(Read.class, snap.getClass());
        // only one document to the output view
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        assertEquals(0, outputRecorder.getDocumentCount());
        outputRecorder = testResult.getErrorViewByName("err1");
        assertEquals(1, outputRecorder.getDocumentCount());
    }
}
