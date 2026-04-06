package com.jos.ant.config;

import com.jos.ant.common.properties.SwaggerProperties;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Log4j2
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig
{
    private final SwaggerProperties swaggerProperties;

    @Bean
    public OpenAPI customerOpenAPI()
    {
        log.info( "SwaggerConfig -> customerOpenAPI" );
        return new OpenAPI().addSecurityItem( getSecurityRequirement() ).components( getComponents() ).info( getInfo() );
    }

    private SecurityRequirement getSecurityRequirement()
    {
        return new SecurityRequirement().addList( swaggerProperties.getSecuritySchemeName() );
    }

    private Components getComponents()
    {
        return new Components().addSecuritySchemes( swaggerProperties.getSecuritySchemeName(), getSecurityScheme() );
    }

    private SecurityScheme getSecurityScheme()
    {
        return new SecurityScheme().name( swaggerProperties.getSecuritySchemeName() ).type( SecurityScheme.Type.APIKEY ).in( SecurityScheme.In.HEADER );
    }

    private Info getInfo()
    {
        return new Info().title( swaggerProperties.getTitle() ).description( swaggerProperties.getDescription() ).version( swaggerProperties.getVersion() ).license( getLicense() ).contact( getContact() );
    }

    private License getLicense()
    {
        return new License().name( swaggerProperties.getLicense().getName() ).url( swaggerProperties.getLicense().getUrl() );
    }

    private Contact getContact()
    {
        return new Contact().name( swaggerProperties.getContact().getName() ).url( swaggerProperties.getContact().getUrl() ).email( swaggerProperties.getContact().getEmail() );
    }
}
