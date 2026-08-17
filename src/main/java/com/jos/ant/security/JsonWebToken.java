package com.jos.ant.security;

import com.jos.ant.service.AuthService;
import io.jsonwebtoken.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Log4j2
@Component
@RequiredArgsConstructor
public class JsonWebToken
{
    private final AuthService authService;

    public Claims getClaims( String token )
    {
        Claims claims = null;
        try
        {
            claims = Jwts.parser().verifyWith( authService.getPublicKey( getKeyId( token ) ) ).build().parseSignedClaims( token ).getPayload();
        }
        catch ( ExpiredJwtException | MalformedJwtException | UnsupportedJwtException | IllegalArgumentException e )
        {
            log.error( "JWT Token could not be deserialized: " );
            if ( e instanceof ExpiredJwtException ) log.error( "JWT token is expired: ", e );
            else if ( e instanceof MalformedJwtException ) log.error( "Invalid JWT token: ", e );
            else log.error( "JWT token is unsupported or claims string is empty: ", e );
        }
        return claims;
    }

    private String getKeyId( String token )
    {
        return new JSONObject( new String( Base64.getUrlDecoder().decode( token.split( "\\." )[ 0 ] ) ) ).getString( "kid" );
    }
}