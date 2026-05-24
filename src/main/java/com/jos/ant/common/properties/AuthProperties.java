package com.jos.ant.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Setter
@Getter
@Configuration
@ConfigurationProperties( prefix = "auth" )
public class AuthProperties
{
    private String baseUrl;
    private String keysApi;
    private String authorizeApi;
    private String tokenApi;
    private String validateApi;
    private String userInfoApi;
    private String revokeApi;
    private String logoutApi;
    private String clientId;
    private String clientSecret;
    private String callbackRedirectUri;
    private String logoutRedirectUri;
    private String loginRedirectUri;
    private String dashboardRedirectUri;
}
