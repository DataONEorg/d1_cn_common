package org.dataone.cn.dao;

import java.sql.Types;

import org.springframework.jdbc.core.JdbcTemplate;

public class ReplicationDaoMetacatImplTestUtil {

    public static void createTables(JdbcTemplate jdbc) {
        jdbc.execute("CREATE TABLE IF NOT EXISTS systemmetadatareplicationstatus "
                + //
                "(guid text, member_node varchar(250), " + //
                "status varchar(250), " + //
                "date_verified timestamp)");//
    }

    public static void dropTables(JdbcTemplate jdbc) {
        jdbc.execute("DROP TABLE IF EXISTS systemmetadatareplicationstatus;");
    }

    public static void createReplicationStatusRecord(JdbcTemplate jdbc,
            String guid, String memberNodeId, String status, String dateVerified) {

        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
                Types.TIMESTAMP };
        jdbc.update(
                "INSERT INTO systemmetadatareplicationstatus VALUES (?,?,?,?)",
                new Object[] { guid, memberNodeId, status, dateVerified },
                types);
    }
}
