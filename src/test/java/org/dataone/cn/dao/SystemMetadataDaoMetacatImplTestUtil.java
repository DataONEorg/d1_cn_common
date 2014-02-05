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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import junit.framework.Assert;

import org.dataone.configuration.Settings;
import org.dataone.service.types.v1.AccessRule;
import org.dataone.service.types.v1.Permission;
import org.dataone.service.types.v1.Replica;
import org.dataone.service.types.v1.ReplicationStatus;
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
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1','urn:node:testNode2','completed','2013-08-05 16:40:00.000');";
        jdbc.execute(statusStatement);

        statusStatement = "INSERT INTO "
                + statusTable
                + " VALUES ('6f632bd1cc2772bdcc43bafdbb9d8669.1.1','urn:node:testNode4','queued','2013-08-05 16:25:00.000');";
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
                .getPermissionList()
                .contains(Permission.READ));
        Assert.assertTrue("Allow rule all permission missing WRITE persmission", rule
                .getPermissionList().contains(Permission.WRITE));
        Assert.assertTrue("Allow rule all permission missing CHANGE persmission", rule
                .getPermissionList().contains(Permission.CHANGE_PERMISSION));
    }
}
