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

import java.util.List;

import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.SystemMetadata;

/**
 * A specification for accessing and manipulating system metadata attributes in a database
 * 
 * @author cjones
 *
 */
public interface SystemMetadataDao {

    /**
     * Get the total count of the system metadata records in the database
     * @return
     * @throws DataAccessException
     */
    public int getSystemMetadataCount() throws DataAccessException;

    /**
     * List object status information with SystemMetadata in the database
     * @param pageNumber
     * @param pageSize
     * @return
     * @throws DataAccessException
     */
    public List<SystemMetadataStatus> listSystemMetadataStatus(int pageNumber, int pageSize)
            throws DataAccessException;

    /**
     * Get a systemMetadata object for a given Identifier, using the given JdbcTemplate and map of
     * system metadata table names.
     * 
     * @param pid
     * @param tableMap   The lookup map of table names. The map must include 'identifier', 'systemmetadata', 
     * 'smreplicationpolicy', 'smreplicationstatus', and 'xml_access' keys and their respective
     * values that are table names specific to the cached CN data (like 'ucsb_identifier')
     * 
     * @return systemMetadata  the constructed system metadata object
     * @throws DataAccessException
     */
    public SystemMetadata getSystemMetadata(Identifier pid) throws DataAccessException;

    /**
     * Save the system metadata entry for a given identifier
     * 
     * @param pid
     * @param dataSource  The data source instance of the database to connect to
     * @param tableMap   The lookup map of table names. The map must include 'identifier', 'systemmetadata', 
     * 'smreplicationpolicy', 'smreplicationstatus', and 'xml_access' keys and their respective
     * values that are table names specific to the cached CN data (like 'ucsb_identifier')
     * @return
     * @throws DataAccessException
     */
    public Identifier saveSystemMetadata(SystemMetadata systemMetadata) throws DataAccessException;

}
