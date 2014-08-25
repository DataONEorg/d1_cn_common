package org.dataone.cn.model.repository;

import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dataone.configuration.Settings;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

/**
 * These annotation need to be present where a concrete persistent model/repository is defined.
 * 
 * @Configuration
 * @EnableJpaRepositories("repository package")
 * @ComponentScan("model package")
**/

public abstract class D1BaseJpaRepositoryConfiguration {

    private static final String show_sql_prop = "datasource.show.sql";
    private static final String generate_ddl_prop = "datasource.generate.ddl";

    protected static final boolean show_sql = Settings.getConfiguration().getBoolean(show_sql_prop,
            false);
    protected static final boolean generate_ddl = Settings.getConfiguration().getBoolean(
            generate_ddl_prop, false);

    private static final Log log = LogFactory.getLog(D1BaseJpaRepositoryConfiguration.class);
    protected AbstractApplicationContext context;

    public void initContext() {
        if (context == null) {
            context = new AnnotationConfigApplicationContext(this.getClass());
        }
    }

    public void closeContext() {
        context.close();
        context = null;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
            JpaVendorAdapter jpaVendorAdapter) {
        LocalContainerEntityManagerFactoryBean lef = new LocalContainerEntityManagerFactoryBean();
        lef.setDataSource(dataSource);
        lef.setJpaVendorAdapter(jpaVendorAdapter);
        lef.setPackagesToScan(this.getPackagesToScan());
        return lef;
    }

    public abstract String getPackagesToScan();

    @Bean
    public PlatformTransactionManager transactionManager() {
        return new JpaTransactionManager();
    }

    public abstract DataSource dataSource();

    public abstract JpaVendorAdapter jpaVendorAdapter();
}
