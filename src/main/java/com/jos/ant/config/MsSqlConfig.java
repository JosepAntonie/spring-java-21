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
@EnableJpaRepositories( transactionManagerRef = "mssqlTransactionManager", entityManagerFactoryRef = "mssqlEntityManager", basePackages = { "com.jos.ant.repository.mssql.impl", "com.jos.ant.repository.mssql.jpa" } )
public class MsSqlConfig
{
    @Value( "${mssql.datasource.hikari.data-source-properties.authentication}" )
    private String dataSourceAuthentication;

    @Value( "${mssql.jpa.hibernate.ddl-auto}" )
    private String jpaDdlAuto;

    @Value( "${mssql.jpa.database-platform}" )
    private String jpaDatabasePlatform;

    @Value( "${mssql.jpa.hibernate.naming.physical-strategy}" )
    private String jpaPhysicalStrategy;

    @Value( "${mssql.jpa.generate-ddl}" )
    private Boolean generateDdl;

    @Value( "${mssql.jpa.show-sql}" )
    private Boolean showSql;

    @Bean
    public PlatformTransactionManager mssqlTransactionManager ( @Qualifier( "mssqlEntityManager" ) EntityManagerFactory mssqlEntityManagerFactory )
    {
        log.info( "MsSqlConfig -> mssqlTransactionManager" );
        return new JpaTransactionManager( mssqlEntityManagerFactory );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mssqlEntityManager(@Qualifier( "mssqlDataSource" ) DataSource mssqlDataSource, @Qualifier( "mssqlEntityManagerFactoryBuilder" ) EntityManagerFactoryBuilder builder )
    {
        log.info( "MsSqlConfig -> mssqlEntityManager" );
        return builder.dataSource( mssqlDataSource ).packages( "com.jos.ant.repository.mssql" ).build();
    }

    @Bean
    public DataSource mssqlDataSource(@Qualifier( "mssqlDataSourceProperties" ) DataSourceProperties mssqlDataSourceProperties )
    {
        log.info( "MsSqlConfig -> mssqlDataSource" );
        return mssqlDataSourceProperties.initializeDataSourceBuilder().build();
    }

    @Bean @ConfigurationProperties( "mssql.datasource" )
    public DataSourceProperties mssqlDataSourceProperties()
    {
        log.info( "MsSqlConfig -> mssqlDataSourceProperties" );
        return new DataSourceProperties();
    }

    @Bean
    public EntityManagerFactoryBuilder mssqlEntityManagerFactoryBuilder()
    {
        log.info( "MsSqlConfig -> mssqlEntityManagerFactoryBuilder" );
        return new EntityManagerFactoryBuilder( getHibernateJpaVendorAdapter(), dataSource -> Map.of( "hibernate.hbm2ddl.auto", jpaDdlAuto, "hibernate.physical_naming_strategy", jpaPhysicalStrategy ), null );
    }

    private HibernateJpaVendorAdapter getHibernateJpaVendorAdapter()
    {
        log.info( "MsSqlConfig -> getHibernateJpaVendorAdapter" );
        HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        vendorAdapter.setDatabasePlatform( jpaDatabasePlatform );
        vendorAdapter.setGenerateDdl( generateDdl );
        vendorAdapter.setShowSql( showSql );
        return vendorAdapter;
    }
}