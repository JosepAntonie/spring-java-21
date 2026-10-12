package com.jos.ant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan( "com.jos.ant.common.properties" )
public class MainApp
{
    public static void main( String[] args )
    {
        SpringApplication.run( MainApp.class, args );
    }
}
