package com.jos.ant.config;

import com.jos.ant.common.properties.JpaProperties;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jdbc.autoconfigure.DataSourceProperties;
import org.springframework.boot.jpa.EntityManagerFactoryBuilder;
import org.springframework.mock.env.MockEnvironment;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mock;

class MsSqlConfigTest
{
    private MsSqlConfig msSqlConfig;

    @BeforeEach
    void setUp()
    {
        msSqlConfig = new MsSqlConfig();
    }

    @Test
    void msSqlDataSourceProperties()
    {
        assertNotNull( msSqlConfig.mssqlDataSourceProperties() );
    }

    @Test
    void msSqlJpaProperties()
    {
        MockEnvironment environment = new MockEnvironment();
        assertNotNull( msSqlConfig.mssqlJpaProperties( environment ) );
    }

    @Test
    void msSqlDataSource()
    {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl( "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1" );
        dataSourceProperties.setUsername( "sa" );
        dataSourceProperties.setPassword( "" );
        dataSourceProperties.setDriverClassName( "org.h2.Driver" );
        assertNotNull( msSqlConfig.mssqlDataSource( dataSourceProperties ) );
    }

    @Test
    void msSqlEntityManagerFactoryBuilder()
    {
        JpaProperties jpaProperties = new JpaProperties( new JpaProperties.HibernateProperties( "update", new JpaProperties.NamingProperties( "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl" ) ), "org.hibernate.dialect.H2Dialect", Boolean.TRUE, Boolean.TRUE );
        assertNotNull( msSqlConfig.mssqlEntityManagerFactoryBuilder( jpaProperties ) );
    }

    @Test
    void msSqlEntityManager()
    {
        EntityManagerFactoryBuilder entityManagerFactoryBuilder = mock( EntityManagerFactoryBuilder.class, RETURNS_DEEP_STUBS );
        DataSource dataSource = mock( DataSource.class );
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        when( entityManagerFactoryBuilder.dataSource( dataSource ).packages( any( String[].class ) ).build() ).thenReturn( localContainerEntityManagerFactoryBean );
        assertNotNull( msSqlConfig.mssqlEntityManager( entityManagerFactoryBuilder, dataSource ) );
    }

    @Test
    void msSqlTransactionManager()
    {
        assertNotNull( msSqlConfig.mssqlTransactionManager( mock( EntityManagerFactory.class ) ) );
    }
}
