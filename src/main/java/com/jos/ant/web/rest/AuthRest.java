package com.jos.ant.web.rest;

import com.jos.ant.common.payload.response.AuthUserInfoResponse;
import org.springframework.http.ResponseEntity;

public interface AuthRest
{
    ResponseEntity<Void> getLogin();
    ResponseEntity<Void> getAuthorize();
    ResponseEntity<Void> getToken( String code );
    ResponseEntity<AuthUserInfoResponse> getUserInfo( String accessToken );
    ResponseEntity<Void> getRevoke( String accessToken );
    ResponseEntity<Void> getLogout();
}
