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

/**
 * JUnit tests for Lunex Delete Snap.
 *
 * @author svatada
 */
@SuppressWarnings("unchecked")
@RunWith(SnapTestRunner.class)
public class DeleteTest extends LunexApiTest {
    @TestFixture(
        snap = Delete.class, outputs = "out1", errors = "err1",
        input = "data/delete/input_valid_data.json",
        properties = "data/delete/property_data_1.json")
    public void testDeleteValidData(TestSetup setup) throws Exception {
        setup.inject()
            .fieldName("account")
            .dependency(account)
            .add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is delete Snap
        Snap snap = testResult.getSnap();
        assertEquals(Delete.class, snap.getClass());
        // only 1 document to the output view
        // only 1 document to the output view
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        OutputRecorder errRecorder = testResult.getErrorViewByName("err1");
        assertEquals(1, outputRecorder.getDocumentCount() + errRecorder.getDocumentCount());
        // status code = 200 meaning "OK"
        if (outputRecorder.getDocumentCount() > 0) {
            Document document = outputRecorder.getRecordedDocuments()
                .get(0);
            Map<String, Object> data = document.get(Map.class);
            int statusCode = (int) data.get("statusCode");
            assertEquals(200, statusCode);
        }
    }

    @TestFixture(
        snap = Delete.class, input = "data/delete/input_invalid_data.json", outputs = "out1",
        errors = "err1", properties = "data/delete/property_data_1.json")
    public void testDeleteInvalidData(TestSetup setup) throws Exception {
        setup.inject()
            .fieldName("account")
            .dependency(account)
            .add();
        TestResult testResult = setup.test();
        // check for no exception
        assertNull(testResult.getException());
        // check if it is delete Snap
        Snap snap = testResult.getSnap();
        assertEquals(Delete.class, snap.getClass());
        // only one document to the output view
        // only one document to the output view
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        assertEquals(0, outputRecorder.getDocumentCount());
        outputRecorder = testResult.getErrorViewByName("err1");
        assertEquals(1, outputRecorder.getDocumentCount());
    }

    @TestFixture(
        snap = Delete.class, outputs = "out1", errors = "err1",
        input = "data/delete/input_bulk_data.json", properties = "data/delete/property_data_1.json")
    public void testDeleteBulkLoad(TestSetup setup) throws Exception {
        setup.inject()
            .fieldName("account")
            .dependency(account)
            .add();
        TestResult testResult = setup.test();
        setup.cleanup();
        OutputRecorder outputRecorder = testResult.getOutputViewByName("out1");
        long dataDocs = outputRecorder.getDocumentCount();
        outputRecorder = testResult.getErrorViewByName("err1");
        long errDocs = outputRecorder.getRecordedDocuments()
            .size();
        long totalDocs = dataDocs + errDocs;
        assertEquals(4, totalDocs);
    }
}
