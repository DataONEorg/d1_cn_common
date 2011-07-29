/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dataone.service.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
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
import org.dataone.service.exceptions.AuthenticationTimeout;
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
import org.dataone.service.exceptions.UnsupportedMetadataType;
import org.dataone.service.exceptions.UnsupportedType;
import org.dataone.service.exceptions.SynchronizationFailed;
import org.dataone.service.exceptions.UnsupportedQueryType;
import org.jibx.runtime.JiBXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * uniform deserialization of DataONE Exceptions that may be rethrown
 *
 * @author waltz
 */
public class ExceptionHandler {
	protected static Log log = LogFactory.getLog(ExceptionHandler.class);


	public static InputStream filterErrors(HttpResponse res)
	throws AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
	InvalidCredentials, InvalidRequest, InvalidSystemMetadata, InvalidToken,
	NotAuthorized, NotFound, NotImplemented, ServiceFailure,
	UnsupportedMetadataType, UnsupportedQueryType, UnsupportedType,
	IllegalStateException, IOException, HttpException, SynchronizationFailed, Throwable
	{
		int code = res.getStatusLine().getStatusCode();
		log.info("response httpCode: " + code);
		log.debug(IOUtils.toString(res.getEntity().getContent()));
		if (code != HttpURLConnection.HTTP_OK) {
			// error, so throw exception
			deserializeAndThrowException(res);
		}
		return res.getEntity().getContent();
	}

	public Header[] filterErrorsHeader(HttpResponse res)
	throws AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
	InvalidCredentials, InvalidRequest, InvalidSystemMetadata, InvalidToken,
	NotAuthorized, NotFound, NotImplemented, ServiceFailure,
	UnsupportedMetadataType, UnsupportedQueryType, UnsupportedType,
	IllegalStateException, IOException, HttpException, SynchronizationFailed, Throwable
	{

		int code = res.getStatusLine().getStatusCode();
		log.info("response httpCode: " + code);
		if (code != HttpURLConnection.HTTP_OK) {
			// error, so throw exception
			deserializeAndThrowException(res);
		}
		return res.getAllHeaders();
	}



