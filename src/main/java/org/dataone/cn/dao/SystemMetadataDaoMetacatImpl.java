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
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.dataone.exceptions.MarshallingException;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.cn.dao.exceptions.DataAccessException;
import org.dataone.service.exceptions.InvalidSystemMetadata;
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
import org.dataone.service.types.v2.SystemMetadata;
import org.dataone.service.util.TypeMarshaller;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.AbstractPlatformTransactionManager;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;

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
    protected static Map<String, String> tableMap = new HashMap<String, String>();
    private AbstractPlatformTransactionManager txManager;
    private TransactionTemplate txTemplate;

    static {
        tableMap.put(IDENTIFIER_TABLE, IDENTIFIER_TABLE);
        tableMap.put(SYSMETA_TABLE, SYSMETA_TABLE);
        tableMap.put(SM_POLICY_TABLE, SM_POLICY_TABLE);
        tableMap.put(SM_STATUS_TABLE, SM_STATUS_TABLE);
        tableMap.put(ACCESS_TABLE, ACCESS_TABLE);
    }

    /**
     * Constructor. Creates an instance of SystemMetadataDaoMetacatImpl
     */
    public SystemMetadataDaoMetacatImpl() {
        this(MetacatDataSourceFactory.getMetacatDataSource());
    }

    /**
     * Constructor. Creates an instance of SystemMetadataDaoMetacatImpl using the given data source
     */
    public SystemMetadataDaoMetacatImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        txManager = new DataSourceTransactionManager(dataSource);
        txTemplate = new TransactionTemplate(txManager);
        txTemplate.setIsolationLevel(TransactionDefinition.ISOLATION_READ_COMMITTED);
    }

    public int getSystemMetadataCount() throws DataAccessException {
        return getSystemMetadataCount(tableMap);
    }

    public List<SystemMetadataStatus> listSystemMetadataStatus(int pageNumber, int pageSize)
            throws DataAccessException {
        return listSystemMetadataStatus(pageNumber, pageSize, tableMap);
    }

    public SystemMetadata getSystemMetadata(Identifier pid) throws DataAccessException {
        return getSystemMetadata(pid, tableMap);
    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#getSystemMetadataCount()
     */
    public int getSystemMetadataCount(Map<String, String> tableMap) throws DataAccessException {

        // query the systemmetadata table
        String sqlStatement = "SELECT count(guid) FROM " + (String) tableMap.get(SYSMETA_TABLE);

        Integer count = 0;
        try {
            count = jdbcTemplate.queryForObject(sqlStatement,Integer.class);

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);
           
        }
        // refactoring out the deprecated queryForInt method allows a null value for count
        // so the converstion of null to 0 matches previous behavior.
        return (count != null ? count.intValue() : 0);
    }

    /*
     * @see org.dataone.cn.dao.SystemMetadataDao#listSystemMetadata()
     */
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
        final String pidStr = pid.getValue();
        final Map<String, String> finalTableMap = tableMap;
        replicaEntries = this.jdbcTemplate.query(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                String sqlStatement = "SELECT guid, member_node, status, date_verified FROM "
                        + finalTableMap.get(SM_STATUS_TABLE) + " WHERE guid = ?";

                PreparedStatement statement = conn.prepareStatement(sqlStatement);
                statement.setString(1, pidStr);
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
        final String pidStr = pid.getValue();

        replicationPolicyEntryList = this.jdbcTemplate.query(new PreparedStatementCreator() {

            @Override
            public PreparedStatement createPreparedStatement(Connection conn) throws SQLException {
                String sqlStatement = "SELECT guid, policy, member_node FROM "
                        + finalTableMap.get(SM_POLICY_TABLE) + " WHERE guid = ?";

                PreparedStatement statement = conn.prepareStatement(sqlStatement);
                statement.setString(1, pidStr);
                return statement;
            }

        }, new ReplicationPolicyEntryMapper());
        // query the smreplicationpolicy table
        return replicationPolicyEntryList;
    }

    /**	
     * @see org.dataone.cn.dao.SystemMetadataDao#getSystemMetadata(org.dataone.service.types.v1.Identifier)
     */
    public SystemMetadata getSystemMetadata(final Identifier pid, Map<String, String> tableMap)
            throws DataAccessException {

        List<SystemMetadata> systemMetadataList = new ArrayList<SystemMetadata>();
        SystemMetadata systemMetadata = null;

        final String sysMetaTable = (String) tableMap.get(SYSMETA_TABLE);

        // query the systemmetadata table        
        try {
            systemMetadataList = this.jdbcTemplate.query(new PreparedStatementCreator() {

                @Override
                public PreparedStatement createPreparedStatement(Connection conn)
                        throws SQLException {
                    String sqlStatement = "SELECT guid, series_id, date_uploaded, rights_holder, checksum, "
                            + "checksum_algorithm, origin_member_node, authoritive_member_node, "
                            + "date_modified, submitter, object_format, size, replication_allowed, "
                            + "number_replicas, obsoletes, obsoleted_by, serial_version, archived "
                            + "FROM " + sysMetaTable + " WHERE guid = ?;";

                    PreparedStatement statement = conn.prepareStatement(sqlStatement);
                    statement.setString(1, pid.getValue());

                    return statement;
                }

            }, new SystemMetadataMapper(tableMap));

        } catch (org.springframework.dao.DataAccessException dae) {
            handleJdbcDataAccessException(dae);

        }

        // The list should have only one record
        if (systemMetadataList.size() > 0) {
            systemMetadata = systemMetadataList.get(0);

        }

        return systemMetadata;

    }

    /**
     * Saves the given system metadata to the tables indicated in the tableMap
     * 
     * @see org.dataone.cn.dao.SystemMetadataDao#saveSystemMetadata(org.dataone.service.types.v1.SystemMetadata)
     */
    public Identifier saveSystemMetadata(SystemMetadata systemMetadata, Map<String, String> tableMap)
            throws DataAccessException {

        Boolean updated = new Boolean(false); // the result of all table updates transaction

        // Is the pid valid? (required)
        final Identifier pid = systemMetadata.getIdentifier();
        if (pid.getValue() == null) {
            throw new DataAccessException(new InvalidSystemMetadata("0000",
                    "Identifier cannot be null"));

        }

        // prep the transaction
        txTemplate.setName(pid.getValue());
        txTemplate.setReadOnly(false);

        SystemMetadata currentSysMeta = getSystemMetadata(pid, tableMap);
        // Is it in the table already?
        if (currentSysMeta == null) {
            Boolean inserted = new Boolean(false);
            final String finalSysMetaTable = tableMap.get(SYSMETA_TABLE);
            // insert just the pid
            inserted = txTemplate.execute(new TransactionCallback<Boolean>() {

                @Override
                public Boolean doInTransaction(TransactionStatus arg0) {
                    Boolean success = new Boolean(false);

                    int rows = jdbcTemplate.update("INSERT INTO " + finalSysMetaTable
                            + " (guid) VALUES (?);", new Object[] { pid.getValue() },
                            new int[] { java.sql.Types.LONGVARCHAR });

                    if (rows == 1) {
                        success = new Boolean(true);
                    }
                    return success;
                }
            });
        }

        // then update the system metadata
        updated = updateSystemMetadata(systemMetadata, tableMap);

        // We failed and rolled back
        if (!updated.equals(true)) {
            throw new DataAccessException(new Exception("Failed to update identifier "
                    + pid.getValue()));
        }

        return pid;

    }

    /**
     * Update the given system metadata using the provided table map.
     * 
     * @param sysMeta
     * @param tableMap
     * @return
     * @throws DataAccessException
     */
    protected Boolean updateSystemMetadata(SystemMetadata sysMeta, Map<String, String> tableMap)
            throws DataAccessException {

        Boolean updated = new Boolean(false);

        // Is the pid valid? (required)
        final Identifier pid = sysMeta.getIdentifier();
        if (pid.getValue() == null) {
            throw new DataAccessException(new InvalidSystemMetadata("0000",
                    "Identifier cannot be null"));

        }

        // Is the size set? (required)
        final BigInteger size = sysMeta.getSize();
        if (size == null) {
            throw new DataAccessException(new InvalidSystemMetadata("0000", "Size cannot be null"));

        }

        // Is the checksum set? (required)
        final Checksum checksum = sysMeta.getChecksum();
        if (checksum == null) {
            throw new DataAccessException(new InvalidSystemMetadata("0000",
                    "Checksum cannot be null"));

        }

        final SystemMetadata finalSysMeta = sysMeta;
        final String sysMetaTable = tableMap.get(SYSMETA_TABLE);
        final String smReplPolicyTable = tableMap.get(SM_POLICY_TABLE);
        final String smReplStatusTable = tableMap.get(SM_STATUS_TABLE);
        final String xmlAccessTable = tableMap.get(ACCESS_TABLE);

        updated = txTemplate.execute(new TransactionCallback<Boolean>() {

            @Override
            public Boolean doInTransaction(TransactionStatus status) {

                boolean success = false;
                // update the system metadata table
                String sqlStatement = getSysMetaUpdateStatement(sysMetaTable);
                Map<String, Object> sysMetaMap = extractSystemMetadataAttrs(finalSysMeta,
                        sysMetaTable);
                Object[] values = getSysMetaAttrValues(sysMetaMap);
                int[] types = getSysMetaAttrTypes();
                int sysMetaRows = jdbcTemplate.update(sqlStatement, values, types);
                if (sysMetaRows == 1) {
                    success = true;
                }

                // Update the smreplicationpolicy table
                ReplicationPolicy replPolicy = finalSysMeta.getReplicationPolicy();
                int totalReplPolicies = 0;
                int updatedReplPolicies = 0;

                if (replPolicy != null) {
                    List<NodeReference> preferredNodes = replPolicy.getPreferredMemberNodeList();
                    List<NodeReference> blockedNodes = replPolicy.getBlockedMemberNodeList();

                    // first remove listed policy entries
                    if (preferredNodes != null || blockedNodes != null) {
                        jdbcTemplate.update("DELETE FROM " + smReplPolicyTable + " WHERE guid = ?",
                                new Object[] { pid.getValue() });
                    }

                    // count the number of total policies needed to be updated
                    if (preferredNodes != null) {
                        totalReplPolicies = totalReplPolicies + preferredNodes.size();

                        // then update the preferred entries
                        for (NodeReference preferredNode : preferredNodes) {
                            String preferredNodeStr = preferredNode.getValue();
                            int preferredRows = jdbcTemplate.update("INSERT INTO "
                                    + smReplPolicyTable
                                    + " (guid, member_node, policy) VALUES (?, ?, ?);",
                                    new Object[] { pid.getValue(), preferredNodeStr, "preferred" },
                                    new int[] { java.sql.Types.LONGVARCHAR, java.sql.Types.VARCHAR,
                                            java.sql.Types.VARCHAR });
                            updatedReplPolicies += preferredRows;
                        }

                    }

                    if (blockedNodes != null) {
                        totalReplPolicies = totalReplPolicies + blockedNodes.size();

                        // then update the blocked entries
                        for (NodeReference blockedNode : blockedNodes) {
                            int blockedRows = jdbcTemplate.update("INSERT INTO "
                                    + smReplPolicyTable
                                    + " (guid, member_node, policy) VALUES (?, ?, ?);",
                                    new Object[] { pid.getValue(), blockedNode.getValue(),
                                            "blocked" }, new int[] { java.sql.Types.LONGVARCHAR,
                                            java.sql.Types.VARCHAR, java.sql.Types.VARCHAR });
                            updatedReplPolicies += blockedRows;

                        }

                    }

                    // did we update what we were supposed to?
                    if (updatedReplPolicies == totalReplPolicies) {
                        success = true;

                    } else {
                        success = false;
                        log.error("For identifier " + pid.getValue() + ", only "
                                + updatedReplPolicies + "replication policies of "
                                + totalReplPolicies + "were inserted.");

                    }
                }

                // Update the smreplicationstatus table
                List<Replica> replicas = finalSysMeta.getReplicaList();
                int totalReplicas = 0;
                int updatedReplicas = 0;

                if (replicas != null) {
                    totalReplicas = totalReplicas + replicas.size();

                    // first remove listed replicas
                    jdbcTemplate.update("DELETE FROM " + smReplStatusTable + " WHERE guid = ?",
                            new Object[] { pid.getValue() });

                    for (Replica replica : replicas) {
                        int replicaRows = jdbcTemplate
                                .update("INSERT INTO "
                                        + smReplStatusTable
                                        + " (guid, member_node, status, date_verified) VALUES (?, ?, ?, ?)",
                                        new Object[] {
                                                pid.getValue(),
                                                (replica.getReplicaMemberNode().getValue()),
                                                replica.getReplicationStatus().toString(),
                                                new Timestamp(replica.getReplicaVerified()
                                                        .getTime()) }, new int[] {
                                                java.sql.Types.LONGVARCHAR, java.sql.Types.VARCHAR,
                                                java.sql.Types.VARCHAR, java.sql.Types.TIMESTAMP });
                        updatedReplicas += replicaRows;
                    }

                }

                if (updatedReplicas == totalReplicas) {
                    success = true;

                } else {
                    success = false;
                    log.error("For identifier " + pid.getValue() + ", only " + updatedReplicas
                            + "replicas of " + totalReplicas + "were inserted.");

                }

                // Update the xml_access table
                AccessPolicy accessPolicy = finalSysMeta.getAccessPolicy();
                int updatedAccessRows = 0;
                int numberOfSubjects = 0;
                List<AccessRule> accessRules = new ArrayList<AccessRule>();

                if (accessPolicy != null) {
                    accessRules = accessPolicy.getAllowList();

                    // first delete existing rules for this pid
                    jdbcTemplate.update("DELETE FROM " + xmlAccessTable + " WHERE guid = ?",
                            new Object[] { pid.getValue() });

                    // add the new rules back in
                    for (AccessRule accessRule : accessRules) {
                        List<Subject> subjects = accessRule.getSubjectList();
                        numberOfSubjects += subjects.size();
                        List<Permission> permissions = accessRule.getPermissionList();
                        // convert permissions from text to int
                        Integer perm = null;
                        for (Permission permission : permissions) {
                            if (perm != null) {
                                perm |= convertPermission(permission);

                            } else {
                                perm = convertPermission(permission);
                            }

                        }

                        for (Subject subject : subjects) {
                            int accessRows = jdbcTemplate.update("INSERT INTO " + xmlAccessTable
                                    + " (guid, principal_name, permission, perm_type, perm_order) "
                                    + " VALUES (?, ?, ?, ?, ?)", new Object[] { pid.getValue(),
                                    subject.getValue(), perm, "allow", "allowFirst" }, new int[] {
                                    java.sql.Types.LONGVARCHAR, java.sql.Types.VARCHAR,
                                    java.sql.Types.INTEGER, java.sql.Types.VARCHAR,
                                    java.sql.Types.VARCHAR });
                            updatedAccessRows += accessRows;
                        }
                    }

                    // Determine success for access policy updates
                    if (updatedAccessRows == numberOfSubjects) {
                        success = true;

                    } else {
                        success = false;
                        log.error("For identifier " + pid.getValue() + ", only "
                                + updatedAccessRows + "replicas of " + numberOfSubjects
                                + "were inserted.");

                    }

                }

                // rollback if we don't succeed on all calls
                //  status.setRollbackOnly();  // seems to only trigger rollback, no commit
                return new Boolean(success);
            }

        });

        return updated;

    }

    /**
     * Returns a map of attribute names and values to be used in the statement
     * to update the given systemmetadata table
     * 
     * @param systemMetadata
     * @param tableName
     * @return
     * @throws DataAccessException 
     */
    protected Map<String, Object> extractSystemMetadataAttrs(SystemMetadata systemMetadata,
            String tableName) {

        Map<String, Object> attrMap = new HashMap<String, Object>();
        
        // get seriesId
        Identifier seriesId = systemMetadata.getSeriesId();
        String seriesIdStr = seriesId == null ? null : seriesId.getValue();
        attrMap.put("series_id", seriesIdStr);

        // get serial_version
        BigInteger serialVersion = systemMetadata.getSerialVersion();
        String versionStr = serialVersion.toString() == null ? null : serialVersion.toString();
        attrMap.put("serial_version", versionStr);

        // get date_uploaded
        Date dateUploaded = systemMetadata.getDateUploaded();
        Timestamp uploadedTime = dateUploaded == null ? null
                : new Timestamp(dateUploaded.getTime());
        attrMap.put("date_uploaded", uploadedTime);

        // get rights_holder
        Subject rightsHolder = systemMetadata.getRightsHolder();
        String rightsHolderStr = rightsHolder == null ? null : rightsHolder.getValue();
        attrMap.put("rights_holder", rightsHolderStr);

        // get checksum
        Checksum checksum = systemMetadata.getChecksum();
        String checksumStr = checksum == null ? null : checksum.getValue();
        attrMap.put("checksum", checksumStr);

        // get checksum_algorithm
        String algorithm = null;
        if (checksum != null) {
            algorithm = checksum.getAlgorithm();
            algorithm = algorithm == null ? null : algorithm;

        }
        attrMap.put("checksum_algorithm", algorithm);

        // get origin_member_node
        NodeReference originNodeid = systemMetadata.getOriginMemberNode();
        String originNodeidStr = originNodeid == null ? null : originNodeid.getValue();
        attrMap.put("origin_member_node", originNodeidStr);

        // get authoritive_member_node
        NodeReference authNodeid = systemMetadata.getAuthoritativeMemberNode();
        String authNodeidStr = authNodeid == null ? null : authNodeid.getValue();
        attrMap.put("authoritive_member_node", authNodeidStr);

        // get date_modified
        Date dateModified = systemMetadata.getDateSysMetadataModified();
        Timestamp modTime = dateModified == null ? null : new Timestamp(dateModified.getTime());
        attrMap.put("date_modified", modTime);

        // get submitter
        Subject submitter = systemMetadata.getSubmitter();
        String submitterStr = submitter == null ? null : submitter.getValue();
        attrMap.put("submitter", submitterStr);

        // get object_format
        ObjectFormatIdentifier formatId = systemMetadata.getFormatId();
        String formatIdStr = formatId == null ? null : formatId.getValue();
        attrMap.put("object_format", formatIdStr);

        // get size
        BigInteger size = systemMetadata.getSize();
        String sizeStr = size == null ? null : size.toString();
        attrMap.put("size", sizeStr);

        // get archived
        Boolean archived = systemMetadata.getArchived();
        archived = archived == null ? false : archived;
        attrMap.put("archived", archived);

        // get replication_allowed
        // get number_replicas
        Boolean replicationAllowed = null;
        Integer numberReplicas = null;
        ReplicationPolicy replicationPolicy = systemMetadata.getReplicationPolicy();
        if (replicationPolicy != null) {
            replicationAllowed = replicationPolicy.getReplicationAllowed();
            replicationAllowed = replicationAllowed == null ? false : replicationAllowed;
            numberReplicas = replicationPolicy.getNumberReplicas();
            replicationAllowed = replicationAllowed == null ? false : replicationAllowed;
            numberReplicas = numberReplicas == null ? -1 : numberReplicas;
        }
        attrMap.put("replication_allowed", replicationAllowed);
        attrMap.put("number_replicas", numberReplicas);

        // get obsoletes
        Identifier obsoletes = systemMetadata.getObsoletes();
        String obsoletesStr = obsoletes == null ? null : obsoletes.getValue();
        attrMap.put("obsoletes", obsoletesStr);

        // get obsoleted_by
        Identifier obsoletedBy = systemMetadata.getObsoletedBy();
        String obsoletedByStr = obsoletedBy == null ? null : obsoletedBy.getValue();
        attrMap.put("obsoleted_by", obsoletedByStr);

        // get guid
        Identifier pid = systemMetadata.getIdentifier();
        String pidStr = pid == null ? null : pid.getValue();
        attrMap.put("guid", pidStr);

        return attrMap;
    }

    /**
     * Builds a SQL update statement for use against the systemmetadata table
     * 
     * @param sysMetaTable
     * @return
     */
    protected String getSysMetaUpdateStatement(String sysMetaTable) {

        StringBuilder sql = new StringBuilder();
        sql.append("UPDATE " + sysMetaTable + " SET ");
        // sql.append("guid                    = ?, ");
        sql.append("series_id		        = ?, ");
        sql.append("serial_version          = ?, ");
        sql.append("date_uploaded           = ?, ");
        sql.append("rights_holder           = ?, ");
        sql.append("checksum                = ?, ");
        sql.append("checksum_algorithm      = ?, ");
        sql.append("origin_member_node      = ?, ");
        sql.append("authoritive_member_node = ?, ");
        sql.append("date_modified           = ?, ");
        sql.append("submitter               = ?, ");
        sql.append("object_format           = ?, ");
        sql.append("size                    = ?, ");
        sql.append("archived                = ?, ");
        sql.append("replication_allowed     = ?, ");
        sql.append("number_replicas         = ?, ");
        sql.append("obsoletes               = ?, ");
        sql.append("obsoleted_by            = ?");
        sql.append(" WHERE guid = ? ;");

        return sql.toString();
    }

    /**
     * Returns the values from the given map as an Object array
     * 
     * @param sysMetaMap
     * @return
     */
    protected Object[] getSysMetaAttrValues(Map<String, Object> sysMetaMap) {

        Object[] values = new Object[] { 
        		(String) sysMetaMap.get("series_id"),
        		(String) sysMetaMap.get("serial_version"),
                (Timestamp) sysMetaMap.get("date_uploaded"),
                (String) sysMetaMap.get("rights_holder"), 
                (String) sysMetaMap.get("checksum"),
                (String) sysMetaMap.get("checksum_algorithm"),
                (String) sysMetaMap.get("origin_member_node"),
                (String) sysMetaMap.get("authoritive_member_node"),
                (Timestamp) sysMetaMap.get("date_modified"), 
                (String) sysMetaMap.get("submitter"),
                (String) sysMetaMap.get("object_format"), 
                (String) sysMetaMap.get("size"),
                (Boolean) sysMetaMap.get("archived"),
                (Boolean) sysMetaMap.get("replication_allowed"),
                (Integer) sysMetaMap.get("number_replicas"), 
                (String) sysMetaMap.get("obsoletes"),
                (String) sysMetaMap.get("obsoleted_by"), 
                (String) sysMetaMap.get("guid"), };
        return values;
    }

    /**
     * Returns the attribute types for the systemmetadata table as an int array
     * @return
     */
    protected int[] getSysMetaAttrTypes() {
        int[] types = new int[] { 
                java.sql.Types.LONGVARCHAR, //text        
        		java.sql.Types.VARCHAR, //character varying(256)     
                java.sql.Types.TIMESTAMP, //timestamp without time zone
                java.sql.Types.VARCHAR, //character varying(250)     
                java.sql.Types.VARCHAR, //character varying(512)     
                java.sql.Types.VARCHAR, //character varying(250)     
                java.sql.Types.VARCHAR, //character varying(250)     
                java.sql.Types.VARCHAR, //character varying(250)     
                java.sql.Types.TIMESTAMP, //timestamp without time zone
                java.sql.Types.VARCHAR, //character varying(256)     
                java.sql.Types.VARCHAR, //character varying(256)     
                java.sql.Types.VARCHAR, //character varying(256)     
                java.sql.Types.BOOLEAN, //boolean                    
                java.sql.Types.BOOLEAN, //boolean                    
                java.sql.Types.BIGINT, //bigint                     
                java.sql.Types.LONGVARCHAR, //text                       
                java.sql.Types.LONGVARCHAR, //text                       
                java.sql.Types.LONGVARCHAR, //text                       

        };
        return types;
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
        Integer countReturned = 0;

        if (pid.getValue() == null) {
            throw new DataAccessException(new Exception("The given identifier was null"));
        }

        // query the identifier table
        String sqlStatement = "SELECT guid FROM " + IDENTIFIER_TABLE + "where guid = ?";

        countReturned = this.jdbcTemplate
                .queryForObject(sqlStatement, new Object[] { pid.getValue() }, Integer.class);

        if (countReturned != null && countReturned > 0) {
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
        Integer countReturned = 0;

        if (pid.getValue() == null) {
            throw new DataAccessException(new Exception("The given identifier was null"));
        }

        // query the identifier table
        String sqlStatement = "SELECT guid FROM " + SYSMETA_TABLE + "where guid = ?";

        countReturned = this.jdbcTemplate
                .queryForObject(sqlStatement, new Object[] { pid.getValue() }, Integer.class);

        if (countReturned != null && countReturned > 0) {
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
            Date dateSystemMetadataLastModified = resultSet.getTimestamp("date_modified");
            systemMetadataStatus
                    .setLastSystemMetadataModificationDate(dateSystemMetadataLastModified);

            // add archived
            boolean archived = resultSet.getBoolean("archived");  // getBoolean returns false for null results
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
            ReplicationStatus replStatus = ReplicationStatus.valueOf(status);
            replica.setReplicationStatus(replStatus);

            // add date_verified
            Date dateVerified = resultSet.getTimestamp("date_verified");
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

    /**
     * A class to map access control entry results into AccessRule objects to populate the 
     * AccessPolicy section of SystemMetadata objects
     * 
     * @author cjones
     */
    public final class AccessRuleMapper implements RowMapper<AccessRule> {

        @Override
        public AccessRule mapRow(ResultSet resultSet, int rowNumber) throws SQLException {

            // add the subject
            AccessRule accessRule = new AccessRule();
            Subject subject = new Subject();
            subject.setValue(resultSet.getString("principal_name"));
            accessRule.addSubject(subject);

            // add the permissions
            // a 0 return for null value situations is harmless
            List<Permission> permissions = convertPermission(resultSet.getInt("permission")); // getInt returns 0 for null values
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

        private Map<String, String> localTableMap;

        public SystemMetadataMapper(Map<String, String> tableMap) {
            localTableMap = tableMap;
        }

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
            
            // add seriesId
            Identifier seriesId = new Identifier();
            seriesId.setValue(resultSet.getString("series_id"));
            systemMetadata.setSeriesId(seriesId);

            // add serialVersion
            BigInteger serialVersion = new BigInteger(resultSet.getString("serial_version"));
            systemMetadata.setSerialVersion(serialVersion);

            // add date_modified
            Date dateSystemMetadataLastModified = resultSet.getTimestamp("date_modified");
            systemMetadata.setDateSysMetadataModified(dateSystemMetadataLastModified);

            // add archived value, if it was stored
            boolean archived = resultSet.getBoolean("archived");  // getBoolean returns false for null results
            if (!resultSet.wasNull())
                systemMetadata.setArchived(new Boolean(archived));

            // add date_uploaded
            Date dateUploaded = resultSet.getTimestamp("date_uploaded");
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
                obsoletedById.setValue(obsoletedBy);
                systemMetadata.setObsoletedBy(obsoletedById);
            }

            // potentially build a ReplicationPolicy
            
            // add replication_allowed, if it was persisted
            boolean replAllowed = resultSet.getBoolean("replication_allowed");  // getBoolean returns false for null results
            
            if (!resultSet.wasNull()) {
                // populate and add ReplicationPolicy
                ReplicationPolicy replicationPolicy = new ReplicationPolicy();
            
                replicationPolicy.setReplicationAllowed(new Boolean(replAllowed));

                // add number_replicas
                int numberOfReplicas = resultSet.getInt("number_replicas"); // getInt returns 0 for null values
                if (numberOfReplicas > 0) {
                    replicationPolicy.setNumberReplicas(new Integer(numberOfReplicas));

                }

                // add preferred and blocked lists
                List<ReplicationPolicyEntry> replPolicies = new ArrayList<ReplicationPolicyEntry>();
                List<NodeReference> preferredNodes = new ArrayList<NodeReference>();
                List<NodeReference> blockedNodes = new ArrayList<NodeReference>();

                replPolicies = listReplicationPolicies(pid, localTableMap);

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
            } else {
                systemMetadata.setReplicationPolicy(null);
            }

            // populate and add replicas list

            List<Replica> replicas = new ArrayList<Replica>();
            replicas = listReplicaEntries(pid, localTableMap);
            systemMetadata.setReplicaList(replicas);

            // populate and add AccessPolicy
            List<AccessRule> accessRules = new ArrayList<AccessRule>();
            accessRules = listAccessRules(pid, localTableMap);
            accessPolicy.setAllowList(accessRules);
            systemMetadata.setAccessPolicy(accessPolicy);

            // Validate the system metadata in debug mode using TypeMarshaller
            if (log.isDebugEnabled()) {
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    TypeMarshaller.marshalTypeToOutputStream(systemMetadata, baos);
                    log.debug("SystemMetadata for pid " + pid.getValue() + " is: "
                            + baos.toString());

                } catch (MarshallingException e) {
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

    /**
     * Convert string-based permission values to Metacat integer-based values
     * 
     * @param permission
     * @return
     */
    public int convertPermission(Permission permission) {

        final int CHMOD = 1;
        final int WRITE = 2;
        final int READ = 4;
        final int ALL = 7;

        if (permission.equals(Permission.READ)) {
            return READ;
        }
        if (permission.equals(Permission.WRITE)) {
            return WRITE;
        }
        if (permission.equals(Permission.CHANGE_PERMISSION)) {
            return CHMOD;
        }
        return -1;
    }

    /**
     * Convert integer-based permission values to string-based permissions
     * A value of 0 is silently ignored
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
