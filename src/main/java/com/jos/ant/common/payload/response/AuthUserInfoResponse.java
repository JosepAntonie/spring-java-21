package com.jos.ant.common.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties( ignoreUnknown = true )
public record AuthUserInfoResponse(
        String sub,
        @JsonProperty( "email_verified" ) Boolean emailVerified,
        String name,
        @JsonProperty( "preferred_username" ) String preferredUsername,
        @JsonProperty( "given_name" ) String givenName,
        @JsonProperty( "family_name" ) String familyName,
        String email
)
{
}
