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

import junit.framework.Assert;

import org.dataone.cn.dao.ReplicationDao.ReplicaDto;
import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;
import org.dataone.service.types.v1.ReplicationStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class ReplicationDaoMetacatImplTest {

    private JdbcTemplate jdbc = new JdbcTemplate(DataSourceFactory.getMetacatDataSource());

    private ReplicationDao replicationDao = DaoFactory.getReplicationDao();

    @Before
    public void createTables() {
        ReplicationDaoMetacatImplTestUtil.createTables(jdbc);
    }

    @After
    public void dropTables() {
        ReplicationDaoMetacatImplTestUtil.dropTables(jdbc);
    }

    @Test
    public void testMemberNodeReplicasByDateQuery() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','"
                + ReplicationDaoMetacatImplTestUtil.cnNodeId
                + "','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','COMPLETED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        List<Identifier> results = replicationDao.getCompletedMemberNodeReplicasByDate(new Date(
                System.currentTimeMillis()), 0, 0);
        Assert.assertTrue(results.size() == 2);
        Assert.assertTrue(results.get(0).getValue().equals("test_guid"));
        Assert.assertTrue(results.get(1).getValue().equals("test_guid2"));
    }

    @Test
    public void testCoordinatingNodeReplicasByDateQuery() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','"
                + ReplicationDaoMetacatImplTestUtil.cnNodeId
                + "','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','COMPLETED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        List<Identifier> results = replicationDao.getCompletedCoordinatingNodeReplicasByDate(
                new Date(System.currentTimeMillis()), 0, 0);
        Assert.assertTrue(results.size() == 1);
        Assert.assertTrue(results.get(0).getValue().equals("test_guid"));
    }

    @Test
    public void testPendingReplicasByNode() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','QUEUED',TIMESTAMP '2020-01-01 12:00:00')");

        Map<NodeReference, Integer> results = replicationDao.getPendingReplicasByNode();
        Assert.assertTrue(results.size() == 2);

    }

    @Test
    public void testRecentFailedReplicasByNode() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','QUEUED',TIMESTAMP '2020-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:2','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        Map<NodeReference, Integer> results = replicationDao.getRecentFailedReplicas();
        Assert.assertTrue(results.size() == 0);

    }

    @Test
    public void testRecentCompletedReplicasByNode() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        Map<NodeReference, Integer> results = replicationDao.getRecentCompletedReplicas();
        Assert.assertTrue(results.size() == 0);

    }

    @Test
    public void testCountsByNodeStatus() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        Map<String, Integer> results = replicationDao.getCountsByNodeStatus();
        Assert.assertTrue(results.size() == 3); // expect 3 unique node-status
                                                // keys

    }

    @Test
    public void testRequestedReplicasByDate() throws DataAccessException {

        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:3','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        List<ReplicaDto> results = replicationDao.getRequestedReplicasByDate(new Date(System
                .currentTimeMillis()));
        Assert.assertTrue(results.size() == 2);
        for (ReplicaDto result : results) {
            Assert.assertTrue(ReplicationStatus.REQUESTED.equals(result.replica
                    .getReplicationStatus()));
        }
    }

    @Test
    public void testRequestedReplicasCount() throws DataAccessException {

        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:3','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        NodeReference nodeRef = new NodeReference();
        nodeRef.setValue("mn:test:1");

        int count = replicationDao.getRequestedReplicationCount(nodeRef);
        Assert.assertTrue(count == 2);
    }

    @Test
    public void testGetMemberNodesWithQueuedReplica() throws DataAccessException {

        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid4','mn:test:1','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid5','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid6','mn:test:3','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        Collection<NodeReference> nodes = replicationDao.getMemberNodesWithQueuedReplica();
        Assert.assertTrue(nodes.size() == 2);
    }

    @Test
    public void testGetQueuedReplicaCountByNode() throws DataAccessException {

        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid7','mn:test:3','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid4','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid5','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid6','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid4','mn:test:1','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid5','mn:test:3','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid6','mn:test:4','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:4','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");

        int count = replicationDao.getQueuedReplicaCountByNode("mn:test:33");
        Assert.assertTrue(count == 0);

        count = replicationDao.getQueuedReplicaCountByNode("mn:test:1");
        Assert.assertTrue(count == 2);

        count = replicationDao.getQueuedReplicaCountByNode("mn:test:2");
        Assert.assertTrue(count == 4);

        count = replicationDao.getQueuedReplicaCountByNode("mn:test:3");
        Assert.assertTrue(count == 1);

        count = replicationDao.getQueuedReplicaCountByNode("mn:test:4");
        Assert.assertTrue(count == 2);
    }

    @Test
    public void testGetQueuedReplicasByNode() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid7','mn:test:3','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid4','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid5','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid6','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid4','mn:test:1','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid5','mn:test:3','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid6','mn:test:4','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:4','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");

        Assert.assertTrue(2 == replicationDao.getQueuedReplicasByNode("mn:test:1").size());
        Assert.assertTrue(4 == replicationDao.getQueuedReplicasByNode("mn:test:2").size());
        Assert.assertTrue(1 == replicationDao.getQueuedReplicasByNode("mn:test:3").size());
        Assert.assertTrue(2 == replicationDao.getQueuedReplicasByNode("mn:test:4").size());
    }

    @Test
    public void testQueuedReplicaExists() throws DataAccessException {

        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETED',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid7','mn:test:3','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid4','mn:test:3','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");

        Assert.assertFalse(replicationDao.queuedReplicaExists("nosuch", "mn:test:2"));
        Assert.assertTrue(replicationDao.queuedReplicaExists("test_guid3", "mn:test:2"));
        Assert.assertTrue(replicationDao.queuedReplicaExists("test_guid4", "mn:test:3"));
    }
}
