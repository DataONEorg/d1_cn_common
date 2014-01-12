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
import java.util.Date;

import org.dataone.service.types.v1.Identifier;

/**
 * * A data transfer object that represents minimal information about an object, including its Identifier, 
 * serial version, last system metadata modification date, and deleted status of an object
 * 
 * @author cjones
 *
 */
public class SystemMetadataStatus {
	private Identifier pid; // the identifer of the object
	private BigInteger serialVersion; // the serialVersion of the identifier
	private Date lastSystemMetadataModificationDate; // the last mod date of the system metadata of th identifier
	private Boolean deleted; // the deleted status of the identifier

	/**
	 * Constructor. Get an instance of SystemMetadataStatus
	 */
	public SystemMetadataStatus() {
		
	}
	
	/**
	 * Get the identifier of the object
	 * 
	 * @return the identifier
	 */
	public Identifier getPid() {
		return pid;
		
	}
	
	/**
	 * Set the identifier of the object
	 * 
	 * @param identifier the identifier to set
	 */
	public void setPid(Identifier identifier) {
		this.pid = identifier;
		
	}
	
	/**
	 * Get the serial version of the object
	 * 
	 * @return the serialVersion
	 */
	public BigInteger getSerialVersion() {
		return serialVersion;
		
	}
	
	/**
	 * Set the serial version of the identifier
	 * 
	 * @param serialVersion the serialVersion to set
	 */
	public void setSerialVersion(BigInteger serialVersion) {
		this.serialVersion = serialVersion;
		
	}
	
	/**
	 * Get the last modification date of the system metadata for the identifier
	 * 
	 * @return the lastSystemMetadataModificationDate
	 */
	public Date getLastSystemMetadataModificationDate() {
		return lastSystemMetadataModificationDate;
		
	}
	
	/**
	 * Set the last modification date of the system metadata for the identifier
	 * 
	 * @param lastSystemMetadataModificationDate the last system metadata modification date
	 */
	public void setLastSystemMetadataModificationDate(Date lastSystemMetadataModificationDate) {
		this.lastSystemMetadataModificationDate = lastSystemMetadataModificationDate;
		
	}	
	
    /**
     * Get the deleted status of the identifier
     * 
	 * @return deleted
	 */
	public Boolean getDeleted() {
		return deleted;
		
	}
	
	/**
	 * Set the deleted status of the identifier
	 *  
	 * @param deleted the deleted status
	 */
	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
		
	}
	
}
