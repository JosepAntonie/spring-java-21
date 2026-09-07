package com.jos.ant.repository.mysql.runner;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NonNull;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Log4j2
@Component
@RequiredArgsConstructor
public class ShedLockCommandLineRunner implements CommandLineRunner
{
    private final DataSource mysqlDataSource;

    public void run( String @NonNull ... args )
    {
        log.info( "ShedLockCommandLineRunner -> run" );

        JdbcTemplate jdbcTemplate = new JdbcTemplate( mysqlDataSource );

        String createTableQuery = """
                CREATE TABLE IF NOT EXISTS SHEDLOCK (
                    NAME VARCHAR(64) NOT NULL,
                    LOCK_UNTIL TIMESTAMP(3) NOT NULL,
                    LOCKED_AT TIMESTAMP(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
                    LOCKED_BY VARCHAR(255) NOT NULL,
                    PRIMARY KEY (name)
                );
            """;

        jdbcTemplate.execute(createTableQuery);
    }
}
