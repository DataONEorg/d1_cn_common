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

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.SystemMetadata;
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
        int systemMetadataCount = systemMetadataDao.getSystemMetadataCount();
        Assert.assertTrue(systemMetadataCount == 10);
    }

    /**
     * Test listing of system metadata status DTOs
     */
    @Test
    public void testListSystemMetadataStatus() throws DataAccessException {
        SystemMetadataDaoMetacatImplTestUtil.populateSystemMetadata(jdbc);
        List<SystemMetadataStatus> statusList = systemMetadataDao.listSystemMetadataStatus(0, 0);
        Assert.assertTrue(statusList.size() == 10);
    }

    @Test
    public void testGetSystemMetadata() throws DataAccessException, ParseException {
        SystemMetadataDaoMetacatImplTestUtil.populateTablesWithTestA(jdbc);

        Identifier id = new Identifier();
        id.setValue("6f632bd1cc2772bdcc43bafdbb9d8669.1.1");
        SystemMetadata smd = systemMetadataDao.getSystemMetadata(id);
        Assert.assertNotNull(smd);

        SystemMetadataDaoMetacatImplTestUtil.verifyTestA(smd);
    }

    @Test
    public void testSimpleSaveSystemMetadata() throws DataAccessException {
        List<SystemMetadata> smdList = new ArrayList<SystemMetadata>();
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createSimpleSystemMetadata("testId",
                "123456"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createSimpleSystemMetadata("noise.1",
                "999"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createSimpleSystemMetadata("noise.3",
                "33331"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createSimpleSystemMetadata("foo", "1"));

        for (SystemMetadata smd : smdList) {
            systemMetadataDao.saveSystemMetadata(smd, SystemMetadataDaoMetacatImpl.tableMap);
        }

        Assert.assertEquals(smdList.size(), systemMetadataDao.getSystemMetadataCount());

        for (SystemMetadata smd : smdList) {
            SystemMetadata actualSmd = systemMetadataDao.getSystemMetadata(smd.getIdentifier());
            SystemMetadataDaoMetacatImplTestUtil.verifySystemMetadataFields(smd, actualSmd);
        }
    }

    @Test
    public void testComplexSaveSystemMetadata() throws DataAccessException {
        List<SystemMetadata> smdList = new ArrayList<SystemMetadata>();
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata("testCompPid",
                "12345"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata("noisePid1",
                "456"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata("noisePid.2",
                "1111"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata("noisePid.3",
                "2222"));

        for (SystemMetadata smd : smdList) {
            systemMetadataDao.saveSystemMetadata(smd, SystemMetadataDaoMetacatImpl.tableMap);
        }

        Assert.assertEquals(smdList.size(), systemMetadataDao.getSystemMetadataCount());

        for (SystemMetadata smd : smdList) {
            SystemMetadata actualSmd = systemMetadataDao.getSystemMetadata(smd.getIdentifier());
            SystemMetadataDaoMetacatImplTestUtil.verifySystemMetadataFields(smd, actualSmd);
        }
    }

    @Test
    public void testSimpleAndComplexSaveSystemMetadata() throws DataAccessException {
        List<SystemMetadata> smdList = new ArrayList<SystemMetadata>();
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata("testCompPid",
                "12345"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata("noisePid1",
                "456"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata("noisePid.2",
                "1111"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata("noisePid.3",
                "2222"));

        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createSimpleSystemMetadata(
                "simple.test-id", "887721"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createSimpleSystemMetadata("noise.99",
                "999"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createSimpleSystemMetadata("noise.3",
                "33331"));
        smdList.add(SystemMetadataDaoMetacatImplTestUtil.createSimpleSystemMetadata("foo", "1"));

        for (SystemMetadata smd : smdList) {
            systemMetadataDao.saveSystemMetadata(smd, SystemMetadataDaoMetacatImpl.tableMap);
        }
        Assert.assertEquals(smdList.size(), systemMetadataDao.getSystemMetadataCount());
        for (SystemMetadata smd : smdList) {
            SystemMetadata actualSmd = systemMetadataDao.getSystemMetadata(smd.getIdentifier());
            SystemMetadataDaoMetacatImplTestUtil.verifySystemMetadataFields(smd, actualSmd);
        }
    }

    @Test
    public void testUniquePidConstraint() throws DataAccessException {
        SystemMetadata smd1 = SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata(
                "pid1", "12345");
        SystemMetadata smd2 = SystemMetadataDaoMetacatImplTestUtil.createComplexSystemMetadata(
                "pid1", "456");
        Identifier id1 = systemMetadataDao.saveSystemMetadata(smd1,
                SystemMetadataDaoMetacatImpl.tableMap);
        Identifier id2 = systemMetadataDao.saveSystemMetadata(smd2,
                SystemMetadataDaoMetacatImpl.tableMap);
        Assert.assertEquals(1, systemMetadataDao.getSystemMetadataCount());
    }
}
