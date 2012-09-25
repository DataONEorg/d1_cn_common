package org.dataone.cn.dao;

import java.sql.Types;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;

public class ReplicationDaoMetacatImplTestUtil {

    public static void createTables(JdbcTemplate jdbc) {
        jdbc.execute("CREATE TABLE IF NOT EXISTS smreplicationstatus " + //
                "(guid text, member_node varchar(250), " + //
                "status varchar(250), " + //
                "date_verified timestamp)");//
    }

    public static void dropTables(JdbcTemplate jdbc) {
        jdbc.execute("DROP TABLE IF EXISTS smreplicationstatus;");
    }

    public static void createReplicationStatusRecord(JdbcTemplate jdbc, String guid,
            String memberNodeId, String status, String dateVerified) {

        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };
        jdbc.update("INSERT INTO smreplicationstatus VALUES (?,?,?,?)", new Object[] { guid,
                memberNodeId, status, dateVerified }, types);
    }

    public static void createReplicationStatusRecord(JdbcTemplate jdbc, String guid,
            String memberNodeId, String status, Date dateVerified) {

        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP };
        jdbc.update("INSERT INTO smreplicationstatus VALUES (?,?,?,?)", new Object[] { guid,
                memberNodeId, status, dateVerified }, types);
    }
}
