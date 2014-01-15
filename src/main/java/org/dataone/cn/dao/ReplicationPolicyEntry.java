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

import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;

/**
 * A data transfer object the represents a replication policy record, used to transfer 
 * the preferred and blocked lists found in a policy in the database.
 * 
 * @author cjones
 */
public class ReplicationPolicyEntry {

	private Identifier pid;
	private String policy;
	private NodeReference memberNode;

	/**
	 * Constructor. Create an instance of ReplicationPolicyEntry
	 */
	public ReplicationPolicyEntry() {
		
	}
	
	/**
	 * Get the identifier associated with the replication policy entry
	 * @return the pid
	 */
	public Identifier getPid() {
		return pid;
	}
	/**
	 * Set the identifier associated with the replication policy entry
	 * @param pid the pid to set
	 */
	public void setPid(Identifier pid) {
		this.pid = pid;
	}
	/**
	 * Get the policy entry type (preferred or blocked) for the given entry
	 * @return the policy
	 */
	public String getPolicy() {
		return policy;
	}
	/**
	 * Set the policy entry type (preferred or blocked) for the given entry
	 * @param policy the policy to set
	 */
	public void setPolicy(String policy) {
		this.policy = policy;
	}
	/**
	 * Get the member node id associated with the policy entry
	 * @return the memberNode
	 */
	public NodeReference getMemberNode() {
		return this.memberNode;
	}
	/**
	 * Set the member node id associated with the policy entry
	 * @param memberNode the memberNode to set
	 */
	public void setMemberNode(NodeReference memberNode) {
		this.memberNode = memberNode;
	}	
	
}
