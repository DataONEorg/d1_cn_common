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
import org.dataone.service.types.v1.NodeReference;

/**
 * A data transfer object the represents a replica record, used to transfer 
 * the status of each replica entry found in the database.
 * 
 * @author cjones
 */
public class ReplicaEntry {

	private Identifier pid;
	private String status;
	private NodeReference memberNode;
	private Date dateVerified;

	
	/**
	 * Constructor. Create an instance of ReplicaEntry
	 */
	public ReplicaEntry() {
		
	}
	
	/**
	 * Get the identifier associated with the replica entry
	 * @return the pid
	 */
	public Identifier getPid() {
		return pid;
	}
	/**
	 * Set the identifier associated with the replica entry
	 * @param pid the pid to set
	 */
	public void setPid(Identifier pid) {
		this.pid = pid;
	}
	/**
	 * Get the replica status for the given entry
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * Set the replica status for the given entry
	 * @param status  the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * Get the member node id associated with the replica entry
	 * @return the memberNode
	 */
	public NodeReference getMemberNode() {
		return this.memberNode;
	}
	
	/**
	 * Set the member node id associated with the replica entry
	 * @param memberNode the memberNode to set
	 */
	public void setMemberNode(NodeReference memberNode) {
		this.memberNode = memberNode;
	}	
		
	/**
	 * Get the date the replica was verified
	 * @return the dateVerified
	 */
	public Date getDateVerified() {
		return dateVerified;
		
	}

	/**
	 * Set the date the replica was verified
	 * @param dateVerified the dateVerified to set
	 */
	public void setDateVerified(Date dateVerified) {
		this.dateVerified = dateVerified;
		
	}

}
