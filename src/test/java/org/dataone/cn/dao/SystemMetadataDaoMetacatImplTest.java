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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.Assert;

import org.dataone.cn.dao.exceptions.DataAccessException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * A class to test /select/insert/update operations on the pertinent Metacat system metadata tables
 * 
 * @author cjones
 *
 */
public class SystemMetadataDaoMetacatImplTest {

    private JdbcTemplate jdbc = new JdbcTemplate(DataSourceFactory.getMetacatDataSource());

    private SystemMetadataDaoMetacatImpl systemMetadataDao = new SystemMetadataDaoMetacatImpl();

    /**
     * Create tables before testing
     */
    @Before
    public void createTables() {
        SystemMetadataDaoMetacatImplTestUtil.createTables(jdbc);

    }

    /**
     * Drop tables after testing
     */
    @After
    public void dropTables() {
        SystemMetadataDaoMetacatImplTestUtil.dropTables(jdbc);

    }

    @Test
    public void testGetSystemMetadataCount() throws DataAccessException {

        // Get some data into systemmetadata
        SystemMetadataDaoMetacatImplTestUtil.populateSystemMetadata(jdbc);
        Map<String, String> tableMap = getTableMap(""); //use an empty prefix for this test
        int systemMetadataCount = systemMetadataDao.getSystemMetadataCount(tableMap);
        Assert.assertTrue(systemMetadataCount == 10);
    }

    /**
     * Test listing of system metadata status DTOs
     */
    @Test
    public void testListSystemMetadataStatus() throws DataAccessException {

        SystemMetadataDaoMetacatImplTestUtil.populateSystemMetadata(jdbc);
        Map<String, String> tableMap = getTableMap(""); //use an empty prefix for this test

        List<SystemMetadataStatus> statusList = systemMetadataDao.listSystemMetadataStatus(0, 0,
                tableMap);

        Assert.assertTrue(statusList.size() == 10);

    }

    /*
     * Create a table map of standard system metadata table names to custom prefixed names
     */
    private Map<String, String> getTableMap(String prefix) {
        Map<String, String> tableMap = new HashMap<String, String>();
        tableMap.put(SystemMetadataDaoMetacatImpl.IDENTIFIER_TABLE, prefix + "identifier");
        tableMap.put(SystemMetadataDaoMetacatImpl.SYSMETA_TABLE, prefix + "systemmetadata");
        tableMap.put(SystemMetadataDaoMetacatImpl.SM_POLICY_TABLE, prefix + "smreplicationpolicy");
        tableMap.put(SystemMetadataDaoMetacatImpl.SM_STATUS_TABLE, prefix + "smreplicationstatus");
        tableMap.put(SystemMetadataDaoMetacatImpl.ACCESS_TABLE, prefix + "xml_access");
        return tableMap;
    }
}
