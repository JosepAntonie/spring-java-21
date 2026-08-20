package com.jos.ant.security;

import com.jos.ant.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class JsonWebTokenTest
{
    private JsonWebToken jsonWebToken;
    private AuthService authService;

    private PublicKey publicKey;
    private PrivateKey privateKey;
    private static final String KEY_ID = "key-id";

    @BeforeEach
    void setUp() throws Exception
    {
        authService = mock( AuthService.class );
        jsonWebToken = new JsonWebToken( authService );

        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance( "RSA" );
        keyPairGenerator.initialize( 2048 );
        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        publicKey = keyPair.getPublic();
        privateKey = keyPair.getPrivate();
    }

    @Test
    void getClaims_tokenValid()
    {
        String token = Jwts.builder().header().keyId( KEY_ID ).and().subject( "user" ).claim( "email", "user@domain.com" ).issuedAt( new Date() ).expiration( new Date( System.currentTimeMillis() + 60000 ) ).signWith( privateKey ).compact();
        when( authService.getPublicKey( eq( KEY_ID ) ) ).thenReturn( publicKey );

        Claims claims = jsonWebToken.getClaims( token );

        assertNotNull( claims );
        assertEquals( "user", claims.getSubject() );
        assertEquals( "user@domain.com", claims.get( "email" ) );
        verify( authService, times( 1 ) ).getPublicKey( KEY_ID );
    }

    @Test
    void getClaims_tokenIsExpired()
    {
        String expiredToken = Jwts.builder().header().keyId( KEY_ID ).and().subject( "user" ).issuedAt( new Date( System.currentTimeMillis() - 100000 ) ).expiration( new Date( System.currentTimeMillis() - 50000 ) ).signWith( privateKey ).compact();
        when( authService.getPublicKey( eq( KEY_ID ) ) ).thenReturn( publicKey );
        assertNull( jsonWebToken.getClaims( expiredToken ) );
        verify( authService, times( 1 ) ).getPublicKey( KEY_ID );
    }

    @Test
    void getClaims_tokenException() throws Exception
    {
        KeyPairGenerator anotherGenerator = KeyPairGenerator.getInstance( "RSA" );
        anotherGenerator.initialize( 2048 );
        KeyPair wrongKeyPair = anotherGenerator.generateKeyPair();

        String tokenSignedWithWrongKey = Jwts.builder().header().keyId( KEY_ID ).and().subject( "user" ).issuedAt( new Date() ).expiration( new Date( System.currentTimeMillis() + 60000 ) ).signWith( wrongKeyPair.getPrivate() ).compact();

        when( authService.getPublicKey( eq( KEY_ID ) ) ).thenReturn( publicKey );
        assertNull( jsonWebToken.getClaims( tokenSignedWithWrongKey ) );
        verify( authService, times( 1 ) ).getPublicKey( KEY_ID );
    }
}
