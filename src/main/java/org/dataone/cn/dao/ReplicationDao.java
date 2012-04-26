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
 */
package org.dataone.cn.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;

/**
 * Abstract definition of a replication data access object used to identify
 * replicas that need to be audited and replicas that are pending/queued.
 * 
 * @author sroseboo
 * 
 */
public interface ReplicationDao {

    public List<Identifier> getReplicasByDate(Date auditDate, int pageSize, int pageNumber);

    public List<Identifier> getFailedReplicas(int pageSize, int pageNumber);

    public List<Identifier> getInvalidReplicas(int pageSize, int pageNumber);

    public List<Identifier> getStaleQueuedReplicas(int pageSize, int pageNumber);
    
    /**
     * Return a map of member node to replica count entries where the replica
     * status is either queued or requested
     */
    public Map<NodeReference, Integer> getPendingReplicasByNode();
    
    /**
     * Return a map of member node to replica count entries where the replica
     * status is failed and the date_verified is within a given timeframe
     */
    public Map<NodeReference, Integer> getRecentFailedReplicas();

    /**
     * Return a map of member node to replica count entries where the replica
     * status is completed and the date_verified is within a given timeframe
     */
    public Map<NodeReference, Integer> getRecentCompletedReplicas();
}
