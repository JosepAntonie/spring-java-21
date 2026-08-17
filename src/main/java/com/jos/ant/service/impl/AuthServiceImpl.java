package com.jos.ant.service.impl;

import com.jos.ant.common.model.AuthStorage;
import com.jos.ant.common.payload.response.AuthTokenResponse;
import com.jos.ant.common.payload.response.AuthUserInfoResponse;
import com.jos.ant.common.payload.response.AuthValidateResponse;
import com.jos.ant.common.properties.AuthProperties;
import com.jos.ant.service.AuthService;
import com.jos.ant.service.util.AuthUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.security.PublicKey;

@Log4j2
@Service
public class AuthServiceImpl implements AuthService
{
    private final AuthProperties authProperties;
    private final WebClient webClient;

    @Autowired
    public AuthServiceImpl( WebClient.Builder webClientBuilder, AuthProperties authProperties )
    {
        this.authProperties = authProperties;
        this.webClient = webClientBuilder.baseUrl( authProperties.baseUrl() ).build();
    }

    public PublicKey getPublicKey( String keyId )
    {
        log.info( "Service -> getPublicKey" );
        return AuthUtil.getPublicKey( keyId, webClient.get().uri( authProperties.keysApi() ).retrieve().bodyToMono( String.class ).block() );
    }

    public String getAuthorize()
    {
        log.info( "Service -> getAuthorize" );
        return getUri( authProperties.baseUrl(), authProperties.authorizeApi(), AuthUtil.authorizeRequest( authProperties.clientId(), authProperties.callbackRedirectUri() ) );
    }

    public AuthTokenResponse getToken( String code )
    {
        log.info( "Service -> getToken" );
        return webClient.post().uri( authProperties.tokenApi() )
                .header( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE )
                .body( BodyInserters.fromFormData( AuthUtil.tokenRequest( authProperties.clientId(), authProperties.clientSecret(), code, authProperties.callbackRedirectUri() ) ) )
                .retrieve().onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> handleClientError( clientResponse, "Error Get Token" )
                ).bodyToMono( AuthTokenResponse.class ).block();
    }

    public AuthValidateResponse getValidate( String accessToken )
    {
        log.info( "Service -> getValidate" );
        return webClient.post().uri( authProperties.validateApi() )
                .headers( header -> header.setBasicAuth( authProperties.clientId(), authProperties.clientSecret() ) )
                .header( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE )
                .body( BodyInserters.fromFormData( AuthUtil.validateRequest( accessToken) ) )
                .retrieve().onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> handleClientError( clientResponse, "Error Get Validate" )
                ).bodyToMono( AuthValidateResponse.class ).block();
    }

    public AuthUserInfoResponse getUserInfo( String accessToken )
    {
        log.info( "Service -> getUserInfo" );
        return webClient.get().uri( authProperties.userInfoApi() )
                .headers( header -> header.setBearerAuth( accessToken) )
                .header( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE )
                .retrieve().onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> handleClientError( clientResponse, "Error Get UserInfo" )
                ).bodyToMono( AuthUserInfoResponse.class ).block();
    }

    public void getRevoke( String accessToken )
    {
        log.info( "Service -> getRevoke" );
        webClient.post().uri( authProperties.revokeApi() )
                .headers( header -> header.setBasicAuth( authProperties.clientId(), authProperties.clientSecret() ) )
                .header( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED_VALUE )
                .body( BodyInserters.fromFormData( AuthUtil.revokeRequest( accessToken ) ) )
                .retrieve().onStatus(
                        status -> status.is4xxClientError() || status.is5xxServerError(),
                        clientResponse -> handleClientError( clientResponse, "Error Get Revoke" )
                ).bodyToMono( Void.class ).block();
    }

    public String getLogout()
    {
        log.info( "Service -> getLogout" );
        return getUri( authProperties.baseUrl(), authProperties.logoutApi(), AuthUtil.logoutRequest( AuthStorage.getAuthTokenResponse().tokenId(), authProperties.logoutRedirectUri() ) );
    }

    private String getUri( String baseUrl, String api, String parameters )
    {
        return String.format( "%s%s?%s", baseUrl,  api, parameters );
    }

    private Mono<? extends Throwable> handleClientError( ClientResponse clientResponse, String message )
    {
        return clientResponse.bodyToMono( String.class ).doOnNext( errorBody -> log.error( "Error Status: {}", errorBody ) ).then( Mono.error( new RuntimeException( message ) ) );
    }
}
