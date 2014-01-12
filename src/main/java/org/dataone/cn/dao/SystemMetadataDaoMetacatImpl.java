/**
 * This work was created by participants in the DataONE project, and is
 * jointly copyrighted by participating institutions in DataONE. For
 * more information on DataONE, see our web site at http://dataone.org.
 *
 *   Copyright 2014
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
package org.dataone.cn.dao;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.SystemMetadata;

/**
 * A concrete implementation of the systemMetadataDao inteface against the Metacat 
 * database tables (systemmetadata, smreplicationpolicy, smreplicationstatus, xml_access).
 * Only use this class for audit/repair purposes - not general access to the underlying database
 * 
 * @author cjones
 */
public class SystemMetadataDaoMetacatImpl implements SystemMetadataDao {

    private static int documentIdCounter = 0;
    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#getSystemMetadataCount()
     */
	@Override
	public int getSystemMetadataCount() throws DataAccessException {
		return 0;
	}

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#listSystemMetadata()
     */
    @Override
    public List<SystemMetadataStatus> listSystemMetadataStatus(int pageNumber, int pageSize) 
    		throws DataAccessException {
        return null;
    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#getSystemMetadata(org.dataone.service.types.v1.Identifier)
     */
    @Override
    public SystemMetadata getSystemMetadata(Identifier pid)
            throws DataAccessException {
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#updateSystemMetadata(org.dataone.service.types.v1.SystemMetadata)
     */
    @Override
    public Identifier updateSystemMetadata(SystemMetadata systemMetadata)
            throws DataAccessException {
        return null;
    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#insertSystemMetadata(org.dataone.service.types.v1.Identifier, org.dataone.service.types.v1.SystemMetadata)
     */
    @Override
    public Identifier insertSystemMetadata(Identifier pid,
            SystemMetadata systemMetadata) throws DataAccessException {
        return null;
    }

    /**
     * create a mapping in the identifier table
     * @param guid
     * @param localId
     */
    private void createMapping(String guid, String localId) {        
    	// stub only
    }

    /**
     * Create a unique docid for use in inserts and updates using the prefix
     * that is provided. Does not include the 'revision' part of the id if 
     * revision is '0', otherwise sets the revision number to 'revision'.
     * 
     * @param idPrefix the prefix to be used to construct the scope portion of the docid
     * @param revision the integer revision to use for this docid
     * @return a String docid based on the current date and time
     */
    private String generateDocumentId(String idPrefix, int revision) {
    	
    	StringBuffer docid;
    	if ( idPrefix != null ) {
        	docid = new StringBuffer(idPrefix);
    		
    	} else {
    		docid = new StringBuffer("autogen");
    	}
        docid.append(".");

        // Create a calendar to get the date formatted properly
        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        // using yyyymmddhhmmssmmm by convention (zero padding to preserve places)
        // will help with looking at logs and especially database tables.
        // for each millisecond we can support up to 99 before resetting to 0
        // NOTE: if you make it larger, docid is too big for a Long 
        if (documentIdCounter > 100) {
            documentIdCounter = 0;
        }
        docid.append(String.format("%04d%02d%02d%02d%02d%02d%03d%02d",
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH) + 1,  // adjust 0-11 range to 1-12
        calendar.get(Calendar.DAY_OF_MONTH),
        calendar.get(Calendar.HOUR_OF_DAY),
        calendar.get(Calendar.MINUTE),
        calendar.get(Calendar.SECOND),
        calendar.get(Calendar.MILLISECOND),
        documentIdCounter++)
        );
        if (revision > 0) {
            docid.append(".").append(revision);
        }
        return docid.toString();
    }

}
