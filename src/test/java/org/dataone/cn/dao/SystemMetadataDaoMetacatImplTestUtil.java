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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import junit.framework.Assert;

import org.dataone.configuration.Settings;
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
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * A utility class to ensure that the Metacat data tables are in place for testing
 * 
 * @author cjones
 */
public class SystemMetadataDaoMetacatImplTestUtil {

    private static final DateFormat uploadFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final DateFormat modFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    // provide the CN router id for insert statements
    public static final String cnNodeId = Settings.getConfiguration().getString("cn.router.nodeId",
            "urn:node:cnDev");

    public static void createTables(JdbcTemplate jdbc) {
        SystemMetadataDaoMetacatImplTestUtil.createTables(jdbc,
                SystemMetadataDaoMetacatImpl.IDENTIFIER_TABLE,
                SystemMetadataDaoMetacatImpl.SYSMETA_TABLE,
                SystemMetadataDaoMetacatImpl.SM_POLICY_TABLE,
                SystemMetadataDaoMetacatImpl.SM_STATUS_TABLE,
                SystemMetadataDaoMetacatImpl.ACCESS_TABLE);
    }

    /**
     * Create the Metacat tables of interest to testing
     * 
     * @param jdbc  the JdbcTemplate used to execute the statements
     */
    public static void createTables(JdbcTemplate jdbc, String identiferTable, String sysMetaTable,
            String replicaPolicyTable, String replicaStatusTable, String accessTable) {

        // identifier table
        jdbc.execute("CREATE TABLE IF NOT EXISTS "
                + identiferTable
                + " (                                            "
                + "  guid text NOT NULL,                        "
                + "  docid character varying(250),              "
                + "  rev bigint,                                " //
                + "  CONSTRAINT " + identiferTable + "_pk PRIMARY KEY (guid)"
                + ");	                                          ");

        // systemmetadata table
        jdbc.execute("CREATE TABLE IF NOT EXISTS " + sysMetaTable + "   "
                + " (                                                  "
                + "  guid text NOT NULL,                              "
                + "  serial_version character varying(256),           "
                + "  date_uploaded timestamp,                         "
                + "  rights_holder character varying(250),            "
                + "  checksum character varying(512),                 "
                + "  checksum_algorithm character varying(250),       "
                + "  origin_member_node character varying(250),       "
                + "  authoritive_member_node character varying(250),  "
                + "  date_modified timestamp,					        "
                + "  submitter character varying(256),                "
                + "  object_format character varying(256),            "
                + "  size character varying(256),                     "
                + "  archived boolean,                                "
                + "  replication_allowed boolean,                     "
                + "  number_replicas bigint,                          "
                + "  obsoletes text,                                  "
                + "  obsoleted_by text,                               " //
                + "  CONSTRAINT " + sysMetaTable + "_pk PRIMARY KEY (guid));");

        // xml_access table
        jdbc.execute("CREATE TABLE IF NOT EXISTS " + accessTable
                + "   "
                + " (                                                       "
                + "  guid text,                                            "
                + "  accessfileid text,                                    "
                + "  principal_name character varying(100),                "
                + "  permission bigint,                                    "
                + "  perm_type character varying(32),                      "
                + "  perm_order character varying(32),                     "
                + "  begin_time date,                                      "
                + "  end_time date,                                        "
                + "  ticket_count bigint,                                  "
                + "  subtreeid character varying(32),                      "
                + "  startnodeid bigint,                                   "
                + "  endnodeid bigint,                                     " //
                + "  CONSTRAINT " + accessTable + "_ck CHECK (begin_time < end_time)"
                + ");                                                      ");

        // smreplicationpolicy table
        jdbc.execute("CREATE TABLE IF NOT EXISTS " + replicaPolicyTable + "      "
                + " (                                                                 "
                + "  guid text,                                                      "
                + "  member_node character varying(250),                             "
                + "  policy text,                                                    "
                + "  CONSTRAINT " + replicaPolicyTable + "_fk FOREIGN KEY (guid) "
                + "  REFERENCES " + sysMetaTable + " (guid));                    ");

        // smreplicationstatus table
        jdbc.execute("CREATE TABLE IF NOT EXISTS " + replicaStatusTable + "      "
                + " (                                                                 "
                + "  guid text,                                                      "
                + "  member_node character varying(250),                             "
                + "  status character varying(250),                                  "
                + "  date_verified timestamp,                                        "
                + "  CONSTRAINT " + replicaStatusTable + "_fk FOREIGN KEY (guid) "
                + "  REFERENCES " + sysMetaTable + " (guid));                     ");

    }

