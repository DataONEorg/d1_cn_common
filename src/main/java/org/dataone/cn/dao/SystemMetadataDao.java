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
	 * Get a systemMetadata object for a given Identifier
	 * @param pid
	 * @return
	 * @throws DataAccessException
	 */
	public SystemMetadata getSystemMetadata(Identifier pid) throws DataAccessException;
	
	/**
	 * Update the system metadata entry for a given identifier
	 * 
	 * @param pid
	 * @return
	 * @throws DataAccessException
	 */
	public Identifier updateSystemMetadata(SystemMetadata systemMetadata) throws DataAccessException;

	/**
	 * Insert system metadata entry for a given identifier (when the db doesn't have it)
	 * 
	 * @param pid
	 * @return
	 * @throws DataAccessException
	 */
	public Identifier insertSystemMetadata(Identifier pid, SystemMetadata systemMetadata) 
		throws DataAccessException;

}
