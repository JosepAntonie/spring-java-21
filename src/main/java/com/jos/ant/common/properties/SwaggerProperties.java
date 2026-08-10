package com.jos.ant.common.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties( prefix = "swagger" )
public record SwaggerProperties(
        String securitySchemeName,
        String title,
        String description,
        String version,
        License license,
        Contact contact
)
{
    public record License( String name, String url ) {}
    public record Contact( String name, String url, String email ) {}
}
