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

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MySqlConfigTest
{
    private MySqlConfig mySqlConfig;

    @BeforeEach
    void setUp()
    {
        mySqlConfig = new MySqlConfig();
    }

    @Test
    void mysqlDataSourceProperties()
    {
        assertNotNull( mySqlConfig.mysqlDataSourceProperties() );
    }

    @Test
    void mysqlJpaProperties()
    {
        MockEnvironment environment = new MockEnvironment();
        assertNotNull( mySqlConfig.mysqlJpaProperties( environment ) );
    }

    @Test
    void mysqlDataSource()
    {
        DataSourceProperties dataSourceProperties = new DataSourceProperties();
        dataSourceProperties.setUrl( "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1" );
        dataSourceProperties.setUsername( "sa" );
        dataSourceProperties.setPassword( "" );
        dataSourceProperties.setDriverClassName( "org.h2.Driver" );
        assertNotNull( mySqlConfig.mysqlDataSource( dataSourceProperties ) );
    }

    @Test
    void mysqlEntityManagerFactoryBuilder()
    {
        JpaProperties jpaProperties = new JpaProperties( new JpaProperties.HibernateProperties( "update", new JpaProperties.NamingProperties( "org.hibernate.boot.model.naming.PhysicalNamingStrategyStandardImpl" ) ), "org.hibernate.dialect.H2Dialect", Boolean.TRUE, Boolean.TRUE );
        assertNotNull( mySqlConfig.mysqlEntityManagerFactoryBuilder( jpaProperties ) );
    }

    @Test
    void mysqlEntityManager()
    {
        EntityManagerFactoryBuilder entityManagerFactoryBuilder = mock( EntityManagerFactoryBuilder.class, RETURNS_DEEP_STUBS );
        DataSource dataSource = mock( DataSource.class );
        LocalContainerEntityManagerFactoryBean localContainerEntityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        when( entityManagerFactoryBuilder.dataSource( dataSource ).packages( any( String[].class ) ).build() ).thenReturn( localContainerEntityManagerFactoryBean );
        assertNotNull( mySqlConfig.mysqlEntityManager( entityManagerFactoryBuilder, dataSource ) );
    }

    @Test
    void mysqlTransactionManager()
    {
        assertNotNull( mySqlConfig.mysqlTransactionManager( mock( EntityManagerFactory.class ) ) );
    }
}
