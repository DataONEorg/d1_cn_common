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

import java.util.Date;

import org.dataone.service.types.v1.Identifier;

/**
 * A data transfer object that represent an access control record in the database.
 * 
 * @author cjones
 */
public class AccessEntry {

	private Identifier pid       ; // guid
	private String accessFileId  ; // accessfileid
	private String principalName ; // principal_name
	private Long   permission    ; // permission
	private String permType      ; // perm_type
	private String permOrder     ; // perm_order
	private Date   beginTime     ; // begin_time
	private Date   endTime       ; // end_time
	private Long   ticketCount   ; // ticket_count
	private String subTreeId     ; // subtreeid
	private String startNodeId   ; // startnodeid
	private String endNodeId     ; // endnodeid

	/**
	 * @return the pid
	 */
	public Identifier getPid() {
		return pid;
	}
	
	/**
	 * @param pid the pid to set
	 */
	public void setPid(Identifier pid) {
		if (pid != null && ! pid.getValue().equals("")) {
			this.pid = pid;
		}
	}
	
	/**
	 * @return the accessFileId
	 */
	public String getAccessFileId() {
		return accessFileId;
	}
	
	/**
	 * @param accessFileId the accessFileId to set
	 */
	public void setAccessFileId(String accessFileId) {
		if ( accessFileId != null && ! accessFileId.equals("") ) {
			this.accessFileId = accessFileId;
		}
	}
	
	/**
	 * @return the principalName
	 */
	public String getPrincipalName() {
		return principalName;
	}
	
	/**
	 * @param principalName the principalName to set
	 */
	public void setPrincipalName(String principalName) {
		if ( principalName != null && ! principalName.equals("") ) {
			this.principalName = principalName;
		}
	}
	
	/**
	 * @return the permission
	 */
	public Long getPermission() {
		return permission;
	}
	
	/**
	 * @param permission the permission to set
	 */
	public void setPermission(Long permission) {
		if ( permission != null ) {
			this.permission = permission;
		}
	}
	
	/**
	 * @return the permType
	 */
	public String getPermType() {
		return permType;
	}
	
	/**
	 * @param permType the permType to set
	 */
	public void setPermType(String permType) {
		if ( permType != null && ! permType.equals("") ) {
			this.permType = permType;
		}
	}
	
	/**
	 * @return the permOrder
	 */
	public String getPermOrder() {
		return permOrder;
	}
	
	/**
	 * @param permOrder the permOrder to set
	 */
	public void setPermOrder(String permOrder) {
		if ( permOrder != null && ! permOrder.equals("") ) {
			this.permOrder = permOrder;
		}
	}
	
	/**
	 * @return the beginTime
	 */
	public Date getBeginTime() {
		return beginTime;
	}
	
	/**
	 * @param beginTime the beginTime to set
	 */
	public void setBeginTime(Date beginTime) {
		if (beginTime != null ) {
			this.beginTime = beginTime;
		}
	}
	
	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}
	
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		if (endTime != null ) {
			this.endTime = endTime;
		}
	}
	
	/**
	 * @return the ticketCount
	 */
	public Long getTicketCount() {
		return ticketCount;
	}
	
	/**
	 * @param ticketCount the ticketCount to set
	 */
	public void setTicketCount(Long ticketCount) {
		if ( ticketCount != null ) {
			this.ticketCount = ticketCount;
		}
	}
	
	/**
	 * @return the subTreeId
	 */
	public String getSubTreeId() {
		return subTreeId;
	}
	
	/**
	 * @param subTreeId the subTreeId to set
	 */
	public void setSubTreeId(String subTreeId) {
		if ( subTreeId != null ) {
			this.subTreeId = subTreeId;			
		}
	}
	
	/**
	 * @return the startNodeId
	 */
	public String getStartNodeId() {
		return startNodeId;
	}
	
	/**
	 * @param startNodeId the startNodeId to set
	 */
	public void setStartNodeId(String startNodeId) {
		if ( startNodeId != null ) {
			this.startNodeId = startNodeId;
		}
	}
	
	/**
	 * @return the endNodeId
	 */
	public String getEndNodeId() {
		return endNodeId;
	}
	
	/**
	 * @param endNodeId the endNodeId to set
	 */
	public void setEndNodeId(String endNodeId) {
		if ( endNodeId != null ) {
			this.endNodeId = endNodeId;
		}
	}

}
