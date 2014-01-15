/**
 * 
 */
package org.dataone.cn.dao;

import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;

/**
 * A data transfer object the represents a replication policy record, used to transfer 
 * the preferred and blocked lists found in a policy in the database.
 * @author cjones
 *
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
