package org.dataone.cn.dao;

import org.springframework.jdbc.core.JdbcTemplate;

public class ReplicationDaoMetacatImplTestUtil {

    public static void createTables(JdbcTemplate jdbc) {
        jdbc.execute("CREATE TABLE IF NOT EXISTS systemmetadatareplicationstatus " + //
                "(guid text, member_node varchar(250), " + //
                "status varchar(250), " + //
                "date_verified timestamp)");//
    }

    public static void dropTables(JdbcTemplate jdbc) {
        jdbc.execute("DROP TABLE IF EXISTS systemmetadatareplicationstatus;");
    }

}
