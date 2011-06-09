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

package org.dataone.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.dataone.cn.batch.utils.TypeMarshaller;
import org.dataone.service.exceptions.InsufficientResources;
import org.dataone.service.exceptions.InvalidRequest;
import org.dataone.service.exceptions.NotFound;
import org.dataone.service.exceptions.NotImplemented;
import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.ObjectFormat;
import org.dataone.service.types.ObjectFormatIdentifier;
import org.dataone.service.types.ObjectFormatList;
import org.jibx.runtime.JiBXException;

public class ObjectFormatServiceImpl {

  /* The instance of the logging class */
  private Logger logger;
  
  /* The instance of the object format service */
  private static ObjectFormatServiceImpl instance = null;
  
  /* The list of object formats */
  private ObjectFormatList objectFormatList = null;
  
  /* the searchable map of object formats */
  private HashMap<String, ObjectFormat> objectFormatMap;
  
  /* the package path of the cached object format list*/ 
  private String objectFormatFilePath = "/org/dataone/service/resources/" +
                                        "config/objectFormatList.xml";

  /*
   * Constructor: Creates an instance of the object format service. Since
   * this uses a singleton pattern, use getInstance() to gain the instance.
   */
  public ObjectFormatServiceImpl() {
    
  	logger = Logger.getLogger(ObjectFormatServiceImpl.class);
  	
    try {
      
    	// create the in-memory list of object formats
      getCachedList();
      
    } catch (ServiceFailure se) {
      logger.debug("There was a problem creating the ObjectFormatServiceImpl. " +
                       "The error message was: " + se.getMessage());
      
    }
    
  }

  /**
   *  Get the instance of the ObjectFormatServiceImpl that has been instantiated,
   *  or instantiate one if it has not been already.
   *
   * @return ObjectFormatServiceImpl - The instance of the object format service
   * @throws ServiceFailure 
   */
  public static synchronized ObjectFormatServiceImpl getInstance() {
    
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
	public ObjectFormatList listFormats() 
	  throws InvalidRequest, ServiceFailure,
	  NotFound, InsufficientResources, NotImplemented {
    
		return objectFormatList;

  }

  /**
   * Get the object format based on the given identifier.
   * 
   * @param fmtid - the object format identifier
   * @return objectFormat - the ObjectFormat represented by the format identifier
   * @throws InvalidRequest 
   * @throws ServiceFailure 
   * @throws NotFound 
   * @throws InsufficientResources 
   * @throws NotImplemented 
   */
	public ObjectFormat getFormat(ObjectFormatIdentifier fmtid)
	  throws InvalidRequest, ServiceFailure, NotFound, InsufficientResources,
	  NotImplemented {
    
		ObjectFormat objectFormat = null;
    String fmtidStr = fmtid.getValue();
    objectFormat = getObjectFormatMap().get(fmtidStr);
    
    if ( objectFormat == null ) {
      
    	throw new NotFound("4848", "The format specified by " + fmtid.getValue() + 
    			               " does not exist at this node.");
    }

    
    return objectFormat;
	}
	
  /**
   * Get the object format list from the cached file on disk
   * 
   * @return objectFormatList - the cached object format list
   */
  private void getCachedList()
    throws ServiceFailure {
            
    // get the object format list from disk and parse it
    try {
      InputStream inputStream = 
    	this.getClass().getResourceAsStream(objectFormatFilePath);
            
    	objectFormatList = 
    		TypeMarshaller.unmarshalTypeFromStream(ObjectFormatList.class, inputStream);
    	
      // index the object format list based on the format identifier
      int listSize = this.objectFormatList.sizeObjectFormats();
      
      for (int i = 0; i < listSize; i++ ) {
        
        ObjectFormat objectFormat = 
          objectFormatList.getObjectFormat(i);
        String identifier = objectFormat.getFmtid().getValue();
        getObjectFormatMap().put(identifier, objectFormat);
        
      }

    } catch (JiBXException jibxe) {
      
    	String message = "The object format list could not be deserialized. " +
    	"The error message was: " + jibxe.getMessage();
        logger.error(message);
        throw new ServiceFailure("4841", message);
             
    } catch (IOException ioe) {
    	String message = "The object format list could not be read. " +
    	"The error message was: " + ioe.getMessage();
        logger.error(message);
        throw new ServiceFailure("4841", message);
 
    } catch (InstantiationException ie) {
    	String message = "The object format list could not be instantiated. " +
    	"The error message was: " + ie.getMessage();
        logger.error(message);
        throw new ServiceFailure("4841", message);

    } catch (IllegalAccessException iae) {
    	String message = "The object format list could not be accessed. " +
    	"The error message was: " + iae.getMessage();
        logger.error(message);
        throw new ServiceFailure("4841", message);
 
    }   
    return;
  
  }
 
  /*
   * Return the hash containing the fmtid and format mapping
   * 
   * @return objectFormatMap - the hash of fmtid/format pairs
   */
  private HashMap<String, ObjectFormat> getObjectFormatMap() {
  	
  	if ( objectFormatMap == null ) {
  		objectFormatMap = new HashMap<String, ObjectFormat>();
  		
  	}
  	return objectFormatMap;
  	
  }


}
