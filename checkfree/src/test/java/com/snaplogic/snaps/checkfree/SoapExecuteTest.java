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
package com.snaplogic.snaps.checkfree;

import com.google.inject.Binder;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Scopes;
import com.snaplogic.api.ExecutionException;
import com.snaplogic.common.SnapType;
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
import com.snaplogic.snap.schema.api.ObjectSchema;
import com.snaplogic.snap.schema.api.Schema;
import com.snaplogic.snap.test.harness.SnapTestRunner;
import com.snaplogic.snap.test.harness.TestFixture;
import com.snaplogic.snap.test.harness.TestResult;
import com.snaplogic.snap.test.harness.TestSetup;
import com.snaplogic.snaps.checkfree.SOAPExecuteTemplateEvaluatorImpl;
import com.snaplogic.snaps.checkfree.CheckfreeExecute;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.runner.RunWith;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.TransformerFactory;
import javax.xml.ws.Dispatch;

import static com.snaplogic.snaps.checkfree.TestMessages.CHECK_TEMPLATE_CONTENTS;
import static com.snaplogic.snaps.checkfree.TestMessages.EMPTY_TEMPLATE;
import static com.snaplogic.snaps.checkfree.TestMessages.ERROR_READING_TEMPLATE;
import static com.snaplogic.snaps.checkfree.TestMessages.ERR_URL_CONNECT;
import static com.snaplogic.snaps.checkfree.TestMessages.REASON_URL_CONNECT;
import static com.snaplogic.snaps.checkfree.TestMessages.RESOLUTION_URL_CONNECT;
import static com.snaplogic.snaps.checkfree.TestMessages.TEMPLATE_READ_FAIL;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;

/**
 * SoapExecuteTest
 *
 * @author svatada
 */
@Ignore
@RunWith(SnapTestRunner.class)
public class SoapExecuteTest {
    /**
     * Mock invocation
     */
    private static class MockInvocationService extends InvocationServiceImpl {
        private String expectedEnvelope;

        @Inject
        MockInvocationService(final TransformerFactory transformerFactory,
                final DocumentBuilderFactory documentBuilderFactory,
                final ClientBuilderFactory clientBuilderFactory, final IntrospectionService
                introspectionService, final XmlUtils xmlUtils) {
            super(transformerFactory, documentBuilderFactory, clientBuilderFactory,
                    introspectionService, xmlUtils);
        }

        public void setExpectedEnvelope(String expectedEnvelopeURL) {
            URL url = Thread.currentThread().getContextClassLoader().getResource
                    (expectedEnvelopeURL);
            this.expectedEnvelope = getContentFrom(url);
        }

        @Override
        public SOAPMessage call(final ClientBuilder clientBuilder, final String envelope) throws
                SOAPException {
            assertEquals(expectedEnvelope, envelope);
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
                binder.bind(IntrospectionService.class).to(WsdlIntrospectionService.class).in
                        (Scopes.SINGLETON);

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
                binder.bind(TemplateEvaluator.class).to(SOAPExecuteTemplateEvaluatorImpl.class).in(Scopes
                        .SINGLETON);
                binder.bind(EditorContentProvider.class).to(XMLEditorContentProviderImpl.class);
                binder.bind(JsonEditorContentProvider.class).to(JsonEditorContentProviderImpl
                        .class);
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
    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapexecute/soapexecute.json",
            outputs = "out",
            properties = "data/soapread/properties1.json",
            dataFiles = {"wsdl_files/test_helloworld.wsdl"})
    public void testExecute(TestSetup setup) throws Exception {
        Map<String, Object> serviceSuggestion = setup.suggest(CheckfreeExecute.PROP_SERVICE);
        List<Object> services = (List<Object>) serviceSuggestion.get(CheckfreeExecute.PROP_SERVICE);
        assertEquals(1, services.size());
        assertEquals("{http://apache.org/hello_world_soap_http}SOAPService", services.get(0));
        Map<String, Object> endpointSuggestion = setup.suggest(CheckfreeExecute.PROP_ENDPOINT);
        List<Object> endpoints = (List<Object>) endpointSuggestion.get(CheckfreeExecute.PROP_ENDPOINT);
        assertEquals(1, endpoints.size());
        assertEquals("{http://apache.org/hello_world_soap_http}SoapPort", endpoints.get(0));
        Map<String, Object> operationSuggestion = setup.suggest(CheckfreeExecute.PROP_OPERATION);
        List<Object> operations = (List<Object>) operationSuggestion.get(
                CheckfreeExecute.PROP_OPERATION);
        assertEquals(4, operations.size());
        assertTrue(operations.contains("{http://apache.org/hello_world_soap_http}sayHi"));
        assertTrue(operations.contains("{http://apache.org/hello_world_soap_http}greetMe"));
        assertTrue(operations.contains("{http://apache.org/hello_world_soap_http}greetMeOneWay"));
        assertTrue(operations.contains("{http://apache.org/hello_world_soap_http}pingMe"));
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/execute/test_helloworld_greetMe.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }

