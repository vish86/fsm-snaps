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
package com.snaplogic.snaps.soap;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.common.services.ws.ClientBuilder;
import com.snaplogic.common.services.ws.ClientBuilderFactory;
import com.snaplogic.common.services.ws.IntrospectionService;
import com.snaplogic.common.services.ws.InvocationService;
import com.snaplogic.snap.api.EditorContentProvider;
import com.snaplogic.snap.api.JsonEditorContentProvider;
import com.snaplogic.snap.api.TemplateEvaluator;
import com.snaplogic.snap.api.editor.JsonEditorContentProviderImpl;
import com.snaplogic.snap.api.editor.XMLEditorContentProviderImpl;
import com.snaplogic.snap.api.soap.ClientBuilderFactoryImpl;
import com.snaplogic.snap.api.soap.ClientBuilderImpl;
import com.snaplogic.snap.api.soap.InvocationServiceImpl;
import com.snaplogic.snap.api.soap.WsdlIntrospectionService;
import com.snaplogic.snap.api.xml.XmlUtils;
import com.snaplogic.snap.api.xml.XmlUtilsImpl;
import com.snaplogic.snap.test.harness.SnapTestRunner;
import com.snaplogic.snap.test.harness.TestFixture;
import com.snaplogic.snap.test.harness.TestSetup;
import com.snaplogic.snaps.checkfree.Execute;
import com.snaplogic.snaps.checkfree.SOAPExecuteTemplateEvaluatorImpl;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerFactory;
import javax.xml.ws.Dispatch;

import static com.snaplogic.snaps.soap.TestMessages.CHECK_TEMPLATE_CONTENTS;
import static com.snaplogic.snaps.soap.TestMessages.EMPTY_TEMPLATE;
import static com.snaplogic.snaps.soap.TestMessages.ERROR_READING_TEMPLATE;
import static com.snaplogic.snaps.soap.TestMessages.ERR_URL_CONNECT;
import static com.snaplogic.snaps.soap.TestMessages.REASON_URL_CONNECT;
import static com.snaplogic.snaps.soap.TestMessages.RESOLUTION_URL_CONNECT;
import static com.snaplogic.snaps.soap.TestMessages.TEMPLATE_READ_FAIL;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;

/**
 * SoapExecuteTest
 *
 * @author mklumpp
 */
@RunWith(SnapTestRunner.class)
public class ExecuteTest {
    /**
     * Mock invocation
     */
    private static class MockInvocationService extends InvocationServiceImpl {
        private String expectedEnvelope;

        @Inject
        MockInvocationService(final TransformerFactory transformerFactory,
                final DocumentBuilderFactory documentBuilderFactory,
                final ClientBuilderFactory clientBuilderFactory,
                final IntrospectionService introspectionService, final XmlUtils xmlUtils) {
            super(transformerFactory, documentBuilderFactory, clientBuilderFactory,
                    introspectionService, xmlUtils);
        }

        public void setExpectedEnvelope(String expectedEnvelopeURL) {
            URL url = Thread.currentThread()
                    .getContextClassLoader()
                    .getResource(expectedEnvelopeURL);
            this.expectedEnvelope = getContentFrom(url);
        }

        @Override
        public SOAPMessage call(final ClientBuilder clientBuilder, final String envelope)
                throws SOAPException {
            assertEquals(envelope, expectedEnvelope);
            return super.call(clientBuilder, expectedEnvelope);
        }

        @Override
        protected SOAPMessage execCall(final ClientBuilderImpl clientBuilderImpl,
                final Dispatch<SOAPMessage> dispatch, final SOAPMessage requestMessage) {
            return null;
        }
    }

    private Injector injector;

