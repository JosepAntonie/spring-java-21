package com.jos.ant.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jos.ant.common.model.AuthStorage;
import com.jos.ant.common.payload.response.AuthTokenResponse;
import com.jos.ant.common.payload.response.AuthUserInfoResponse;
import com.jos.ant.common.payload.response.AuthValidateResponse;
import com.jos.ant.common.properties.AuthProperties;
import com.jos.ant.service.impl.AuthServiceImpl;
import com.jos.ant.service.util.AuthUtil;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;

import java.io.IOException;
import java.security.PublicKey;
import java.util.Objects;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthServiceTest
{
    private MockWebServer mockWebServer;
    private AuthService authService;
    private AuthProperties authProperties;

    private MockedStatic<AuthUtil> authUtilMockedStatic;
    private MockedStatic<AuthStorage> authStorageMockedStatic;

    @BeforeEach
    void setUp() throws IOException
    {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        String baseUrl = mockWebServer.url( "/" ).toString();
        authProperties = mock( AuthProperties.class );
        when( authProperties.baseUrl() ).thenReturn( baseUrl );

        WebClient.Builder builder = WebClient.builder();
        authService = new AuthServiceImpl( authProperties, builder );

        authUtilMockedStatic = mockStatic( AuthUtil.class );
        authStorageMockedStatic = mockStatic( AuthStorage.class );
    }

    @AfterEach
    void setDown() throws IOException
    {
        mockWebServer.shutdown();
        authUtilMockedStatic.close();
        authStorageMockedStatic.close();
    }

    @Test
    void getPublicKey() throws Exception
    {
        when( authProperties.keysApi() ).thenReturn( "/keys" );
        String mockJwksJson = "{\"keys\":[]}";
        PublicKey mockPublicKey = mock( PublicKey.class );
        mockWebServer.enqueue( new MockResponse().setResponseCode( 200 ).setHeader( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE ).setBody( mockJwksJson ) );
        authUtilMockedStatic.when( () -> AuthUtil.getPublicKey(  eq( "key-123" ), anyString() ) ).thenReturn( mockPublicKey );

        PublicKey result = authService.getPublicKey( "key-123" );
        assertNotNull( result );
        assertEquals( mockPublicKey, result );

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals( "GET", request.getMethod() );
        assertEquals( "/keys", request.getPath() );
    }

    @Test
    void getAuthorize()
    {
        when( authProperties.authorizeApi() ).thenReturn( "/authorize" );
        when( authProperties.clientId() ).thenReturn( "client-id" );
        when( authProperties.callbackRedirectUri() ).thenReturn( "http://localhost:8080/callback" );
        authUtilMockedStatic.when( () -> AuthUtil.authorizeRequest( anyString(), anyString() ) ).thenReturn( "response_type=code&client_id=client-id-123" );
        String resultUrl = authService.getAuthorize();

        assertNotNull( resultUrl );
        assertTrue( resultUrl.startsWith( authProperties.baseUrl() ) );
        assertTrue( resultUrl.contains( "/authorize?response_type=code&client_id=client-id-123" ) );
    }

    @Test
    void getToken() throws Exception
    {
        when( authProperties.tokenApi() ).thenReturn( "/token" );
        when( authProperties.clientId() ).thenReturn( "client-id" );
        when( authProperties.clientSecret() ).thenReturn( "client-secret" );
        when( authProperties.callbackRedirectUri() ).thenReturn( "http://localhost:8080/callback" );

        mockWebServer.enqueue( new MockResponse().setResponseCode( 200 ).setHeader( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE ).setBody( new ObjectMapper().writeValueAsString( mock ( AuthTokenResponse.class ) ) ) );
        authUtilMockedStatic.when( () -> AuthUtil.tokenRequest( anyString(), anyString(), anyString(), anyString() ) ).thenReturn( new LinkedMultiValueMap<>() );
        AuthTokenResponse actualResponse = authService.getToken( "auth-code-001" );

        assertNotNull( actualResponse );

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals( "POST", request.getMethod() );
        assertEquals( "/token", request.getPath() );
        assertTrue( Objects.requireNonNull( request.getHeader( HttpHeaders.CONTENT_TYPE ) ).contains( MediaType.APPLICATION_FORM_URLENCODED_VALUE ) );
    }

    @Test
    void getToken_Error()
    {
        when( authProperties.tokenApi() ).thenReturn( "/token" );
        when( authProperties.clientId() ).thenReturn( "client-id" );
        when( authProperties.clientSecret() ).thenReturn( "client-secret" );
        when( authProperties.callbackRedirectUri() ).thenReturn( "http://localhost:8080/callback" );
        mockWebServer.enqueue( new MockResponse().setResponseCode( 400 ).setBody( "{\"error\": \"invalid_grant\"}" ) );
        authUtilMockedStatic.when( () -> AuthUtil.tokenRequest( anyString(), anyString(), anyString(), anyString() ) ).thenReturn( new LinkedMultiValueMap<>() );
        RuntimeException exception = assertThrows( RuntimeException.class, () -> authService.getToken( "access-token" ) );
        assertEquals( "Error Get Token", exception.getMessage() );

        mockWebServer.enqueue( new MockResponse().setResponseCode( 500 ).setBody( "{\"error\": \"invalid_grant\"}" ) );
        exception = assertThrows( RuntimeException.class, () -> authService.getToken( "access-token" ) );
        assertEquals( "Error Get Token", exception.getMessage() );
    }

    @Test
    void getValidate() throws Exception
    {
        when( authProperties.validateApi() ).thenReturn( "/validate" );
        when( authProperties.clientId() ).thenReturn( "client-id" );
        when( authProperties.clientSecret() ).thenReturn( "client-secret" );

        mockWebServer.enqueue( new MockResponse().setResponseCode( 200 ).setHeader( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE ).setBody( new ObjectMapper().writeValueAsString( mock( AuthValidateResponse.class ) ) ) );
        authUtilMockedStatic.when( () -> AuthUtil.validateRequest( anyString() ) ).thenReturn( new LinkedMultiValueMap<>() );
        AuthValidateResponse actualResponse = authService.getValidate( "access-token" );

        assertNotNull( actualResponse );

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals( "POST", request.getMethod() );
        assertEquals( "/validate", request.getPath() );
        assertNotNull( request.getHeader( HttpHeaders.AUTHORIZATION ) );
        assertTrue( Objects.requireNonNull( request.getHeader( HttpHeaders.AUTHORIZATION ) ).startsWith( "Basic " ) );
    }

    @Test
    void getValidate_Error()
    {
        when( authProperties.validateApi() ).thenReturn( "/validate" );
        when( authProperties.clientId() ).thenReturn( "client-id" );
        when( authProperties.clientSecret() ).thenReturn( "client-secret" );

        mockWebServer.enqueue( new MockResponse().setResponseCode( 400 ).setBody( "{\"error\": \"invalid_grant\"}" ) );
        authUtilMockedStatic.when( () -> AuthUtil.validateRequest( anyString() ) ).thenReturn( new LinkedMultiValueMap<>() );

        RuntimeException exception = assertThrows( RuntimeException.class, () -> authService.getValidate( "access-token" ) );
        assertEquals( "Error Get Validate", exception.getMessage() );

        mockWebServer.enqueue( new MockResponse().setResponseCode( 500 ).setBody( "{\"error\": \"invalid_grant\"}" ) );
        exception = assertThrows( RuntimeException.class, () -> authService.getValidate( "access-token" ) );
        assertEquals( "Error Get Validate", exception.getMessage() );
    }

    @Test
    void getUserInfo() throws Exception
    {
        when( authProperties.userInfoApi() ).thenReturn( "/user-info" );
        mockWebServer.enqueue( new MockResponse().setResponseCode( 200 ).setHeader( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE ).setBody( new ObjectMapper().writeValueAsString( mock( AuthUserInfoResponse.class ) ) ) );
        AuthUserInfoResponse actualResponse = authService.getUserInfo( "access-token" );
        assertNotNull( actualResponse );

        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals( "GET", request.getMethod() );
        assertEquals( "/user-info", request.getPath() );
        assertEquals( "Bearer access-token", request.getHeader( HttpHeaders.AUTHORIZATION ) );
    }

    @Test
    void getUserInfo_Error()
    {
        when( authProperties.userInfoApi() ).thenReturn( "/user-info" );
        mockWebServer.enqueue( new MockResponse().setResponseCode( 400 ).setBody( "{\"error\": \"invalid_grant\"}" ) );
        RuntimeException exception = assertThrows( RuntimeException.class, () -> authService.getUserInfo( "access-token" ) );
        assertEquals( "Error Get UserInfo", exception.getMessage() );

        mockWebServer.enqueue( new MockResponse().setResponseCode( 500 ).setBody( "{\"error\": \"invalid_grant\"}" ) );
        exception = assertThrows( RuntimeException.class, () -> authService.getUserInfo( "access-token" ) );
        assertEquals( "Error Get UserInfo", exception.getMessage() );
    }

    @Test
    void getRevoke() throws Exception
    {
        when( authProperties.revokeApi() ).thenReturn( "/revoke" );
        when( authProperties.clientId() ).thenReturn( "client-id" );
        when( authProperties.clientSecret() ).thenReturn( "client-secret" );
        mockWebServer.enqueue( new MockResponse().setResponseCode( 200 ) );
        authUtilMockedStatic.when( () -> AuthUtil.revokeRequest( anyString() ) ).thenReturn( new LinkedMultiValueMap<>() );

        assertDoesNotThrow( () -> authService.getRevoke( "access-token" ) );
        RecordedRequest request = mockWebServer.takeRequest();
        assertEquals( "POST", request.getMethod() );
        assertEquals( "/revoke", request.getPath() );
    }

    @Test
    void getRevoke_Error()
    {
        when( authProperties.revokeApi() ).thenReturn( "/revoke" );
        when( authProperties.clientId() ).thenReturn( "client-id" );
        when( authProperties.clientSecret() ).thenReturn( "client-secret" );
        mockWebServer.enqueue( new MockResponse().setResponseCode( 400 ).setBody( "{\"error\": \"invalid_grant\"}" ) );
        authUtilMockedStatic.when( () -> AuthUtil.revokeRequest( anyString() ) ).thenReturn( new LinkedMultiValueMap<>() );
        RuntimeException exception = assertThrows( RuntimeException.class, () -> authService.getRevoke( "access-token" ) );
        assertEquals( "Error Get Revoke", exception.getMessage() );

        mockWebServer.enqueue( new MockResponse().setResponseCode( 500 ).setBody( "{\"error\": \"invalid_grant\"}" ) );
        exception = assertThrows( RuntimeException.class, () -> authService.getRevoke( "access-token" ) );
        assertEquals( "Error Get Revoke", exception.getMessage() );
    }

    @Test
    void getLogout()
    {
        AuthTokenResponse tokenResponseMock = mock( AuthTokenResponse.class );
        when( tokenResponseMock.tokenId() ).thenReturn( "token-id" );
        when( authProperties.logoutApi() ).thenReturn( "/logout" );
        when( authProperties.logoutRedirectUri() ).thenReturn( "http://localhost:8080/logout-callback" );
        // noinspection ResultOfMethodCallIgnored
        authStorageMockedStatic.when( AuthStorage::getAuthTokenResponse ).thenReturn( tokenResponseMock );
        authUtilMockedStatic.when( () -> AuthUtil.logoutRequest( eq( "token-id" ), anyString() ) ).thenReturn( "id_token_hint=id-token-abc" );
        String logoutUrl = authService.getLogout();
        assertNotNull( logoutUrl );
        assertTrue( logoutUrl.contains( "/logout?id_token_hint=id-token-abc" ) );
    }
}
