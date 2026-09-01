package com.jos.ant.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint
{
    public void commence( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException authenticationException ) throws IOException
    {
        log.error( "JWT Authentication Entry Point -> Unauthorized Error: {}", authenticationException.getMessage() );
        httpServletResponse.setContentType( MediaType.APPLICATION_JSON_VALUE );
        httpServletResponse.setStatus( HttpServletResponse.SC_UNAUTHORIZED );

        Map<String, Object> body = new HashMap<>();
        body.put( "status", HttpServletResponse.SC_UNAUTHORIZED );
        body.put( "error", "UNAUTHORIZED" );
        body.put( "message", authenticationException.getMessage() );
        body.put( "path", httpServletRequest.getServletPath() );

        new ObjectMapper().writeValue( httpServletResponse.getOutputStream(), body );
    }
}
