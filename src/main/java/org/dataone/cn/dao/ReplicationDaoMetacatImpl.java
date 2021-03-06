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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.FastDateFormat;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.configuration.Settings;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;
import org.dataone.service.types.v1.Replica;
import org.dataone.service.types.v1.ReplicationStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

/**
 * ReplicationDao implementation based on the metacat products database schema.
 * Uses metacat's relational database to implement sql queries to satisfy
 * ReplicationDao. ReplicationDaoMetacatImpl uses a postgres configured
 * BasicDataSource to connect to the metacat postgres instance.
 * 
 * This class should not be used directly, rather the DaoFactory should be used
 * to obtain a handle on the current ReplicationDao implementation.
 * 
 * @author sroseboo
 * 
 */
public class ReplicationDaoMetacatImpl implements ReplicationDao {

    private static final Log log = LogFactory.getLog(ReplicationDaoMetacatImpl.class);

    private static final String cnNodeId = Settings.getConfiguration().getString(
            "cn.router.nodeId", "urn:node:CN");

    private JdbcTemplate jdbcTemplate;

    private final FastDateFormat format = FastDateFormat.getInstance("yyyy-MM-dd HH:mm:ss");

    /* The number of seconds before now() to query for failures */
    private int failureWindow = 3600;

    public ReplicationDaoMetacatImpl() {
        this.jdbcTemplate = new JdbcTemplate(MetacatDataSourceFactory.getMetacatDataSource());
        this.failureWindow = Settings.getConfiguration().getInt("replication.failure.query.window",
                failureWindow);
    }

