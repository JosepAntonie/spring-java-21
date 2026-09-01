package com.jos.ant.security;

import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;

import java.io.IOException;

import static org.mockito.Mockito.*;

class JwtAccessDeniedHandlerTest
{
    private JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @BeforeEach
    void setUp()
    {
        jwtAccessDeniedHandler = new JwtAccessDeniedHandler();
    }

    @Test
    void handle() throws IOException
    {
        HttpServletRequest httpServletRequestMock = mock( HttpServletRequest.class );
        HttpServletResponse httpServletResponseMock = mock( HttpServletResponse.class );
        AccessDeniedException accessDeniedExceptionMock = mock( AccessDeniedException.class );
        ServletOutputStream servletOutputStreamMock = mock( ServletOutputStream.class );

        when( httpServletRequestMock.getServletPath() ).thenReturn( "/api/test" );
        when( httpServletResponseMock.getOutputStream() ).thenReturn( servletOutputStreamMock );
        when( accessDeniedExceptionMock.getMessage() ).thenReturn( "Error Test" );

        jwtAccessDeniedHandler.handle( httpServletRequestMock, httpServletResponseMock, accessDeniedExceptionMock );

        verify( httpServletResponseMock ).setContentType( MediaType.APPLICATION_JSON_VALUE );
        verify( httpServletResponseMock, times( 1 ) ).setStatus( HttpServletResponse.SC_FORBIDDEN );
        verify( httpServletResponseMock, times( 1 ) ).getOutputStream();
    }
}
