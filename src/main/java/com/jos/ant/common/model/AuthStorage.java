package com.jos.ant.common.model;

import com.jos.ant.common.payload.response.AuthTokenResponse;
import com.jos.ant.common.payload.response.AuthUserInfoResponse;
import lombok.Getter;
import lombok.Setter;

public class AuthStorage
{
    @Setter @Getter
    private static AuthTokenResponse authTokenResponse = null;

    @Setter @Getter
    private static AuthUserInfoResponse authUserInfoResponse = null;

    @Setter @Getter
    private static String codeVerifier = null;

    private AuthStorage()
    {
    }
}
