package com.jos.ant.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WebClientConfigTest
{
    private WebClientConfig webClientConfig;

    @BeforeEach
    void setUp()
    {
        webClientConfig = new WebClientConfig();
    }

    @Test
    void webClientBuilder()
    {
        assertNotNull( webClientConfig.webClientBuilder() );
    }
}
