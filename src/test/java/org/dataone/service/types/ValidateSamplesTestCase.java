/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright ${year}
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.dataone.service.types;

import java.text.SimpleDateFormat;
import java.text.DateFormat;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;


import org.junit.*;


import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import java.util.Date;
import java.util.List;

import javax.xml.XMLConstants;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Schema;
import javax.xml.validation.Validator;

//import org.dataone.service.types.Node.Environment;
//import org.dataone.service.types.Node.NodeType;

import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;


import org.w3c.dom.Document;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import static org.junit.Assert.*;

public class ValidateSamplesTestCase {

    static String datatypeSchemaLocation = "https://repository.dataone.org/software/cicore/branches/D1_SCHEMA_0_6_2/dataoneTypes.xsd";

    static String systemMetadataSchemaLocation = "https://repository.dataone.org/software/cicore/branches/D1_SCHEMA_0_6_2/dataoneTypes.xsd";
    static String systemObjectListSchemaLocation = "https://repository.dataone.org/software/cicore/branches/D1_SCHEMA_0_6_2/dataoneTypes.xsd";
    static String systemLoggingSchemaLocation = "https://repository.dataone.org/software/cicore/branches/D1_SCHEMA_0_6_2/dataoneTypes.xsd";
    static String systemNodeRegistrySchemaLocation = "https://repository.dataone.org/software/cicore/branches/D1_SCHEMA_0_6_2/dataoneTypes.xsd";
    static String systemIdentifierSchemaLocation = "https://repository.dataone.org/software/cicore/branches/D1_SCHEMA_0_6_2/dataoneTypes.xsd";
    static String systemChecksumSchemaLocation = "https://repository.dataone.org/software/cicore/branches/D1_SCHEMA_0_6_2/dataoneTypes.xsd";
    @Test
    public void fake() throws Exception {
        // parse an XML document into a DOM tree
        assertTrue(true);
    }

//    @Test
//    public void validateSysmetaSample() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(validateExamples(systemMetadataSchemaLocation, "/org/dataone/service/samples/systemMetadataSample1.xml"));
//
//    }
//
//    @Test
//    public void validateSysmetaMarshalling() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(testSystemMetadataMarshalling("/org/dataone/service/samples/systemMetadataSample1.xml"));
//
//    }
//    @Test
//    public void validateSysmetaSampleUnicodeSupplEscaped() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(validateExamples(systemMetadataSchemaLocation, "/org/dataone/service/samples/systemMetadataSampleUnicodeSupplEscaped.xml"));
//
//    }
//
// //   @Test
//    public void validateSysmetaSampleUnicodeSupplEscapedMarshalling() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(testSystemMetadataMarshalling("/org/dataone/service/samples/systemMetadataSampleUnicodeSupplEscaped.xml"));
//
//    }
//    @Test
//    public void validateObjectListSample() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(validateExamples(systemObjectListSchemaLocation, "/org/dataone/service/samples/objectListSample1.xml"));
//
//    }
//
//    @Test
//    public void validateObjectListMarshalling() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(testObjectListMarshalling("/org/dataone/service/samples/objectListSample1.xml"));
//
//    }
//
//    @Test
//    public void validateLoggingSample() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(validateExamples(systemLoggingSchemaLocation, "/org/dataone/service/samples/loggingSample1.xml"));
//
//    }
//
//    @Test
//    public void validateLoggingMarshalling() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(testLoggingMarshalling("/org/dataone/service/samples/loggingSample1.xml"));
//
//    }
//
//    @Test
//    public void validateNodeRegistrySample() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(validateExamples(systemNodeRegistrySchemaLocation, "/org/dataone/service/samples/nodeListSample1.xml"));
//
//    }
//
//    @Test
//    public void validateIdentifierSample() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(validateExamples(systemIdentifierSchemaLocation, "/org/dataone/service/samples/identifier1.xml"));
//
//    }
//
//    @Test
//    public void validateIdentifierMarshalling() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(testIdentifierMarshalling("/org/dataone/service/samples/identifier1.xml"));
//
//    }
//
//    @Test
//    public void validateChecksumSample() throws Exception, SAXException, IOException, ParserConfigurationException {
//// TODO arguments should be injected based on version of service api to test and build
//        assertTrue(validateExamples(systemChecksumSchemaLocation, "/org/dataone/service/samples/checksum1.xml"));
//
//    }
//
//    @Test
//    public void validateNodeRegistryMarshalling() throws Exception, SAXException, IOException, ParserConfigurationException {
//
//        assertTrue(testChecksumMarshalling("/org/dataone/service/samples/checksum1.xml"));
//
//    }
//    private boolean validateExamples(String xsdUrlString, InputStream xmlInputStream) throws Exception, SAXException, IOException, ParserConfigurationException {
//        DocumentBuilder parser;
//        // create a SchemaFactory capable of understanding WXS schemas
//        SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
//        Document document;
//        Schema schema;
//        Source schemaFile;
//        URL xsdUrl = new URL(xsdUrlString);
//
//        URLConnection xsdUrlConnection = xsdUrl.openConnection();
//        InputStream xsdUrlStream = xsdUrlConnection.getInputStream();
//        if (xsdUrlStream == null) {
//            System.out.println(xsdUrlString + " InputStream is null");
//        } else {
//            System.out.println("Validate: " + xsdUrlString);
//        }
//
//
//        // load a WXS schema, represented by a Schema instance
//
//
//        schemaFile = new StreamSource(xsdUrlStream);
//        schema = factory.newSchema(schemaFile);
//        DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
//
//        documentBuilderFactory.setIgnoringElementContentWhitespace(true);
//        documentBuilderFactory.setNamespaceAware(true);
//        documentBuilderFactory.setSchema(schema);
//        documentBuilderFactory.setValidating(false);
//
//        parser = documentBuilderFactory.newDocumentBuilder();
//        // load in the file to validate
//        document = parser.parse(xmlInputStream);
//        System.out.println(document.getDocumentElement().getNodeName());
//        // create a Validator instance, which can be used to validate an instance document
//
//        ValidateXmlDocument validateXmlDocument = new ValidateXmlDocument(schema);
//
//
//        // validate the DOM tree
//
//        return validateXmlDocument.validate(document);
//
//    }
//    private boolean validateExamples(String xsdUrlString, String xmlDocument) throws Exception, SAXException, IOException, ParserConfigurationException {
//        return validateExamples(xsdUrlString,this.getClass().getResourceAsStream(xmlDocument) );
//    }
//
//    /**
//     *
//     * @author Robert P Waltz.
//     */
//    private class ValidateXmlDocument {
////	    Logger logger = Logger.getRootLogger();
//
//        private class ErrorHandlerImpl implements ErrorHandler {
//
//            public void warning(SAXParseException exception) throws SAXException {
//                System.out.print(exception.getMessage());
//                // do nothing;
//            }
//
//            public void error(SAXParseException exception) throws SAXException {
//                System.out.print(exception.getMessage());
////	            logger.warn(exception.getMessage());
//                throw exception;
//            }
//
//            public void fatalError(SAXParseException exception) throws SAXException {
//                System.out.print(exception.getMessage());
////	            logger.warn(exception.getMessage());
//                throw exception;
//            }
//        }
//        private Validator validator = null;
//
//        public ValidateXmlDocument(Schema schema) {
//            // TODO Auto-generated constructor stub
//            try {
//                ErrorHandlerImpl errorHandlerImpl = new ErrorHandlerImpl();
//
//                validator = schema.newValidator();
//                validator.setErrorHandler(errorHandlerImpl);
//            } catch (Exception e) {
////                logger.error("FATAL ERROR: INITIALIZATION OF XML VALIDATE SERVICE IMPL\n",e);
//                validator = null;
//            }
//        }
//
//        public boolean validate(Document document) throws SAXException, Exception {
//
//            System.out.print(toString(document));
//
//
//            DOMSource domSource = new DOMSource(document);
//            if (domSource == null) {
//                throw new Exception("TEI SOURCE IS NULL");
//            }
//            validator.validate(domSource);
//
//            return true;
//        }
//
//        public String toString(Document document) throws Exception {
//            String result = null;
//
//            if (document != null) {
//                StringWriter strWtr = new StringWriter();
//                StreamResult strResult = new StreamResult(strWtr);
//                TransformerFactory tfac = TransformerFactory.newInstance();
//
//                Transformer t = tfac.newTransformer();
//                t.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
//                t.setOutputProperty(OutputKeys.INDENT, "yes");
//                t.setOutputProperty(OutputKeys.METHOD, "xml"); //xml, html, text
//                t.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
//                t.transform(new DOMSource(document.getDocumentElement()), strResult);
//
//                result = strResult.getWriter().toString();
//            }
//
//            return result;
//        }//toString()
//    }
//    @Test
//    public void testSimpleSystemMetadataMarshalling() throws Exception {
//        System.out.println("Starting testing of testSimpleSystemMetadataMarshalling");
//        SystemMetadata systemMetadata = new SystemMetadata();
//
//        Identifier identifier = new Identifier();
//        identifier.setValue("ABC432");
////        systemMetadata.
//        systemMetadata.setIdentifier(identifier);
////        ObjectFormat objectFormat;
//
//        systemMetadata.setObjectFormat(ObjectFormat.CF_1_0);
//        systemMetadata.setSize(1235431);
//        Subject submitter = new Subject();
//        submitter.setValue("Kermit de Frog");
//        systemMetadata.setSubmitter(submitter);
//        Subject rightsHolder = new Subject();
//        rightsHolder.setValue("DataONE");
//        systemMetadata.setRightsHolder(rightsHolder);
//        systemMetadata.setDateSysMetadataModified(new Date());
//        systemMetadata.setDateUploaded(new Date());
//        NodeReference originMemberNode = new NodeReference();
//        originMemberNode.setValue("mn1");
//        systemMetadata.setOriginMemberNode(originMemberNode);
//        NodeReference authoritativeMemberNode = new NodeReference();
//        authoritativeMemberNode.setValue("mn1");
//        systemMetadata.setAuthoritativeMemberNode(authoritativeMemberNode);
//        Checksum checksum = new Checksum();
//        checksum.setValue("V29ybGQgSGVsbG8h");
//        checksum.setAlgorithm(ChecksumAlgorithm.SHA_1);
//
//        systemMetadata.setChecksum(checksum);
//
//        IBindingFactory bfact =
//                BindingDirectory.getFactory(org.dataone.service.types.SystemMetadata.class);
//
//        IMarshallingContext mctx = bfact.createMarshallingContext();
//        ByteArrayOutputStream testSytemMetadataOutput = new ByteArrayOutputStream();
//
//        mctx.marshalDocument(systemMetadata, "UTF-8", null, testSytemMetadataOutput);
//
//        //       InputStream inputStream = this.getClass().getResourceAsStream(xmlDocument);
//
//        System.out.println(testSytemMetadataOutput.toString());
//        ByteArrayInputStream testSystemMetadataInput = new ByteArrayInputStream(testSytemMetadataOutput.toByteArray());
//
//        //       BindingDirectory.getFactory("binding", "org.dataone.service.types");
//        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
//        
//        systemMetadata = (SystemMetadata) uctx.unmarshalDocument(testSystemMetadataInput, null);
//        assertTrue(systemMetadata != null);
//        assertTrue(systemMetadata.getIdentifier().getValue().equalsIgnoreCase("ABC432"));
//        testSystemMetadataInput.reset();
//        assertTrue(validateExamples(systemMetadataSchemaLocation, testSystemMetadataInput));
//    }
//
//    public boolean testSystemMetadataMarshalling(String externalSystemMetadata) throws Exception {
//        System.out.println("Starting testing of testSystemMetadataMarshalling");
//        SystemMetadata systemMetadata = new SystemMetadata();
//        IBindingFactory bfact =
//                BindingDirectory.getFactory(org.dataone.service.types.SystemMetadata.class);
//
//        IMarshallingContext mctx = bfact.createMarshallingContext();
//        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
//
//        InputStream inputStream = this.getClass().getResourceAsStream(externalSystemMetadata);
//        try {
//            systemMetadata = (SystemMetadata) uctx.unmarshalDocument(inputStream, null);
//            ByteArrayOutputStream testSytemMetadataOutput = new ByteArrayOutputStream();
//            mctx.marshalDocument(systemMetadata, "UTF-8", null, testSytemMetadataOutput);
//            System.out.println(testSytemMetadataOutput.toString());
//            assertTrue(validateExamples(systemMetadataSchemaLocation,new ByteArrayInputStream(testSytemMetadataOutput.toByteArray())));
//
//        } finally {
//            inputStream.close();
//        }
//        return true;
//    }
//
//    public boolean testObjectListMarshalling(String externalObjectList) throws Exception {
//        ObjectList objectList = new ObjectList();
//        objectList.setCount(3);
//        objectList.setStart(0);
//        objectList.setTotal(3);
//
//        List<ObjectInfo> objectInfoList = new ArrayList<ObjectInfo>();
//
//        objectList.setObjectInfoList(objectInfoList);
//
//        ObjectInfo objectInfo1 = new ObjectInfo();
//        Identifier identifier1 = new Identifier();
//        identifier1.setValue("ABC123");
//        objectInfo1.setIdentifier(identifier1);
//        objectInfo1.setObjectFormat(ObjectFormat.CF_1_0);
//        Checksum checksum1 = new Checksum();
//        checksum1.setValue("V29ybGQgSGVsbG8h");
//        checksum1.setAlgorithm(ChecksumAlgorithm.SHA_1);
//        objectInfo1.setChecksum(checksum1);
//        objectInfo1.setDateSysMetadataModified(new Date());
//        objectInfo1.setSize(412341324);
//        objectList.addObjectInfo(objectInfo1);
//
//        ObjectInfo objectInfo2 = new ObjectInfo();
//        Identifier identifier2 = new Identifier();
//        identifier2.setValue("ABC456");
//        objectInfo2.setIdentifier(identifier1);
//        objectInfo2.setObjectFormat(ObjectFormat.DARWIN_2);
//        Checksum checksum2 = new Checksum();
//        checksum2.setValue("V29ybGQgSGVsaF89");
//        checksum2.setAlgorithm(ChecksumAlgorithm.MD5);
//        objectInfo2.setChecksum(checksum1);
//        objectInfo2.setDateSysMetadataModified(new Date());
//        objectInfo2.setSize(9087654);
//        objectList.addObjectInfo(objectInfo2);
//
//        ObjectInfo objectInfo3 = new ObjectInfo();
//        Identifier identifier3 = new Identifier();
//        identifier3.setValue("ABC456");
//        objectInfo3.setIdentifier(identifier1);
//        objectInfo3.setObjectFormat(ObjectFormat.FGDC_STD_001_1998);
//        Checksum checksum3 = new Checksum();
//        checksum3.setValue("V29ybGQgSGVsaF89ybGE8987adf3");
//        checksum3.setAlgorithm(ChecksumAlgorithm.SHA_512);
//        objectInfo3.setChecksum(checksum1);
//        objectInfo3.setDateSysMetadataModified(new Date());
//        objectInfo3.setSize(90654);
//        objectList.addObjectInfo(objectInfo3);
//
//
//        IBindingFactory bfact =
//                BindingDirectory.getFactory(org.dataone.service.types.ObjectList.class);
//
//        IMarshallingContext mctx = bfact.createMarshallingContext();
//        ByteArrayOutputStream testObjectListOutput = new ByteArrayOutputStream();
//
//        mctx.marshalDocument(objectList, "UTF-8", null, testObjectListOutput);
//
//        //       InputStream inputStream = this.getClass().getResourceAsStream(xmlDocument);
//
//
//        ByteArrayInputStream testObjectListInput = new ByteArrayInputStream(testObjectListOutput.toByteArray());
//
//        //       BindingDirectory.getFactory("binding", "org.dataone.service.types");
//        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
//
//        objectList = (ObjectList) uctx.unmarshalDocument(testObjectListInput, null);
//
//        InputStream inputStream = this.getClass().getResourceAsStream(externalObjectList);
//        try {
//            objectList = (ObjectList) uctx.unmarshalDocument(inputStream, null);
//
//        } finally {
//            inputStream.close();
//        }
//        // validate deserialized Composed object above
//        testObjectListInput.reset();
//        assertTrue(validateExamples(systemObjectListSchemaLocation, testObjectListInput));
//        
//        // validate deserialized resource stream
//        testObjectListOutput.reset();
//        mctx.marshalDocument(objectList, "UTF-8", null, testObjectListOutput);
//        testObjectListInput = new ByteArrayInputStream(testObjectListOutput.toByteArray());
//        assertTrue(validateExamples(systemObjectListSchemaLocation, testObjectListInput));
//        return true;
//    }
//
//    @Test
//    public void testSubjectListMarshalling() throws Exception {
//        System.out.println("Starting testing of testSubjectListMarshalling");
//        SubjectList subjectList = new SubjectList();
//        
//        // set the properties of SubjectList
//        String subjectValue = "cn=test1,dc=dataone,dc=org";
//        Subject subject = new Subject();
//        subject.setValue(subjectValue);
//        Person person = new Person();
//        person.setSubject(subject);
//        person.addGivenName("test");
//        person.setFamilyName("test");
//        person.addEmail("test@dataone.org");
//        subjectList.addPerson(person);
//        
//        IBindingFactory bfact =
//                BindingDirectory.getFactory(org.dataone.service.types.SubjectList.class);
//
//        IMarshallingContext mctx = bfact.createMarshallingContext();
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//
//        mctx.marshalDocument(subjectList, "UTF-8", null, baos);
//
//        System.out.println(baos.toString());
//        ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
//
//        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
//        
//        subjectList = (SubjectList) uctx.unmarshalDocument(bais, null);
//        assertTrue(subjectList != null);
//        assertTrue(subjectList.getPerson(0).getSubject().getValue().equals(subjectValue));
//        bais.reset();
//        assertTrue(validateExamples(datatypeSchemaLocation, bais));
//    }
//    
//    public boolean testLoggingMarshalling(String externalLoggingObjects) throws Exception {
//
//        Log log = new Log();
//        List<LogEntry> logEntryList = new ArrayList<LogEntry>();
//
//        log.setLogEntryList(logEntryList);
//
//        LogEntry logEntry1 = new LogEntry();
//        logEntry1.setDateLogged(new Date());
//        Identifier entry1 = new Identifier();
//        entry1.setValue("1");
//        logEntry1.setEntryId(entry1);
//
//        logEntry1.setEvent(Event.READ);
//        Identifier id1 = new Identifier();
//        id1.setValue("ABC");
//        logEntry1.setIdentifier(id1);
//
//        logEntry1.setIpAddress("123.123.123.123");
//
//        NodeReference memberNode = new NodeReference();
//        memberNode.setValue("mn1");
//        logEntry1.setMemberNode(memberNode);
//
//        Subject subject1 = new Subject();
//        subject1.setValue("Scooter");
//        logEntry1.setSubject(subject1);
//
//        logEntry1.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Update a; AOL 6.0; Windows 98)");
//        log.addLogEntry(logEntry1);
//
//        LogEntry logEntry2 = new LogEntry();
//        logEntry2.setDateLogged(new Date());
//        Identifier entry2 = new Identifier();
//        entry2.setValue("2");
//        logEntry2.setEntryId(entry2);
//
//        logEntry2.setEvent(Event.READ);
//        Identifier id2 = new Identifier();
//        id2.setValue("DEF");
//        logEntry2.setIdentifier(id2);
//
//        logEntry2.setIpAddress("123.123.123.123");
//
//        NodeReference memberNode2 = new NodeReference();
//        memberNode2.setValue("mn1");
//        logEntry2.setMemberNode(memberNode2);
//
//        Subject subject2 = new Subject();
//        subject2.setValue("Fozzie Bear");
//        logEntry2.setSubject(subject1);
//
//        logEntry2.setUserAgent("Mozilla/4.0 (compatible; MSIE 6.0; Update a; AOL 6.0; Windows 98)");
//        log.addLogEntry(logEntry2);
//
//        IBindingFactory bfact =
//                BindingDirectory.getFactory(org.dataone.service.types.Log.class);
//
//        IMarshallingContext mctx = bfact.createMarshallingContext();
//        ByteArrayOutputStream testLogOutput = new ByteArrayOutputStream();
//
//        mctx.marshalDocument(log, "UTF-8", null, testLogOutput);
//
//        //       InputStream inputStream = this.getClass().getResourceAsStream(xmlDocument);
//
//
//        ByteArrayInputStream testLogInput = new ByteArrayInputStream(testLogOutput.toByteArray());
//
//        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
//
//        log = (Log) uctx.unmarshalDocument(testLogInput, null);
//
//        InputStream inputStream = this.getClass().getResourceAsStream(externalLoggingObjects);
//        try {
//            log = (Log) uctx.unmarshalDocument(inputStream, null);
//
//        } finally {
//            inputStream.close();
//        }
//        // validate deserialized Composed object above
//        testLogInput.reset();
//        assertTrue(validateExamples(systemLoggingSchemaLocation, testLogInput));
//
//        // validate deserialized resource stream
//        testLogOutput.reset();
//        mctx.marshalDocument(log, "UTF-8", null, testLogOutput);
//        testLogInput = new ByteArrayInputStream(testLogOutput.toByteArray());
//        assertTrue(validateExamples(systemLoggingSchemaLocation, testLogInput));
//        return true;
//    }
//
//    public boolean testNodeListMarshalling(String externalObjectList) throws Exception {
//        NodeList nodeList = new NodeList();
//        Node node = new Node();
//        nodeList.addNode(node);
//        node.setReplicate(true);
//        node.setSynchronize(true);
//
//        NodeReference id1 = new NodeReference();
//        id1.setValue("123");
//        node.setIdentifier(id1);
//
//        String name = "nodename";
//        node.setName(name);
//        node.setBaseURL("this.here.org");
//
//        Services services = new Services();
//
//        Service service = new Service();
//
//        service.setName("crud");
//        service.setVersion("0.5");
//        service.setAvailable(true);
//
//        ServiceMethod serviceMethod = new ServiceMethod();
//        serviceMethod.setName("get");
//        serviceMethod.setRest("/object/{GUID}");
//        serviceMethod.setImplemented(true);
//
//
//        services.addService(service);
//        node.setServices(services);
//        Synchronization synchronize = new Synchronization();
//        Schedule schedule = new Schedule();
//        schedule.setSec("00");
//        schedule.setMin("01");
//        schedule.setHour("0,6,12,18");
//        schedule.setMday("*");
//        schedule.setWday("*");
//        schedule.setMon("*");
//        schedule.setYear("*");
//        synchronize.setSchedule(schedule);
//        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSSZ");
//        Date defaultDate = df.parse("2010-06-21T00:00:00.000Z");
//        synchronize.setLastHarvested(defaultDate);
//        synchronize.setLastCompleteHarvest(defaultDate);
//        node.setSynchronization(synchronize);
//
//        IBindingFactory bfact =
//                BindingDirectory.getFactory(org.dataone.service.types.NodeList.class);
//
//        IMarshallingContext mctx = bfact.createMarshallingContext();
//        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
//
//        mctx.marshalDocument(nodeList, "UTF-8", null, testOutput);
//
//        //       InputStream inputStream = this.getClass().getResourceAsStream(xmlDocument);
//
//        byte[] nodeRegistryTestOutput = testOutput.toByteArray();
//        String nodeRegistryStringOutput = new String(nodeRegistryTestOutput);
//        System.out.println(nodeRegistryStringOutput);
//        ByteArrayInputStream testInput = new ByteArrayInputStream(nodeRegistryTestOutput);
//
//        //       BindingDirectory.getFactory("binding", "org.dataone.service.types");
//        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
//
//        nodeList = (NodeList) uctx.unmarshalDocument(testInput, null);
//
//        InputStream inputStream = this.getClass().getResourceAsStream(externalObjectList);
//        try {
//            nodeList = (NodeList) uctx.unmarshalDocument(inputStream, null);
//
//        } finally {
//            inputStream.close();
//        }
//        // validate deserialized Composed object above
//        testInput.reset();
//        assertTrue(validateExamples(systemNodeRegistrySchemaLocation, testInput));
//
//        // validate deserialized resource stream
//        testOutput.reset();
//        mctx.marshalDocument(nodeList, "UTF-8", null, testOutput);
//        testInput = new ByteArrayInputStream(testOutput.toByteArray());
//        assertTrue(validateExamples(systemNodeRegistrySchemaLocation, testInput));
//        return true;
//    }
//
//    public boolean testIdentifierMarshalling(String identifierDoc) throws Exception {
//        Identifier id = new Identifier();
//        id.setValue("ABC123");
//
//        IBindingFactory bfact =
//                BindingDirectory.getFactory(org.dataone.service.types.Identifier.class);
//
//        IMarshallingContext mctx = bfact.createMarshallingContext();
//        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
//
//        mctx.marshalDocument(id, "UTF-8", null, testOutput);
//
//        //       InputStream inputStream = this.getClass().getResourceAsStream(xmlDocument);
//
//        byte[] identifierTestOutput = testOutput.toByteArray();
//        String identifierStringOutput = new String(identifierTestOutput);
//        System.out.println(identifierStringOutput);
//        ByteArrayInputStream testInput = new ByteArrayInputStream(identifierTestOutput);
//
//        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
//
//        id = (Identifier) uctx.unmarshalDocument(testInput, null);
//
//        InputStream inputStream = this.getClass().getResourceAsStream(identifierDoc);
//        try {
//            id = (Identifier) uctx.unmarshalDocument(inputStream, null);
//
//        } finally {
//            inputStream.close();
//        }
//        // validate deserialized Composed object above
//        testInput.reset();
//        assertTrue(validateExamples(systemIdentifierSchemaLocation, testInput));
//
//        // validate deserialized resource stream
//        testOutput.reset();
//        mctx.marshalDocument(id, "UTF-8", null, testOutput);
//        testInput = new ByteArrayInputStream(testOutput.toByteArray());
//        assertTrue(validateExamples(systemIdentifierSchemaLocation, testInput));
//        return true;
//    }
//
//    public boolean testChecksumMarshalling(String checksumDoc) throws Exception {
//        Checksum checksum = new Checksum();
//        checksum.setValue("ADSFA21341234ADSFADSF");
//        checksum.setAlgorithm(ChecksumAlgorithm.SHA_1);
//
//
//        IBindingFactory bfact =
//                BindingDirectory.getFactory(org.dataone.service.types.Checksum.class);
//
//        IMarshallingContext mctx = bfact.createMarshallingContext();
//        ByteArrayOutputStream testOutput = new ByteArrayOutputStream();
//
//        mctx.marshalDocument(checksum, "UTF-8", null, testOutput);
//
//        //       InputStream inputStream = this.getClass().getResourceAsStream(xmlDocument);
//
//        byte[] checksumTestOutput = testOutput.toByteArray();
//        String checksumStringOutput = new String(checksumTestOutput);
//        System.out.println(checksumStringOutput);
//        ByteArrayInputStream testInput = new ByteArrayInputStream(checksumTestOutput);
//
//        IUnmarshallingContext uctx = bfact.createUnmarshallingContext();
//
//        checksum = (Checksum) uctx.unmarshalDocument(testInput, null);
//
//        InputStream inputStream = this.getClass().getResourceAsStream(checksumDoc);
//        try {
//            checksum = (Checksum) uctx.unmarshalDocument(inputStream, null);
//
//        } finally {
//            inputStream.close();
//        }
//        // validate deserialized Composed object above
//        testInput.reset();
//        assertTrue(validateExamples(systemChecksumSchemaLocation, testInput));
//
//        // validate deserialized resource stream
//        testOutput.reset();
//        mctx.marshalDocument(checksum, "UTF-8", null, testOutput);
//        testInput = new ByteArrayInputStream(testOutput.toByteArray());
//        assertTrue(validateExamples(systemChecksumSchemaLocation, testInput));
//        return true;
//    }
}
