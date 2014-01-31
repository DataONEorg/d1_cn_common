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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
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
import org.dataone.service.util.TypeMarshaller;
import org.jibx.runtime.JiBXException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;

/**
 * A concrete implementation of the systemMetadataDao inteface against the Metacat 
 * database tables (systemmetadata, smreplicationpolicy, smreplicationstatus, xml_access).
 * Only use this class for audit/repair purposes - not general access to the underlying database
 * 
 * @author cjones
 */
public class SystemMetadataDaoMetacatImpl implements SystemMetadataDao {

    private static final Log log = LogFactory.getLog(SystemMetadataDaoMetacatImpl.class);

    protected static int documentIdCounter = 0;
    public static final String IDENTIFIER_TABLE = "identifier";
    public static final String SYSMETA_TABLE = "systemmetadata";
    public static final String SM_POLICY_TABLE = "smreplicationpolicy";
    public static final String SM_STATUS_TABLE = "smreplicationstatus";
    public static final String ACCESS_TABLE = "xml_access";

    private JdbcTemplate jdbcTemplate;
    private Map<String, String> tableMap;

    /**
     * Constructor. Creates an instance of SystemMetadataDaoMetacatImpl
     */
    public SystemMetadataDaoMetacatImpl() {
        jdbcTemplate = new JdbcTemplate(DataSourceFactory.getMetacatDataSource());
    }

    /**
     * Constructor. Creates an instance of SystemMetadataDaoMetacatImpl using the given data source
     */
    public SystemMetadataDaoMetacatImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#getSystemMetadataCount()
     */
    @Override
    public int getSystemMetadataCount(Map<String, String> tableMap) throws DataAccessException {

        // query the systemmetadata table
        String sqlStatement = "SELECT count(guid) FROM " + (String) tableMap.get(SYSMETA_TABLE);

        int count = 0;
        try {
            count = jdbcTemplate.queryForInt(sqlStatement);

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);

        }

        return count;
    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#listSystemMetadata()
     */
    @Override
    public List<SystemMetadataStatus> listSystemMetadataStatus(int pageNumber, int pageSize,
            Map<String, String> tableMap) throws DataAccessException {

        List<SystemMetadataStatus> sysMetaStatusList = new ArrayList<SystemMetadataStatus>();

        // reset negative page numbers and sizes
        if (pageNumber < 1) {
            pageNumber = 0;
        }
        if (pageSize < 0) {
            pageSize = 0;
        }

        final int finalPageNumber = pageNumber;
        final int finalPageSize = pageSize;
        final int offset = (pageNumber - 1) * pageSize;
        final Map<String, String> finalTableMap = tableMap;
        try {
            // populate the systemMetadataStatus list with rows from the database
            sysMetaStatusList = this.jdbcTemplate.query(new PreparedStatementCreator() {
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {

                    String sqlStatement = "SELECT guid, serial_version, date_modified, archived FROM "
                            + finalTableMap.get(SYSMETA_TABLE) + " ORDER BY guid";

                    if (finalPageSize > 0 && finalPageNumber > 0) {
                        sqlStatement += " LIMIT " + finalPageSize;
                    }

                    if (finalPageNumber > 0) {
                        sqlStatement += " OFFSET " + offset;
                    }

                    sqlStatement += ";";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    log.debug("sysMetaStatusList statement is: " + statement);
                    return statement;
                }
            }, new SystemMetadataStatusMapper());

        } catch (org.springframework.dao.DataAccessException dae) {
            throw new DataAccessException(dae);

        }

        return sysMetaStatusList;
    }

