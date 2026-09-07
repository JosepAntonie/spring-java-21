package com.jos.ant.config;

import com.jos.ant.common.factory.DatabaseConfigFactory;
import com.jos.ant.common.properties.JpaProperties;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Log4j2
@Configuration
@EnableTransactionManagement
@EnableJpaRepositories( transactionManagerRef = "mysqlTransactionManager", entityManagerFactoryRef = "mysqlEntityManager", basePackages = { "com.jos.ant.repository.mysql.impl", "com.jos.ant.repository.mysql.jpa" } )
public class MySqlConfig extends DatabaseConfigFactory
{
    private static final String CONFIG_NAME = "MySqlConfig";

    @Primary @Bean @ConfigurationProperties( "mysql.datasource" )
    public DataSourceProperties mysqlDataSourceProperties()
    {
        log.info( "{} -> dataSourceProperties", CONFIG_NAME );
        return new DataSourceProperties();
    }

    @Primary @Bean
    public JpaProperties mysqlJpaProperties( Environment environment )
    {
        log.info( "{} -> JpaProperties", CONFIG_NAME );
        return Binder.get( environment ).bind( "mysql.jpa", JpaProperties.class ).orElseGet( () -> new JpaProperties( null, null, null, false, false ) );
    }

    @Primary @Bean
    public DataSource mysqlDataSource( @Qualifier( "mysqlDataSourceProperties" ) DataSourceProperties mysqlDataSourceProperties )
    {
        return dataSource( mysqlDataSourceProperties, CONFIG_NAME );
    }

    @Primary @Bean
    public EntityManagerFactoryBuilder mysqlEntityManagerFactoryBuilder( @Qualifier( "mysqlJpaProperties" )JpaProperties jpaProperties )
    {
        return entityManagerFactoryBuilder( jpaProperties, CONFIG_NAME );
    }

    @Primary @Bean
    public LocalContainerEntityManagerFactoryBean mysqlEntityManager( @Qualifier( "mysqlEntityManagerFactoryBuilder" ) EntityManagerFactoryBuilder entityManagerFactoryBuilder, @Qualifier( "mysqlDataSource" ) DataSource mysqlDataSource )
    {
        return entityManager( entityManagerFactoryBuilder, mysqlDataSource, "com.jos.ant.repository.mysql", CONFIG_NAME );
    }

    @Primary @Bean
    public PlatformTransactionManager mysqlTransactionManager( @Qualifier( "mysqlEntityManager" ) EntityManagerFactory mysqlEntityManagerFactory )
    {
        return platformTransactionManager( mysqlEntityManagerFactory, CONFIG_NAME );
    }
}