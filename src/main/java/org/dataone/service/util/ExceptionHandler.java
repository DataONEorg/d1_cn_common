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
 * 
 * $Id$
 */

package org.dataone.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
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
import org.dataone.service.exceptions.VersionMismatch;
import org.jibx.runtime.JiBXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * uniform deserialization of DataONE Exceptions that may be rethrown
 *
 * @author waltz, rnahf
 */
public class ExceptionHandler {

    protected static Log log = LogFactory.getLog(ExceptionHandler.class);


    public static InputStream filterErrors(HttpResponse res)
            throws AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
            InvalidCredentials, InvalidRequest, InvalidSystemMetadata, InvalidToken,
            NotAuthorized, NotFound, NotImplemented, ServiceFailure,
            UnsupportedMetadataType, UnsupportedType, VersionMismatch,
            IllegalStateException, IOException, HttpException, SynchronizationFailed {
    	
    	return filterErrors(res,false);
    }
    
    public static InputStream filterErrors(HttpResponse res, boolean allowRedirect)
            throws AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
            InvalidCredentials, InvalidRequest, InvalidSystemMetadata, InvalidToken,
            NotAuthorized, NotFound, NotImplemented, ServiceFailure,
            UnsupportedMetadataType, UnsupportedType, VersionMismatch,
            IllegalStateException, IOException, HttpException, SynchronizationFailed {

    	int code = res.getStatusLine().getStatusCode();
        log.info("response httpCode: " + code);
        // cannot read from an input stream twice.
//        if (log.isDebugEnabled()) {
//        	log.debug(IOUtils.toString(res.getEntity().getContent()));
//        }
        
        if (code == HttpURLConnection.HTTP_OK) {
        	// fall through
        } 
        else if (allowRedirect && code == HttpURLConnection.HTTP_SEE_OTHER) {
        	// fall through
        }
        else {
            // error, so throw exception
            deserializeAndThrowException(res);
        }
        return res.getEntity().getContent();
    }

    
    /**
     * will filter serialized errors coming from the HEAD request
     * by deserializing them and putting on the exception thread(?).
     * Exception state is triggered if http status code is not 200 (OK)
     *  or 204 (NO_CONTENT). Where possible, assembles a dataone exception
     *  from header entries corresponding to the standard base exception parts.
     *  
     *  Because this method functions (like filterErrors) as an adapter method, 
     *  returning just the headers from the response, it consumes the entity
     *  contained in the response that is passed in, for proper release of
     *  connection resources.
     * 
     * @param response - the http response
     * @return - Header[] from the http response
     * @throws AuthenticationTimeout
     * @throws IdentifierNotUnique
     * @throws InsufficientResources
     * @throws InvalidCredentials
     * @throws InvalidRequest
     * @throws InvalidSystemMetadata
     * @throws InvalidToken
     * @throws NotAuthorized
     * @throws NotFound
     * @throws NotImplemented
     * @throws ServiceFailure
     * @throws UnsupportedMetadataType
     * @throws UnsupportedQueryType
     * @throws UnsupportedType
     * @throws VersionMismatch
     * @throws IllegalStateException
     * @throws IOException
     * @throws HttpException
     * @throws SynchronizationFailed
     */
    public static Header[] filterErrorsHeader(HttpResponse response, String httpMethod)
            throws AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
            InvalidCredentials, InvalidRequest, InvalidSystemMetadata, InvalidToken,
            NotAuthorized, NotFound, NotImplemented, ServiceFailure,
            UnsupportedMetadataType, UnsupportedType, VersionMismatch,
            IllegalStateException, IOException, HttpException, SynchronizationFailed {

        try {
        	int code = response.getStatusLine().getStatusCode();
        	log.info("response httpCode: " + code);
        	if (code != HttpURLConnection.HTTP_OK) {
        		// error, so throw exception
        		if (httpMethod == Constants.HEAD) {
        			deserializeHeadersAndThrowException(code,response.getAllHeaders());        		
        		} else { 
        			deserializeAndThrowException(response);
        		}
        	}
        } finally {
        	EntityUtils.consume(response.getEntity());
        }
        
        return response.getAllHeaders();
    }
    
    
    public static void deserializeHeadersAndThrowException(int code, Header[] headers)
    throws AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
    InvalidCredentials, InvalidRequest, InvalidSystemMetadata, InvalidToken,
    NotAuthorized, NotFound, NotImplemented, ServiceFailure,
    UnsupportedMetadataType, UnsupportedType, VersionMismatch,
    IllegalStateException, IOException, HttpException, SynchronizationFailed {
        
        
    	Map<String, String> headersMap = new HashMap<String,String>();
    	for (Header header: headers) {
    		if (log.isDebugEnabled())
    			log.debug(String.format("header: %s = %s", 
    									header.getName(), 
    									header.getValue() ));
    		headersMap.put(header.getName(), header.getValue());
    	}
    	
    	if (headersMap.containsKey("DataONE-Exception-Name")) {
    		String d1ExceptionName = headersMap.get("DataONE-Exception-Name");
    		String detailCode = headersMap.get("DataONE-Exception-DetailCode");
    		String description = headersMap.get("DataONE-Exception-Description");
    		String pid = headersMap.get("DataONE-Exception-PID");
    	
    		if (d1ExceptionName.equals("AuthenticationTimeout")) {
                throw new AuthenticationTimeout(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("IdentifierNotUnique")) {
                throw new IdentifierNotUnique(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("InsufficientResources")) {
                throw new InsufficientResources(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("InvalidCredentials")) {
                throw new InvalidCredentials(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("InvalidRequest")) {
                throw new InvalidRequest(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("InvalidSystemMetadata")) {
                throw new InvalidSystemMetadata(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("InvalidToken")) {
                throw new InvalidToken(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("NotAuthorized")) {
                throw new NotAuthorized(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("NotFound")) {
                throw new NotFound(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("NotImplemented")) {
                throw new NotImplemented(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("ServiceFailure")) {
                throw new ServiceFailure(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("UnsupportedMetadataType")) {
                throw new UnsupportedMetadataType(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("UnsupportedType")) {
                throw new UnsupportedType(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("SynchronizationFailed")) {
                throw new SynchronizationFailed(detailCode, description, pid, null);
            } else if (d1ExceptionName.equals("VersionMismatch")) {
                throw new VersionMismatch(detailCode, description, pid, null);
            } else {
                throw new ServiceFailure(detailCode, "status " + code + ": " + description, pid, null);
            }
    	} else {
            throw new ServiceFailure("0000: NON-D1-EXCEPTION", "status: " + code);
        }
    }

    
    public static InputStream filterErrors(InputStream is, boolean isException, String contentType)
            throws AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
            InvalidCredentials, InvalidRequest, InvalidSystemMetadata, InvalidToken,
            NotAuthorized, NotFound, NotImplemented, ServiceFailure,
            UnsupportedMetadataType, UnsupportedType, VersionMismatch,
            IllegalStateException, IOException, SynchronizationFailed {
        if (isException) {
            deserializeAndThrowException(is, contentType, null, null);
        }
        return is;
    }

    /**
     *
     * @param response
     * @throws NotFound
     * @throws InvalidToken
     * @throws ServiceFailure
     * @throws NotAuthorized
     * @throws NotFound
     * @throws IdentifierNotUnique
     * @throws UnsupportedType
     * @throws InsufficientResources
     * @throws InvalidSystemMetadata
     * @throws NotImplemented
     * @throws InvalidCredentials
     * @throws InvalidRequest
     * @throws IOException
     * @throws AuthenticationTimeout
     * @throws UnsupportedMetadataType
     * @throws VersionMismatch
     * @throws HttpException
     */
    public static void deserializeAndThrowException(HttpResponse response)
            throws NotFound, InvalidToken, ServiceFailure, NotAuthorized,
            NotFound, IdentifierNotUnique, UnsupportedType,
            InsufficientResources, InvalidSystemMetadata, NotImplemented,
            InvalidCredentials, InvalidRequest, IOException, AuthenticationTimeout,
            UnsupportedMetadataType, VersionMismatch,
            HttpException, SynchronizationFailed {

        // use content-type to determine what format the response is
        Header[] h = response.getHeaders("content-type");
        String contentType = "unset";
        if (h.length == 1) {
            contentType = h[0].getValue();
        } else if (h.length > 1) {
        	EntityUtils.consume(response.getEntity());
            throw new IOException("Should not get more than one content-type returned");
        }

        Integer statusCode = null;
        String statusReason = null;
        InputStream responseStream = null;
        try {
            statusCode = new Integer(response.getStatusLine().getStatusCode());
            statusReason = response.getStatusLine().getReasonPhrase();
            if (response.getEntity() != null)
            	responseStream = response.getEntity().getContent();
        } catch (RuntimeException re) {
        }
        deserializeAndThrowException(responseStream, contentType, statusCode, statusReason);
    }

    /**
     * Throw the exception that is contained in an HTTP response
     *
     * A response from an HTTP call may be determined to have an error.
     * The reponse is wrapped in an InputStream and its content type is determined.
     *
     * @param errorStream
     * @param  contentType
     * @throws NotFound
     * @throws InvalidToken
     * @throws ServiceFailure
     * @throws NotAuthorized
     * @throws NotFound
     * @throws IdentifierNotUnique
     * @throws UnsupportedType
     * @throws InsufficientResources
     * @throws InvalidSystemMetadata
     * @throws NotImplemented
     * @throws InvalidCredentials
     * @throws InvalidRequest
     * @throws IOException
     * @throws AuthenticationTimeout
     * @throws UnsupportedMetadataType
     * @throws UnsupportedQueryType
     * @throws VersionMismatch
     * @throws HttpException
     */
    public static void deserializeAndThrowException(InputStream errorStream, String contentType, Integer statusCode, String reason)
    throws
    AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
    InvalidCredentials, InvalidRequest, InvalidSystemMetadata,
    InvalidToken, NotAuthorized, NotFound, NotImplemented, ServiceFailure,
    UnsupportedMetadataType, UnsupportedType, 
    SynchronizationFailed, VersionMismatch
    {
        String defaultMessage = "";
        if (contentType == null) {
            contentType = "unset";
        }
        if (statusCode != null) {
            defaultMessage = String.valueOf(statusCode) + ": ";
        }
        if (reason != null) {
            defaultMessage = defaultMessage + reason + ": ";
        }
        if (contentType.contains("xml")) {
            try {
                deserializeXmlAndThrowException(errorStream, defaultMessage);
            } catch (SAXException e) {
                deserializeAndThrowServiceFailure(errorStream, e, defaultMessage);
            } catch (IOException e) {
                deserializeAndThrowServiceFailure(errorStream, e, defaultMessage);
            } catch (ParserConfigurationException e) {
                deserializeAndThrowServiceFailure(errorStream, e, defaultMessage);
            }
        } else if (contentType.contains("html")) {
            deserializeHtmlAndThrowException(errorStream, defaultMessage);
        } else if (contentType.contains("json")) {
            deserializeJsonAndThrowException(errorStream, defaultMessage);
        } else if (contentType.contains("csv")) {
            deserializeCsvAndThrowException(errorStream, defaultMessage);
        } else if (contentType.contains("text/plain")) {
            deserializeTextPlainAndThrowException(errorStream, defaultMessage);
        } else if (contentType.equals("unset")) {
            deserializeTextPlainAndThrowException(errorStream, defaultMessage);
        } else {
            // attempt the default...
            deserializeTextPlainAndThrowException(errorStream, defaultMessage);
        }
    }

    /*
     * Default code is the XML is unreadable for some reason
     *
     * @param errorStream
     * @param e
     * @throws ServiceFailure
     *
     */
    private static void deserializeAndThrowServiceFailure(InputStream errorStream, Exception e, String defaultMessage) 
    throws ServiceFailure 
    {
        TreeMap<String, String> stackTrace = new TreeMap<String, String>();
        StackTraceElement stackTraceElements[] = e.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; ++i) {
            stackTrace.put(String.valueOf(stackTraceElements[i].getLineNumber()), stackTraceElements[i].toString());
        }
        try {
            throw new ServiceFailure("-1", defaultMessage + e.getMessage() + "\n" + IOUtils.toString(errorStream), "", stackTrace);
        } catch (IOException e1) {
            throw new ServiceFailure("-1", defaultMessage + "errorStream could not be reset/reread" + e1.getMessage(), "", stackTrace);
        }

    }
    /*
     * repackage the HTML error as a ServiceFailure with bogus detailCode
     *
     * @param errorStream
     * @throws ServiceFailure
     *
     */

    private static void deserializeHtmlAndThrowException(InputStream errorStream, String defaultMessage) 
    throws ServiceFailure {
        try {
            throw new ServiceFailure("-1", defaultMessage + "parser for deserializing HTML not written yet.  Providing message body:\n" + IOUtils.toString(errorStream));
        } catch (IOException e1) {
            throw new ServiceFailure("-1", defaultMessage + "errorStream could not be reset/reread" + e1.getMessage());
        }
    }
    /*
     * repackage the Json error as a ServiceFailure with bogus detailCode
     *
     * @param errorStream
     * @throws ServiceFailure
     *
     */

    private static void deserializeJsonAndThrowException(InputStream errorStream, String defaultMessage) 
    throws ServiceFailure {
        try {
            throw new ServiceFailure("-1", defaultMessage + "parser for deserializing JSON not written yet.  Providing message body:\n" + IOUtils.toString(errorStream));
        } catch (IOException e1) {
            throw new ServiceFailure("-1", defaultMessage + "errorStream could not be reset/reread" + e1.getMessage());
        }
    }
    /*
     * repackage the Csv error as a ServiceFailure with bogus detailCode
     *
     * @param errorStream
     * @throws ServiceFailure
     *
     */

    private static void deserializeCsvAndThrowException(InputStream errorStream, String defaultMessage) 
    throws ServiceFailure {
        try {
            throw new ServiceFailure("-1", defaultMessage + "parser for deserializing CSV not written yet.  Providing message body:\n" + IOUtils.toString(errorStream));
        } catch (IOException e1) {
            throw new ServiceFailure("-1", defaultMessage + "errorStream could not be reset/reread" + e1.getMessage());
        }
    }
    /*
     * repackage the TextPlain error as a ServiceFailure with bogus detailCode
     *
     * @param errorStream
     * @throws ServiceFailure
     *
     */

    private static void deserializeTextPlainAndThrowException(InputStream errorStream, String defaultMessage) 
    throws ServiceFailure {
        try {
            throw new ServiceFailure("-1", defaultMessage + "Deserializing Text/Plain: Just providing message body:\n" + IOUtils.toString(errorStream) + "\n{EndOfMessage}");
        } catch (IOException e1) {
            throw new ServiceFailure("-1", defaultMessage + "errorStream could not be reset/reread" + e1.getMessage());
        }
    }

    /*
     * Throw the exception returned by the deserializeXml call
     * @param errorStream
     * @throws NotFound
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     * @throws InvalidToken
     * @throws ServiceFailure
     * @throws NotAuthorized
     * @throws NotFound
     * @throws IdentifierNotUnique
     * @throws UnsupportedType
     * @throws InsufficientResources
     * @throws InvalidSystemMetadata
     * @throws NotImplemented
     * @throws InvalidCredentials
     * @throws InvalidRequest
     * @throws IOException
     * @throws AuthenticationTimeout
     * @throws UnsupportedMetadataType
     * @throws HttpException
     * @throws VersionMismatch
     */
    private static void deserializeXmlAndThrowException(InputStream errorStream, String defaultMessage)
            throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
            InvalidCredentials, InvalidRequest, InvalidSystemMetadata,
            InvalidToken, NotAuthorized, NotFound, NotImplemented, ServiceFailure,
            UnsupportedMetadataType, UnsupportedType, 
            SynchronizationFailed, VersionMismatch 
    {
        BaseException be = deserializeXml(errorStream, defaultMessage);
        if (be instanceof AuthenticationTimeout) {
        	throw (AuthenticationTimeout) be;
        } else if (be instanceof IdentifierNotUnique) {
        	throw (IdentifierNotUnique) be;
        } else if (be instanceof InsufficientResources) {
        	throw (InsufficientResources) be;
        } else if (be instanceof InvalidCredentials) {
        	throw (InvalidCredentials) be;
        } else if (be instanceof InvalidRequest) {
        	throw (InvalidRequest) be;
        } else if (be instanceof InvalidSystemMetadata) {
        	throw (InvalidSystemMetadata) be;
        } else if (be instanceof InvalidToken) {
        	throw (InvalidToken) be;
        } else if (be instanceof NotAuthorized) {
        	throw (NotAuthorized) be;
        } else if (be instanceof NotFound) {
        	throw (NotFound) be;
        } else if (be instanceof NotImplemented) {
        	throw (NotImplemented) be;
        } else if (be instanceof ServiceFailure) {
        	throw (ServiceFailure) be;
        } else if (be instanceof UnsupportedMetadataType) {
        	throw (UnsupportedMetadataType) be;
        } else if (be instanceof UnsupportedType) {
        	throw (UnsupportedType) be;
        } else if (be instanceof SynchronizationFailed) {
        	throw (SynchronizationFailed) be;
        } else if (be instanceof VersionMismatch) {
        	throw (VersionMismatch) be;
        }
    }
    /*
     * deserialize the xml of an errorStream and return the DataONE exception
     * @param errorStream
     * @return <T>T
     *      An Exception of the following type: NotFound,
     *                                          ParserConfigurationException,
     *                                          SAXException
     *                                          IOException
     *                                          InvalidToken
     *                                          ServiceFailure
     *                                          NotAuthorized
     *                                          NotFound
     *                                          IdentifierNotUnique
     *                                          UnsupportedType
     *                                          InsufficientResources
     *                                          InvalidSystemMetadata
     *                                          NotImplemented
     *                                          InvalidCredentials
     *                                          InvalidRequest
     *                                          IOException
     *                                          AuthenticationTimeout
     *                                          UnsupportedMetadataType
     *                                          VersionMismatch
     *                                          HttpException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */

    public static <T> BaseException deserializeXml(InputStream errorStream, String defaultMessage) 
    throws ParserConfigurationException, SAXException, IOException 
    {
        TreeMap<String, String> trace_information = new TreeMap<String, String>();
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        Document doc;
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(errorStream);
        Element root = doc.getDocumentElement();
        root.normalize();
        String detailCode = "-1";
        String name = "";
        String pid = null;
        String nodeId = null;
        if (root.hasAttribute("detailCode")) {
            detailCode = root.getAttribute("detailCode");
        }
        if (root.hasAttribute("name")) {
            name = root.getAttribute("name");
        }
        if (root.hasAttribute("pid")) {
            pid = root.getAttribute("pid");
        }
        if (root.hasAttribute("nodeId")) {
        	nodeId = root.getAttribute("nodeId");
        }
        getTraceValue(root, trace_information);
        String description = getDescriptionValue(root);
        // pid can be null if not known or supplied and BaseException works right
        // detailCode should be -1 if not known or supplied
        // description should be empty string if not known or not supplied
        // traceInformation should be empty if not known or not supplied
        if (!name.isEmpty()) {
            if (name.equals("AuthenticationTimeout")) {
                return new AuthenticationTimeout(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("IdentifierNotUnique")) {
                return new IdentifierNotUnique(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("InsufficientResources")) {
                return new InsufficientResources(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("InvalidCredentials")) {
                return new InvalidCredentials(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("InvalidRequest")) {
                return new InvalidRequest(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("InvalidSystemMetadata")) {
                return new InvalidSystemMetadata(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("InvalidToken")) {
                return new InvalidToken(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("NotAuthorized")) {
                return new NotAuthorized(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("NotFound")) {
                return new NotFound(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("NotImplemented")) {
                return new NotImplemented(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("ServiceFailure")) {
                return new ServiceFailure(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("UnsupportedMetadataType")) {
                return new UnsupportedMetadataType(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("UnsupportedType")) {
                return new UnsupportedType(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("SynchronizationFailed")) {
                return new SynchronizationFailed(detailCode, description, pid, nodeId, trace_information);
            } else if (name.equals("VersionMismatch")) {
                return new VersionMismatch(detailCode, description, pid, nodeId, trace_information);
            } else {
                return new ServiceFailure(detailCode, defaultMessage + description, pid, nodeId, trace_information);
            }
        } else {
            return new ServiceFailure(detailCode, defaultMessage + description, pid, nodeId, trace_information);
        }
    }

    /**
     * Find the description xml element , return the text content of the child
     * element.
     * @param e
     * @return String
     */
    private static String getDescriptionValue(Element e) {
        String text = "";
        NodeList nl = e.getElementsByTagName("description");
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            if (el.hasChildNodes() && ((el.getFirstChild().getNodeType() == Element.TEXT_NODE)
                    || el.getFirstChild().getNodeType() == Element.CDATA_SECTION_NODE)) {
                text = el.getFirstChild().getNodeValue();
            }
        }

        return text;
    }
    /*
     * populate the provided Trace Information with the trace information in the XML
     * @param  e
     * @param trace_information
     */

    private static void getTraceValue(Element e, TreeMap<String, String> trace_information) {
        String text = "";
        NodeList nl = e.getElementsByTagName("traceInformation");
        if (nl != null && nl.getLength() > 0) {
            Element el = (Element) nl.item(0);
            if (el.hasChildNodes()) {
                NodeList traceValues = el.getChildNodes();
                if (el.hasChildNodes() && ((el.getFirstChild().getNodeType() == Element.TEXT_NODE)
                        || el.getFirstChild().getNodeType() == Element.CDATA_SECTION_NODE)) {
                    trace_information.put(el.getAttribute("key"), el.getFirstChild().getNodeValue());
                }
            }
        }
    }

    /**
     * return an xml attribute as String
     * @param e
     * @param attName
     * @return String
     */
    private static String getTextAttribute(Element e, String attName) {
        if (e.hasAttribute(attName)) {
            String attText = e.getAttribute(attName);
            return attText;
        } else {
            return "";
        }
    }

    /**
     * Wrapper for Marshalling method that recasts exceptions
     * to ServiceFailure;
     *
     * @param type
     *            the class of the object to serialize (i.e.
     *            SystemMetadata.class)
     * @param is
     *            the stream to deserialize from
     * @throws ServiceFailure
     */
    @SuppressWarnings("rawtypes")
    protected static <T> T deserializeServiceType(Class<T> domainClass, InputStream is)
    throws ServiceFailure 
    {
        try {
            return TypeMarshaller.unmarshalTypeFromStream(domainClass, is);
        } catch (JiBXException e) {
            throw new ServiceFailure("0",
                    "Could not deserialize the " + domainClass.getCanonicalName() + ": " + e.getMessage());
        } catch (IOException e) {
            throw new ServiceFailure("0",
                    "Could not deserialize the " + domainClass.getCanonicalName() + ": " + e.getMessage());
        } catch (InstantiationException e) {
            throw new ServiceFailure("0",
                    "Could not deserialize the " + domainClass.getCanonicalName() + ": " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new ServiceFailure("0",
                    "Could not deserialize the " + domainClass.getCanonicalName() + ": " + e.getMessage());
        }
    } 
}
