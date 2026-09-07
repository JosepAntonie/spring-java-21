package com.jos.ant.common.factory;

import com.jos.ant.common.properties.JpaProperties;
import jakarta.persistence.EntityManagerFactory;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.util.Map;

@Log4j2
public abstract class DatabaseConfigFactory
{
    private HibernateJpaVendorAdapter getHibernateJpaVendorAdapter( JpaProperties jpaProperties, String configName )
    {
        log.info( "{} -> getHibernateJpaVendorAdapter",  configName );
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabasePlatform( jpaProperties.databasePlatform() );
        hibernateJpaVendorAdapter.setGenerateDdl( jpaProperties.generateDdl() );
        hibernateJpaVendorAdapter.setShowSql( jpaProperties.showSql() );
        return hibernateJpaVendorAdapter;
    }

    protected DataSource dataSource( DataSourceProperties dataSourceProperties, String configName )
    {
        log.info( "{} -> dataSource", configName );
        return dataSourceProperties.initializeDataSourceBuilder().build();
    }

    protected EntityManagerFactoryBuilder entityManagerFactoryBuilder( JpaProperties jpaProperties, String configName )
    {
        log.info( "{} -> entityManagerFactoryBuilder", configName );
        return new EntityManagerFactoryBuilder( getHibernateJpaVendorAdapter( jpaProperties, configName ), dataSource -> Map.of( "hibernate.hbm2ddl.auto", jpaProperties.ddlAuto(), "hibernate.physical_naming_strategy", jpaProperties.physicalStrategy() ), null );
    }

    protected LocalContainerEntityManagerFactoryBean entityManager( EntityManagerFactoryBuilder entityManagerFactoryBuilder, DataSource dataSource, String packages, String configName )
    {
        log.info( "{} -> entityManager", configName );
        return entityManagerFactoryBuilder.dataSource( dataSource ).packages( packages ).build();
    }

    protected PlatformTransactionManager platformTransactionManager( EntityManagerFactory entityManagerFactory, String configName )
    {
        log.info( "{} -> platformTransactionManager", configName );
        return new JpaTransactionManager( entityManagerFactory );
    }
}
