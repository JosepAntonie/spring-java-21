package com.jos.ant.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.ZoneId;

@Log4j2
@Configuration
public class AppConfig
{
    @Bean
    public Clock clock()
    {
        log.info( "{} -> clock", getClass().getSimpleName() );
        return Clock.system( ZoneId.of( "America/Mexico_City" ) );
    }
}