    /**
     * Test soap list envelope generation
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapexecute/corticonInput.json",
            outputs = "out",
            properties = "data/soapread/propertiesSoapWithList.json",
            dataFiles = {"wsdl_files/corticon.wsdl"})
    public void testExecuteWithList(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/execute/soapWithList.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }

    /**
     * Test soap list envelope generation
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapexecute/blackberryInput.json",
            outputs = "out",
            properties = "data/soapread/execute/licensemanagerProperties.json",
            dataFiles = {"wsdl_files/blackberry/licensemanager.wsdl"})
    public void testSoapBlackBerry1(TestSetup setup) throws Exception {
        Map<String, Object> serviceSuggestion = setup.suggest(CheckfreeExecute.PROP_SERVICE);
        List<Object> services = (List<Object>) serviceSuggestion.get(CheckfreeExecute.PROP_SERVICE);
        assertEquals(1, services.size());
        assertEquals("{http://ws.licmgr.eos.ecomm.rim.com/}LicenseManagerWebServiceImplService",
                services.get(0));
        Map<String, Object> endpointSuggestion = setup.suggest(CheckfreeExecute.PROP_ENDPOINT);
        List<Object> endpoints = (List<Object>) endpointSuggestion.get(CheckfreeExecute.PROP_ENDPOINT);
        assertEquals(1, endpoints.size());
        assertEquals("{http://ws.licmgr.eos.ecomm.rim.com/}LicenseManagerWebServiceImplPort",
                endpoints.get(0));
        Map<String, Object> operationSuggestion = setup.suggest(CheckfreeExecute.PROP_OPERATION);
        List<Object> operations = (List<Object>) operationSuggestion.get(
                CheckfreeExecute.PROP_OPERATION);
        assertEquals(4, operations.size());
        assertTrue(operations.contains("{http://ws.licmgr.eos.ecomm.rim.com/}updateOrderLicenses"));
        assertTrue(operations.contains("{http://ws.licmgr.eos.ecomm.rim.com/}createEntitlement"));
        assertTrue(operations.contains("{http://ws.licmgr.eos.ecomm.rim.com/}obtainOrderSrps"));
        assertTrue(operations.contains("{http://ws.licmgr.eos.ecomm.rim.com/}updateEntitlement"));
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/execute/licensemanagerExpected.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }

    /**
     * Test soap list envelope generation
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapexecute/blackberryInput1.json",
            outputs = "out",
            properties = "data/soapread/execute/licensemanagerProperties1.json",
            dataFiles = {"wsdl_files/blackberry/licensemanager.wsdl"})
    public void testSoapBlackBerry2(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/execute/licensemanagerExpected1.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }

    /**
     * Test soap list envelope generation  (Attributes are encoded)
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapexecute/ta_AccountInput.json",
            outputs = "out",
            properties = "data/soapread/propertiesSoapWithListAndInput.json",
            dataFiles = {"wsdl_files/ta_account.wsdl"})
    public void testExecuteTAAccountWithEncodedAttr(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/execute/taAccountExpected.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }

    /**
     * Test soap list envelope generation (Attributes are not encoded)
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapexecute/ta_AccountInput1.json",
            outputs = "out",
            properties = "data/soapread/propertiesSoapWithListAndInput1.json",
            dataFiles = {"wsdl_files/ta_account.wsdl"})
    public void testExecuteTAAccount(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/execute/taAccountExpected.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }


    /**
     * Test soap list envelope generation
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapexecute/sap_accountInput.json",
            outputs = "out",
            properties = "data/soapread/sap_account_properties.json",
            dataFiles = {"wsdl_files/sap_account.wsdl"})
    public void testExecuteSAPAccount(TestSetup setup) throws Exception {
        Map<String, Object> serviceSuggestion = setup.suggest(CheckfreeExecute.PROP_SERVICE);
        List<Object> services = (List<Object>) serviceSuggestion.get(CheckfreeExecute.PROP_SERVICE);
        assertEquals(1, services.size());
        assertEquals("{http://sap.com/xi/A1S/Global}service", services.get(0));
        Map<String, Object> endpointSuggestion = setup.suggest(CheckfreeExecute.PROP_ENDPOINT);
        List<Object> endpoints = (List<Object>) endpointSuggestion.get(CheckfreeExecute.PROP_ENDPOINT);
        assertEquals(2, endpoints.size());
        assertTrue(endpoints.contains("{http://sap.com/xi/A1S/Global}binding_SOAP12"));
        assertTrue(endpoints.contains("{http://sap.com/xi/A1S/Global}binding"));
        Map<String, Object> operationSuggestion = setup.suggest(CheckfreeExecute.PROP_OPERATION);
        List<Object> operations = (List<Object>) operationSuggestion.get(
                CheckfreeExecute.PROP_OPERATION);
        assertEquals(2, operations.size());
        assertTrue(operations.contains("{http://sap.com/xi/A1S/Global}MaintainBundle_V1"));
        assertTrue(operations.contains("{http://sap.com/xi/A1S/Global}CheckMaintainBundle_V1"));
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/execute/sapAccountExpected.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
        setup.setPropertyValue(CheckfreeExecute.PROP_ENDPOINT,
                "{http://sap.com/xi/A1S/Global}binding_SOAP12");
        operationSuggestion = setup.suggest(CheckfreeExecute.PROP_OPERATION);
        operations = (List<Object>) operationSuggestion.get(CheckfreeExecute.PROP_OPERATION);
        assertEquals(2, operations.size());
        assertTrue(operations.contains("{http://sap.com/xi/A1S/Global}MaintainBundle_V1"));
        assertTrue(operations.contains("{http://sap.com/xi/A1S/Global}CheckMaintainBundle_V1"));
    }

    /**
     * Test for clicktel wsdl processing and envelope generation
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapexecute/clicktelInput.json",
            outputs = "out",
            properties = "data/soapread/properties2.json",
            dataFiles = {"wsdl_files/test_clicktel.wsdl",
                         "wsdl_files/encoding.xsd",
                         "wsdl_files/wsdl.xsd"})
    public void testReadClickTel(TestSetup setup) throws Exception {
        Map<String, Object> serviceSuggestion = setup.suggest(CheckfreeExecute.PROP_SERVICE);
        List<Object> services = (List<Object>) serviceSuggestion.get(CheckfreeExecute.PROP_SERVICE);
        assertEquals(1, services.size());
        assertEquals("{http://api.clickatell.com/soap/webservice}PushServerWS", services.get(0));
        Map<String, Object> endpointSuggestion = setup.suggest(CheckfreeExecute.PROP_ENDPOINT);
        List<Object> endpoints = (List<Object>) endpointSuggestion.get(CheckfreeExecute.PROP_ENDPOINT);
        assertEquals(1, endpoints.size());
        assertEquals("{http://api.clickatell.com/soap/webservice}PushServerWSPort",
                endpoints.get(0));
        Map<String, Object> operationSuggestion = setup.suggest(CheckfreeExecute.PROP_OPERATION);
        List<Object> operations = (List<Object>) operationSuggestion.get(
                CheckfreeExecute.PROP_OPERATION);
        assertEquals(15, operations.size());
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}auth"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}ping"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}sendmsg"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}querymsg"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}delmsg"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}getbalance"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}routeCoverage"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}si_push"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}ind_push"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}token_pay"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}startbatch"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}senditem"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}quicksend"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}endbatch"));
        assertTrue(operations.contains("{http://api.clickatell.com/soap/webservice}getmsgcharge"));
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/test_clicktel_ping.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }

    /**
     * Test for read with input view
     *
     * @param setup
     *
     * @throws Exception
     */
    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapread/test_servicenow_input_data.json",
            outputs = "out",
            properties = "data/soapread/properties3.json",
            dataFiles = {"wsdl_files/test_serviceNow.wsdl"})
    public void testReadServiceNowWithInput(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope
                ("expected/execute/test_servicenow_get_with_input.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        TestResult result = setup.test();
        result.validateInputDocuments("inp1");
        Schema schema = result.getSchemaProvider().getSchemaForViewName("inp1");
        assertTrue(schema instanceof ObjectSchema);
        Schema childSchema = ((ObjectSchema) schema).getChild("sys_id");
        assertEquals(childSchema.getType(), SnapType.STRING);
        childSchema = ((ObjectSchema) schema).getChild("use_view");
        assertEquals(childSchema.getType(), SnapType.STRING);
    }

    @TestFixture(snap = CheckfreeExecute.class,
            outputs = "out",
            properties = "data/soapread/properties4.json",
            dataFiles = {"wsdl_files/netsuite/netsuite.wsdl",
                    "wsdl_files/netsuite/platform.common.xsd",
                    "wsdl_files/netsuite/platform.commonTypes.xsd",
                    "wsdl_files/netsuite/platform.coreTypes.xsd",
                    "wsdl_files/netsuite/platform.core.xsd",
                    "wsdl_files/netsuite/platform.faults.xsd",
                    "wsdl_files/netsuite/platform.faultTypes.xsd",
                    "wsdl_files/netsuite/platform.messages.xsd",
                    "wsdl_files/netsuite/activities.scheduling.xsd",
                    "wsdl_files/netsuite/activities.schedulingTypes.xsd",
                    "wsdl_files/netsuite/documents.fileCabinet.xsd",
                    "wsdl_files/netsuite/documents.fileCabinetTypes.xsd",
                    "wsdl_files/netsuite/general.communication.xsd",
                    "wsdl_files/netsuite/general.communicationTypes.xsd",
                    "wsdl_files/netsuite/lists.accounting.xsd",
                    "wsdl_files/netsuite/lists.accountingTypes.xsd",
                    "wsdl_files/netsuite/lists.employees.xsd",
                    "wsdl_files/netsuite/lists.employeeTypes.xsd",
                    "wsdl_files/netsuite/lists.marketing.xsd",
                    "wsdl_files/netsuite/lists.marketingTypes.xsd",
                    "wsdl_files/netsuite/lists.relationships.xsd",
                    "wsdl_files/netsuite/lists.relationshipTypes.xsd",
                    "wsdl_files/netsuite/lists.support.xsd",
                    "wsdl_files/netsuite/lists.supportTypes.xsd",
                    "wsdl_files/netsuite/lists.website.xsd",
                    "wsdl_files/netsuite/lists.websiteTypes.xsd",
                    "wsdl_files/netsuite/setup.customization.xsd",
                    "wsdl_files/netsuite/setup.customizationTypes.xsd",
                    "wsdl_files/netsuite/transactions.bank.xsd",
                    "wsdl_files/netsuite/transactions.bankTypes.xsd",
                    "wsdl_files/netsuite/transactions.customers.xsd",
                    "wsdl_files/netsuite/transactions.customerTypes.xsd",
                    "wsdl_files/netsuite/transactions.employees.xsd",
                    "wsdl_files/netsuite/transactions.employeeTypes.xsd",
                    "wsdl_files/netsuite/transactions.financial.xsd",
                    "wsdl_files/netsuite/transactions.financialTypes.xsd",
                    "wsdl_files/netsuite/transactions.general.xsd",
                    "wsdl_files/netsuite/transactions.inventory.xsd",
                    "wsdl_files/netsuite/transactions.inventoryTypes.xsd",
                    "wsdl_files/netsuite/transactions.purchases.xsd",
                    "wsdl_files/netsuite/transactions.purchaseTypes.xsd",
                    "wsdl_files/netsuite/transactions.sales.xsd",
                    "wsdl_files/netsuite/transactions.saleTypes.xsd"})
    public void testReadNetsuiteNoInput(TestSetup setup) throws Exception {
        // TODO - MK: The suggested template is very basic
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/execute/test_netsuite_add_expected" +
                ".xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }

    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapread/test_composite_input_data.json",
            outputs = "out",
            properties = "data/soapread/properties5.json",
            dataFiles = {"wsdl_files/test_composite.wsdl"})
    public void testComposite(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/test_composite_expected.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }

    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapread/test_hello_input_data.json",
            outputs = "out",
            properties = "data/soapread/properties7.json",
            dataFiles = {"wsdl_files/hello.wsdl"})
    @Ignore
    public void testWSDL20(TestSetup setup) throws Exception {
        MockInvocationService mockInvocationService = (MockInvocationService) injector
                .getInstance(InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/test_composite_expected.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
        setup.test();
    }

    @TestFixture(snap = CheckfreeExecute.class,
            input = "data/soapread/test_global_weather_input_data.json",
            outputs = "out",
            properties = "data/soapread/properties6.json",
            dataFiles = {"wsdl_files/global_weather.wsdl"})
    public void testGlobalWeather(TestSetup setup) throws Exception {
        Map<String, Object> serviceSuggestion = setup.suggest(CheckfreeExecute.PROP_SERVICE);
        List<Object> services = (List<Object>) serviceSuggestion.get(CheckfreeExecute.PROP_SERVICE);
        assertEquals(1, services.size());
        assertEquals("{http://www.webserviceX.NET}GlobalWeather", services.get(0));
        Map<String, Object> endpointSuggestion = setup.suggest(CheckfreeExecute.PROP_ENDPOINT);
        List<Object> endpoints = (List<Object>) endpointSuggestion.get(CheckfreeExecute.PROP_ENDPOINT);
        assertEquals(2, endpoints.size());
        assertTrue(endpoints.contains("{http://www.webserviceX.NET}GlobalWeatherSoap"));
        assertTrue(endpoints.contains("{http://www.webserviceX.NET}GlobalWeatherSoap12"));
        Map<String, Object> operationSuggestion = setup.suggest(CheckfreeExecute.PROP_OPERATION);
        List<Object> operations = (List<Object>) operationSuggestion.get(
                CheckfreeExecute.PROP_OPERATION);
        assertEquals(2, operations.size());
        assertTrue(operations.contains("{http://www.webserviceX.NET}GetWeather"));
        assertTrue(operations.contains("{http://www.webserviceX.NET}GetCitiesByCountry"));
        MockInvocationService mockInvocationService = (MockInvocationService) injector.getInstance
                (InvocationService.class);
        mockInvocationService.setExpectedEnvelope("expected/test_global_weather.xml");
        setup.inject().fieldName("invocationService")
                .dependency(mockInvocationService)
                .add();
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
                throw new ExecutionException(ERR_URL_CONNECT)
                        .formatWith(fileUrl.toString())
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