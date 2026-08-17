package com.jos.ant.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Log4j2
public class JwtAccessDeniedHandler implements AccessDeniedHandler
{
    public void handle( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException accessDeniedException ) throws IOException
    {
        log.error( "JWT Access Denied -> Forbidden error: {}", accessDeniedException.getMessage() );
        httpServletResponse.setContentType( MediaType.APPLICATION_JSON_VALUE );
        httpServletResponse.setStatus( HttpServletResponse.SC_FORBIDDEN );

        Map<String,Object> body = new HashMap<>();
        body.put( "status", HttpServletResponse.SC_FORBIDDEN );
        body.put( "error", "FORBIDDEN" );
        body.put( "message", accessDeniedException.getMessage() );
        body.put( "path", httpServletRequest.getServletPath() );

        new ObjectMapper().writeValue( httpServletResponse.getOutputStream(), body );
    }
}
