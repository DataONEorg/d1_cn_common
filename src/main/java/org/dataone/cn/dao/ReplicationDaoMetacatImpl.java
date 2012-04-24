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

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.service.types.v1.Identifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

public class ReplicationDaoMetacatImpl implements ReplicationDao {

    private static final Log log = LogFactory.getLog(ReplicationDaoMetacatImpl.class);

    private JdbcTemplate jdbcTemplate;

    private final DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    public ReplicationDaoMetacatImpl() {
        this.jdbcTemplate = new JdbcTemplate(DataSourceFactory.getMetacatDataSource());
    }

    public List<Identifier> getReplicasByDate(Date auditDate, int pageSize, int pageNumber) {

        // SELECT systemmetadata.guid, systemmetadata.number_replicas,
        // systemmetadatareplicationstatus.member_node,
        // systemmetadatareplicationstatus.status,
        // systemmetadatareplicationstatus.date_verified FROM
        // systemmetadata,systemmetadatareplicationstatus WHERE
        // systemmetadata.guid = systemmetadatareplicationstatus.guid AND
        // systemmetadatareplicationstatus.date_verified <= '2012-04-02
        // 00:00:00' LIMIT 10 OFFSET 0;

        String dateString = format.format(auditDate);
        List<Identifier> results = this.jdbcTemplate.query(
                "SELECT systemmetadatareplicationstatus.guid, "
                        + "systemmetadatareplicationstatus.date_verified "
                        + "FROM systemmetadatareplicationstatus "
                        + "WHERE systemmetadatareplicationstatus.date_verified <= ? "
                        + "ORDER BY systemmetadatareplicationstatus.date_verified ASC;",
                new Object[] { dateString }, new IdentifierMapper());

        return results;
    }

    public List<Identifier> getFailedReplicas(int pageSize, int pageNumber) {
        return new ArrayList<Identifier>();
    }

    public List<Identifier> getInvalidReplicas(int pageSize, int pageNumber) {
        return new ArrayList<Identifier>();
    }

    public List<Identifier> getStaleQueuedRelicas(int pageSize, int pageNumber) {
        return new ArrayList<Identifier>();
    }

    private static final class IdentifierMapper implements RowMapper<Identifier> {
        public Identifier mapRow(ResultSet rs, int rowNum) throws SQLException {
            Identifier pid = new Identifier();
            pid.setValue(rs.getString("guid"));
            return pid;
        }
    }
}
