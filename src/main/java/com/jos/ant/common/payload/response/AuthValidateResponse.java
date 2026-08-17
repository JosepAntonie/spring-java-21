package com.jos.ant.common.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

public record AuthValidateResponse(
        Long exp,
        Long iat,
        @JsonProperty( "auth_time" ) Long authTime,
        String jti,
        String iss,
        String aud,
        String sub,
        String typ,
        String azp,
        String sid,
        String acr,
        @JsonProperty( "allowed-origins" ) List<String> allowedOrigins,
        @JsonProperty( "realm_access" ) RealmAccess realmAccess,
        @JsonProperty( "resource_access" ) Map<String, ResourceAccess> resourceAccess,
        String scope,
        @JsonProperty( "email_verified" ) Boolean emailVerified,
        String name,
        @JsonProperty( "preferred_username" ) String preferredUsername,
        @JsonProperty( "given_name" ) String giveName,
        @JsonProperty( "family_name" ) String familyName,
        String email,
        @JsonProperty( "client_id" ) String clientId,
        String username,
        @JsonProperty( "token_type" ) String tokenType,
        Boolean active
)
{
    public record RealmAccess( List<String> roles ){}
    public record ResourceAccess( List<String> roles ){}
}
