package com.jos.ant.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.EnableJdbcJobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.DataSourceInitializer;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;

import javax.sql.DataSource;

@Log4j2
@Configuration
@EnableBatchProcessing
@EnableJdbcJobRepository( dataSourceRef = "mysqlDataSource", transactionManagerRef = "mysqlTransactionManager" )
public class BatchConfig
{
    @Bean
    public DataSourceInitializer dataSourceInitializer( DataSource mysqlDataSource )
    {
        log.info( "{} -> dataSourceInitializer", getClass().getSimpleName() );
        DataSourceInitializer initializer = new DataSourceInitializer();
        initializer.setDataSource( mysqlDataSource );

        ResourceDatabasePopulator databasePopulator = new ResourceDatabasePopulator();
        databasePopulator.addScript( new ClassPathResource( "org/springframework/batch/core/schema-mysql.sql" ) );
        databasePopulator.setContinueOnError( Boolean.TRUE );

        initializer.setDatabasePopulator( databasePopulator );
        return initializer;
    }
}
