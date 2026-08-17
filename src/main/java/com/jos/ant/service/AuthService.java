package com.jos.ant.service;

import com.jos.ant.common.payload.response.AuthTokenResponse;
import com.jos.ant.common.payload.response.AuthUserInfoResponse;
import com.jos.ant.common.payload.response.AuthValidateResponse;

import java.security.PublicKey;

public interface AuthService
{
    PublicKey getPublicKey( String keyId );
    String getAuthorize();
    AuthTokenResponse getToken( String code );
    AuthValidateResponse getValidate( String accessToken );
    AuthUserInfoResponse getUserInfo( String accessToken );
    void getRevoke( String accessToken );
    String getLogout();
}
