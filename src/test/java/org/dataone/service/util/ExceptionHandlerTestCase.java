/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.service.util;

import org.apache.tools.ant.filters.StringInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.commons.io.IOUtils;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.ProtocolVersion;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicStatusLine;
import org.dataone.service.exceptions.AuthenticationTimeout;
import org.dataone.service.exceptions.BaseException;
import org.dataone.service.exceptions.IdentifierNotUnique;
import org.dataone.service.exceptions.InsufficientResources;
import org.dataone.service.exceptions.InvalidCredentials;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.InvalidSystemMetadata;
import org.dataone.service.exceptions.InvalidToken;
import org.dataone.service.exceptions.NotAuthorized;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.exceptions.SynchronizationFailed;
import org.dataone.service.exceptions.UnsupportedMetadataType;
import org.dataone.service.exceptions.UnsupportedType;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Ignore;
import org.xml.sax.SAXException;

/**
 *
 * @author waltz
 */
public class ExceptionHandlerTestCase {

    @Before
    public void setUpBeforeClass() throws Exception {
        NotFound nfe = new NotFound("12345", "some generic description");
        StringInputStream jsonErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_JSON));
    }

    @Test
    public void testFilterErrors_IS_nonErrors() {
        String nonErrorString = "fa la la la la";
        StringInputStream nonErrorStream = new StringInputStream(nonErrorString);
        try {
            InputStream is = ExceptionHandler.filterErrors(nonErrorStream, false, "text");
            assertEquals(nonErrorString, IOUtils.toString(is));

        } catch (BaseException be) {
            fail("shouldn't throw exception");
        } catch (IllegalStateException e) {
            fail("shouldn't throw exception");
        } catch (IOException e) {
            fail("shouldn't throw exception");
        }
    }

    @Test
    public void testFilterErrors_IS_xmlError() {
        String setDetailCode = "12345";
        String setDescription = "some generic description";
        NotFound nfe = new NotFound(setDetailCode, setDescription);
        StringInputStream xmlErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_XML));
        try {
            InputStream is = ExceptionHandler.filterErrors(xmlErrorStream, true, "xml");
            fail("should throw exception");
        } catch (NotFound e) {
            assertEquals(setDetailCode, e.getDetail_code());
            assertEquals(setDescription, e.getDescription());
        } catch (BaseException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (IllegalStateException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (IOException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        }
    }

    @Ignore("still needs work")
    @Test
    public void testFilterErrors_IS_jsonError() {
        String setDetailCode = "12345";
        String setDescription = "some generic description";
        NotFound nfe = new NotFound(setDetailCode, setDescription);
        StringInputStream xmlErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_JSON));
        try {
            InputStream is = ExceptionHandler.filterErrors(xmlErrorStream, true, "json");
            fail("should throw exception");
        } catch (NotFound e) {
            assertEquals(setDetailCode, e.getDetail_code());
            assertEquals(setDescription, e.getDescription());
        } catch (BaseException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (IllegalStateException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (IOException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        }
    }

    @Test
    public void testDeserializeAndThrowException() {

        Integer errorCode = new Integer(404);
        String errorReason = "Not Found";
        String contentType = "xml";
        String setDescription = "a description";
        NotFound nfe = new NotFound("123", setDescription);
        StringInputStream xmlErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_XML));
        try {
            ExceptionHandler.deserializeAndThrowException(xmlErrorStream, contentType, errorCode, errorReason);
            fail("should throw exception");
        } catch (NotFound e) {
            assertEquals(setDescription, e.getDescription());
        } catch (BaseException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (IllegalStateException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        }


    }
    
    
    @Test
    public void testDeserializeHeadersAndThrowException() {

        int errorCode = 404;
        String setDescription = "a description"; 
        Header[] headers = new Header[]{ 
        		new BasicHeader("DataONE-Exception-Name","NotFound"),
        		new BasicHeader("DataONE-Exception-DetailCode","123"),
        		new BasicHeader("DataONE-Exception-Description",setDescription),
        		new BasicHeader("DataONE-Exception-PID","aPid")};   

        try {
            ExceptionHandler.deserializeHeadersAndThrowException(errorCode, headers);
            fail("should throw exception");
        } catch (NotFound e) {
            assertEquals(setDescription, e.getDescription());
        } catch (BaseException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (IllegalStateException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (IOException e) {
        	fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		} catch (HttpException e) {
			fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
		}
    }
    
    
    

    @Test
    public void filterErrorsTest() throws IOException {

        String errorString = "<?xml version='1.0' encoding='UTF-8'?>"
                + "<error name='NotFound' "
                + " errorCode='404' detailCode='1020' "
                + " pid='123XYZ' nodeId='c3p0'> "
                + " <description>The specified object does not exist on this node.</description> "
                + " <traceInformation> "
                + "   <value key='1'>some stuff goes here </value> "
                + "   <value key='2'>some other stuff goes here </value> "
                + " </traceInformation> "
                + " </error>";
        ByteArrayEntity testInputStream = new ByteArrayEntity(errorString.getBytes());
        BasicStatusLine basicStatusLine = new BasicStatusLine(new ProtocolVersion("HTTP", 1, 1), 404, "NotFound");
        BasicHttpResponse basicHttpResponse = new BasicHttpResponse(basicStatusLine);
        BasicHeader basicHeader = new BasicHeader("content-type", "text/xml");
        basicHttpResponse.setEntity(testInputStream);
        basicHttpResponse.addHeader(basicHeader);
        try
         {
            ExceptionHandler.filterErrors(basicHttpResponse);
        } catch (NotFound e) {
            assertEquals("The specified object does not exist on this node.", e.getDescription());
        } catch (Exception e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName() + " " + e.getMessage());
        }
    }

    @Test
    public void AuthenticationTimeoutTest() throws ParserConfigurationException, SAXException, IOException,
            IdentifierNotUnique, InsufficientResources, InvalidCredentials, InvalidRequest,
            InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(408);
            String errorReason = "AuthenticationTimeout";
            AuthenticationTimeout authTimeout = new AuthenticationTimeout("100", "test AuthenticationTimeout");
            String exceptTestSerial = authTimeout.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (AuthenticationTimeout ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void IdentifierNotUniqueTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, InsufficientResources, InvalidCredentials, InvalidRequest,
            InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(409);
            String errorReason = "IdentifierNotUnique";
            IdentifierNotUnique exceptTest = new IdentifierNotUnique("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (IdentifierNotUnique ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void InsufficientResourcesTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InvalidCredentials, InvalidRequest,
            InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(413);
            String errorReason = "InsufficientResources";
            InsufficientResources exceptTest = new InsufficientResources("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (InsufficientResources ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void InvalidCredentialsTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidRequest,
            InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(401);
            String errorReason = "InvalidCredentials";
            InvalidCredentials exceptTest = new InvalidCredentials("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (InvalidCredentials ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void InvalidRequestTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(400);
            String errorReason = "InvalidRequest";
            InvalidRequest exceptTest = new InvalidRequest("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (InvalidRequest ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void InvalidSystemMetadataTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidToken, NotAuthorized, NotFound, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(400);
            String errorReason = "InvalidSystemMetadata";
            InvalidSystemMetadata exceptTest = new InvalidSystemMetadata("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (InvalidSystemMetadata ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void InvalidTokenTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, NotAuthorized, NotFound, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(401);
            String errorReason = "InvalidToken";
            InvalidToken exceptTest = new InvalidToken("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (InvalidToken ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void NotAuthorizedTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, InvalidToken, NotFound, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(401);
            String errorReason = "NotAuthorized";
            NotAuthorized exceptTest = new NotAuthorized("100", "test Not Authorized");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (NotAuthorized ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void NotFoundTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, InvalidToken, NotAuthorized, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(404);
            String errorReason = "NotFound";
            NotFound exceptTest = new NotFound("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (NotFound ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void NotImplementedTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(501);
            String errorReason = "NotImplemented";
            NotImplemented exceptTest = new NotImplemented("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (NotImplemented ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void ServiceFailureTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound,
            NotImplemented, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(500);
            String errorReason = "ServiceFailure";
            ServiceFailure exceptTest = new ServiceFailure("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (ServiceFailure ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void UnsupportedMetadataTypeTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound,
            NotImplemented, ServiceFailure, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(400);
            String errorReason = "UnsupportedMetadataType";
            UnsupportedMetadataType exceptTest = new UnsupportedMetadataType("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (UnsupportedMetadataType ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void UnsupportedTypeTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound,
            NotImplemented, ServiceFailure, UnsupportedMetadataType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(400);
            String errorReason = "UnsupportedType";
            UnsupportedType exceptTest = new UnsupportedType("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (UnsupportedType ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void SynchronizationFailedTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound,
            NotImplemented, ServiceFailure, UnsupportedMetadataType, UnsupportedType,
            BaseException {
        boolean success = false;
        try {
            Integer errorCode = new Integer(500);
            String errorReason = "SynchronizationFailed";
            SynchronizationFailed exceptTest = new SynchronizationFailed("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (SynchronizationFailed ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void UnsupportedExceptionTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound,
            NotImplemented, ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            int code = 0;
            String detail_code = null;
            String description = null;
            Integer errorCode = new Integer(404);
            String errorReason = "NotFound";
            String exceptTestSerial = "<?xml version='1.0' encoding='UTF-8'?><error name='JUNK' errorCode='404' detailCode='-1'><description></description></error>";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (ServiceFailure ex) {
            success = true;
        }
        assertTrue(success);
    }

    @Test
    public void NotNamedExceptionTest() throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, InvalidCredentials,
            InvalidRequest, InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound,
            NotImplemented, ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            BaseException {
        boolean success = false;
        try {
            int code = 0;
            String detail_code = null;
            String description = null;
            Integer errorCode = new Integer(404);
            String errorReason = "NotFound";
            String exceptTestSerial = "<?xml version='1.0' encoding='UTF-8'?><error errorCode='404' detailCode='-1'><description></description></error>";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml", errorCode, errorReason);
        } catch (ServiceFailure ex) {
            success = true;
        }
        assertTrue(success);
    }
}
