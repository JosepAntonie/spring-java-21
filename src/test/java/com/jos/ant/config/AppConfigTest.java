package com.jos.ant.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppConfigTest
{
    private AppConfig appConfig;

    @BeforeEach
    void setUp()
    {
        appConfig = new AppConfig();
    }

    @Test
    void clock()
    {
        assertNotNull( appConfig.clock() );
    }
}
