package com.jos.ant.service.util;

import com.jos.ant.common.model.AuthStorage;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import org.springframework.util.MultiValueMap;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PublicKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Base64;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class AuthUtilTest
{
    private MockedStatic<AuthStorage> authStorageMockedStatic;

    @BeforeEach
    void setUp()
    {
        authStorageMockedStatic = mockStatic( AuthStorage.class );
    }

    @AfterEach
    void setDown()
    {
        authStorageMockedStatic.close();
    }

    @Test
    void authorizeRequest()
    {
        String result = AuthUtil.authorizeRequest( "client-id", "http://localhost:8080/redirect-uri" );
        assertNotNull( result );
        assertTrue( result.contains( "client_id=client-id" ) );
        assertTrue( result.contains( "redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Fredirect-uri" ) );
        assertTrue( result.contains( "response_type=code" ) );
        assertTrue( result.contains( "code_challenge=" ) );
        assertTrue( result.contains( "code_challenge_method=S256" ) );
        assertTrue( result.contains( "scope=openid+profile+email+offline_access" ) );
        assertTrue( result.contains( "state=1" ) );

        authStorageMockedStatic.verify( () -> AuthStorage.setCodeVerifier( anyString() ), times( 1 ) );
    }

    @Test
    void tokenRequest()
    {
        String clientId = "client-id";
        String clientSecret = "client-secret";
        String code = "code";
        String redirectUri = "http://localhost:8080/redirect-uri";
        String mockedVerifier = "verifier";

        // noinspection ResultOfMethodCallIgnored
        authStorageMockedStatic.when( AuthStorage::getCodeVerifier ).thenReturn( mockedVerifier );
        MultiValueMap<String, String> result = AuthUtil.tokenRequest( clientId, clientSecret, code, redirectUri );

        assertNotNull( result );
        assertEquals( clientId, result.getFirst( "client_id" ) );
        assertEquals( clientSecret, result.getFirst( "client_secret" ) );
        assertEquals( "authorization_code", result.getFirst( "grant_type" ) );
        assertEquals( code, result.getFirst( "code" ) );
        assertEquals( redirectUri, result.getFirst( "redirect_uri" ) );
        assertEquals( mockedVerifier, result.getFirst( "code_verifier" ) );
    }

    @Test
    void validateRequest()
    {
        String accessToken = "access-token";
        MultiValueMap<String, String> result = AuthUtil.validateRequest( accessToken );

        assertNotNull( result );
        assertEquals( 1, result.size() );
        assertEquals( accessToken, result.getFirst( "token" ) );
    }

    @Test
    void revokeRequest()
    {
        String accessToken = "access-token";
        MultiValueMap<String, String> result = AuthUtil.revokeRequest( accessToken );

        assertNotNull( result );
        assertEquals( 2, result.size() );
        assertEquals( accessToken, result.getFirst( "token" ) );
        assertEquals( "access_token", result.getFirst( "token_type_hint" ) );
    }

    @Test
    void logoutRequest()
    {
        String tokenId = "token-id";
        String logoutRedirectUri = "http://localhost:8080/logout-callback";

        String result = AuthUtil.logoutRequest( tokenId, logoutRedirectUri );

        assertNotNull( result );
        assertTrue( result.contains( "id_token_hint=" + tokenId ) );
        assertTrue( result.contains( "post_logout_redirect_uri=http%3A%2F%2Flocalhost%3A8080%2Flogout-callback" ) );
    }

    @Test
    void getPublicKey() throws Exception
    {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        keyPairGenerator.initialize( 2048 );
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        RSAPublicKey originalPublicKey = (RSAPublicKey) keyPair.getPublic();

        String keyId = "key-id";
        String modulusBase64 = Base64.getUrlEncoder().withoutPadding().encodeToString( originalPublicKey.getModulus().toByteArray() );
        String exponentBase64 = Base64.getUrlEncoder().withoutPadding().encodeToString( originalPublicKey.getPublicExponent().toByteArray() );

        String jwksResponse = String.format( "{\"keys\":[{\"kid\":\"%s\",\"kty\":\"RSA\",\"n\":\"%s\",\"e\":\"%s\"}]}", keyId, modulusBase64, exponentBase64 );

        PublicKey reconstructedPublicKey = AuthUtil.getPublicKey( keyId, jwksResponse );

        assertNotNull( reconstructedPublicKey );
        assertEquals( "RSA", reconstructedPublicKey.getAlgorithm() );
        assertArrayEquals( originalPublicKey.getEncoded(), reconstructedPublicKey.getEncoded() );

        jwksResponse = "{\"keys\":[{\"kid\":\"other-key-id\",\"kty\":\"RSA\",\"n\":\"abc\",\"e\":\"AQAB\"}]}";
        assertNull( AuthUtil.getPublicKey( "non-existent-kid", jwksResponse ) );
        assertNull( AuthUtil.getPublicKey( "any-kid", null ) );

    }
}
