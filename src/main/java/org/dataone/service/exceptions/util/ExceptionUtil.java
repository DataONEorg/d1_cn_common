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

package org.dataone.service.exceptions.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.rmi.ServerException;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.dataone.mimemultipart.SimpleMultipartEntity;
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
import org.dataone.service.exceptions.UnsupportedQueryType;
import org.dataone.service.exceptions.UnsupportedType;
import org.dataone.service.types.Identifier;
import org.dataone.service.types.ObjectList;
import org.dataone.service.types.SystemMetadata;
import org.dataone.service.util.TypeMarshaller;
import org.jibx.runtime.BindingDirectory;
import org.jibx.runtime.IBindingFactory;
import org.jibx.runtime.IMarshallingContext;
import org.jibx.runtime.IUnmarshallingContext;
import org.jibx.runtime.JiBXException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * This class wraps the RestClient, adding uniform exception deserialization
 * (subclassing the RestClient was impractical due to differences in method signatures)
 */
public class ExceptionUtil {
	
	protected static Log log = LogFactory.getLog(ExceptionUtil.class);


	public static InputStream filterErrors(HttpResponse res) 
	throws AuthenticationTimeout, IdentifierNotUnique, InsufficientResources, 
	InvalidCredentials, InvalidRequest, InvalidSystemMetadata, InvalidToken, 
	NotAuthorized, NotFound, NotImplemented, ServiceFailure, 
	UnsupportedMetadataType, UnsupportedQueryType, UnsupportedType,
	IllegalStateException, IOException, HttpException 
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
	IllegalStateException, IOException, HttpException 
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
	IllegalStateException, IOException 
	{
		if (isException) {
			deserializeAndThrowException(is,null,null,contentType);
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
	InvalidCredentials, InvalidRequest, IOException, AuthenticationTimeout, UnsupportedMetadataType, HttpException {

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
    	deserializeAndThrowException(responseStream, statusCode, statusReason, contentType);
	}
	
	
	public static void deserializeAndThrowException(InputStream is, Integer statusCode, String reason, String contentType)
    throws NotFound, InvalidToken, ServiceFailure, NotAuthorized,
	NotFound, IdentifierNotUnique, UnsupportedType,
	InsufficientResources, InvalidSystemMetadata, NotImplemented,
	InvalidCredentials, InvalidRequest, IOException, AuthenticationTimeout, UnsupportedMetadataType {
    	
  
    	if (contentType == null)  contentType = "unset";   	
    	if (statusCode == null)  statusCode = new Integer(-1);
    	
    	ErrorElements ee = null;
    	if (contentType.contains("xml"))
    		ee = deserializeXml(is, statusCode);
    	else if (contentType.contains("html"))
    		ee = deserializeHtml(is, statusCode);
    	else if (contentType.contains("json"))
    		ee = deserializeJson(is, statusCode);
       	else if (contentType.contains("csv"))
    		ee = deserializeCsv(is, statusCode);
      	else if (contentType.contains("text/plain"))
    		ee = deserializeTextPlain(is, statusCode);
     	else if (contentType.equals("unset"))
    		ee = deserializeTextPlain(is, statusCode);
    	else 
    		// attempt the default...
    		ee = deserializeTextPlain(is, statusCode);
    	
 
    	String exceptionName = ee.getName();
    	if (exceptionName == null) 
    		throw new ServerException(reason + ": " + ee.getDescription());
 //   		throw new HttpException(reason + ": " + ee.getDescription());
    	
    	
		// last updated 27-Jan-2011
       	if (ee.getName().equals("AuthenticationTimeout"))
    		throw new AuthenticationTimeout(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);  
       	else if (ee.getName().equals("IdentifierNotUnique"))
    		throw new IdentifierNotUnique(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
    	else if (ee.getName().equals("InsufficientResources"))
    		throw new InsufficientResources(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
       	else if (ee.getName().equals("InvalidCredentials"))
    		throw new InvalidCredentials(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
      	else if (ee.getName().equals("InvalidRequest"))
    		throw new InvalidRequest(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
    	else if (exceptionName.equals("InvalidSystemMetadata"))
    		throw new InvalidSystemMetadata("1180", ee.getDescription(), ee.getPid(), null);
    	else if (ee.getName().equals("InvalidToken"))
    		throw new InvalidToken(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null); 
    	else if (ee.getName().equals("NotAuthorized"))
    		throw new NotAuthorized(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
    	else if (ee.getName().equals("NotFound"))
    		throw new NotFound(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
    	else if (ee.getName().equals("NotImplemented"))
    		throw new NotImplemented(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
       	else if (ee.getName().equals("ServiceFailure"))
    		throw new ServiceFailure(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
    	else if (ee.getName().equals("UnsupportedMetadataType"))
    		throw new UnsupportedMetadataType(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
       	else if (ee.getName().equals("UnsupportedType"))
    		throw new UnsupportedType(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null); 
    	else 
    		throw new ServiceFailure(ee.getDetailCode(), ee.getDescription(), ee.getPid(), null);
    }
    	

    	
    private static ErrorElements deserializeHtml(InputStream responseStream, int statusCode) 
    throws IllegalStateException, IOException 
    {
    	ErrorElements ee = new ErrorElements();
    	ee.setCode(statusCode);

    	String body = IOUtils.toString(responseStream);
   		ee.setDescription("parser for deserializing HTML not written yet.  Providing message body:\n" + body);
    	
    	return ee;
    }

    
    private static ErrorElements deserializeJson(InputStream responseStream, int statusCode)
    throws IllegalStateException, IOException 
    {
    	ErrorElements ee = new ErrorElements();
    	ee.setCode(statusCode);

    	String body = IOUtils.toString(responseStream);
   		ee.setDescription("parser for deserializing JSON not written yet.  Providing message body:\n" + body);
    
    	return ee;
    }
  
    
    private static ErrorElements deserializeCsv(InputStream responseStream, int statusCode)
    throws IllegalStateException, IOException 
    {
    	ErrorElements ee = new ErrorElements();
    	ee.setCode(statusCode);

    	String body = IOUtils.toString(responseStream);
   		ee.setDescription("parser for deserializing CSV not written yet.  Providing message body:\n" + body);

   		return ee;
    }

    
    private static ErrorElements deserializeTextPlain(InputStream responseStream, int statusCode) 
    throws IllegalStateException, IOException 
    {  	   	
    	ErrorElements ee = new ErrorElements();
    	ee.setCode(statusCode);

    	String body = IOUtils.toString(responseStream);
    	ee.setDescription("Deserializing Text/Plain: Just providing message body:\n" + body + "\n{EndOfMessage}");

    	return ee;
    }

    
    private static ErrorElements deserializeXml(InputStream responseStream, int statusCode) 
    throws IllegalStateException, IOException
    {
    	ErrorElements ee = new ErrorElements();
    	ee.setCode(statusCode);
    	
    	DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
    	Document doc;

    	
    	BufferedInputStream bErrorStream = new BufferedInputStream(responseStream);
    	bErrorStream.mark(5000);  // good for resetting up to 5000 bytes	

    	String detailCode = null;
    	String description = null;
    	String name = null;
    	String pid = null;
    	String traceInfo = null;

    	int errorCode = -1;
    	try {
    		DocumentBuilder db = dbf.newDocumentBuilder();
    		doc = db.parse(bErrorStream);
    		Element root = doc.getDocumentElement();
    		root.normalize();
    		try {
    			errorCode = getIntAttribute(root, "errorCode");
    		} catch (NumberFormatException nfe){
    			System.out.println("errorCode unexpectedly not able to parse to int," +
    			" using http status for creating exception");
    			errorCode = statusCode;
    		}    		
    		if (errorCode != statusCode)
    			//				throw new ServiceFailure("1000","errorCode in message body doesn't match httpStatus");
    			System.out.println("errorCode in message body doesn't match httpStatus," +
    			" using errorCode for creating exception");

    		if (root.hasAttribute("detailCode")) {
    			detailCode = root.getAttribute("detailCode");
    		}  else {
    			detailCode = "-1";
    		}
    		if (root.hasAttribute("name")) {
    			name = root.getAttribute("name");
    		} else {
    			name = "Unknown";
    		}
    		if (root.hasAttribute("pid")) {
    			pid = root.getAttribute("pid");
    		}
    		traceInfo = getTextValue(root, "traceInformation");
    		description = getTextValue(root, "description");

    	} catch (SAXException e) {
    		description = deserializeNonXMLErrorStream(bErrorStream,e);
    	} catch (IOException e) {
    		description = deserializeNonXMLErrorStream(bErrorStream,e);
    	} catch (ParserConfigurationException e) {
    		description = deserializeNonXMLErrorStream(bErrorStream,e);
    	}

    	ee.setCode(errorCode);
    	ee.setName(name);
    	ee.setDetailCode(detailCode);
    	ee.setDescription(description);
    	ee.setPid(pid);
    	ee.setTraceInformation(traceInfo);

    	return ee;
    }

    /**
     * helper method for deserializeAndThrowException.  Used for problems parsing errorStream as XML
     */
    private static String deserializeNonXMLErrorStream(BufferedInputStream errorStream, Exception e) 
    {
    	String errorString = null;
    	try {
    		errorStream.reset();
    		errorString = e.getMessage() + "\n" + IOUtils.toString(errorStream);
    	} catch (IOException e1) {
    		errorString = "errorStream could not be reset/reread";
    	}
    	return errorString;
    }
    
    
    
	/**
	 * Take a xml element and the tag name, return the text content of the child
	 * element.
	 */
	protected static String getTextValue(Element e, String tag) {
		String text = null;
		NodeList nl = e.getElementsByTagName(tag);
		if (nl != null && nl.getLength() > 0) {
			Element el = (Element) nl.item(0);
                    if (el.hasChildNodes() && ((el.getFirstChild().getNodeType() == Element.TEXT_NODE) ||
                                el.getFirstChild().getNodeType() == Element.CDATA_SECTION_NODE)) {
                        text = el.getFirstChild().getNodeValue();
                    } else {
                        text = "";
                    }
		}

		return text;
	}

	/**
	 * return an xml attribute as an int
	 * @param e
	 * @param attName
	 * @return
	 */
	protected static int getIntAttribute(Element e, String attName)
	throws NumberFormatException {
            if (e.hasAttribute(attName)) {
		String attText = e.getAttribute(attName);
		int x = Integer.parseInt(attText);
		return x;
            } else {
                return -1;
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
	
	
	/**
	 * An internal class used as data structure for storing the parsed out pieces of 
	 * serialized errors in an intermediary format prior to creating the exception.
	 *
	 * @author rnahf
	 *
	 */
	public static class ErrorElements {
		private int code;
		private String name;
		private String detailCode;
		private String description;
		private String pid;
		private String traceInfo;
		
		protected ErrorElements() {
			super();
		}
		
		protected ErrorElements(int statusCode) {
			super();
			setCode(statusCode);
		}
	
		protected int getCode() {
			return code;
		}		
		protected void setCode(int c) {
			this.code = c;
		}

		protected String getName() {
			return name;
		}		
		protected void setName(String n) {
			this.name = n;
		}
		
		protected String getDetailCode() {
			return detailCode;
		}		
		protected void setDetailCode(String dc) {
			this.detailCode = dc;
		}

		protected String getDescription() {
			return this.description;
		}		
		protected void setDescription(String d) {
			this.description = d;
		}

		protected String getPid() {
			return this.pid;
		}		
		protected void setPid(String p) {
			this.pid = p;
		}
		
		protected String getTraceInformation() {
			return this.traceInfo;
		}		
		protected void setTraceInformation(String ti) {
			this.traceInfo = ti;
		}
	}
}
