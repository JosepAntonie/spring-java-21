package com.jos.ant.common.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthUserInfoResponse
{
    private String email;

    @JsonProperty( "email_verified" )
    private String emailVerified;

    @JsonProperty( "family_name" )
    private String familyName;

    @JsonProperty( "given_name" )
    private String givenName;

    private String locale;

    private String name;

    private String nickname;

    @JsonProperty( "preferred_username" )
    private String preferredUsername;

    private String sub;
}
