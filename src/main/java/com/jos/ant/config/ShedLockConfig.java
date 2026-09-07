package com.jos.ant.config;

import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider.ColumnNames;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@EnableSchedulerLock( defaultLockAtMostFor = "10m" )
public class ShedLockConfig
{
    @Bean
    public LockProvider lockProvider( @Qualifier( "mysqlDataSource" ) DataSource dataSource )
    {
        return new JdbcTemplateLockProvider(
                JdbcTemplateLockProvider.Configuration.builder()
                        .withJdbcTemplate( new JdbcTemplate( dataSource ) )
                        .withTableName( "SHEDLOCK" )
                        .withColumnNames( new ColumnNames( "NAME", "LOCK_UNTIL", "LOCKED_AT", "LOCKED_BY" ) )
                        .usingDbTime()
                        .build()
        );
    }
}
