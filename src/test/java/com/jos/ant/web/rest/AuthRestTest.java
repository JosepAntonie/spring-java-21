package com.jos.ant.web.rest;

import com.jos.ant.common.payload.response.AuthTokenResponse;
import com.jos.ant.common.payload.response.AuthUserInfoResponse;
import com.jos.ant.common.payload.response.AuthValidateResponse;
import com.jos.ant.common.properties.AuthProperties;
import com.jos.ant.service.AuthService;
import com.jos.ant.web.rest.impl.AuthRestImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.HashMap;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith( MockitoExtension.class )
class AuthRestTest
{
    private AuthRest authRest;
    private AuthService authService;
    private AuthProperties authProperties;

    @BeforeEach
    void setUp()
    {
        authProperties = mock( AuthProperties.class );
        authService = mock( AuthService.class );
        authRest = new AuthRestImpl( authService, authProperties );
    }

    @Test
    void getAuthorized()
    {
        when( authService.getAuthorize() ).thenReturn( "http://localhost:8080/auth" );
        assertNotNull( authRest.getAuthorize() );
    }

    @Test
    void getToken()
    {
        when( authService.getToken( anyString() ) ).thenReturn( getAuthTokenResponse() );
        when( authService.getValidate( anyString() ) ).thenReturn( getAuthValidateResponse( Boolean.FALSE ) );
        when( authProperties.loginRedirectUri() ).thenReturn( "http://localhost:8080/auth" );
        assertNotNull( authRest.getToken( "code" ) );

        when( authService.getValidate( anyString() ) ).thenReturn( getAuthValidateResponse( Boolean.TRUE ) );
        when( authProperties.dashboardRedirectUri() ).thenReturn( "http://localhost:8080/auth" );
        assertNotNull( authRest.getToken( "code" ) );
    }
    private AuthTokenResponse getAuthTokenResponse()
    {
        return new AuthTokenResponse( "", "", "", "", "", "", 0, 0, 0 );
    }
    private AuthValidateResponse getAuthValidateResponse( Boolean active )
    {
        return new AuthValidateResponse( 0L, 0L, 0L ,"", "", "", "", "", "", "", "", Collections.emptyList(), mock( AuthValidateResponse.RealmAccess.class ), new HashMap<>(), "", Boolean.TRUE,"",  "","","","", "", "", "", active );
    }

    @Test
    void getUserInfo()
    {
        when( authService.getUserInfo( anyString() ) ).thenReturn( mock( AuthUserInfoResponse.class ) );
        assertNotNull( authRest.getUserInfo( "accessToken" ) );
    }

    @Test
    void getRevoke()
    {
        assertNotNull( authRest.getRevoke( "accessToken" ) );
        verify( authService, times( 1 ) ).getRevoke( "accessToken" );
    }

    @Test
    void getLogout()
    {
        when( authService.getLogout() ).thenReturn( "http://localhost:8080/auth" );
        assertNotNull( authRest.getLogout() );
    }

    @Test
    void getLogin()
    {
        when( authProperties.loginRedirectUri() ).thenReturn( "http://localhost:8080/auth" );
        assertNotNull( authRest.getLogin() );
    }
}
