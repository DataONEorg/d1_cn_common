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

package org.dataone.service;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.dataone.service.exceptions.NotFound;
import org.dataone.service.types.ObjectFormatIdentifier;
import org.dataone.service.types.ObjectFormatList;

import org.junit.Test;

/**
 * Test the ObjectFormatDiskCache to retrieve the object format list, a single 
 * object format, and test a known bad format to handle the NotFound exception.
 * @author cjones
 *
 */
public class ObjectFormatDiskCacheTest {

	@Test
  public void testHarnessCheck() {
      assertTrue(true);
  }
  
  /**
   * Test getting the entire object format list.  The default list has at least
   * 31 entries.
   */
  @Test
  public void testListFormats() {
  	
  	int formatsCount = 31;
  	ObjectFormatList objectFormatList = ObjectFormatDiskCache.listFormats();
  	assertTrue(objectFormatList.getTotal() >= formatsCount);
  	
  }
  
  /**
   * Test getting a single object format from the registered list
   */
  @Test
  public void testGetFormat() {
  	
  	String knownFormat = "text/plain";
  	ObjectFormatIdentifier fmtid = new ObjectFormatIdentifier();
  	fmtid.setValue(knownFormat);
    
  	try {
	    
			String result = 
				ObjectFormatDiskCache.getFormat(fmtid).getFmtid().getValue();
	  	System.out.println("Expected result: " + knownFormat);
	  	System.out.println("Found    result: " + result);
	  	assertTrue(result.equals(knownFormat));
  
    } catch (NullPointerException npe) {
	  
	    fail("The returned format was null: " + npe.getMessage());
    
    } catch (NotFound nfe) {
      
    	fail("The format " + knownFormat + " was not found.");
    	
    }
  	
  }
  
  /**
   * Test getting a non-existent object format, returning NotFound
   */
  @Test
  public void testObjectFormatNotFoundException() {
  
  	String badFormat = "text/bad-format";
  	ObjectFormatIdentifier fmtid = new ObjectFormatIdentifier();
  	fmtid.setValue(badFormat);

  	try {
  		
	    String result = 
	    	ObjectFormatDiskCache.getFormat(fmtid).getFmtid().getValue();
      
  	} catch (Exception e) {
	    
  		assertTrue(e instanceof NotFound);
  	}
  	
  }
  
}
