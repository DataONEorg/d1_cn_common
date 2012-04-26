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

import junit.framework.Assert;

import org.dataone.service.types.v1.Identifier;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

public class ReplicationDaoTest {

    private JdbcTemplate jdbc = new JdbcTemplate(DataSourceFactory.getMetacatDataSource());

    @Before
    public void createTables() {
        jdbc.execute("CREATE TABLE IF NOT EXISTS systemmetadatareplicationstatus " + //
                "(guid text, " + //
                "member_node varchar(250), " + //
                "status varchar(250), " + //
                "date_verified timestamp)");

    }

    @After
    public void dropTables() {
        jdbc.execute("DROP TABLE IF EXISTS systemmetadatareplicationstatus;");
    }

    @Test
    public void testReplicasByDateQuery() {
        jdbc.execute("INSERT INTO systemmetadatareplicationstatus VALUES ('test_guid','mn:test:1','REQUESTED',TIMESTAMP '2008-01-01 12:00:00')");
        ReplicationDao dao = DaoFactory.getReplicationDao();
        List<Identifier> results = dao.getReplicasByDate(new Date(System.currentTimeMillis()), 100,
                1);
        System.out.println("result: " + results.size());
        Assert.assertTrue(results.size() == 1);
    }
}