    public Collection<NodeReference> getMemberNodesWithQueuedReplica() throws DataAccessException {
        List<NodeReference> results = new ArrayList<NodeReference>();
        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT        " +
                    		" DISTINCT member_node       " +
                    		" FROM smreplicationstatus   " +
                    		" WHERE status='QUEUED';     ";
                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    log.debug("getMemberNodesWithQueuedReplica statement is: " + statement);
                    return statement;
                }
            }, new NodeReferenceMapper());

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        return results;
    }

    public int getQueuedReplicaCountByNode(final String nodeId) throws DataAccessException {
        int count = 0;
        List<Integer> counts = new ArrayList<Integer>();
        try {
            counts = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT      "
                            + "  count(*)                             "
                            + "  FROM  smreplicationstatus            "
                            + "  WHERE member_node = ?                "
                            + "  AND status = 'QUEUED';             ";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setString(1, nodeId);
                    log.debug("getQueuedReplicaCountByNode statement is: " + statement);
                    return statement;
                }
            }, new CountResultMapper());
        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        if (counts.size() > 0) {
            count = counts.get(0);
        }
        return count;
    }

    public Collection<ReplicaDto> getQueuedReplicasByNode(final String mnId)
            throws DataAccessException {
        Collection<ReplicaDto> results = new ArrayList<ReplicaDto>();
        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT      "
                            + "  guid,                                "
                            + "  member_node,                         "
                            + "  status,                              "
                            + "  date_verified                        "
                            + "  FROM  smreplicationstatus            "
                            + "  WHERE member_node = ?             "
                            + "  AND status = 'QUEUED'             "
                            + "  ORDER BY date_verified ASC;          ";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setString(1, mnId);
                    log.debug("getQueuedReplicasByDate statement is: " + statement);
                    return statement;
                }
            }, new ReplicaResultMapper());
        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        return results;
    }

    public boolean queuedReplicaExists(final String identifier, final String nodeId)
            throws DataAccessException {
        List<Integer> counts = new ArrayList<Integer>();
        try {
            counts = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT      "
                            + "  count(*)                             "
                            + "  FROM  smreplicationstatus            "
                            + "  WHERE guid = ?                       "
                            + "  AND member_node = ?                  "
                            + "  AND status = 'QUEUED'                ";
//                            + "  ORDER BY date_verified ASC;          ";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setString(1, identifier);
                    statement.setString(2, nodeId);
                    log.debug("getQueuedReplicasByDate statement is: " + statement);
                    return statement;
                }
            }, new CountResultMapper());
        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        if (counts.size() > 0 && counts.get(0) != null && counts.get(0).intValue() > 0) {
            return true;
        }
        return false;
    }

    public List<Identifier> getInvalidMemberNodeReplicasByDate(Date auditDate, int pageNumber,
            int pageSize) throws DataAccessException {

        List<Identifier> results = new ArrayList<Identifier>();

        final Timestamp timestamp = new Timestamp(auditDate.getTime());
        if (pageSize < 1) {
            pageNumber = 0;
        }
        final int finalPageNumber = pageNumber;
        final int finalPageSize = pageSize;
        final int offset = (pageNumber - 1) * pageSize;
        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT DISTINCT guid, date_verified" //
                            + "  FROM  smreplicationstatus" //
                            + "  WHERE date_verified <= ? " //
                            + "  AND status = 'INVALIDATED' " //
                            + "  AND member_node <> '" + cnNodeId + "'" //
                            + "  ORDER BY date_verified ASC "; //

                    if (finalPageSize > 0 && finalPageNumber > 0) {
                        sqlStatement += " LIMIT " + finalPageSize;
                    }

                    if (finalPageNumber > 0) {
                        sqlStatement += " OFFSET " + offset;
                    }

                    sqlStatement += ";";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setTimestamp(1, timestamp);
                    log.debug("getInvalidMemberNodeReplicas statement is: " + statement);
                    return statement;
                }
            }, new IdentifierMapper());

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        return results;
    }

    public List<Identifier> getCompletedMemberNodeReplicasByDate(Date auditDate, int pageNumber,
            int pageSize) throws DataAccessException {

        List<Identifier> results = new ArrayList<Identifier>();

        final Timestamp timestamp = new Timestamp(auditDate.getTime());
        if (pageSize < 1) {
            pageNumber = 0;
        }
        final int finalPageNumber = pageNumber;
        final int finalPageSize = pageSize;

        final int offset = (pageNumber - 1) * pageSize;

        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT DISTINCT guid, date_verified" //
                            + "  FROM  smreplicationstatus" //
                            + "  WHERE date_verified <= ? " //
                            + "  AND status = 'COMPLETED' " //
                            + "  AND member_node <> '" + cnNodeId + "'" //
                            + "  ORDER BY date_verified ASC "; //

                    if (finalPageSize > 0 && finalPageNumber > 0) {
                        sqlStatement += " LIMIT " + finalPageSize;
                    }

                    if (finalPageNumber > 0) {
                        sqlStatement += " OFFSET " + offset;
                    }

                    sqlStatement += ";";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setTimestamp(1, timestamp);
                    log.debug("getCompletedMemberNodeReplicas statement is: " + statement);
                    return statement;
                }
            }, new IdentifierMapper());

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        return results;
    }

    public List<Identifier> getCompletedCoordinatingNodeReplicasByDate(Date auditDate,
            int pageNumber, int pageSize) throws DataAccessException {

        List<Identifier> results = new ArrayList<Identifier>();

        final Timestamp timestamp = new Timestamp(auditDate.getTime());
        if (pageSize < 1) {
            pageNumber = 0;
        }
        final int finalPageNumber = pageNumber;
        final int finalPageSize = pageSize;
        final int offset = (pageNumber - 1) * pageSize;

        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT DISTINCT guid, date_verified" //
                            + "  FROM  smreplicationstatus" //
                            + "  WHERE date_verified <= ? " //
                            + "  AND status = 'COMPLETED' " //
                            + "  AND member_node = '" + cnNodeId + "'" //
                            + "  ORDER BY date_verified ASC"; //

                    if (finalPageSize > 0 && finalPageNumber > 0) {
                        sqlStatement += " LIMIT " + finalPageSize;
                    }

                    if (finalPageNumber > 0) {
                        sqlStatement += " OFFSET " + offset;
                    }

                    sqlStatement += ";";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setTimestamp(1, timestamp);
                    log.debug("getCompletedCoordinatingNodeReplicas statement is: " + statement);
                    return statement;
                }
            }, new IdentifierMapper());

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        return results;
    }

    public List<ReplicaDto> getRequestedReplicasByDate(Date cutoffDate) throws DataAccessException {

        final Timestamp timestamp = new Timestamp(cutoffDate.getTime());
        List<ReplicaDto> results = new ArrayList<ReplicaDto>();
        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT      "
                            + "  guid,                                "
                            + "  member_node,                         "
                            + "  status,                              "
                            + "  date_verified                        "
                            + "  FROM  smreplicationstatus            "
                            + "  WHERE date_verified <= ?             "
                            + "  AND status = 'REQUESTED'             "
                            + "  ORDER BY date_verified ASC;          ";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setTimestamp(1, timestamp);
                    log.debug("getRequestedReplicasByDate statement is: " + statement);
                    return statement;
                }
            }, new ReplicaResultMapper());

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);

        }
        return results;
    }

    public int getRequestedReplicationCount(NodeReference nodeRef) throws DataAccessException {
        int count = 0;
        final String nodeId = nodeRef.getValue();
        List<Integer> counts = new ArrayList<Integer>();
        try {
            counts = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT      "
                            + "  count(*)                                "
                            + "  FROM  smreplicationstatus            "
                            + "  WHERE member_node = ?                "
                            + "  AND status = 'REQUESTED';             ";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setString(1, nodeId);
                    log.debug("getRequestedReplicasByDate statement is: " + statement);
                    return statement;
                }
            }, new CountResultMapper());
        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        if (counts.size() > 0) {
            count = counts.get(0);
        }
        return count;
    }

    /**
     * Retrieve the count of pending replica requests per target node listed in
     * the Coordinating Node's smreplicationstatus table. The result is used to
     * determine the current request load for a given Member Node
     * 
     * @return pendingReplicasByNodeMap - the map of nodeId/count pairs
     */
    @Override
    public Map<NodeReference, Integer> getPendingReplicasByNode() throws DataAccessException {

        log.debug("Getting current pending replicas by node.");

        // The map to hold the nodeId/count K/V pairs
        Map<NodeReference, Integer> pendingReplicasByNodeMap = new HashMap<NodeReference, Integer>();

        List<Map<NodeReference, Integer>> results = null;
        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT       " 
                            + "    member_node,              "
                            + "    count(status) AS count     " 
                            + "  FROM  smreplicationstatus  "
                            + "  WHERE status = 'REQUESTED' " 
                            + "    OR  status = 'QUEUED'    "
                            + "  GROUP BY member_node       " 
                            + "  ORDER BY member_node;      ";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    log.debug("getPendingReplicasbyNode statement is: " + statement);
                    return statement;
                }
            }, new ReplicaCountMap());
            log.debug("Pending replicas by node result size is " + results.size());

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);

        }
        consolidateResultsIntoSingleMap(pendingReplicasByNodeMap, results);

        if (log.isDebugEnabled()) {
            Iterator<Map.Entry<NodeReference, Integer>> iterator = pendingReplicasByNodeMap
                    .entrySet().iterator();
            log.debug("Pending replica map by node: ");
            while (iterator.hasNext()) {
                Map.Entry<NodeReference, Integer> pairs = (Map.Entry<NodeReference, Integer>) iterator
                        .next();
                log.debug("Node: " + pairs.getKey().getValue() + ", count: "
                        + pairs.getValue().intValue());
            }
        }
        return pendingReplicasByNodeMap;
    }

    /**
     * Retrieve the count of recently failed replica requests per target node
     * listed in the Coordinating Node's systemetadatareplicationstatus table.
     * The result is used to determine the current failure rate for a given
     * Member Node
     * 
     * @return recentFailedReplicasByNodeMap - the map of nodeId/count pairs
     */
    @Override
    public Map<NodeReference, Integer> getRecentFailedReplicas() throws DataAccessException {
        log.debug("Getting recently failed replicas by node.");

        // The map to hold the nodeId/count K/V pairs
        Map<NodeReference, Integer> recentFailedReplicasByNodeMap = new HashMap<NodeReference, Integer>();

        List<Map<NodeReference, Integer>> results = null;
        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    Timestamp cutoffDate = generateStatusCutoffDate();

                    String sqlStatement = "SELECT                 "
                            + "  member_node,                         "
                            + "  count(status) AS count               "
                            + "  FROM  smreplicationstatus"
                            + "  WHERE status = 'FAILED'              "
                            + "  AND   date_verified >= ?             "
                            + "  GROUP BY member_node                 "
                            + "  ORDER BY member_node;                ";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setTimestamp(1, cutoffDate);
                    log.debug("getRecentFailedReplicas statement is: " + statement);
                    return statement;
                }
            }, new ReplicaCountMap());
            log.debug("Failed replicas by node result size is " + results.size());

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);

        }
        consolidateResultsIntoSingleMap(recentFailedReplicasByNodeMap, results);

        if (log.isDebugEnabled()) {
            Iterator<Map.Entry<NodeReference, Integer>> iterator = recentFailedReplicasByNodeMap
                    .entrySet().iterator();
            log.debug("Recent failed replica map by node: ");
            while (iterator.hasNext()) {
                Map.Entry<NodeReference, Integer> pairs = (Map.Entry<NodeReference, Integer>) iterator
                        .next();
                log.debug("Node: " + pairs.getKey().getValue() + ", count: "
                        + pairs.getValue().intValue());
            }
        }
        return recentFailedReplicasByNodeMap;
    }

    /**
     * Retrieve the count of recently completed replica requests per target node
     * listed in the Coordinating Node's systemetadatareplicationstatus table.
     * The result is used to determine the current failure rate for a given
     * Member Node
     * 
     * @return recentCompletedReplicasByNodeMap - the map of nodeId/count pairs
     */
    @Override
    public Map<NodeReference, Integer> getRecentCompletedReplicas() throws DataAccessException {
        log.debug("Getting recently completed replicas by node.");

        // The map to hold the nodeId/count K/V pairs
        Map<NodeReference, Integer> recentCompletedReplicasByNodeMap = new HashMap<NodeReference, Integer>();

        List<Map<NodeReference, Integer>> results = null;
        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    Timestamp cutoffDate = generateStatusCutoffDate();

                    String sqlStatement = "SELECT                 "
                            + "  member_node,                         "
                            + "  count(status) AS count               "
                            + "  FROM  smreplicationstatus"
                            + "  WHERE status = 'COMPLETED'           "
                            + "  AND   date_verified >= ?             "
                            + "  GROUP BY member_node                 "
                            + "  ORDER BY member_node;                ";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setTimestamp(1, cutoffDate);
                    log.debug("getRecentCompletedReplicas statement is: " + statement);
                    return statement;
                }
            }, new ReplicaCountMap());
            log.debug("Recent completed replicas by node result size is " + results.size());
        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        consolidateResultsIntoSingleMap(recentCompletedReplicasByNodeMap, results);

        if (log.isDebugEnabled()) {
            Iterator<Map.Entry<NodeReference, Integer>> iterator = recentCompletedReplicasByNodeMap
                    .entrySet().iterator();
            log.debug("Recent completed replica map by node: ");
            while (iterator.hasNext()) {
                Map.Entry<NodeReference, Integer> pairs = (Map.Entry<NodeReference, Integer>) iterator
                        .next();
                log.debug("Node: " + pairs.getKey().getValue() + ", count: "
                        + pairs.getValue().intValue());
            }
        }
        return recentCompletedReplicasByNodeMap;
    }

    /*
     * Handle data access exceptions thrown by the underlying JDBC calls
     * 
     * @param dae the DataAccessException thrown
     * 
     * @throws DataAccessException
     */
    private void handleJdbcDataAccessException(org.springframework.dao.DataAccessException dae)
            throws DataAccessException {
        log.error("Jdbc Data access exception occurred: " + dae.getRootCause().getMessage());
        dae.printStackTrace();
        throw dae;
    }

    /*
     * Helper method to add results to the given map
     */
    private void consolidateResultsIntoSingleMap(Map<NodeReference, Integer> replicasByNodeMap,
            List<Map<NodeReference, Integer>> results) {
        for (Map<NodeReference, Integer> result : results) {
            replicasByNodeMap.putAll(result);

        }
    }

    /*
     * Create a date timestamp to be used in SQL queries that represents the
     * oldest records falling within a particular failure window
     * 
     * @return cutoffDate - the timestamp
     */
    private Timestamp generateStatusCutoffDate() {
        Calendar cal = Calendar.getInstance();
        log.debug("Calendar date is: " + this.format.format(cal));
        cal.setTimeInMillis(System.currentTimeMillis());
        cal.add(Calendar.SECOND, -this.failureWindow);
        Timestamp cutoffDate = new Timestamp(cal.getTimeInMillis());
        log.debug("Cutoff date is: " + cutoffDate.toString());
        return cutoffDate;
    }

    /*
     * An internal class representing a Map of replica counts by node.
     * Implements the RowMapper interface to populate the resultant Map.
     */
    private static final class ReplicaCountMap implements RowMapper<Map<NodeReference, Integer>> {

        /*
         * Map each row of the resultset into a map of nodeId/count entries
         * 
         * @return replicaCountByNodeMap - the count of replicas by node
         * 
         * @throws SQLException
         */
        @Override
        public Map<NodeReference, Integer> mapRow(ResultSet resultSet, int rowNum)
                throws SQLException {
            Map<NodeReference, Integer> replicaCountByNodeMap = new HashMap<NodeReference, Integer>();
            NodeReference nodeId = new NodeReference();
            nodeId.setValue(resultSet.getString("member_node"));
            Integer count = resultSet.getInt("count");
            replicaCountByNodeMap.put(nodeId, count);

            return replicaCountByNodeMap;
        }

    }

    /*
     * An internal class representing a Map of counts by node-status. Implements
     * the RowMapper interface to populate the resultant Map.
     */
    private static final class PendingReplicaMapper implements
            RowMapper<Map<Identifier, NodeReference>> {

        /*
         * Map a row of the resultset into a map with a identifier/nodeid entry
         * 
         * @return pendingReplicasByNode - the pid/nodeId pairs
         * 
         * @throws SQLException
         */
        @Override
        public Map<Identifier, NodeReference> mapRow(ResultSet resultSet, int rowNum)
                throws SQLException {
            Map<Identifier, NodeReference> pendingReplicasByNode = new HashMap<Identifier, NodeReference>();
            // set the id
            String identifier = resultSet.getString("guid");
            Identifier pid = new Identifier();
            pid.setValue(identifier);

            // set the nodeId
            String memberNode = resultSet.getString("member_node");
            NodeReference nodeId = new NodeReference();
            nodeId.setValue(memberNode);
            log.debug("Adding pending replica request for removal: " + identifier + " at "
                    + memberNode);

            pendingReplicasByNode.put(pid, nodeId);

            return pendingReplicasByNode;
        }

    }

    /*
     * An internal class representing a Map of counts by node-status. Implements
     * the RowMapper interface to populate the resultant Map.
     */
    private static final class CountsByNodeStatusMap implements RowMapper<Map<String, Integer>> {

        /*
         * Map each row of the resultset into a map of nodeStatus/count entries
         * 
         * @return replicaCountByNodeStatusMap - the count of replicas by node
         * 
         * @throws SQLException
         */
        @Override
        public Map<String, Integer> mapRow(ResultSet resultSet, int rowNum) throws SQLException {
            Map<String, Integer> countsByNodeStatusMap = new HashMap<String, Integer>();
            String memberNode = resultSet.getString("member_node");
            String status = resultSet.getString("status");
            Integer count = resultSet.getInt("count");
            countsByNodeStatusMap.put(memberNode + "-" + status, count);

            return countsByNodeStatusMap;
        }

    }

    private static final class CountResultMapper implements RowMapper<Integer> {
        public Integer mapRow(ResultSet rs, int rowNum) throws SQLException {
            return rs.getInt(1);
        }
    }

    private static final class IdentifierMapper implements RowMapper<Identifier> {
        public Identifier mapRow(ResultSet rs, int rowNum) throws SQLException {
            Identifier pid = new Identifier();
            pid.setValue(rs.getString("guid"));
            return pid;
        }
    }

    private static final class ReplicaResultMapper implements RowMapper<ReplicaDto> {
        public ReplicaDto mapRow(ResultSet rs, int rowNum) throws SQLException {

            ReplicaDto result = new ReplicaDto();

            Identifier pid = new Identifier();
            pid.setValue(rs.getString("guid"));
            result.identifier = pid;

            Replica replica = new Replica();

            NodeReference nodeRef = new NodeReference();
            nodeRef.setValue(rs.getString("member_node"));
            replica.setReplicaMemberNode(nodeRef);

            ReplicationStatus status = ReplicationStatus.convert(StringUtils.lowerCase(rs
                    .getString("status")));
            replica.setReplicationStatus(status);

            Date verifiedDate = rs.getTimestamp("date_verified");
            replica.setReplicaVerified(verifiedDate);

            result.replica = replica;

            return result;
        }
    }

    private static final class NodeReferenceMapper implements RowMapper<NodeReference> {
        public NodeReference mapRow(ResultSet rs, int rowNum) throws SQLException {
            NodeReference nodeRef = new NodeReference();
            nodeRef.setValue(rs.getString("member_node"));
            return nodeRef;
        }
    }

    /**
     * Get a map of replica counts by node-status in order to support
     * instrumentation of system wide MN to MN replication functionality.
     * Summary counts of of each status type (QUEUED, REQUESTED, COMPLETED,
     * FAILED, INVALIDATED) are given for each node. E.g.,
     * <urn:Node:mnDevUCSB1-QUEUED, 3>
     * 
     * @return replicaCountsByNodeStatus the map of node-status/count K/V pairs
     * @throws DataAccessException
     */
    @Override
    public Map<String, Integer> getCountsByNodeStatus() throws DataAccessException {

        log.debug("Getting current entry counts by node status.");

        // The map to hold the nodeId-status/count K/V pairs
        Map<String, Integer> replicaCountsByNodeStatus = new HashMap<String, Integer>();

        List<Map<String, Integer>> results = null;
        try {
            results = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT                "
                            + "   member_node, status,               "
                            + "  count(status) AS count              "
                            + "  FROM  smreplicationstatus           "
                            + "  GROUP BY member_node, status        "
                            + "  ORDER BY member_node, status;       ";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    log.debug("getCountsByNodeStatus statement is: " + statement);
                    return statement;
                }
            }, new CountsByNodeStatusMap());
            log.debug("Counts by node-status result size is " + results.size());

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);

        }

        for (Map<String, Integer> result : results) {
            replicaCountsByNodeStatus.putAll(result);

        }

        return replicaCountsByNodeStatus;
    }
}
