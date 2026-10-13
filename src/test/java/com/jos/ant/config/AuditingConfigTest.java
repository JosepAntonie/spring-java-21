package com.jos.ant.config;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuditingConfigTest
{
    private AuditingConfig auditingConfig;
    private AuditorAwareImpl auditorAware;

    @BeforeEach
    void setUp()
    {
        auditingConfig = new AuditingConfig();
        auditorAware = new AuditorAwareImpl();
        SecurityContextHolder.clearContext();
    }

    @AfterEach
    void tearDown()
    {
        SecurityContextHolder.clearContext();
    }

    @Test
    void auditorAware()
    {
        assertNotNull( auditingConfig.auditorAware() );

        assertTrue( auditingConfig.auditorAware().getCurrentAuditor().isEmpty() );

        Authentication authentication = mock( Authentication.class );
        when( authentication.isAuthenticated() ).thenReturn( false );

        SecurityContext securityContext = mock( SecurityContext.class );
        when( securityContext.getAuthentication() ).thenReturn( authentication );
        SecurityContextHolder.setContext( securityContext );
        assertTrue( auditingConfig.auditorAware().getCurrentAuditor().isEmpty() );

        AnonymousAuthenticationToken anonymousAuthenticationToken = new AnonymousAuthenticationToken( "key", "anonymousUser", AuthorityUtils.createAuthorityList( "ROLE_ANONYMOUS" ) );
        when( securityContext.getAuthentication() ).thenReturn( anonymousAuthenticationToken );
        SecurityContextHolder.setContext( securityContext );
        assertTrue( auditingConfig.auditorAware().getCurrentAuditor().isEmpty() );

        when( authentication.isAuthenticated() ).thenReturn( true );
        when( authentication.getName() ).thenReturn( "Jos.Ant" );
        when( securityContext.getAuthentication() ).thenReturn( authentication );
        SecurityContextHolder.setContext( securityContext );
        assertTrue( auditingConfig.auditorAware().getCurrentAuditor().isPresent() );
    }
}
