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
import org.dataone.service.types.v1.AccessPolicy;
import org.dataone.service.types.v1.AccessRule;
import org.dataone.service.types.v1.Checksum;
import org.dataone.service.types.v1.Identifier;
import org.dataone.service.types.v1.NodeReference;
import org.dataone.service.types.v1.ObjectFormatIdentifier;
import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Replica;
import org.dataone.service.types.v1.ReplicationPolicy;
import org.dataone.service.types.v1.ReplicationStatus;
import org.dataone.service.types.v1.Subject;
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

        systemMetadataDao.saveSystemMetadata(expectedSmd, SystemMetadataDaoMetacatImpl.tableMap);
        SystemMetadata actualSmd = systemMetadataDao.getSystemMetadata(expectedSmd.getIdentifier());

        SystemMetadataDaoMetacatImplTestUtil.verifySystemMetadataFields(expectedSmd, actualSmd);
    }

    @Test
    public void testComplexSaveSystemMetadata() throws DataAccessException {
        SystemMetadata expectedSmd = new SystemMetadata();
        // required (by dao) attributes - id, size, checksum
        Identifier id = new Identifier();
        id.setValue("testCompPid");
        expectedSmd.setIdentifier(id);
        expectedSmd.setSize(new BigInteger("45321"));

        Checksum checksum = new Checksum();
        checksum.setAlgorithm("MD5");
        checksum.setValue("e334wasf3w3akja03j2h3hj490ajh3101");
        expectedSmd.setChecksum(checksum);

        expectedSmd.setDateUploaded(new Date(System.currentTimeMillis()));
        expectedSmd.setSerialVersion(new BigInteger("8"));

        expectedSmd.setDateSysMetadataModified(new Date(System.currentTimeMillis()));

        Subject rightsHolder = new Subject();
        rightsHolder.setValue("test subject rights holder");
        expectedSmd.setRightsHolder(rightsHolder);

        NodeReference originNodeRef = new NodeReference();
        originNodeRef.setValue("urn:node:testOriginMN");
        expectedSmd.setOriginMemberNode(originNodeRef);

        NodeReference authNodeRef = new NodeReference();
        authNodeRef.setValue("urn:node:testAuthMN");
        expectedSmd.setAuthoritativeMemberNode(authNodeRef);

        Subject submitter = new Subject();
        submitter.setValue("test submitter subject");
        expectedSmd.setSubmitter(submitter);

        ObjectFormatIdentifier objectFormatIdentifier = new ObjectFormatIdentifier();
        objectFormatIdentifier.setValue("testFormatIdentifier");
        expectedSmd.setFormatId(objectFormatIdentifier);

        expectedSmd.setArchived(Boolean.FALSE);

        Identifier obsoletesId = new Identifier();
        obsoletesId.setValue("obsoletesPid");
        expectedSmd.setObsoletes(obsoletesId);

        Identifier obsoletedById = new Identifier();
        obsoletedById.setValue("obsoletedByPid");
        expectedSmd.setObsoletedBy(obsoletedById);

        // replication policy
        ReplicationPolicy replicationPolicy = new ReplicationPolicy();
        replicationPolicy.setReplicationAllowed(Boolean.TRUE);
        replicationPolicy.setNumberReplicas(Integer.valueOf(5));

        NodeReference preferred1 = new NodeReference();
        preferred1.setValue("urn:node:preferred1");
        replicationPolicy.addPreferredMemberNode(preferred1);

        NodeReference preferred2 = new NodeReference();
        preferred2.setValue("urn:node:preferred2");
        replicationPolicy.addPreferredMemberNode(preferred2);

        NodeReference blocked1 = new NodeReference();
        blocked1.setValue("urn:node:blockedA");
        replicationPolicy.addBlockedMemberNode(blocked1);

        NodeReference blocked2 = new NodeReference();
        blocked2.setValue("urn:node:blockedB");
        replicationPolicy.addBlockedMemberNode(blocked2);

        expectedSmd.setReplicationPolicy(replicationPolicy);

        // replication list
        Replica replica1 = new Replica();
        replica1.setReplicaMemberNode(preferred1);
        replica1.setReplicationStatus(ReplicationStatus.COMPLETED);
        replica1.setReplicaVerified(new Date(System.currentTimeMillis()));
        expectedSmd.addReplica(replica1);

        Replica replica2 = new Replica();
        replica2.setReplicaMemberNode(preferred2);
        replica2.setReplicationStatus(ReplicationStatus.FAILED);
        replica2.setReplicaVerified(new Date(System.currentTimeMillis()));
        expectedSmd.addReplica(replica2);

        Replica replica3 = new Replica();
        NodeReference cnNodeRef = new NodeReference();
        cnNodeRef.setValue("urn:node:cnDev");
        replica3.setReplicaMemberNode(cnNodeRef);
        replica3.setReplicationStatus(ReplicationStatus.REQUESTED);
        replica3.setReplicaVerified(new Date(System.currentTimeMillis()));
        expectedSmd.addReplica(replica3);

        // access policy
        AccessPolicy accessPolicy = new AccessPolicy();
        AccessRule allowRule1 = new AccessRule();
        allowRule1.addPermission(Permission.READ);
        Subject publicSub = new Subject();
        publicSub.setValue("public");
        allowRule1.addSubject(publicSub);
        accessPolicy.addAllow(allowRule1);

        AccessRule allowRule2 = new AccessRule();
        allowRule2.addPermission(Permission.READ);
        allowRule2.addPermission(Permission.WRITE);
        allowRule2.addPermission(Permission.CHANGE_PERMISSION);
        allowRule2.addSubject(rightsHolder);
        accessPolicy.addAllow(allowRule2);
        expectedSmd.setAccessPolicy(accessPolicy);

        systemMetadataDao.saveSystemMetadata(expectedSmd, SystemMetadataDaoMetacatImpl.tableMap);
        SystemMetadata actualSmd = systemMetadataDao.getSystemMetadata(expectedSmd.getIdentifier());

        SystemMetadataDaoMetacatImplTestUtil.verifySystemMetadataFields(expectedSmd, actualSmd);
    }
}
