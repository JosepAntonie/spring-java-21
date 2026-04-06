package com.jos.ant.common.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
@ConfigurationProperties( prefix = "swagger" )
public class SwaggerProperties
{
    private String securitySchemeName;
    private String title;
    private String description;
    private String version;
    private License license = new License();
    private Contact contact = new Contact();

    @Setter
    @Getter
    public static class License
    {
        private String name;
        private String url;
    }

    @Setter @Getter
    public static class Contact
    {
        private String name;
        private String url;
        private String email;
    }
}
