package com.jos.ant.common.payload.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class AuthTokenResponse
{
    @JsonProperty( "id_token" )
    private String tokenId;

    @JsonProperty( "access_token" )
    private String accessToken;

    @JsonProperty( "refresh_token" )
    private String refreshToken;

    @JsonProperty( "token_type" )
    private String tokenType;

    @JsonProperty( "scope" )
    private String scope;

    @JsonProperty( "expires_in" )
    private int expiresIn;
}
