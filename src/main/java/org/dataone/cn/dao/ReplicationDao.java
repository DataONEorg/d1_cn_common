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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;
import org.dataone.service.types.v1.Replica;

/**
 * Abstract definition of a replication data access object used to identify
 * replicas that need to be audited and replicas that are pending/queued.
 * 
 * @author sroseboo
 * 
 */
public interface ReplicationDao {

    /**
     * Returns paged list of distinct DataONE Identifier objects with at least
     * one replica with a replica verified date previous to the auditDate
     * parameter. Results to be ordered so identifiers with oldest replica
     * verified dates are returned first. (ascending by replica verified date).
     * 
     * @param auditDate
     *            - Identifiers with one or more replica verified dates after
     *            audit date are returned.
     * @param pageSize
     * @param pageNumber
     * @return distinct identifiers ordered by ascending replica verified date.
     */
    public List<Identifier> getReplicasByDate(Date auditDate, int pageSize, int pageNumber)
            throws DataAccessException;

    /**
     * Return a map of member node to replica count entries where the replica
     * status is either queued or requested
     */
    public Map<NodeReference, Integer> getPendingReplicasByNode() throws DataAccessException;

    /**
     * Returns a list of distinct identifier-nodeId to Map.Entry pairs
     * (Identifier and NodeReference in the Entry), with at least one replica
     * with a replica verified date previous to the auditDate parameter and a
     * status of requested or queued. Results are ordered so identifiers with
     * oldest replica verified dates are returned first (ascending by replica
     * verified date).
     * 
     * @param auditDate
     *            - Identifiers with one or more replica verified dates after
     *            audit date are returned.
     * @return map a map of distinct identifiers ordered by ascending replica
     *         verified date.
     */
    public List<Entry<Identifier, NodeReference>> getPendingReplicasByDate(Date auditDate)
            throws DataAccessException;

    /**
     * Return a map of member node to replica count entries where the replica
     * status is failed and the date_verified is within a given timeframe
     */
    public Map<NodeReference, Integer> getRecentFailedReplicas() throws DataAccessException;

    /**
     * Return a map of member node to replica count entries where the replica
     * status is completed and the date_verified is within a given timeframe
     */
    public Map<NodeReference, Integer> getRecentCompletedReplicas() throws DataAccessException;

    /**
     * Return a map of member node status to count entries for tracking node
     * replica status statistics. The key is a string of node id and status, and
     * the value is the count of that status for the node
     */
    public Map<String, Integer> getCountsByNodeStatus() throws DataAccessException;

    public List<ReplicaDto> getRequestedReplicasByDate(Date cutoffDate) throws DataAccessException;

    public int getRequestedReplicationCount(NodeReference nodeReference) throws DataAccessException;

    public List<ReplicaDto> getQueuedReplicasByDate(Date cutoffDate) throws DataAccessException;

    public Collection<NodeReference> getMemberNodesWithQueuedReplica() throws DataAccessException;

    public int getQueuedReplicaCountByNode(String nodeId) throws DataAccessException;

    public Collection<ReplicaDto> getQueuedReplicasByNode(String mnId) throws DataAccessException;

    public boolean queuedReplicaExists(String identifier, String nodeId) throws DataAccessException;

    /**
     * Data transfer object for replica queries.
     * 
     * @author sroseboo
     * 
     */
    public static class ReplicaDto {
        public Identifier identifier;
        public Replica replica;
    }
}
