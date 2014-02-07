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

import java.math.BigInteger;
import java.text.ParseException;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.types.v1.Checksum;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.ObjectFormatIdentifier;
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
    public void testSaveSystemMetadata() throws DataAccessException {
        SystemMetadata expectedSmd = new SystemMetadata();
        // required (by dao) attributes - id, size, checksum
        Identifier id = new Identifier();
        id.setValue("testId");
        expectedSmd.setIdentifier(id);
        expectedSmd.setSize(new BigInteger("123456"));

        Checksum checksum = new Checksum();
        checksum.setAlgorithm("MD5");
        checksum.setValue("e3l2k4kja03j2h3hj490ajh3101");
        expectedSmd.setChecksum(checksum);

        expectedSmd.setDateUploaded(new Date(System.currentTimeMillis()));
        expectedSmd.setSerialVersion(new BigInteger("87"));

        ObjectFormatIdentifier objectFormatIdentifier = new ObjectFormatIdentifier();
        objectFormatIdentifier.setValue("testFormatIdentifier");
        expectedSmd.setFormatId(objectFormatIdentifier);

        systemMetadataDao.saveSystemMetadata(expectedSmd, SystemMetadataDaoMetacatImpl.tableMap);
        SystemMetadata actualSmd = systemMetadataDao.getSystemMetadata(expectedSmd.getIdentifier());

        SystemMetadataDaoMetacatImplTestUtil.verifySystemMetadataFields(expectedSmd, actualSmd);
    }
}
