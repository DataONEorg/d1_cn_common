package org.dataone.cn.model.repository;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.cn.dao.DataSourceFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

public abstract class PostgresRepositoryConfiguration extends D1BaseJpaRepositoryConfiguration {

    private static final Log log = LogFactory.getLog(PostgresRepositoryConfiguration.class);

    @Bean
    public DataSource dataSource() {
        return DataSourceFactory.getPostgresDataSource();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setShowSql(show_sql);
        hibernateJpaVendorAdapter.setGenerateDdl(generate_ddl);
        hibernateJpaVendorAdapter.setDatabase(Database.POSTGRESQL);
        return hibernateJpaVendorAdapter;
    }

}
