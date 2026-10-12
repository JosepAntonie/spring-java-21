package com.jos.ant.repository.mysql.runner;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import java.sql.Connection;
import java.sql.Statement;

import static org.mockito.Mockito.*;

class ShedLockCommandLineRunnerTest
{
    private ShedLockCommandLineRunner shedLockCommandLineRunner;
    private DataSource dataSource;

    @BeforeEach
    void setUp()
    {
        dataSource = mock( DataSource.class );
        shedLockCommandLineRunner = new ShedLockCommandLineRunner( dataSource );
    }

    @Test
    void run() throws Exception
    {
        Connection connection = mock( Connection.class );
        Statement statement = mock( Statement.class );
        when( dataSource.getConnection() ).thenReturn( connection );
        when( connection.createStatement() ).thenReturn( statement );
        shedLockCommandLineRunner.run();
        verify( statement, times( 1 ) ).close();
        verify( connection, times( 1 ) ).close();
    }
}
