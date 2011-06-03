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

package org.dataone.service;

import java.io.InputStream;
import java.util.HashMap;

import org.apache.log4j.Logger;

import org.dataone.service.exceptions.ServiceFailure;
import org.dataone.service.types.ObjectFormat;
import org.dataone.service.types.ObjectFormatIdentifier;
import org.dataone.service.types.ObjectFormatList;
import org.dataone.service.types.util.ServiceTypeUtil;
import org.jibx.runtime.JiBXException;

/**
 *  The ObjectFormatDiskCache provides a default list of object formats.
 *  This includes schema types, mime types, and other
 *  information related to a particular format.  
 */
public class ObjectFormatDiskCache {
  
  /* The instance of the logging class */
  private Logger logger = Logger.getLogger(ObjectFormatDiskCache.class);
  
  /* The instance of the object format service */
  public ObjectFormatDiskCache objectFormatCache = null;
  
  /* The list of object formats */
  public ObjectFormatList objectFormatList = null;
  
  /* the searchable map of object formats */
  public HashMap<String, ObjectFormat> objectFormatMap = 
    new HashMap<String, ObjectFormat>();
  
  /* the package path of the cached object format list*/ 
  private String objectFormatFilePath = "/org/dataone/service/resources/" +
                                        "config/objectFormatList.xml";

  /*
   * Constructor: Creates an instance of the object format service. Since
   * this uses a singleton pattern, use getInstance() to gain the instance.
   */
  public ObjectFormatDiskCache() {
    
    try {
      
    	// create the in-memory list of object formats
      populateObjectFormatList();
      
    } catch (ServiceFailure se) {
      logger.debug("There was a problem creating the ObjectFormatDiskCache. " +
                       "The error message was: " + se.getMessage());
      
    }
    
  }
  
  /**
   *  Get the instance of the ObjectFormatDiskCache that has been instantiated,
   *  or instantiate one if it has not been already.
   *
   * @return objectFormatCache - The instance of the object format cache
   */
  public ObjectFormatDiskCache getInstance(){
    
    if ( objectFormatCache == null ) {
      
      objectFormatCache = new ObjectFormatDiskCache();
      
    }
    return objectFormatCache;
  }
  
  /**
   * Populate the ObjectFormatDiskCache's objectFormatList from the cached list.
   * 
   * @throws ServiceFailure
   */
  private void populateObjectFormatList() throws ServiceFailure {
    
    try {
      
    	getCachedList();
      
    } catch ( ServiceFailure sfe ) {
      
      String message = "There was a problem finding the coordinating node " +
                       "URL in the properties file.  The message was: "     +
                       sfe.getMessage();
      logger.error(message);
      throw sfe;
    }

    // index the object format list based on the format identifier
    int listSize = this.objectFormatList.sizeObjectFormats();
    
    for (int i = 0; i < listSize; i++ ) {
      
      ObjectFormat objectFormat = 
        this.objectFormatList.getObjectFormat(i);
      String identifier = objectFormat.getFmtid().toString();
      this.objectFormatMap.put(identifier, objectFormat);
      
    }
    
  }
  
  /**
   * List the object formats registered with the object format service.
   * 
   * @return objectFormatList - the list of object formats
   */
  public ObjectFormatList listFormats() {
    
    return this.objectFormatList;
    
  }
  
  /**
   * Get the object format based on the given identifier.
   * 
   * @param format - the object format identifier
   * @return objectFormat - the ObjectFormat represented by the format identifier
   */
  public ObjectFormat getFormat(ObjectFormatIdentifier fmtid) {
    
    ObjectFormat objectFormat = null;
    objectFormat = this.objectFormatMap.get(fmtid);
    
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
            
      this.objectFormatList = (ObjectFormatList)
    	  ServiceTypeUtil.deserializeServiceType(ObjectFormatList.class, inputStream);
    } catch (JiBXException jibxe) {
      
    	String message = "The object format list could not be deserialized. " +
    	"The error message was: " + jibxe.getMessage();
        logger.error(message);
        jibxe.printStackTrace();
        throw new ServiceFailure("4841", message);

    } catch (Exception e) {
        
    	// deal with other exceptions if the resource isn't readable
    	String message = "The object format list could not be created. " +
    	"The error message was: " + e.getMessage();
        logger.error(message);
        throw new ServiceFailure("4841", message);
        
      
    }
    
    return;
  
  }

}