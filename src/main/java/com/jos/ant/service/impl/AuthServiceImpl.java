package com.jos.ant.service.impl;

import com.jos.ant.common.properties.AuthProperties;
import com.jos.ant.service.AuthService;
import com.jos.ant.service.util.AuthUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

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
        this.webClient = webClientBuilder.baseUrl( authProperties.getBaseUrl() ).build();
    }

    public PublicKey getPublicKey( String keyId )
    {
        log.info( "Service -> getPublicKey" );
        return AuthUtil.getPublicKey( keyId, webClient.get().uri( authProperties.getKeysApi() ).retrieve().bodyToMono( String.class ).block() );
    }
}
