package com.jos.ant.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix = "auth" )
public record AuthProperties(
        String baseUrl,
        String keysApi,
        String authorizeApi,
        String tokenApi,
        String validateApi,
        String userInfoApi,
        String revokeApi,
        String logoutApi,
        String clientId,
        String clientSecret,
        String callbackRedirectUri,
        String logoutRedirectUri,
        String loginRedirectUri,
        String dashboardRedirectUri
)
{
}
