package com.jos.ant.security;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtAuthenticationEntryPointTest
{
    private JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @BeforeEach
    void setUp()
    {
        jwtAuthenticationEntryPoint = new JwtAuthenticationEntryPoint();
    }

    @Test
    void commence() throws IOException
    {
        HttpServletRequest httpServletRequestMock = mock( HttpServletRequest.class );
        HttpServletResponse httpServletResponseMock = mock( HttpServletResponse.class );
        AuthenticationException authenticationExceptionMock = mock( AuthenticationException.class );
        ServletOutputStream servletOutputStreamMock = mock( ServletOutputStream.class );

        when( httpServletRequestMock.getServletPath() ).thenReturn( "/api/test" );
        when( httpServletResponseMock.getOutputStream() ).thenReturn( servletOutputStreamMock );
        when( authenticationExceptionMock.getMessage() ).thenReturn( "Error Test" );

        jwtAuthenticationEntryPoint.commence(  httpServletRequestMock, httpServletResponseMock, authenticationExceptionMock );

        verify( httpServletResponseMock, times( 1 ) ).setContentType( MediaType.APPLICATION_JSON_VALUE );
        verify( httpServletResponseMock, times( 1 ) ).setStatus( HttpServletResponse.SC_UNAUTHORIZED );
        verify( httpServletResponseMock, times( 1 ) ).getOutputStream();

    }
}
