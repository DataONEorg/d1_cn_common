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

package org.dataone.service.types.v1.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dataone.service.util.TypeMarshaller;
import org.dataone.service.exceptions.InsufficientResources;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.v1.ObjectFormat;
import org.dataone.service.types.v1.ObjectFormatIdentifier;
import org.dataone.service.types.v1.ObjectFormatList;
import org.jibx.runtime.JiBXException;

public class ObjectFormatServiceImpl {

  /* The instance of the logging class */
  private Logger logger;
  
  /* The instance of the object format service */
  private static ObjectFormatServiceImpl instance = null;
  
  /* The list of object formats */
  protected ObjectFormatList objectFormatList = null;
  
  /* the searchable map of object formats */
  protected HashMap<ObjectFormatIdentifier, ObjectFormat> objectFormatMap;
  
  /* the package path of the cached object format list*/ 
  private String objectFormatFilePath = "/org/dataone/service/resources/" +
                                        "config/v1/objectFormatList.xml";

  /*
   * Constructor: Creates an instance of the object format service. Since
   * this uses a singleton pattern, use getInstance() to gain the instance.
   */
  protected ObjectFormatServiceImpl() throws ServiceFailure {
    
  	logger = Logger.getLogger(ObjectFormatServiceImpl.class);
  	
  	objectFormatMap = new HashMap<ObjectFormatIdentifier, ObjectFormat>();
    try {
    	refreshCache();
      
    } catch (ServiceFailure se) {
      logger.debug("There was a problem creating the ObjectFormatServiceImpl. " +
                       "The error message was: " + se.getMessage());
      throw se;
    }  
  }

  /**
   *  Get the instance of the ObjectFormatServiceImpl that has been instantiated,
   *  or instantiate one if it has not been already.
   *
   * @return ObjectFormatServiceImpl - The instance of the object format service
   * @throws ServiceFailure 
   */
  public static synchronized ObjectFormatServiceImpl getInstance() throws ServiceFailure {
    
    if ( instance == null ) {
      instance = new ObjectFormatServiceImpl();
      
    }
    return instance;
    
  }
  
  /**
   * List the object formats registered with the object format service.
   * 
   * @return objectFormatList - the list of object formats
   */
	public ObjectFormatList listFormats() {
		return objectFormatList;
  }

  /**
   * Get the object format based on the given identifier.
   * 
   * @param formatId - the object format identifier
   * @return objectFormat - the ObjectFormat represented by the format identifier
   * @throws NotFound
   * @throws NotImplemented - not thrown by this base class, in signature to allow subclasses to override
   * @throws ServiceFailure - not thrown by this base class, in signature to allow subclasses to override
   */
	public ObjectFormat getFormat(ObjectFormatIdentifier formatId)
	throws NotFound, ServiceFailure, NotImplemented {
		
		ObjectFormat objectFormat = getObjectFormatMap().get(formatId);

		if ( objectFormat == null ) {

			throw new NotFound("0000", "The format specified by '" + formatId.getValue() + 
			"' does not exist at this node.");
		}
		return objectFormat;
	}

	
  /**
   * Get the object format list from the cached file on disk
   * 
   * @throws ServiceFailure - if any trouble creating the cache (logs error, too)
   */
  private void refreshCache()
    throws ServiceFailure {
            
    // get the object format list from disk and parse it
    try {
    	InputStream inputStream = getObjectFormatFile();

    	objectFormatList = 
    		TypeMarshaller.unmarshalTypeFromStream(ObjectFormatList.class, inputStream);

    	// index the object format list by the format identifier
    	for (ObjectFormat objectFormat : objectFormatList.getObjectFormatList())
    	{
    		getObjectFormatMap().put(objectFormat.getFormatId(), objectFormat);
    	}

    } catch (JiBXException jibxe) {
    	String message = "The object format list could not be deserialized. " +
    	"The error message was: " + jibxe.getMessage();
    	logger.error(message);
    	throw new ServiceFailure("0000", message);

    } catch (IOException ioe) {
    	String message = "The object format list could not be read. " +
    	"The error message was: " + ioe.getMessage();
    	logger.error(message);
    	throw new ServiceFailure("0000", message);

    } catch (InstantiationException ie) {
    	String message = "The object format list could not be instantiated. " +
    	"The error message was: " + ie.getMessage();
    	logger.error(message);
    	throw new ServiceFailure("0000", message);

    } catch (IllegalAccessException iae) {
    	String message = "The object format list could not be accessed. " +
    	"The error message was: " + iae.getMessage();
    	logger.error(message);
    	throw new ServiceFailure("0000", message);

    }   
    return; 
  }

  
  /**
   * Convenience method to retrieve the default format list as a stream
   * @return inputstream of the objectFormatList file contents
   */
  public InputStream getObjectFormatFile() {
	  InputStream inputStream = 
	    	this.getClass().getResourceAsStream(objectFormatFilePath);
	  return inputStream;
  }
 
  
  /**
   * Return the hash containing the formatId and format mapping
   * 
   * @return objectFormatMap - the hash of formatiId/format pairs
   */
  protected HashMap<ObjectFormatIdentifier, ObjectFormat> getObjectFormatMap() {
  	
  	return objectFormatMap;  	
  }


}