    /*
     * Get the list of access rules for a given
     * @param pid
     * @param tableMap
     * @return
     * @throws DataAccessException
     */
    private List<AccessRule> listAccessRules(final Identifier pid, Map<String, String> tableMap)
            throws SQLException {
        List<AccessRule> accessEntries = new ArrayList<AccessRule>();
        final Map<String, String> finalTableMap = tableMap;

        accessEntries = this.jdbcTemplate.query(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                String sqlStatement = "SELECT guid, principal_name, permission FROM "
                        + finalTableMap.get(ACCESS_TABLE)
                        + " WHERE perm_type = 'allow' AND guid = ?;";

                PreparedStatement statement = conn.prepareStatement(sqlStatement);
                statement.setString(1, pid.getValue());

                return statement;
            }

        }, new AccessRuleMapper());
        return accessEntries;
    }

    /*
     * Get the replica entries list for a given identifier.  This returns the replica status for 
     * each entry, along with the member node and date verified fields.
     * @param pid
     * @return
     * @throws DataAccessException
     */
    private List<Replica> listReplicaEntries(Identifier pid, Map<String, String> tableMap)
            throws SQLException {

        List<Replica> replicaEntries = new ArrayList<Replica>();
        final Map<String, String> finalTableMap = tableMap;
        replicaEntries = this.jdbcTemplate.query(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                String sqlStatement = "SELECT guid, member_node, status, date_verified FROM "
                        + finalTableMap.get(SM_STATUS_TABLE) + ";";

                PreparedStatement statement = conn.prepareStatement(sqlStatement);

                return statement;
            }

        }, new ReplicaEntryMapper());

        return replicaEntries;
    }

    /*
     * Get the replication policy entries for a given identifier. This returns the entries with
     * the preferred or blocked member nodes.
     * @param pid
     * @param policy
     * @return
     * @throws DataAccessException
     */
    private List<ReplicationPolicyEntry> listReplicationPolicies(Identifier pid,
            Map<String, String> tableMap) throws SQLException {

        List<ReplicationPolicyEntry> replicationPolicyEntryList = new ArrayList<ReplicationPolicyEntry>();
        final Map<String, String> finalTableMap = tableMap;

        replicationPolicyEntryList = this.jdbcTemplate.query(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                String sqlStatement = "SELECT guid, policy, member_node FROM "
                        + finalTableMap.get(SM_POLICY_TABLE) + ";";

                PreparedStatement statement = conn.prepareStatement(sqlStatement);
                return statement;
            }

        }, new ReplicationPolicyEntryMapper());
        // query the smreplicationpolicy table
        return replicationPolicyEntryList;
    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#getSystemMetadata(org.dataone.service.types.v1.Identifier)
     */
    @Override
    public SystemMetadata getSystemMetadata(final Identifier pid, Map<String, String> tableMap)
            throws DataAccessException {

        setTableMap(tableMap);
        List<SystemMetadata> systemMetadataList = new ArrayList<SystemMetadata>();
        SystemMetadata systemMetadata = null;

        final String idTable = (String) tableMap.get(IDENTIFIER_TABLE);
        final String sysMetaTable = (String) tableMap.get(SYSMETA_TABLE);
        final String replPolicyTable = (String) tableMap.get(SM_POLICY_TABLE);
        final String replStatusTable = (String) tableMap.get(SM_STATUS_TABLE);
        final String xmlAccessTable = (String) tableMap.get(ACCESS_TABLE);

        // query the systemmetadata table        
        try {
            systemMetadataList = this.jdbcTemplate.query(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {
                    String sqlStatement = "SELECT guid, date_uploaded, rights_holder, checksum, "
                            + "checksum_algorithm, origin_member_node, authoritive_member_node, "
                            + "date_modified, submitter, object_format, size, replication_allowed, "
                            + "number_replicas, obsoletes, obsoleted_by, serial_version, archived "
                            + "FROM " + sysMetaTable + " WHERE guid = ?;";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setString(1, pid.getValue());

                    return statement;
                }

            }, new SystemMetadataMapper());

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);

        }

        // The list should have only one record
        if (systemMetadataList.size() > 0) {
            systemMetadata = systemMetadataList.get(0);

        } else {
            throw new DataAccessException(new Exception(
                    "Couldn't get system metadata for identifier " + pid.getValue()));
        }

        return systemMetadata;

    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#saveSystemMetadata(org.dataone.service.types.v1.SystemMetadata)
     */
    @Override
    public Identifier saveSystemMetadata(SystemMetadata systemMetadata, Map<String, String> tableMap)
            throws DataAccessException {

        return null;

    }

    /**
     * Create a mapping in the identifier table of the pid to local docid. this method should be
     * used cautiously.  Metacat should have created a mapping on create() of an object, or via 
     * Metacat replication.  Creating a mapping should only happen if an audit shows a clear need
     * to repair the database content.  In theory, hasMapping() should always return true for a
     * pid known on one CN, and this method shouldn't be used.
     * 
     * @param guid
     * @param localId
     */
    private void createMapping(String guid, String localId) throws DataAccessException {

        String separator = ".";
        String docNumber;
        String docid;
        String rev;
        Identifier identifier = new Identifier();
        // Ensure the strings are not null
        if (localId == null || guid == null) {
            throw new DataAccessException(new Exception("The given id was null"));

        }

        // Ensure the localId syntax is correct
        int lastIndex;
        int secondToLastIndex;
        try {
            lastIndex = localId.lastIndexOf(separator);
            secondToLastIndex = localId.lastIndexOf(separator, lastIndex);
            rev = localId.substring(lastIndex + 1);
            docNumber = localId.substring(secondToLastIndex, lastIndex - 1);
            docid = localId.substring(0, secondToLastIndex - 1);
            int revAsInt = (new Integer(rev)).intValue();
            int docNumberAsInt = (new Integer(docNumber)).intValue();
            if (log.isDebugEnabled()) {
                log.debug("Creating mapping for - docid: " + docid + ", docNumber: " + docNumber
                        + ", rev: " + rev);
            }

        } catch (IndexOutOfBoundsException iobe) {
            throw new DataAccessException(iobe.getCause()); // bad localId syntax

        } catch (NumberFormatException nfe) {
            throw new DataAccessException(nfe.getCause()); // bad localId syntax

        }

        // does the mapping already exist?
        identifier.setValue(guid);
        if (hasMapping(identifier)) {
            log.info("The database already has a mapping for " + guid + ". Skipping the create.");
            return;

        } else {
            String sqlStatement = "INSERT into " + IDENTIFIER_TABLE
                    + " (guid, docid, rev) VALUES (?, ?, ?);";
            this.jdbcTemplate.update(sqlStatement, new Object[] { guid, docid, rev }, new int[] {
                    Types.VARCHAR, Types.VARCHAR, Types.INTEGER });
            log.info("Created mapping for " + guid + "and " + localId);
        }

    }

    /**
     * Create a unique docid for use in inserts and updates using the prefix
     * that is provided. Does not include the 'revision' part of the id if 
     * revision is '0', otherwise sets the revision number to 'revision'.
     * 
     * @param idPrefix the prefix to be used to construct the scope portion of the docid
     * @param revision the integer revision to use for this docid
     * @return a String docid based on the current date and time
     */
    private String generateDocumentId(String idPrefix, int revision) {

        StringBuffer docid;
        if (idPrefix != null) {
            docid = new StringBuffer(idPrefix);

        } else {
            docid = new StringBuffer("autogen");
        }
        docid.append(".");

        // Create a calendar to get the date formatted properly
        Calendar calendar = new GregorianCalendar();
        Date trialTime = new Date();
        calendar.setTime(trialTime);
        // using yyyymmddhhmmssmmm by convention (zero padding to preserve places)
        // will help with looking at logs and especially database tables.
        // for each millisecond we can support up to 99 before resetting to 0
        // NOTE: if you make it larger, docid is too big for a Long 
        if (documentIdCounter > 100) {
            documentIdCounter = 0;
        }
        docid.append(String.format(
                "%04d%02d%02d%02d%02d%02d%03d%02d",
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH) + 1, // adjust 0-11 range to 1-12
                calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.HOUR_OF_DAY),
                calendar.get(Calendar.MINUTE), calendar.get(Calendar.SECOND),
                calendar.get(Calendar.MILLISECOND), documentIdCounter++));
        if (revision > 0) {
            docid.append(".").append(revision);
        }
        return docid.toString();
    }

    /*
     * Check to see if a mapping exists for the pid
     * @param pid
     * @return mapped  true if it exists
     * @throws DataAccessException
     */
    private boolean hasMapping(Identifier pid) throws DataAccessException {

        boolean mapped = false;
        int countReturned = 0;

        if (pid.getValue() == null) {
            throw new DataAccessException(new Exception("The given identifier was null"));
        }

        // query the identifier table
        String sqlStatement = "SELECT guid FROM " + IDENTIFIER_TABLE + "where guid = ?";

        countReturned = this.jdbcTemplate
                .queryForInt(sqlStatement, new Object[] { pid.getValue() });

        if (countReturned > 0) {
            mapped = true;
        }

        return mapped;
    }

    /*
     * Check to see if a system metadata record exists for the pid
     * @param pid
     * @return hasSysMeta  true if it exists
     * @throws DataAccessException
     */
    private boolean hasSystemMetadata(Identifier pid) throws DataAccessException {

        boolean hasSysMeta = false;
        int countReturned = 0;

        if (pid.getValue() == null) {
            throw new DataAccessException(new Exception("The given identifier was null"));
        }

        // query the identifier table
        String sqlStatement = "SELECT guid FROM " + SYSMETA_TABLE + "where guid = ?";

        countReturned = this.jdbcTemplate
                .queryForInt(sqlStatement, new Object[] { pid.getValue() });

        if (countReturned > 0) {
            hasSysMeta = true;
        }

        return hasSysMeta;
    }

    /**
     * A class used to map system metadata status results into SystemMetadataStatus data transfer objects
     * 
     * @author cjones
     *
     */
    public final class SystemMetadataStatusMapper implements RowMapper<SystemMetadataStatus> {

        /**
         * Map each row into a SystemMetadataStatus object
         */
        @Override
        public SystemMetadataStatus mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

            // resultSet contains guid, serialVersion, date_modified, and archived column data
            SystemMetadataStatus systemMetadataStatus = new SystemMetadataStatus();

            // add guid
            Identifier pid = new Identifier();
            pid.setValue(resultSet.getString("guid"));
            systemMetadataStatus.setPid(pid);

            // add serialVersion
            BigInteger serialVersion = new BigInteger(resultSet.getString("serial_version"));
            systemMetadataStatus.setSerialVersion(serialVersion);

            // add date_modified
            Date dateSystemMetadataLastModified = resultSet.getDate("date_modified");
            systemMetadataStatus
                    .setLastSystemMetadataModificationDate(dateSystemMetadataLastModified);

            // add archived
            boolean archived = resultSet.getBoolean("archived");
            systemMetadataStatus.setDeleted(new Boolean(archived));

            return systemMetadataStatus;
        }

    }

    /**
     * A class to map replica entry list results into ReplicayEntry data transfer objects
     * 
     * @author cjones
     */
    public final class ReplicaEntryMapper implements RowMapper<Replica> {

        /**
         * Map each row into a ReplicaEntry object
         */
        @Override
        public Replica mapRow(ResultSet resultSet, int rowNumber) throws SQLException {
            Replica replica = new Replica();

            // add member_node
            NodeReference nodeid = new NodeReference();
            nodeid.setValue(resultSet.getString("member_node"));
            replica.setReplicaMemberNode(nodeid);

            // add status
            String status = resultSet.getString("status");
            ReplicationStatus replStatus = ReplicationStatus.convert(status);
            replica.setReplicationStatus(replStatus);

            // add date_verified
            Date dateVerified = resultSet.getDate("date_verified");
            replica.setReplicaVerified(dateVerified);

            return replica;
        }

    }

    /**
     * A class to map replication policy list results into ReplicationPolicyEntry data transfer objects
     * 
     * @author cjones
     */
    public final class ReplicationPolicyEntryMapper implements RowMapper<ReplicationPolicyEntry> {

        /**
         * Map each row into a ReplicationPolicyEntry object
         */
        @Override
        public ReplicationPolicyEntry mapRow(ResultSet resultSet, int rowNumber)
                throws SQLException {
            ReplicationPolicyEntry replPolicyEntry = new ReplicationPolicyEntry();

            // add guid
            Identifier pid = new Identifier();
            pid.setValue(resultSet.getString("guid"));
            replPolicyEntry.setPid(pid);

            // add policy type
            String policy = resultSet.getString("policy");
            replPolicyEntry.setPolicy(policy);

            // add member node
            String nodeid = resultSet.getString("member_node");
            NodeReference nodeRef = new NodeReference();
            nodeRef.setValue(nodeid);
            replPolicyEntry.setMemberNode(nodeRef);

            return replPolicyEntry;
        }

    }

    public final class AccessRuleMapper implements RowMapper<AccessRule> {

        @Override
        public AccessRule mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

            // add the subject
            AccessRule accessRule = new AccessRule();
            Subject subject = new Subject();
            subject.setValue(resultSet.getString("principal_name"));
            accessRule.addSubject(subject);

            // add the permissions
            List<Permission> permissions = convertPermission(resultSet.getInt("permission"));
            accessRule.setPermissionList(permissions);

            return accessRule;
        }

    }

    /**
     * A class used to map system metadata status results into SystemMetadata data transfer objects
     * 
     * @author cjones
     */
    public final class SystemMetadataMapper implements RowMapper<SystemMetadata> {

        /**
         * Map each row into a SystemMetadata object
         */
        @Override
        public SystemMetadata mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

            // resultSet contains guid, serialVersion, date_modified, and archived column data
            SystemMetadata systemMetadata = new SystemMetadata();
            ReplicationPolicy replPolicy = new ReplicationPolicy();
            AccessPolicy accessPolicy = new AccessPolicy();

            // add guid
            Identifier pid = new Identifier();
            pid.setValue(resultSet.getString("guid"));
            systemMetadata.setIdentifier(pid);

            // add serialVersion
            BigInteger serialVersion = new BigInteger(resultSet.getString("serial_version"));
            systemMetadata.setSerialVersion(serialVersion);

            // add date_modified
            Date dateSystemMetadataLastModified = resultSet.getDate("date_modified");
            systemMetadata.setDateSysMetadataModified(dateSystemMetadataLastModified);

            // add archived
            boolean archived = resultSet.getBoolean("archived");
            systemMetadata.setArchived(new Boolean(archived));

            // add date_uploaded
            Date dateUploaded = resultSet.getDate("date_uploaded");
            systemMetadata.setDateUploaded(dateUploaded);

            // add rights_holder
            Subject rightsHolderSubject = new Subject();
            String rightsHolder = resultSet.getString("rights_holder");
            rightsHolderSubject.setValue(rightsHolder);
            systemMetadata.setRightsHolder(rightsHolderSubject);

            // add checksum, checksum_algorithm
            String checksum = resultSet.getString("checksum");
            String checksumAlgorithm = resultSet.getString("checksum_algorithm");
            Checksum checksumObject = new Checksum();
            checksumObject.setValue(checksum);
            checksumObject.setAlgorithm(checksumAlgorithm);
            systemMetadata.setChecksum(checksumObject);

            // add origin_member_node
            String originMemberNode = resultSet.getString("origin_member_node");
            if (originMemberNode != null) {
                NodeReference omn = new NodeReference();
                omn.setValue(originMemberNode);
                systemMetadata.setOriginMemberNode(omn);
            }

            // add authoritive_member_node
            String authoritativeMemberNode = resultSet.getString("authoritive_member_node");
            if (originMemberNode != null) {
                NodeReference amn = new NodeReference();
                amn.setValue(authoritativeMemberNode);
                systemMetadata.setAuthoritativeMemberNode(amn);
            }

            // add submitter
            String submitter = resultSet.getString("submitter");
            if (submitter != null) {
                Subject submitterSubject = new Subject();
                submitterSubject.setValue(submitter);
                systemMetadata.setSubmitter(submitterSubject);
            }

            // add object_format
            String fmtidStr = resultSet.getString("object_format");
            ObjectFormatIdentifier fmtid = new ObjectFormatIdentifier();
            fmtid.setValue(fmtidStr);
            systemMetadata.setFormatId(fmtid);

            // add size
            String size = resultSet.getString("size");
            systemMetadata.setSize(new BigInteger(size));

            // add obsoletes
            String obsoletes = resultSet.getString("obsoletes");
            if (obsoletes != null) {
                Identifier obsoletesId = new Identifier();
                obsoletesId.setValue(obsoletes);
                systemMetadata.setObsoletes(obsoletesId);
            }
            // add obsoleted_by
            String obsoletedBy = resultSet.getString("obsoleted_by");
            if (obsoletedBy != null) {
                Identifier obsoletedById = new Identifier();
                obsoletedById.setValue(obsoletes);
                systemMetadata.setObsoletedBy(obsoletedById);
            }

            // populate and add ReplicationPolicy
            ReplicationPolicy replicationPolicy = new ReplicationPolicy();

            // add replication_allowed
            boolean replAllowed = resultSet.getBoolean("replication_allowed");
            replicationPolicy.setReplicationAllowed(new Boolean(replAllowed));

            // add number_replicas
            int numberOfReplicas = resultSet.getInt("number_replicas");
            if (numberOfReplicas > 0) {
                replicationPolicy.setNumberReplicas(new Integer(numberOfReplicas));

            }

            // add preferred and blocked lists
            List<ReplicationPolicyEntry> replPolicies = new ArrayList<ReplicationPolicyEntry>();
            List<NodeReference> preferredNodes = new ArrayList<NodeReference>();
            List<NodeReference> blockedNodes = new ArrayList<NodeReference>();

            replPolicies = listReplicationPolicies(pid, SystemMetadataDaoMetacatImpl.this.tableMap);

            for (ReplicationPolicyEntry policy : replPolicies) {
                Identifier id = policy.getPid();
                String entryPolicy = policy.getPolicy();
                NodeReference node = policy.getMemberNode();

                if (entryPolicy.equals("preferred")) {
                    preferredNodes.add(node);

                } else if (entryPolicy.equals("blocked")) {
                    blockedNodes.add(node);

                }
            }

            replicationPolicy.setPreferredMemberNodeList(preferredNodes);
            replicationPolicy.setBlockedMemberNodeList(blockedNodes);

            systemMetadata.setReplicationPolicy(replicationPolicy);

            // populate and add replicas list

            List<Replica> replicas = new ArrayList<Replica>();
            replicas = listReplicaEntries(pid, SystemMetadataDaoMetacatImpl.this.tableMap);
            systemMetadata.setReplicaList(replicas);

            // populate and add AccessPolicy
            List<AccessRule> accessRules = new ArrayList<AccessRule>();
            accessRules = listAccessRules(pid, SystemMetadataDaoMetacatImpl.this.tableMap);
            accessPolicy.setAllowList(accessRules);

            // Validate the system metadata in debug mode using TypeMarshaller
            if (log.isDebugEnabled()) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    TypeMarshaller.marshalTypeToOutputStream(systemMetadata, baos);
                    log.debug("SystemMetadata for pid " + pid.getValue() + " is: "
                            + baos.toString());

                } catch (JiBXException e) {
                    e.printStackTrace();

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }
            return systemMetadata;
        }

    }

    /*
     * Handle data access exceptions thrown by the underlying JDBC calls
     * 
     * @param dae the DataAccessException thrown
     * 
     * @throws DataAccessException
     */
    private void handleJdbcDataAccessException(org.springframework.dao.DataAccessException dae)
            throws DataAccessException {
        log.error("Jdbc Data access exception occurred: " + dae.getRootCause().getMessage());
        dae.printStackTrace();
        throw dae;
    }

    public void setTableMap(Map<String, String> tableMap) {
        this.tableMap = tableMap;

    }

    /**
     * Convert integer-based permission values to string-based permissions
     * @param value  the integer value of the permission
     * @return permissions  the list of permissions 
     */
    private List<Permission> convertPermission(int value) {

        List<Permission> permissions = new ArrayList<Permission>();

        final int CHMOD = 1;
        final int WRITE = 2;
        final int READ = 4;
        final int ALL = 7;

        if (value == ALL) {
            permissions.add(Permission.READ);
            permissions.add(Permission.WRITE);
            permissions.add(Permission.CHANGE_PERMISSION);
            return permissions;
        }

        if ((value & CHMOD) == CHMOD) {
            permissions.add(Permission.CHANGE_PERMISSION);
        }
        if ((value & READ) == READ) {
            permissions.add(Permission.READ);
        }
        if ((value & WRITE) == WRITE) {
            permissions.add(Permission.WRITE);
        }

        return permissions;
    }
}
