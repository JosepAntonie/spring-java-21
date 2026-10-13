package com.jos.ant.config;

import com.jos.ant.common.properties.SwaggerProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class SwaggerConfigTest
{
    private SwaggerConfig swaggerConfig;
    private SwaggerProperties swaggerProperties;

    @BeforeEach
    void setUp()
    {
        swaggerProperties = mock( SwaggerProperties.class );
        swaggerConfig = new SwaggerConfig( swaggerProperties );
    }

    @Test
    void customerOpenAPI()
    {
        when( swaggerProperties.securitySchemeName() ).thenReturn( "securitySchemeName" );
        when( swaggerProperties.title() ).thenReturn( "title" );
        when( swaggerProperties.description() ).thenReturn( "description" );
        when( swaggerProperties.version() ).thenReturn( "version" );
        SwaggerProperties.License license = mock( SwaggerProperties.License.class );
        when( swaggerProperties.license() ).thenReturn( license );
        SwaggerProperties.Contact contact = mock( SwaggerProperties.Contact.class );
        when( swaggerProperties.contact() ).thenReturn( contact );

        when( license.name() ).thenReturn( "licenseName" );
        when( license.url() ).thenReturn( "url" );

        when( contact.name() ).thenReturn( "contactName" );
        when( contact.url() ).thenReturn( "url" );
        when( contact.email() ).thenReturn( "email" );
        assertNotNull( swaggerConfig.customerOpenAPI() );
    }
}