    public static void dropTables(JdbcTemplate jdbc) {
        SystemMetadataDaoMetacatImplTestUtil.dropTables(jdbc,
                SystemMetadataDaoMetacatImpl.IDENTIFIER_TABLE,
                SystemMetadataDaoMetacatImpl.SYSMETA_TABLE,
                SystemMetadataDaoMetacatImpl.SM_POLICY_TABLE,
                SystemMetadataDaoMetacatImpl.SM_STATUS_TABLE,
                SystemMetadataDaoMetacatImpl.ACCESS_TABLE);
    }

    /**
     * Drop all database tables used for testing
     * 
     * @param jdbc  the JdbcTemplate used to execute the statements
     */
    public static void dropTables(JdbcTemplate jdbc, String identifierTable, String sysmetaTable,
            String replicaPolicyTable, String replicaStatusTable, String accessTable) {

        // Drop all tables in the correct order
        jdbc.execute("DROP TABLE IF EXISTS " + replicaStatusTable + ";");
        jdbc.execute("DROP TABLE IF EXISTS " + replicaPolicyTable + ";");
        jdbc.execute("DROP TABLE IF EXISTS " + accessTable + ";");
        jdbc.execute("DROP TABLE IF EXISTS " + sysmetaTable + ";");
        jdbc.execute("DROP TABLE IF EXISTS " + identifierTable + ";");

    }

    public static void populateSystemMetadata(JdbcTemplate jdbc) {
        SystemMetadataDaoMetacatImplTestUtil.populateSystemMetadata(jdbc,
                SystemMetadataDaoMetacatImpl.SYSMETA_TABLE);
    }

