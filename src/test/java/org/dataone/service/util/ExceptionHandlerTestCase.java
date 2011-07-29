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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
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
        } catch (Throwable e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        }
    }

    @Test
    public void testDeserializeAndThrowException() {

        String contentType = "xml";
        String setDescription = "a description";
        NotFound nfe = new NotFound("123", setDescription);
        StringInputStream xmlErrorStream = new StringInputStream(nfe.serialize(BaseException.FMT_XML));
        try {
            ExceptionHandler.deserializeAndThrowException(xmlErrorStream, contentType);
            fail("should throw exception");
        } catch (NotFound e) {
            assertEquals(setDescription, e.getDescription());
        } catch (BaseException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (IllegalStateException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (IOException e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        } catch (Throwable e) {
            fail("shouldn't throw this exception: " + e.getClass().getSimpleName());
        }


    }

    @Test
    public void AuthenticationTimeoutTest() throws ParserConfigurationException, SAXException, IOException,
            IdentifierNotUnique, InsufficientResources, InvalidCredentials, InvalidRequest,
            InvalidSystemMetadata, InvalidToken, NotAuthorized, NotFound, NotImplemented,
            ServiceFailure, UnsupportedMetadataType, UnsupportedType, SynchronizationFailed,
            Throwable {
        boolean success = false;
        try {
            AuthenticationTimeout authTimeout = new AuthenticationTimeout("100", "test AuthenticationTimeout");
            String exceptTestSerial = authTimeout.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            IdentifierNotUnique exceptTest = new IdentifierNotUnique("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            InsufficientResources exceptTest = new InsufficientResources("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            InvalidCredentials exceptTest = new InvalidCredentials("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            InvalidRequest exceptTest = new InvalidRequest("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            InvalidSystemMetadata exceptTest = new InvalidSystemMetadata("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            InvalidToken exceptTest = new InvalidToken("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            NotAuthorized exceptTest = new NotAuthorized("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            NotFound exceptTest = new NotFound("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            NotImplemented exceptTest = new NotImplemented("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            ServiceFailure exceptTest = new ServiceFailure("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            UnsupportedMetadataType exceptTest = new UnsupportedMetadataType("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            UnsupportedType exceptTest = new UnsupportedType("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            SynchronizationFailed exceptTest = new SynchronizationFailed("100", "test IdentifierNotUnique");
            String exceptTestSerial = exceptTest.serialize(BaseException.FMT_XML);
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            int code = 0;
            String detail_code = null;
            String description = null;

            String exceptTestSerial = "<?xml version='1.0' encoding='UTF-8'?><error name='JUNK' errorCode='404' detailCode='-1'><description></description></error>";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
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
            Throwable {
        boolean success = false;
        try {
            int code = 0;
            String detail_code = null;
            String description = null;

            String exceptTestSerial = "<?xml version='1.0' encoding='UTF-8'?><error errorCode='404' detailCode='-1'><description></description></error>";
            ByteArrayInputStream inputStream = new ByteArrayInputStream(exceptTestSerial.getBytes());

            ExceptionHandler.deserializeAndThrowException(inputStream, "text/xml");
        } catch (ServiceFailure ex) {
            success = true;
        }
        assertTrue(success);
    }
    //          AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
    //                 InvalidCredentials, InvalidRequest, InvalidSystemMetadata,
    //                 InvalidToken, NotAuthorized, NotFound, NotImplemented, ServiceFailure,
    //                 UnsupportedMetadataType, UnsupportedType, SynchronizationFailed
}