    @Before
    public void setup() {
        injector = Guice.createInjector(new Module() {
            @Override
            public void configure(final Binder binder) {
                binder.bind(ClientBuilderFactory.class).to(ClientBuilderFactoryImpl.class);
                binder.bind(InvocationService.class).to(MockInvocationService.class);
                DocumentBuilderFactory documentBuilderFactory = getDocumentBuilderFactory();
                binder.bind(DocumentBuilderFactory.class).toInstance(documentBuilderFactory);
                binder.bind(IntrospectionService.class)
                        .to(WsdlIntrospectionService.class)
                        .in(Scopes.SINGLETON);

                TransformerFactory transformerFactory = TransformerFactory.newInstance();
                binder.bind(TransformerFactory.class).toInstance(transformerFactory);

                SOAPFactory soapFactory;
                try {
                    soapFactory = SOAPFactory.newInstance();
                    binder.bind(SOAPFactory.class).toInstance(soapFactory);
                } catch (SOAPException e) {
                    fail("Failed to create soap factory");
                }
                binder.bind(XmlUtils.class).to(XmlUtilsImpl.class).in(Scopes.SINGLETON);
                binder.bind(TemplateEvaluator.class)
                        .to(SOAPExecuteTemplateEvaluatorImpl.class)
                        .in(Scopes.SINGLETON);
                binder.bind(EditorContentProvider.class).to(XMLEditorContentProviderImpl.class);
                binder.bind(JsonEditorContentProvider.class)
                        .to(JsonEditorContentProviderImpl.class);
            }
        });
    }

    /**
     * Test for read w/o input view
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = Execute.class, input = "data/soapexecute/execute.json", outputs = "out", properties = "data/soapread/properties1.json", dataFiles = { "wsdl_files/test_helloworld.wsdl" })
    public void testExecute(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance(InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/execute/test_helloworld_greetMe.xml");
        setup.inject().fieldName("invocationService").dependency(mockInvocationService).add();
        setup.test();
    }

    /**
     * Test for clicktel wsdl processing and envelope generation
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = Execute.class, outputs = "out", properties = "data/soapread/properties2.json", dataFiles = { "wsdl_files/test_clicktel.wsdl" })
    @Ignore("to be fixed")
    public void testReadClickTel(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance(InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/test_clicktel_ping.xml");
        setup.inject().fieldName("invocationService").dependency(mockInvocationService).add();
        setup.test();
    }

    @TestFixture(snap = Execute.class, input = "data/soapread/test_composite_input_data.json", outputs = "out", properties = "data/soapread/properties5.json", dataFiles = { "wsdl_files/test_composite.wsdl" })
    @Ignore("No top level schema resolved, instead it is part of types model")
    public void testComposite(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance(InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/test_composite_expected.xml");
        setup.inject().fieldName("invocationService").dependency(mockInvocationService).add();
        setup.test();
    }

    private DocumentBuilderFactory getDocumentBuilderFactory() {
        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setExpandEntityReferences(true);
        documentBuilderFactory.setNamespaceAware(true);
        documentBuilderFactory.setCoalescing(true);
        return documentBuilderFactory;
    }

    static String getContentFrom(URL fileUrl) {
        String templateText;
        try {
            URLConnection urlConnection = fileUrl.openConnection();
            if (urlConnection == null) {
                throw new ExecutionException(ERR_URL_CONNECT).formatWith(fileUrl.toString())
                        .withReason(REASON_URL_CONNECT)
                        .withResolution(RESOLUTION_URL_CONNECT);
            }
            urlConnection.connect();
            try (final InputStream in = urlConnection.getInputStream()) {
                templateText = IOUtils.toString(in);
                if (StringUtils.isBlank(templateText)) {
                    throw new ExecutionException(TEMPLATE_READ_FAIL).formatWith(fileUrl.toString())
                            .withReason(EMPTY_TEMPLATE)
                            .withResolution(CHECK_TEMPLATE_CONTENTS);
                }
            }
        } catch (IOException e) {
            throw new ExecutionException(TEMPLATE_READ_FAIL).formatWith(fileUrl.toString())
                    .withReason(ERROR_READING_TEMPLATE)
                    .withResolution(CHECK_TEMPLATE_CONTENTS);
        }
        return templateText;
    }
}