    public static void populateSystemMetadata(JdbcTemplate jdbc, String sysMetaTable) {

        String str = new String();
        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('891f20463b023bef25758cabee18b460.1.1', '1', '2013-08-01 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'c3450ab5803151f78e9e8f91f30f285a', 'MD5', '', '', '2013-08-01 10:17:42.117', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.1.0', '7482', false, false, -1, NULL, NULL);";
        jdbc.execute(str);

        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', '709eee7f02ff1f12a9084b906ee0770e', 'MD5', '', '', '2013-07-31 15:29:44.429', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '14230', false, false, -1, NULL, NULL);";
        jdbc.execute(str);

        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('261585355b62129e038fabdacc1cd9fa.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eb0b66914142de3e6b2ed949f87096de', 'MD5', '', '', '2013-07-31 15:29:46.774', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '14472', false, false, -1, NULL, NULL);";
        jdbc.execute(str);

        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('pisco3-e2fab857d20b994edb1ad65069407423.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', '14b343527abc4892fb3b7e50b0b4b096', 'MD5', '', '', '2013-07-31 17:39:09.264', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '14514', false, false, -1, NULL, NULL);";
        jdbc.execute(str);

        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('pisco3-f6fcf091959497c2a77967b732d0e631.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'de2c9e3c4a700e3816d4140b8319cce0', 'MD5', '', '', '2013-07-31 17:39:11.085', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '13424', false, false, -1, NULL, NULL);";
        jdbc.execute(str);

        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('pisco-test-6f632bd1cc2772bdcc43bafdbb9d8669.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', '709eee7f02ff1f12a9084b906ee0770e', 'MD5', '', '', '2013-07-31 17:41:34.387', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '14230', false, false, -1, NULL, NULL);";
        jdbc.execute(str);

        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('pisco-test-261585355b62129e038fabdacc1cd9fa.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eb0b66914142de3e6b2ed949f87096de', 'MD5', '', '', '2013-07-31 17:41:36.253', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '14472', false, false, -1, NULL, NULL);";
        jdbc.execute(str);

        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('pisco-test-e2fab857d20b994edb1ad65069407423.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', '14b343527abc4892fb3b7e50b0b4b096', 'MD5', '', '', '2013-07-31 17:41:38.243', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '14514', false, false, -1, NULL, NULL);";
        jdbc.execute(str);

        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('pisco-test-f6fcf091959497c2a77967b732d0e631.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'de2c9e3c4a700e3816d4140b8319cce0', 'MD5', '', '', '2013-07-31 17:41:40.2', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '13424', false, false, -1, NULL, NULL);  ";
        jdbc.execute(str);

        str = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('pisco-test-774d0eb40bb051046a5469be7d912d30.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', '1842c6ce6c398afe62f15a01063b39a9', 'MD5', '', '', '2013-07-31 17:41:42.331', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '17211', false, false, -1, NULL, NULL);";
        jdbc.execute(str);
    }

    public static void populateTablesWithTestA(JdbcTemplate jdbc) {
        populateTablesWithTestA(jdbc, SystemMetadataDaoMetacatImpl.IDENTIFIER_TABLE,
                SystemMetadataDaoMetacatImpl.SYSMETA_TABLE,
                SystemMetadataDaoMetacatImpl.SM_POLICY_TABLE,
                SystemMetadataDaoMetacatImpl.SM_STATUS_TABLE,
                SystemMetadataDaoMetacatImpl.ACCESS_TABLE);
    }

    public static void populateTablesWithTestA(JdbcTemplate jdbc, String idTable,
            String sysMetaTable, String policyTable, String statusTable, String accessTable) {

        String identifierStatement = "INSERT INTO "
                + idTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1', '6f632bd1cc2772bdcc43bafdbb9d8669.1', 1);";
        jdbc.execute(identifierStatement);

        String sysMetaStatement = "INSERT INTO "
                + sysMetaTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1', '1', '2013-07-31 17:00:00', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', '709eee7f02ff1f12a9084b906ee0770e', 'MD5', 'urn:node:testSource', 'urn:node:testSource', '2013-07-31 15:29:44.429', 'uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org', 'eml://ecoinformatics.org/eml-2.0.1', '14230', false, false, 1, '6f632bd1cc2772bdcc43bafdbb9d8669.1.0', '6f632bd1cc2772bdcc43bafdbb9d8669.1.2');";
        jdbc.execute(sysMetaStatement);

        String accessStatement = "INSERT INTO "
                + accessTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1', NULL, 'public', 4, 'allow', 'allowFirst', NULL, NULL, NULL, NULL, NULL, NULL);";
        jdbc.execute(accessStatement);

        accessStatement = "INSERT INTO "
                + accessTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1', NULL, 'testSubject', 7, 'allow', 'allowFirst', NULL, NULL, NULL, NULL, NULL, NULL);";
        jdbc.execute(accessStatement);

        String policyStatement = "INSERT INTO "
                + policyTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1', 'urn:node:testNode2', 'preferred');";
        jdbc.execute(policyStatement);

        policyStatement = "INSERT INTO "
                + policyTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1', 'urn:node:testNode4', 'preferred');";
        jdbc.execute(policyStatement);

        policyStatement = "INSERT INTO "
                + policyTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1', 'urn:node:testNode3', 'blocked');";
        jdbc.execute(policyStatement);

        String statusStatement = "INSERT INTO "
                + statusTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1','urn:node:testNode2','COMPLETED','2013-08-05 16:40:00.000');";
        jdbc.execute(statusStatement);

        statusStatement = "INSERT INTO "
                + statusTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1','urn:node:testNode4','QUEUED','2013-08-05 16:25:00.000');";
        jdbc.execute(statusStatement);
    }

    public static void verifyTestA(SystemMetadata sysMeta) throws ParseException {
        Assert.assertEquals("Identifier does not match.", "6f632bd1cc2772bdcc43bafdbb9d8669.1.1",
                sysMeta.getIdentifier().getValue());

        Assert.assertEquals("Serial Version does not match.", 1, sysMeta.getSerialVersion()
                .intValue());

        Assert.assertEquals("Uploaded Date does not match.",
                uploadFormat.parse("2013-07-31 17:00:00"), sysMeta.getDateUploaded());

        Assert.assertEquals("Rights holder does not match",
                "uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org", sysMeta.getRightsHolder().getValue());

        Assert.assertEquals("Checksum does not match", "709eee7f02ff1f12a9084b906ee0770e", sysMeta
                .getChecksum().getValue());

        Assert.assertEquals("Checksum algo does not match", "MD5", sysMeta.getChecksum()
                .getAlgorithm());

        Assert.assertEquals("Origin Member node does not match", "urn:node:testSource", sysMeta
                .getOriginMemberNode().getValue());

        Assert.assertEquals("Authoritative Member node does not match", "urn:node:testSource",
                sysMeta.getAuthoritativeMemberNode().getValue());

        Assert.assertEquals("Modified Date does not match",
                modFormat.parse("2013-07-31 15:29:44.429"), sysMeta.getDateSysMetadataModified());

        Assert.assertEquals("Submitter does not match",
                "uid=cjones,o=NCEAS,dc=ecoinformatics,dc=org", sysMeta.getSubmitter().getValue());

        Assert.assertEquals("Object format does not match", "eml://ecoinformatics.org/eml-2.0.1",
                sysMeta.getFormatId().getValue());

        Assert.assertEquals("Size does not match", 14230, sysMeta.getSize().intValue());

        Assert.assertEquals("Archived does not match", false, sysMeta.getArchived().booleanValue());

        Assert.assertEquals("Obsoletes does not match", "6f632bd1cc2772bdcc43bafdbb9d8669.1.0",
                sysMeta.getObsoletes().getValue());

        Assert.assertEquals("ObsoletedBy does not match", "6f632bd1cc2772bdcc43bafdbb9d8669.1.2",
                sysMeta.getObsoletedBy().getValue());

        // verify replica policy
        Assert.assertNotNull("Replication Policy should not be null",
                sysMeta.getReplicationPolicy());

        Assert.assertEquals("Replication allowed does not match", false, sysMeta
                .getReplicationPolicy().getReplicationAllowed().booleanValue());

        Assert.assertEquals("Number replicas does not match", 1, sysMeta.getReplicationPolicy()
                .getNumberReplicas().intValue());

        Assert.assertEquals("Number of preferred replica nodes does not match", 2, sysMeta
                .getReplicationPolicy().sizePreferredMemberNodeList());

        Assert.assertEquals("Preferred replica node does not match", "urn:node:testNode2", sysMeta
                .getReplicationPolicy().getPreferredMemberNode(0).getValue());

        Assert.assertEquals("Preferred replica node does not match", "urn:node:testNode4", sysMeta
                .getReplicationPolicy().getPreferredMemberNode(1).getValue());

        Assert.assertEquals("Number of blocked replica nodes does not match", 1, sysMeta
                .getReplicationPolicy().sizeBlockedMemberNodeList(), 1);

        // verify replica list
        Assert.assertEquals("Replica list size is wrong", 2, sysMeta.sizeReplicaList());

        Replica replica = sysMeta.getReplica(0);

        Assert.assertEquals("Replica node does not match", "urn:node:testNode2", replica
                .getReplicaMemberNode().getValue());

        Assert.assertEquals("Replica status does not match", ReplicationStatus.COMPLETED,
                replica.getReplicationStatus());

        Assert.assertEquals("Replica status date does not match",
                modFormat.parse("2013-08-05 16:40:00.000"), replica.getReplicaVerified());

        replica = sysMeta.getReplica(1);

        Assert.assertEquals("Replica node does not match", "urn:node:testNode4", replica
                .getReplicaMemberNode().getValue());

        Assert.assertEquals("Replica status does not match", ReplicationStatus.QUEUED,
                replica.getReplicationStatus());

        Assert.assertEquals("Replica status date does not match",
                modFormat.parse("2013-08-05 16:25:00.000"), replica.getReplicaVerified());

        // verify access policy
        Assert.assertNotNull("Access policy should not be null", sysMeta.getAccessPolicy());

        Assert.assertEquals("Access policy allowed list size is wrong", 2, sysMeta
                .getAccessPolicy().sizeAllowList());

        AccessRule rule = sysMeta.getAccessPolicy().getAllow(0);
        Assert.assertEquals("Allow rule subject list size is off", 1, rule.sizeSubjectList());

        Assert.assertEquals("Access policy allow subject does not match", "public", rule
                .getSubject(0).getValue());

        Assert.assertEquals("Allow rule permission size is off", 1, rule.sizePermissionList());
        Assert.assertEquals("Allow rule permission does not match", Permission.READ,
                rule.getPermission(0));

        rule = sysMeta.getAccessPolicy().getAllow(1);
        Assert.assertEquals("Allow rule subject list size is off", 1, rule.sizeSubjectList());

        Assert.assertEquals("Access policy allow subject does not match", "testSubject", rule
                .getSubject(0).getValue());

        Assert.assertEquals("Allow rule permission size is off", 3, rule.sizePermissionList());
        Assert.assertTrue("Allow rule all permission missing READ persmission", rule
                .getPermissionList().contains(Permission.READ));
        Assert.assertTrue("Allow rule all permission missing WRITE persmission", rule
                .getPermissionList().contains(Permission.WRITE));
        Assert.assertTrue("Allow rule all permission missing CHANGE persmission", rule
                .getPermissionList().contains(Permission.CHANGE_PERMISSION));
    }

    public static void verifySystemMetadataFields(SystemMetadata expected, SystemMetadata actual) {

        // required (by dao)
        Assert.assertEquals("Identifier does not match", expected.getIdentifier(),
                actual.getIdentifier());

        // required (by dao)
        Assert.assertEquals("Checksum does not match", expected.getChecksum().getValue(), actual
                .getChecksum().getValue());

        Assert.assertEquals("Checksum algo does not match", expected.getChecksum().getAlgorithm(),
                actual.getChecksum().getAlgorithm());

        // required (by dao)
        Assert.assertEquals("Size does not match", expected.getSize().intValue(), actual.getSize()
                .intValue());

        Assert.assertEquals("Serial Version does not match.", expected.getSerialVersion()
                .intValue(), actual.getSerialVersion().intValue());

        Assert.assertEquals("Uploaded Date does not match.", expected.getDateUploaded(),
                actual.getDateUploaded());

        Assert.assertEquals("Modified Date does not match", expected.getDateSysMetadataModified(),
                actual.getDateSysMetadataModified());

        if (expected.getRightsHolder() != null && expected.getRightsHolder().getValue() != null) {
            Assert.assertEquals("Rights holder does not match", expected.getRightsHolder()
                    .getValue(), actual.getRightsHolder().getValue());
        } else if (actual.getRightsHolder() != null) {
            Assert.assertNull(actual.getRightsHolder().getValue());
        } else {
            Assert.assertNull(actual.getRightsHolder());
        }

        if (expected.getOriginMemberNode() != null
                && expected.getOriginMemberNode().getValue() != null) {
            Assert.assertEquals("Origin Member node does not match", expected.getOriginMemberNode()
                    .getValue(), actual.getOriginMemberNode().getValue());
        } else if (actual.getOriginMemberNode() != null) {
            Assert.assertNull(actual.getOriginMemberNode().getValue());
        } else {
            Assert.assertNull(actual.getOriginMemberNode());
        }

        if (expected.getAuthoritativeMemberNode() != null
                && expected.getAuthoritativeMemberNode().getValue() != null) {
            Assert.assertEquals("Authoritative Member node does not match", expected
                    .getAuthoritativeMemberNode().getValue(), actual.getAuthoritativeMemberNode()
                    .getValue());
        } else if (actual.getAuthoritativeMemberNode() != null) {
            Assert.assertNull(actual.getAuthoritativeMemberNode().getValue());
        } else {
            Assert.assertNull(actual.getAuthoritativeMemberNode());
        }

        if (expected.getSubmitter() != null && expected.getSubmitter().getValue() != null) {
            Assert.assertEquals("Submitter does not match", expected.getSubmitter().getValue(),
                    actual.getSubmitter().getValue());
        } else if (actual.getSubmitter() != null) {
            Assert.assertNull(actual.getSubmitter().getValue());
        } else {
            Assert.assertNull(actual.getSubmitter());
        }

        if (expected.getFormatId() != null && expected.getFormatId().getValue() != null) {
            Assert.assertEquals("Object format does not match", expected.getFormatId().getValue(),
                    actual.getFormatId().getValue());
        } else if (actual.getFormatId() != null) {
            Assert.assertNull(actual.getFormatId().getValue());
        } else {
            Assert.assertNull(actual.getFormatId());
        }

        if (expected.getArchived() != null) {
            Assert.assertEquals("Archived does not match", expected.getArchived().booleanValue(),
                    actual.getArchived().booleanValue());
        } else if (actual.getArchived() != null) {
            Assert.assertFalse(actual.getArchived().booleanValue());
        }

        if (expected.getObsoletes() != null && expected.getObsoletes().getValue() != null) {
            Assert.assertEquals("Obsoletes does not match", expected.getObsoletes().getValue(),
                    actual.getObsoletes().getValue());
        } else if (actual.getObsoletes() != null) {
            Assert.assertNull(actual.getObsoletes().getValue());
        } else {
            Assert.assertNull(actual.getObsoletes());
        }

        if (expected.getObsoletedBy() != null && expected.getObsoletedBy().getValue() != null) {
            Assert.assertEquals("ObsoletedBy does not match", expected.getObsoletedBy().getValue(),
                    actual.getObsoletedBy().getValue());
        } else if (actual.getObsoletedBy() != null) {
            Assert.assertNull(actual.getObsoletedBy().getValue());
        } else {
            Assert.assertNull(actual.getObsoletedBy());
        }

        // verify replica policy
        if (expected.getReplicationPolicy() != null) {
            if (expected.getReplicationPolicy().getReplicationAllowed() != null) {
                Assert.assertEquals("Replication allowed does not match", expected
                        .getReplicationPolicy().getReplicationAllowed().booleanValue(), actual
                        .getReplicationPolicy().getReplicationAllowed().booleanValue());
            } else if (actual.getReplicationPolicy().getReplicationAllowed() != null) {
                Assert.assertFalse(actual.getReplicationPolicy().getReplicationAllowed()
                        .booleanValue());
            } else {
                Assert.assertNull(actual.getReplicationPolicy().getReplicationAllowed());
            }

            if (expected.getReplicationPolicy().getNumberReplicas() != null) {
                Assert.assertEquals("Number replicas does not match", expected
                        .getReplicationPolicy().getNumberReplicas().intValue(), actual
                        .getReplicationPolicy().getNumberReplicas().intValue());
            } else if (actual.getReplicationPolicy().getNumberReplicas() != null) {
                Assert.assertEquals(0, actual.getReplicationPolicy().getNumberReplicas().intValue());
            } else {
                Assert.assertNull(actual.getReplicationPolicy().getNumberReplicas());
            }

            Assert.assertEquals("Number of preferred replica nodes does not match", expected
                    .getReplicationPolicy().sizePreferredMemberNodeList(), actual
                    .getReplicationPolicy().sizePreferredMemberNodeList());
            for (NodeReference expectedNodeRef : expected.getReplicationPolicy()
                    .getPreferredMemberNodeList()) {
                boolean nodeMatch = false;
                for (NodeReference actualNodeRef : actual.getReplicationPolicy()
                        .getPreferredMemberNodeList()) {
                    if (expectedNodeRef.getValue().equals(actualNodeRef.getValue())) {
                        nodeMatch = true;
                        break;
                    }
                }
                Assert.assertTrue("Missing preferred node", nodeMatch);
            }

            Assert.assertEquals("Number of blocked replica nodes does not match", expected
                    .getReplicationPolicy().sizeBlockedMemberNodeList(), actual
                    .getReplicationPolicy().sizeBlockedMemberNodeList());
            for (NodeReference expectedNodeRef : expected.getReplicationPolicy()
                    .getBlockedMemberNodeList()) {
                boolean nodeMatch = false;
                for (NodeReference actualNodeRef : actual.getReplicationPolicy()
                        .getBlockedMemberNodeList()) {
                    if (expectedNodeRef.getValue().equals(actualNodeRef.getValue())) {
                        nodeMatch = true;
                        break;
                    }
                }
                Assert.assertTrue("Missing blocked node", nodeMatch);
            }

        } else if (actual.getReplicationPolicy() != null) {
            Assert.assertTrue(actual.getReplicationPolicy().getReplicationAllowed() == null
                    || actual.getReplicationPolicy().getReplicationAllowed().booleanValue() == false);
            Assert.assertTrue(actual.getReplicationPolicy().getNumberReplicas() == null
                    || actual.getReplicationPolicy().getNumberReplicas().intValue() == 0);
            Assert.assertEquals(0, actual.getReplicationPolicy().sizeBlockedMemberNodeList());
            Assert.assertEquals(0, actual.getReplicationPolicy().sizePreferredMemberNodeList());
        } else {
            Assert.assertNull(actual.getReplicationPolicy());
        }

        // verify replica list
        Assert.assertEquals("Replica list sizes are different", expected.sizeReplicaList(),
                actual.sizeReplicaList());
        for (Replica expectedReplica : expected.getReplicaList()) {
            Replica actualReplica = getReplicaForMN(expectedReplica.getReplicaMemberNode(),
                    actual.getReplicaList());

            Assert.assertNotNull("Replica not found for node", actualReplica);

            Assert.assertEquals("Replica node does not match",
                    expectedReplica.getReplicaMemberNode(), actualReplica.getReplicaMemberNode());

            Assert.assertEquals("Replica status does not match",
                    expectedReplica.getReplicationStatus(), actualReplica.getReplicationStatus());

            Assert.assertEquals("Replica verified date does not match",
                    expectedReplica.getReplicaVerified(), actualReplica.getReplicaVerified());
        }

        // verify access policy
        if (expected.getAccessPolicy() != null) {
            Assert.assertEquals("Access policy allowed list size do not match", expected
                    .getAccessPolicy().sizeAllowList(), actual.getAccessPolicy().sizeAllowList());
            boolean accessPolicyMatch = areReplicPolicyEquals(expected, actual);
            Assert.assertTrue("Access Policy do not match", accessPolicyMatch);
        } else if (actual.getAccessPolicy() != null) {
            Assert.assertEquals(0, actual.getAccessPolicy().sizeAllowList());
        } else {
            Assert.assertNull(actual.getAccessPolicy());
        }
    }

    private static boolean areReplicPolicyEquals(SystemMetadata expected, SystemMetadata actual) {
        boolean accessPolicyMatch = true;
        for (AccessRule expectedAllowRule : expected.getAccessPolicy().getAllowList()) {
            boolean allowRuleMatch = false;
            for (AccessRule actualAllowRule : actual.getAccessPolicy().getAllowList()) {
                if (expectedAllowRule.sizeSubjectList() == actualAllowRule.sizeSubjectList()
                        && expectedAllowRule.sizePermissionList() == actualAllowRule
                                .sizePermissionList()) {
                    boolean allSubjectMatch = true;
                    boolean allPermissionMatch = true;
                    for (Subject expectedSubject : expectedAllowRule.getSubjectList()) {
                        boolean subjectMatch = false;
                        for (Subject actualSubject : actualAllowRule.getSubjectList()) {
                            if (expectedSubject.getValue().equals(actualSubject.getValue())) {
                                subjectMatch = true;
                                break;
                            }
                        }
                        if (subjectMatch == false) {
                            allSubjectMatch = false;
                            break;
                        }
                    }
                    if (allSubjectMatch == true) {
                        for (Permission expectedPermission : expectedAllowRule.getPermissionList()) {
                            boolean permissionMatch = false;
                            for (Permission actualPermission : actualAllowRule.getPermissionList()) {
                                if (expectedPermission.equals(actualPermission)) {
                                    permissionMatch = true;
                                    break;
                                }
                            }
                            if (permissionMatch == false) {
                                allPermissionMatch = false;
                                break;
                            }
                        }
                    }
                    if (allSubjectMatch == true && allPermissionMatch == true) {
                        allowRuleMatch = true;
                        break;
                    }
                }
            }
            if (allowRuleMatch == false) {
                accessPolicyMatch = false;
                break;
            }
        }
        return accessPolicyMatch;
    }

    private static Replica getReplicaForMN(NodeReference targetMN, List<Replica> replicaList) {
        if (targetMN == null || replicaList == null) {
            return null;
        }
        Replica match = null;
        for (Replica replica : replicaList) {
            if (replica.getReplicaMemberNode().equals(targetMN)) {
                match = replica;
                break;
            }
        }
        return match;
    }

    public static SystemMetadata createSimpleSystemMetadata(String pidValue, String size) {
        SystemMetadata expectedSmd = new SystemMetadata();
        // required (by dao) attributes - id, size, checksum
        Identifier id = new Identifier();
        id.setValue(pidValue);
        expectedSmd.setIdentifier(id);
        expectedSmd.setSize(new BigInteger(size));

        Checksum checksum = new Checksum();
        checksum.setAlgorithm("MD5");
        checksum.setValue("e3l2k4kja03j2h3hj490ajh3101");
        expectedSmd.setChecksum(checksum);

        expectedSmd.setDateUploaded(new Date(System.currentTimeMillis()));
        expectedSmd.setSerialVersion(new BigInteger("87"));
        return expectedSmd;
    }

    public static SystemMetadata createComplexSystemMetadata(String pidValue, String size) {
        SystemMetadata expectedSmd = new SystemMetadata();
        // required (by dao) attributes - id, size, checksum
        Identifier id = new Identifier();
        id.setValue(pidValue);
        expectedSmd.setIdentifier(id);
        expectedSmd.setSize(new BigInteger(size));

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

        return expectedSmd;
    }
}
