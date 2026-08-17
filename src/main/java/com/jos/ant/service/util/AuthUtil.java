package com.jos.ant.service.util;

import com.jos.ant.common.model.AuthStorage;
import lombok.extern.log4j.Log4j2;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.math.BigInteger;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.RSAPublicKeySpec;
import java.util.Base64;
import java.util.stream.Collectors;

@Log4j2
public class AuthUtil
{
    private AuthUtil()
    {
    }

    private static String generateRandomString()
    {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        return new SecureRandom().ints( 128, 0, chars.length() ).mapToObj( chars::charAt ).map( Object::toString ).collect( Collectors.joining() );
    }

    private static String base64URLEncode( byte[] value )
    {
        return Base64.getUrlEncoder().withoutPadding().encodeToString( value );
    }

    private static String codeChallenge() throws NoSuchAlgorithmException
    {
        String codeVerifier = generateRandomString();
        AuthStorage.setCodeVerifier( codeVerifier );
        return base64URLEncode( MessageDigest.getInstance( "SHA-256" ).digest( codeVerifier.getBytes( StandardCharsets.UTF_8 ) ) );
    }

    private static MultiValueMap<String, String> createParams( String[] keys, String[] values )
    {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        if ( keys.length != values.length ) throw new IllegalArgumentException( "keys.length != values.length" );
        for ( int i = 0; i < keys.length; i++ ) params.add( keys[i], values[i] );
        return params;
    }

    private static String toQueryParams( MultiValueMap<String, String> params )
    {
        return params.entrySet().stream()
                .flatMap( entry -> entry.getValue().stream()
                        .map( value -> entry.getKey() + "=" + URLEncoder.encode( value, StandardCharsets.UTF_8 ) ) )
                .collect( Collectors.joining( "&" ) );
    }

    public static String authorizeRequest( String clientId, String redirectUri )
    {
        String[] keys = new String[] {};
        String[] values = new String[] {};
        try
        {
            keys = new String[] { "client_id", "redirect_uri", "response_type", "code_challenge", "code_challenge_method", "scope", "state" };
            values = new String[] { clientId, redirectUri, "code", codeChallenge(), "S256", "openid profile email offline_access", "1" };
        }
        catch ( NoSuchAlgorithmException e )
        {
            log.error( "NoSuchAlgorithmException", e );
        }

        return toQueryParams( createParams( keys, values ) );
    }

    public static MultiValueMap<String, String> tokenRequest( String clientId, String clientSecret, String code, String redirectUri )
    {
        String[] key = { "client_id", "client_secret", "grant_type", "code", "redirect_uri", "code_verifier" };
        String[] value = { clientId, clientSecret, "authorization_code", code, redirectUri, AuthStorage.getCodeVerifier() };
        return createParams( key, value );
    }

    public static MultiValueMap<String, String> validateRequest( String accessToken )
    {
        return createParams( new String[] { "token" }, new String[] { accessToken } );
    }


    public static MultiValueMap<String, String> revokeRequest( String accessToken )
    {
        return createParams( new String[] { "token", "token_type_hint" }, new String[] { accessToken, "access_token" } );
    }

    public static String logoutRequest( String tokenId, String loginRedirectUri )
    {
        return toQueryParams( createParams( new String[] { "id_token_hint", "post_logout_redirect_uri" }, new String[] { tokenId, loginRedirectUri } ) );
    }

    public static PublicKey getPublicKey( String keyId, String response )
    {
        if ( response != null )
        {
            JSONArray jsonKeys = new JSONObject( response ).getJSONArray( "keys" );
            for ( int i = 0; i < jsonKeys.length(); i++ )
            {
                JSONObject jsonKey = jsonKeys.getJSONObject( i );
                if ( keyId.equals( jsonKey.get( "kid" ) ) )
                {
                    BigInteger modulus = new BigInteger( 1, Base64.getUrlDecoder().decode( (String) jsonKey.get( "n" ) ) );
                    BigInteger exponent = new BigInteger( 1, Base64.getUrlDecoder().decode( (String) jsonKey.get( "e" ) ) );
                    RSAPublicKeySpec rsaPublicKeySpec = new RSAPublicKeySpec( modulus, exponent );
                    try
                    {
                        return KeyFactory.getInstance( "RSA" ).generatePublic( rsaPublicKeySpec );
                    }
                    catch ( Exception e )
                    {
                        log.error( "KeyFactory Error: ", e );
                    }
                }
            }
        }
        return null;
    }
}
