package com.jos.ant.config;

import com.jos.ant.security.JwtFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class WebSecurityConfigTest
{
    private WebSecurityConfig webSecurityConfig;

    @BeforeEach
    void setUp()
    {
        webSecurityConfig = new WebSecurityConfig( mock( JwtFilter.class ) );
    }

    @Test
    void securityFilterChain()
    {
        HttpSecurity httpSecurity = mock( HttpSecurity.class, RETURNS_DEEP_STUBS );
        DefaultSecurityFilterChain expectedChain = mock( DefaultSecurityFilterChain.class );

        when( httpSecurity.build() ).thenReturn( expectedChain );
        assertNotNull( webSecurityConfig.securityFilterChain( httpSecurity ) );
        verify( httpSecurity ).build();
    }

    @Test
    void csrfTokenRepository()
    {
        assertNotNull( webSecurityConfig.csrfTokenRepository() );
    }

    @Test
    void corsConfigurationSource()
    {
        assertNotNull( webSecurityConfig.corsConfigurationSource() );
    }
}
