package com.jos.ant.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JwtFilterTest
{
    private JwtFilter jwtFilter;
    private JsonWebToken jsonWebToken;
    private HttpServletRequest request;
    private HttpServletResponse response;
    private FilterChain filterChain;
    private Claims claims;

    @BeforeEach
    void setUp()
    {
        SecurityContextHolder.clearContext();
        request = mock( HttpServletRequest.class );
        response = mock( HttpServletResponse.class );
        filterChain = mock( FilterChain.class );
        claims = mock( Claims.class );
        jsonWebToken = mock( JsonWebToken.class );
        jwtFilter = new JwtFilter( jsonWebToken );
    }

    @AfterEach
    void setDown()
    {
        SecurityContextHolder.clearContext();
    }

    @Test
    void doFilterInternal_ShouldAuthenticate_WhenValidTokenInCookie() throws ServletException, IOException
    {
        String tokenValue = "valid.jwt.token";
        Cookie accessCookie = new Cookie( "ACCESS_TOKEN", tokenValue );
        Cookie otherCookie = new Cookie( "OTHER_COOKIE", "value" );

        when( request.getCookies() ).thenReturn( new Cookie[]{ otherCookie, accessCookie } );
        when( jsonWebToken.getClaims( tokenValue ) ).thenReturn( claims );
        when( claims.get( "preferred_username", String.class ) ).thenReturn( "testuser" );

        List<String> mockAuthorities = List.of( "ROLE_USER", "ROLE_ADMIN" );
        doReturn( mockAuthorities ).when( claims ).get( "authorities" );

        jwtFilter.doFilterInternal( request, response, filterChain );

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull( auth, "Authentication should not be null in SecurityContext" );
        assertInstanceOf( User.class, auth.getPrincipal() );

        User user = ( User ) auth.getPrincipal();
        assertEquals( "testuser", user.getUsername() );
        assertEquals( 2, auth.getAuthorities().size() );
        assertEquals( tokenValue, auth.getCredentials() );

        verify( filterChain, times( 1 ) ).doFilter( request, response );
    }

    @Test
    void doFilterInternal_ShouldAuthenticateWithoutAuthorities_WhenAuthoritiesClaimIsNull() throws ServletException, IOException
    {
        String tokenValue = "valid.jwt.token";
        Cookie accessCookie = new Cookie( "ACCESS_TOKEN", tokenValue );

        when( request.getCookies() ).thenReturn( new Cookie[]{ accessCookie } );
        when( jsonWebToken.getClaims( tokenValue ) ).thenReturn( claims );
        when( claims.get( "preferred_username", String.class ) ).thenReturn( "noauthuser" );
        when( claims.get( "authorities" ) ).thenReturn( null );

        jwtFilter.doFilterInternal( request, response, filterChain );

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        assertNotNull( auth );

        User user = ( User ) auth.getPrincipal();
        assert user != null;
        assertEquals( "noauthuser", user.getUsername() );
        assertTrue( auth.getAuthorities().isEmpty() );

        verify( filterChain, times( 1 ) ).doFilter( request, response );
    }

    @Test
    void doFilterInternal_ShouldClearContext_WhenClaimsAreNull() throws ServletException, IOException
    {
        String invalidToken = "invalid.jwt.token";
        Cookie accessCookie = new Cookie( "ACCESS_TOKEN", invalidToken );

        when( request.getCookies() ).thenReturn( new Cookie[]{ accessCookie } );
        when( jsonWebToken.getClaims( invalidToken ) ).thenReturn( null );

        jwtFilter.doFilterInternal( request, response, filterChain );

        assertNull( SecurityContextHolder.getContext().getAuthentication() );
        verify( filterChain, times( 1 ) ).doFilter( request, response );
    }

    @Test
    void doFilterInternal_ShouldClearContext_WhenAccessTokenCookieNotPresent() throws ServletException, IOException
    {
        Cookie otherCookie = new Cookie( "SOME_OTHER_COOKIE", "value" );
        when( request.getCookies() ).thenReturn( new Cookie[]{ otherCookie } );

        jwtFilter.doFilterInternal( request, response, filterChain );

        assertNull( SecurityContextHolder.getContext().getAuthentication() );
        verify( jsonWebToken, never() ).getClaims( anyString() );
        verify( filterChain, times( 1 ) ).doFilter( request, response );
    }

    @Test
    void doFilterInternal_ShouldClearContext_WhenCookiesAreNull() throws ServletException, IOException
    {
        when( request.getCookies() ).thenReturn( null );

        jwtFilter.doFilterInternal( request, response, filterChain );

        assertNull( SecurityContextHolder.getContext().getAuthentication() );
        verify( jsonWebToken, never() ).getClaims( anyString() );
        verify( filterChain, times( 1 ) ).doFilter( request, response );
    }
}
