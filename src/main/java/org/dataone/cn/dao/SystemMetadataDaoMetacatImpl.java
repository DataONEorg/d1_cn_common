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

import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.SystemMetadata;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

/**
 * A concrete implementation of the systemMetadataDao inteface against the Metacat 
 * database tables (systemmetadata, smreplicationpolicy, smreplicationstatus, xml_access).
 * Only use this class for audit/repair purposes - not general access to the underlying database
 * 
 * @author cjones
 */
public class SystemMetadataDaoMetacatImpl implements SystemMetadataDao {

    private static final Log log = LogFactory.getLog(SystemMetadataDaoMetacatImpl.class);

    private static int documentIdCounter = 0;
    private static final String IDENTIFIER_TABLE = "identifier";
    private static final String SYSMETA_TABLE    = "systemmetadata";
    private static final String SM_POLICY_TABLE  = "smreplicationpolicy";
    private static final String SM_STATUS_TABLE  = "smreplicationstatus";
    private static final String ACCESS_TABLE     = "xml_access";
    private JdbcTemplate jdbcTemplate;

    /**
     * Constructor. Creates an instance of SystemMetadataDaoMetacatImpl
     */
    public SystemMetadataDaoMetacatImpl() {
    	
    }
    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#getSystemMetadataCount()
     */
	@Override
	public int getSystemMetadataCount() throws DataAccessException {
		
        // query the systemmetadata table
        String sqlStatement = "SELECT count(guid) FROM " + SYSMETA_TABLE;

        int count = this.jdbcTemplate.queryForInt(sqlStatement);

		return count;
	}

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#listSystemMetadata()
     */
    @Override
    public List<SystemMetadataStatus> listSystemMetadataStatus(int pageNumber, int pageSize) 
    	throws DataAccessException {
    	
    	List<SystemMetadataStatus> sysMetaStatusList = new ArrayList<SystemMetadataStatus>();
        
    	// reset negative page numbers and sizes
        if ( pageNumber < 1) { pageNumber = 0; }
        if ( pageSize < 0) { pageSize = 0; }

        final int finalPageNumber = pageNumber;
        final int finalPageSize = pageSize;
        final int offset = (pageNumber - 1) * pageSize;
        
        try {
        	// populate the systemMetadataStatus list with rows from the database
        	sysMetaStatusList = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = 
                    	"SELECT guid, serial_version, date_modified, archived FROM " +
                    	SYSMETA_TABLE + " ORDER BY guid;";

                    if (finalPageSize > 0 && finalPageNumber > 0) {
                        sqlStatement += " LIMIT " + finalPageSize;
                    }

                    if (finalPageNumber > 0) {
                        sqlStatement += " OFFSET " + offset;
                    }

                    sqlStatement += ";";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    log.debug("sysMetaStatusList statement is: " + statement);
                    return statement;
                }
            }, new SystemMetadataMapper());

        } catch (org.springframework.dao.DataAccessException dae) {
            throw new DataAccessException(dae);
        }

        return sysMetaStatusList;
    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#getSystemMetadata(org.dataone.service.types.v1.Identifier)
     */
    @Override
    public SystemMetadata getSystemMetadata(Identifier pid)
            throws DataAccessException {
        
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
    	
    	// need to test for mapping existence, sysmeta existence, etc
    	// then will call generateDocumentId(), createMapping(), then the inserts
        return null;
    }

    /**
     * Create a mapping in the identifier table of the pid to local docid. this method should be
     * used cautiously.  Metacat should have created a mapping on create() of an object, or via 
     * Metacat replication.  Creating a mapping should only happen if an audit shows a clear need
     * to repair the database content.  In theory, hasMapping() should always return true for a
     * pid known on one CN, and this method shouldn't be used.
     * 
     * @param guid
     * @param localId
     */
    private void createMapping(String guid, String localId) throws DataAccessException {
    	
    	String separator = ".";
    	String docNumber;
    	String docid;
    	String rev;
    	Identifier identifier = new Identifier();
    	// Ensure the strings are not null
        if ( localId == null || guid == null ) {
        	throw new DataAccessException(new Exception("The given id was null"));
        	
        }
        
        // Ensure the localId syntax is correct
        int lastIndex;
        int secondToLastIndex;
		try {
			lastIndex = localId.lastIndexOf(separator);
			secondToLastIndex = localId.lastIndexOf(separator, lastIndex);
	          rev = localId.substring(lastIndex + 1);
	          docNumber = localId.substring(secondToLastIndex, lastIndex - 1);
	          docid = localId.substring(0, secondToLastIndex -1);
	          int revAsInt = (new Integer(rev)).intValue();
	          int docNumberAsInt = (new Integer(docNumber)).intValue();
	          if ( log.isDebugEnabled() ) {
	        	  log.debug("Creating mapping for - docid: " + docid + 
	        			    ", docNumber: " + docNumber +
	        			    ", rev: " + rev);
	          }
	          
		} catch (IndexOutOfBoundsException iobe) {
			throw new DataAccessException(iobe.getCause()); // bad localId syntax
			
		} catch (NumberFormatException nfe) {
			throw new DataAccessException(nfe.getCause()); // bad localId syntax

		}
		
		// does the mapping already exist?
		identifier.setValue(guid);
		if ( hasMapping(identifier) ) {
			log.info("The database already has a mapping for " + guid + ". Skipping the create.");
			return;
			
		} else {
			String sqlStatement = "INSERT into " + IDENTIFIER_TABLE + 
					" (guid, docid, rev) VALUES (?, ?, ?);";
			this.jdbcTemplate.update(sqlStatement, new Object[]{guid, docid, rev}, 
				new int[]{Types.VARCHAR, Types.VARCHAR, Types.INTEGER});
			log.info("Created mapping for " + guid + "and " + localId);
		}

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
    
    /*
     * Check to see if a mapping exists for the pid
     * @param pid
     * @return mapped  true if it exists
     * @throws DataAccessException
     */
    private boolean hasMapping(Identifier pid) throws DataAccessException {
    	
    	boolean mapped = false;
        int countReturned = 0;
        
    	if ( pid.getValue() == null ) {
    		throw new DataAccessException(new Exception("The given identifier was null"));
    	}
    	        
        // query the identifier table
        String sqlStatement = "SELECT guid FROM " + IDENTIFIER_TABLE + "where guid = ?";

        countReturned = this.jdbcTemplate.queryForInt(sqlStatement, new Object[]{pid.getValue()});

        if ( countReturned > 0 ) {
        	mapped = true;
        }
        
    	return mapped;
    }

    /*
     * Check to see if a system metadata record exists for the pid
     * @param pid
     * @return hasSysMeta  true if it exists
     * @throws DataAccessException
     */
    private boolean hasSystemMetadata(Identifier pid) throws DataAccessException {
    	
    	boolean hasSysMeta = false;
        int countReturned = 0;
        
    	if ( pid.getValue() == null ) {
    		throw new DataAccessException(new Exception("The given identifier was null"));
    	}
    	        
        // query the identifier table
        String sqlStatement = "SELECT guid FROM " + SYSMETA_TABLE + "where guid = ?";

        countReturned = this.jdbcTemplate.queryForInt(sqlStatement, new Object[]{pid.getValue()});

        if ( countReturned > 0 ) {
        	hasSysMeta = true;
        }
        
    	return hasSysMeta;
    }

    /**
     * A class used to map system metadata status results into SystemMetadataStatus data transfer objects
     * 
     * @author cjones
     *
     */
    public class SystemMetadataMapper implements RowMapper<SystemMetadataStatus> {

    	/**
    	 * Map each row into a SystemMetadataStatus object
    	 */
		@Override
		public SystemMetadataStatus mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
			
			// resultSet contains guid, serialVersion, date_modified, and archived column data
			SystemMetadataStatus systemMetadataStatus = new SystemMetadataStatus();
			
			// add guid
			Identifier pid = new Identifier();
			pid.setValue(resultSet.getString("guid"));
			systemMetadataStatus.setPid(pid);
			
			// add serialVersion
			BigInteger serialVersion = new BigInteger(resultSet.getString("serialVersion"));
			systemMetadataStatus.setSerialVersion(serialVersion);
			
			// add date_modified
			Date dateSystemMetadataLastModified = resultSet.getDate("date_modified");
			systemMetadataStatus.setLastSystemMetadataModificationDate(dateSystemMetadataLastModified);
			
			// add archived
			boolean archived = resultSet.getBoolean("archived");
			systemMetadataStatus.setDeleted(new Boolean(archived));
			
			return systemMetadataStatus;
		}
    	
    }
}
