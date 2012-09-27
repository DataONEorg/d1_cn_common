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

import junit.framework.Assert;

import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class ReplicationDaoMetacatImplTest {

    private JdbcTemplate jdbc = new JdbcTemplate(DataSourceFactory.getMetacatDataSource());

    @Before
    public void createTables() {
        ReplicationDaoMetacatImplTestUtil.createTables(jdbc);
    }

    @After
    public void dropTables() {
        ReplicationDaoMetacatImplTestUtil.dropTables(jdbc);
    }

    @Test
    public void testReplicasByDateQuery() throws DataAccessException {
        // test data - 3 records before today, 1 record after today should
        // result in 2 distinct rows (test_guid2 used twice) in results when
        // query date is today. ORDER BY ascending should return least recently
        // verified rows first.
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETE',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        ReplicationDao dao = DaoFactory.getReplicationDao();
        List<Identifier> results = dao.getReplicasByDate(new Date(System.currentTimeMillis()), 100,
                1);
        Assert.assertTrue(results.size() == 2);
        Assert.assertTrue(results.get(0).getValue().equals("test_guid"));
        Assert.assertTrue(results.get(1).getValue().equals("test_guid2"));
    }

    @Test
    public void testPendingReplicasByNode() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETE',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        ReplicationDao dao = DaoFactory.getReplicationDao();
        Map<NodeReference, Integer> results = dao.getPendingReplicasByNode();
        Assert.assertTrue(results.size() == 2);

    }

    @Test
    public void testRecentFailedReplicasByNode() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETE',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        ReplicationDao dao = DaoFactory.getReplicationDao();
        Map<NodeReference, Integer> results = dao.getRecentFailedReplicas();
        Assert.assertTrue(results.size() == 0);

    }

    @Test
    public void testRecentCompletedReplicasByNode() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETE',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        ReplicationDao dao = DaoFactory.getReplicationDao();
        Map<NodeReference, Integer> results = dao.getRecentCompletedReplicas();
        Assert.assertTrue(results.size() == 0);

    }
    
    @Test
    public void testCountsByNodeStatus() throws DataAccessException {
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid','mn:test:1','COMPLETE',TIMESTAMP '2011-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:1','REQUESTED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid2','mn:test:2','QUEUED',TIMESTAMP '2012-01-01 12:00:00')");
        jdbc.execute("INSERT INTO smreplicationstatus VALUES ('test_guid3','mn:test:1','REQUESTED',TIMESTAMP '2020-01-01 12:00:00')");

        ReplicationDao dao = DaoFactory.getReplicationDao();
        Map<String, Integer> results = dao.getCountsByNodeStatus();
        Assert.assertTrue(results.size() == 3); // expect 3 unique node-status keys

    }

}
