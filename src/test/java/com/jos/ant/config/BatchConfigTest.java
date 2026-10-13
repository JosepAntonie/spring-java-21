package com.jos.ant.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.sql.DataSource;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class BatchConfigTest
{
    private BatchConfig batchConfig;

    @BeforeEach
    void setUp()
    {
        batchConfig = new BatchConfig();
    }

    @Test
    void dataSourceInitializer()
    {
        assertNotNull( batchConfig.dataSourceInitializer( mock( DataSource.class ) ) );
    }
}
