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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ReplicationDaoMetacatImpl implements ReplicationDao {

    private static final Log log = LogFactory.getLog(ReplicationDaoMetacatImpl.class);

    private JdbcTemplate jdbcTemplate;

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ReplicationDaoMetacatImpl() {
        this.jdbcTemplate = new JdbcTemplate(DataSourceFactory.getMetacatDataSource());
    }

    public List<Identifier> getReplicasByDate(Date auditDate, int pageSize, int pageNumber)
            throws DataAccessException {

        String dateString = format.format(auditDate);
        List<Identifier> results = new ArrayList<Identifier>();
        try {
            results = this.jdbcTemplate.query(
                    "SELECT DISTINCT systemmetadatareplicationstatus.guid, "
                            + "systemmetadatareplicationstatus.date_verified "
                            + "FROM systemmetadatareplicationstatus "
                            + "WHERE systemmetadatareplicationstatus.date_verified <= ? "
                            + "ORDER BY systemmetadatareplicationstatus.date_verified ASC;",
                    new Object[] { dateString }, new IdentifierMapper());
        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
        }
        return results;
    }

    /**
     * Retrieve the count of pending replica requests per target node listed in
     * the Coordinating Node's systemetadatareplicationstatus table. The result
     * is used to determine the current request load for a given Member Node
     * 
     * @return pendingReplicasByNodeMap - the map of nodeId/count pairs
     */
    @Override
    public Map<NodeReference, Integer> getPendingReplicasByNode() throws DataAccessException {

        log.debug("Getting current pending replicas by node.");

        // The map to hold the nodeId/count K/V pairs
        Map<NodeReference, Integer> pendingReplicasByNodeMap = new HashMap<NodeReference, Integer>();

        String sqlStatement = "SELECT systemmetadatareplicationstatus.member_node,          "
                + "       count(systemmetadatareplicationstatus.status) AS count"
                + "  FROM  systemmetadatareplicationstatus                      "
                + "  WHERE systemmetadatareplicationstatus.status = 'QUEUED'    "
                + "  OR    systemmetadatareplicationstatus.status = 'REQUESTED' "
                + "  GROUP BY systemmetadatareplicationstatus.member_node       "
                + "  ORDER BY systemmetadatareplicationstatus.member_node;      ";

        List<Map<NodeReference, Integer>> results = null;
        try {
            results = this.jdbcTemplate.query(sqlStatement, new ReplicaCountMap());
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

        // TODO: make the date_verified timeframe configurable (currently 3)
        String sqlStatement = "SELECT systemmetadatareplicationstatus.member_node,          "
                + "  count(systemmetadatareplicationstatus.status) AS count"
                + "  FROM  systemmetadatareplicationstatus                      "
                + "  WHERE systemmetadatareplicationstatus.status = 'FAILED'    "
                + "  AND   systemmetadatareplicationstatus.date_verified >=     "
                + "        (SELECT CURRENT_DATE - 3)                            "
                + "  GROUP BY systemmetadatareplicationstatus.member_node       "
                + "  ORDER BY systemmetadatareplicationstatus.member_node;      ";

        List<Map<NodeReference, Integer>> results = null;
        try {
            results = this.jdbcTemplate.query(sqlStatement, new ReplicaCountMap());
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

        // TODO: make the date_verified timeframe configurable (currently 3)
        String sqlStatement = "SELECT systemmetadatareplicationstatus.member_node,          "
                + "  count(systemmetadatareplicationstatus.status) AS count"
                + "  FROM  systemmetadatareplicationstatus                      "
                + "  WHERE systemmetadatareplicationstatus.status = 'COMPLETED' "
                + "  AND   systemmetadatareplicationstatus.date_verified >=     "
                + "        (SELECT CURRENT_DATE - 3)                            "
                + "  GROUP BY systemmetadatareplicationstatus.member_node       "
                + "  ORDER BY systemmetadatareplicationstatus.member_node;      ";

        List<Map<NodeReference, Integer>> results = null;
        try {
            results = this.jdbcTemplate.query(sqlStatement, new ReplicaCountMap());
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

    private void handleJdbcDataAccessException(org.springframework.dao.DataAccessException dae)
            throws DataAccessException {
        log.error("Jdbc Data access exception occurred: " + dae.getRootCause().getMessage());
        throw new DataAccessException(dae);
    }

    private void consolidateResultsIntoSingleMap(Map<NodeReference, Integer> replicasByNodeMap,
            List<Map<NodeReference, Integer>> results) {
        for (Map<NodeReference, Integer> result : results) {
            replicasByNodeMap.putAll(result);
        }
    }

    /*
     * An internal class representing a Map of replica counts by node.
     * Implements the RowMapper interface to populate the resultant Map.
     */
    private static final class ReplicaCountMap implements RowMapper<Map<NodeReference, Integer>> {

        /**
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

    private static final class IdentifierMapper implements RowMapper<Identifier> {
        public Identifier mapRow(ResultSet rs, int rowNum) throws SQLException {
            Identifier pid = new Identifier();
            pid.setValue(rs.getString("guid"));
            return pid;
        }
    }
}