	public static InputStream filterErrors(InputStream is, boolean isException, String contentType)
	throws AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
	InvalidCredentials, InvalidRequest, InvalidSystemMetadata, InvalidToken,
	NotAuthorized, NotFound, NotImplemented, ServiceFailure,
	UnsupportedMetadataType, UnsupportedQueryType, UnsupportedType,
	IllegalStateException, IOException, SynchronizationFailed, Throwable
	{
		if (isException) {
			deserializeAndThrowException(is, contentType);
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
	 * @throws HttpException
	 */

	public static void deserializeAndThrowException(HttpResponse response)
	throws NotFound, InvalidToken, ServiceFailure, NotAuthorized,
	NotFound, IdentifierNotUnique, UnsupportedType,
	InsufficientResources, InvalidSystemMetadata, NotImplemented,
	InvalidCredentials, InvalidRequest, IOException, AuthenticationTimeout, 
        UnsupportedMetadataType, UnsupportedQueryType, HttpException, SynchronizationFailed, Throwable {

		// use content-type to determine what format the response is
    	Header[] h = response.getHeaders("content-type");
    	String contentType = "unset";
    	if (h.length == 1)
    		contentType = h[0].getValue();
    	else if (h.length > 1)
    		throw new IOException("Should not get more than one content-type returned");

    	Integer statusCode = null;
    	String statusReason = null;
    	InputStream responseStream = null;
    	try {
    		statusCode = new Integer(response.getStatusLine().getStatusCode());
    		statusReason = response.getStatusLine().getReasonPhrase();
    		responseStream = response.getEntity().getContent();
    	} catch (RuntimeException re) {
    	}
    	deserializeAndThrowException(responseStream, contentType);
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
     * @throws HttpException
     */
    public static void deserializeAndThrowException(InputStream errorStream, String contentType)
            throws
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
            InvalidCredentials, InvalidRequest, InvalidSystemMetadata,
            InvalidToken, NotAuthorized, NotFound, NotImplemented, ServiceFailure,
            UnsupportedMetadataType, UnsupportedQueryType, UnsupportedType, SynchronizationFailed, Throwable
            {

        if (contentType == null) {
            contentType = "unset";
        }

        if (contentType.contains("xml")) {
            try {
                deserializeXmlAndThrowException(errorStream);
            } catch (SAXException e) {
                deserializeAndThrowServiceFailure(errorStream, e);
            } catch (IOException e) {
                deserializeAndThrowServiceFailure(errorStream, e);
            } catch (ParserConfigurationException e) {
                deserializeAndThrowServiceFailure(errorStream, e);
            }
        } else if (contentType.contains("html")) {
            deserializeHtmlAndThrowException(errorStream);
        } else if (contentType.contains("json")) {
            deserializeJsonAndThrowException(errorStream);
        } else if (contentType.contains("csv")) {
            deserializeCsvAndThrowException(errorStream);
        } else if (contentType.contains("text/plain")) {
            deserializeTextPlainAndThrowException(errorStream);
        } else if (contentType.equals("unset")) {
            deserializeTextPlainAndThrowException(errorStream);
        } else {
            // attempt the default...
            deserializeTextPlainAndThrowException(errorStream);
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
    private static void deserializeAndThrowServiceFailure(InputStream errorStream, Exception e) throws ServiceFailure {
        TreeMap<String, String> stackTrace = new TreeMap<String, String>();
        StackTraceElement stackTraceElements[] = e.getStackTrace();
        for (int i = 0; i < stackTraceElements.length; ++i) {
            stackTrace.put(String.valueOf(stackTraceElements[i].getLineNumber()), stackTraceElements[i].toString());
        }
        try {
            throw new ServiceFailure("-1", e.getMessage() + "\n" + IOUtils.toString(errorStream), "", stackTrace);
        } catch (IOException e1) {
            throw new ServiceFailure("-1", "errorStream could not be reset/reread" + e1.getMessage(), "", stackTrace);
        }

    }
    /*
     * repackage the HTML error as a ServiceFailure with bogus detailCode
     *
     * @param errorStream
     * @throws ServiceFailure
     *
     */

    private static void deserializeHtmlAndThrowException(InputStream errorStream) throws ServiceFailure {
        try {
            throw new ServiceFailure("-1", "parser for deserializing HTML not written yet.  Providing message body:\n" + IOUtils.toString(errorStream));
        } catch (IOException e1) {
            throw new ServiceFailure("-1", "errorStream could not be reset/reread" + e1.getMessage());
        }
    }
    /*
     * repackage the Json error as a ServiceFailure with bogus detailCode
     *
     * @param errorStream
     * @throws ServiceFailure
     *
     */

    private static void deserializeJsonAndThrowException(InputStream errorStream) throws ServiceFailure {
        try {
            throw new ServiceFailure("-1", "parser for deserializing JSON not written yet.  Providing message body:\n" + IOUtils.toString(errorStream));
        } catch (IOException e1) {
            throw new ServiceFailure("-1", "errorStream could not be reset/reread" + e1.getMessage());
        }
    }
    /*
     * repackage the Csv error as a ServiceFailure with bogus detailCode
     *
     * @param errorStream
     * @throws ServiceFailure
     *
     */

    private static void deserializeCsvAndThrowException(InputStream errorStream) throws ServiceFailure {
        try {
            throw new ServiceFailure("-1", "parser for deserializing CSV not written yet.  Providing message body:\n" + IOUtils.toString(errorStream));
        } catch (IOException e1) {
            throw new ServiceFailure("-1", "errorStream could not be reset/reread" + e1.getMessage());
        }
    }
    /*
     * repackage the TextPlain error as a ServiceFailure with bogus detailCode
     *
     * @param errorStream
     * @throws ServiceFailure
     *
     */

    private static void deserializeTextPlainAndThrowException(InputStream errorStream) throws ServiceFailure {
        try {
            throw new ServiceFailure("-1", "Deserializing Text/Plain: Just providing message body:\n" + IOUtils.toString(errorStream) + "\n{EndOfMessage}");
        } catch (IOException e1) {
            throw new ServiceFailure("-1", "errorStream could not be reset/reread" + e1.getMessage());
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
     * @throws Throwable
     */
    private static void deserializeXmlAndThrowException(InputStream errorStream)
            throws ParserConfigurationException, SAXException, IOException,
            AuthenticationTimeout, IdentifierNotUnique, InsufficientResources,
            InvalidCredentials, InvalidRequest, InvalidSystemMetadata,
            InvalidToken, NotAuthorized, NotFound, NotImplemented, ServiceFailure,
            UnsupportedMetadataType, UnsupportedQueryType, UnsupportedType, SynchronizationFailed,
            Throwable {
        throw deserializeXml(errorStream);
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
     *                                          UnsupportedQueryType
     *                                          HttpException
     * @throws ParserConfigurationException
     * @throws SAXException
     * @throws IOException
     */

    public static <T>Throwable deserializeXml(InputStream errorStream) throws ParserConfigurationException, SAXException, IOException {

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
            pid = root.getAttribute("nodeId");
        }
        getTraceValue(root, trace_information);
        String description = getDescriptionValue(root);
        // pid can be null if not known or supplied and BaseException works right
        // detailCode should be -1 if not known or supplied
        // description should be empty string if not known or not supplied
        // traceInformation should be empty if not known or not supplied
        if (!name.isEmpty()) {
            if (name.equals("AuthenticationTimeout")) {
                return  new AuthenticationTimeout(detailCode, description, pid, trace_information);
            } else if (name.equals("IdentifierNotUnique")) {
                return  new IdentifierNotUnique(detailCode, description, pid, trace_information);
            } else if (name.equals("InsufficientResources")) {
                return  new InsufficientResources(detailCode, description, pid, trace_information);
            } else if (name.equals("InvalidCredentials")) {
                return  new InvalidCredentials(detailCode, description, pid, trace_information);
            } else if (name.equals("InvalidRequest")) {
                return  new InvalidRequest(detailCode, description, pid, trace_information);
            } else if (name.equals("InvalidSystemMetadata")) {
                return  new InvalidSystemMetadata(detailCode, description, pid, trace_information);
            } else if (name.equals("InvalidToken")) {
                return  new InvalidToken(detailCode, description, pid, trace_information);
            } else if (name.equals("NotAuthorized")) {
                return  new NotAuthorized(detailCode, description, pid, trace_information);
            } else if (name.equals("NotFound")) {
                return  new NotFound(detailCode, description, pid, trace_information);
            } else if (name.equals("NotImplemented")) {
                return  new NotImplemented(detailCode, description, pid, trace_information);
            } else if (name.equals("ServiceFailure")) {
                return  new ServiceFailure(detailCode, description, pid, trace_information);
            } else if (name.equals("UnsupportedMetadataType")) {
                return  new UnsupportedMetadataType(detailCode, description, pid, trace_information);
            } else if (name.equals("UnsupportedQueryType")) {
                return  new UnsupportedQueryType(detailCode, description, pid, trace_information);
            } else if (name.equals("UnsupportedType")) {
                return  new UnsupportedType(detailCode, description, pid, trace_information);
            } else if (name.equals("SynchronizationFailed")) {
                return  new SynchronizationFailed(detailCode, description, pid, trace_information);
            } else {
                return  new ServiceFailure(detailCode, description, pid, trace_information);
            }
        } else {
            return  new ServiceFailure(detailCode, description, pid, trace_information);
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
            throws ServiceFailure {
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
