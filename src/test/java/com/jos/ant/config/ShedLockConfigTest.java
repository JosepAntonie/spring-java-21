package com.jos.ant.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.mockito.Mockito.*;
import static  org.junit.jupiter.api.Assertions.*;

class ShedLockConfigTest
{
    private ShedLockConfig shedLockConfig;

    @BeforeEach
    void setUp()
    {
        shedLockConfig = new ShedLockConfig();
    }

    @Test
    void lockProvider()
    {
        DataSource dataSource = mock( DataSource.class );
        assertNotNull( shedLockConfig.lockProvider( dataSource ) );
    }
}
