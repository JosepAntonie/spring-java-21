package com.jos.ant.security;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Log4j2
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter
{
    private final JsonWebToken jsonWebToken;

    @Override @SuppressWarnings( "NullableProblems" )
    public void doFilterInternal( HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain ) throws ServletException, IOException
    {
        log.info( "JWT Filter -> doFilterInternal" );
        String jwtToken = jwtExtract( httpServletRequest );
        if ( jwtToken != null )
        {
            Claims claims = jsonWebToken.getClaims( jwtToken );
            if ( claims != null )
            {
                UsernamePasswordAuthenticationToken authentication = getAuthentication( claims, httpServletRequest );
                authentication.setDetails( new WebAuthenticationDetailsSource().buildDetails( httpServletRequest ) );
                SecurityContextHolder.getContext().setAuthentication( authentication );
            }
            else SecurityContextHolder.clearContext();
        }
        else SecurityContextHolder.clearContext();

        filterChain.doFilter( httpServletRequest, httpServletResponse );
    }

    private UsernamePasswordAuthenticationToken getAuthentication( Claims claims, HttpServletRequest httpServletRequest )
    {
        List<? extends GrantedAuthority> authorities = ( claims.get( "authorities" ) != null ) ? ( (List<?> ) claims.get( "authorities" ) ).stream().map(authority -> new SimpleGrantedAuthority( ( String ) authority ) ).toList(): new ArrayList<>();
        return new UsernamePasswordAuthenticationToken( new User( claims.getSubject(), null, authorities ), jwtExtract( httpServletRequest ), authorities );
    }

    private String jwtExtract( HttpServletRequest httpServletRequest )
    {
        if ( httpServletRequest.getCookies() != null )
            for ( Cookie cookie : httpServletRequest.getCookies() )
                if ( "ACCESS_TOKEN".equals( cookie.getName() ) ) return cookie.getValue();
        return null;
    }
}
