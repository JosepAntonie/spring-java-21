package com.jos.ant.config;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.Map;

@Log4j2
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( transactionManagerRef = "mysqlTransactionManager", entityManagerFactoryRef = "mysqlEntityManager", basePackages = { "com.jos.ant.repository.mysql.impl", "com.jos.ant.repository.mysql.jpa" } )
public class MySqlConfig
{
    @Value( "${mysql.jpa.hibernate.ddl-auto}" )
    private String jpaDdlAuto;

    @Value( "${mysql.jpa.database-platform}" )
    private String jpaDatabasePlatform;

    @Value( "${mysql.jpa.hibernate.naming.physical-strategy}" )
    private String jpaPhysicalStrategy;

    @Value( "${mysql.jpa.generate-ddl}" )
    private Boolean generateDdl;

    @Value( "${mysql.jpa.show-sql}" )
    private Boolean showSql;

    @Primary @Bean
    public PlatformTransactionManager mysqlTransactionManager( @Qualifier( "mysqlEntityManager" ) EntityManagerFactory mysqlEntityManagerFactory )
    {
        log.info( "MySqlConfig -> mysqlTransactionManager" );
        return new JpaTransactionManager( mysqlEntityManagerFactory );
    }

    @Primary @Bean
    public LocalContainerEntityManagerFactoryBean mysqlEntityManager( @Qualifier( "mysqlDataSource" ) DataSource mysqlDataSource, @Qualifier( "mysqlEntityManagerFactoryBuilder" ) EntityManagerFactoryBuilder builder )
    {
        log.info( "MySqlConfig -> mysqlEntityManager" );
        return builder.dataSource( mysqlDataSource ).packages( "com.jos.ant.repository.mysql" ).build();
    }

    @Primary @Bean
    public DataSource mysqlDataSource( @Qualifier( "mysqlDataSourceProperties" ) DataSourceProperties mysqlDataSourceProperties )
    {
        log.info( "MySqlConfig -> mysqlDataSource" );
        return mysqlDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Primary @Bean @ConfigurationProperties( "mysql.datasource" )
    public DataSourceProperties mysqlDataSourceProperties()
    {
        log.info( "MySqlConfig -> mysqlDataSourceProperties" );
        return new DataSourceProperties();
    }

    @Primary @Bean
    public EntityManagerFactoryBuilder mysqlEntityManagerFactoryBuilder()
    {
        log.info( "MySqlConfig -> mysqlEntityMAnagerFactoryBuilder" );
        return new EntityManagerFactoryBuilder( getHibernateJpaVendorAdapter(), dataSource -> Map.of( "hibernate.hbm2ddl.auto", jpaDdlAuto, "hibernate.physical_naming_strategy", jpaPhysicalStrategy ), null );
    }

    private HibernateJpaVendorAdapter getHibernateJpaVendorAdapter()
    {
        log.info( "MySqlConfig -> getHibernateJpaVendorAdapter" );
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform( jpaDatabasePlatform );
        vendorAdapter.setGenerateDdl( generateDdl );
        vendorAdapter.setShowSql( showSql );
        return vendorAdapter;
    }
}