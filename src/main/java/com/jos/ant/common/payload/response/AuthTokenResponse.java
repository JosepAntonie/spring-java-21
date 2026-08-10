package com.jos.ant.common.payload.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties( ignoreUnknown = true )
public record AuthTokenResponse(
        @JsonProperty( "id_token" ) String tokenId,
        @JsonProperty( "access_token" ) String accessToken,
        @JsonProperty( "refresh_token" ) String refreshToken,
        @JsonProperty( "session_state" ) String sessionState,
        @JsonProperty( "token_type" ) String tokenType,
        String scope,
        @JsonProperty( "expires_in" ) Integer expiresIn,
        @JsonProperty( "refresh_expires_in" ) Integer refreshExpiresIn,
        @JsonProperty( "not-before_policy" ) Integer notBeforePolicy
)
{}
