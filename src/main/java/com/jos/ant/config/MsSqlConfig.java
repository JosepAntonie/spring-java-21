package com.jos.ant.config;

import com.jos.ant.common.factory.DatabaseConfigFactory;
import com.jos.ant.common.properties.JpaProperties;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Log4j2
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( transactionManagerRef = "mssqlTransactionManager", entityManagerFactoryRef = "mssqlEntityManager", basePackages = { "com.jos.ant.repository.mssql.impl", "com.jos.ant.repository.mssql.jpa" } )
public class MsSqlConfig extends DatabaseConfigFactory
{
    private static final String CONFIG_NAME = "MsSqlConfig";

    @Value( "${mssql.datasource.hikari.data-source-properties.authentication}" )
    private String dataSourceAuthentication;

    @Bean @ConfigurationProperties( "mssql.datasource" )
    public DataSourceProperties mssqlDataSourceProperties()
    {
        log.info( "{} -> dataSourceProperties", CONFIG_NAME );
        return new DataSourceProperties();
    }

    @Bean
    public JpaProperties mssqlJpaProperties( Environment environment )
    {
        log.info( "{} -> jpaProperties", CONFIG_NAME );
        return Binder.get( environment ).bind( "mssql.jpa", JpaProperties.class ).orElseGet( () -> new JpaProperties( null, null, null, false, false ) );
    }

    @Bean
    public DataSource mssqlDataSource( @Qualifier( "mssqlDataSourceProperties" ) DataSourceProperties mssqlDataSourceProperties )
    {
        return dataSource( mssqlDataSourceProperties, CONFIG_NAME );
    }

    @Bean
    public EntityManagerFactoryBuilder mssqlEntityManagerFactoryBuilder( @Qualifier( "mssqlJpaProperties" )JpaProperties jpaProperties )
    {
        return entityManagerFactoryBuilder( jpaProperties, CONFIG_NAME );
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean mssqlEntityManager( @Qualifier( "mssqlEntityManagerFactoryBuilder" ) EntityManagerFactoryBuilder mssqlEntityManagerFactoryBuilder, @Qualifier( "mssqlDataSource" ) DataSource mssqlDataSource )
    {
        return entityManager( mssqlEntityManagerFactoryBuilder, mssqlDataSource, "com.jos.ant.repository.mssql", CONFIG_NAME );
    }

    @Bean
    public PlatformTransactionManager mssqlTransactionManager ( @Qualifier( "mssqlEntityManager" ) EntityManagerFactory mssqlEntityManagerFactory )
    {
        return platformTransactionManager( mssqlEntityManagerFactory, CONFIG_NAME );
    }
}