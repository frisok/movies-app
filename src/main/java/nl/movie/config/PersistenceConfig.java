package nl.movie.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.dao.support.PersistenceExceptionTranslator;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.HibernateExceptionTranslator;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 *
 */
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories({"nl.movie"})
//@Import({SecurityConfig.class})
public class PersistenceConfig {

    public PersistenceConfig() {
        super();
    }

    @Bean
    public EntityManagerFactory entityManagerFactory() {

        final LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();

        factory.setJpaVendorAdapter(jpaVendorAdapter());
        factory.setPackagesToScan("nl.movie");
        factory.setDataSource(dataSource());
        factory.afterPropertiesSet();

        return factory.getObject();
    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {

        final HibernateJpaVendorAdapter jpaVendorAdapter = new HibernateJpaVendorAdapter();

        jpaVendorAdapter.setDatabase(Database.MYSQL);
        jpaVendorAdapter.setDatabasePlatform(org.hibernate.dialect.MySQL5InnoDBDialect.class.getName());
        jpaVendorAdapter.setGenerateDdl(Boolean.TRUE);

        return jpaVendorAdapter;
    }

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        final BasicDataSource dataSource = new BasicDataSource();

        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://127.0.0.1:3306/calendar");
        dataSource.setUsername("root");
        dataSource.setPassword("killer");

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory, final JpaVendorAdapter jpaVendorAdapter) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        transactionManager.setJpaDialect(jpaVendorAdapter.getJpaDialect());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslator hibernateExceptionTranslator() {
        return new HibernateExceptionTranslator();
    }


}
