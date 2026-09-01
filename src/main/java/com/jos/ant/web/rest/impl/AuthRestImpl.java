package com.jos.ant.web.rest.impl;

import com.jos.ant.common.model.AuthStorage;
import com.jos.ant.common.payload.response.AuthTokenResponse;
import com.jos.ant.common.payload.response.AuthUserInfoResponse;
import com.jos.ant.common.payload.response.AuthValidateResponse;
import com.jos.ant.common.properties.AuthProperties;
import com.jos.ant.service.AuthService;
import com.jos.ant.web.rest.AuthRest;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping( "/api/auth" )
public class AuthRestImpl implements AuthRest
{
    private final AuthService authService;
    private final AuthProperties authProperties;

    @GetMapping( "/authorize" )
    public ResponseEntity<Void> getAuthorize()
    {
        log.info( "REST API -> getAuthorize" );
        return ResponseEntity.status( HttpStatus.FOUND ).header( HttpHeaders.LOCATION, authService.getAuthorize() ).build();
    }

    @GetMapping( "/token" )
    public ResponseEntity<Void> getToken( @RequestParam( value = "code" ) String code )
    {
        log.debug( "REST API -> getToken" );
        AuthTokenResponse authTokenResponse = authService.getToken( code );
        AuthValidateResponse authValidateResponse = authService.getValidate( authTokenResponse.accessToken() );
        if ( Boolean.TRUE.equals( authValidateResponse.active() ) )
        {
            AuthStorage.setAuthTokenResponse( authTokenResponse );
            AuthStorage.setAuthUserInfoResponse( authService.getUserInfo( authTokenResponse.accessToken() ) );
            return ResponseEntity.status( HttpStatus.FOUND )
                    .header( HttpHeaders.SET_COOKIE, getResponseCookie( "ACCESS_TOKEN", authTokenResponse.accessToken(), authTokenResponse.expiresIn() ).toString() )
                    .header( HttpHeaders.SET_COOKIE, getResponseCookie( "REFRESH_TOKEN", authTokenResponse.refreshToken(), 2592000 ).toString() )
                    .header( HttpHeaders.LOCATION, authProperties.dashboardRedirectUri() ).build();
        }
        else return ResponseEntity.status( HttpStatus.FOUND ).header( HttpHeaders.LOCATION, authProperties.loginRedirectUri() ).build();
    }

    @GetMapping( "/user-info" )
    public ResponseEntity<AuthUserInfoResponse> getUserInfo( @CookieValue( "ACCESS_TOKE" ) String accessToken )
    {
        log.info( "REST API -> getUserInfo" );
        return ResponseEntity.status( HttpStatus.OK ).body( authService.getUserInfo( accessToken ) );
    }

    @GetMapping( "/revoke" )
    public ResponseEntity<Void> getRevoke( @CookieValue( "ACCESS_TOKE" ) String accessToken )
    {
        log.info( "REST API -> getRevoke" );
        authService.getRevoke( accessToken );
        return ResponseEntity.status( HttpStatus.OK ).build();
    }

    @GetMapping( "/logout" )
    public ResponseEntity<Void> getLogout()
    {
        log.info( "REST API -> getLogout" );
        return ResponseEntity.status( HttpStatus.FOUND ).header( HttpHeaders.LOCATION, authService.getLogout() ).build();
    }

    @GetMapping( "/login" )
    public ResponseEntity<Void> getLogin()
    {
        log.info( "REST API -> getLogin" );
        return ResponseEntity.status( HttpStatus.FOUND )
                .header( HttpHeaders.LOCATION, authProperties.loginRedirectUri() )
                .header( HttpHeaders.SET_COOKIE, getResponseCookie( "ACCESS_TOKEN", "", 0 ).toString() )
                .header( HttpHeaders.SET_COOKIE, getResponseCookie( "REFRESH_TOKEN", "", 0 ).toString() ).build();
    }

    private ResponseCookie getResponseCookie( String name, String token, Integer expiresIn )
    {
        return ResponseCookie.from( name, token ).httpOnly( Boolean.TRUE ).secure( Boolean.TRUE ).sameSite( "None" ).path( "/" ).maxAge( expiresIn ).build();
    }
}
