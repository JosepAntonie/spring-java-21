package com.jos.ant.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Log4j2
@Configuration
public class WebClientConfig
{
    @Bean
    public WebClient.Builder webClientBuilder()
    {
        log.info( "{} -> webClientBuilder", getClass().getSimpleName() );
        return WebClient.builder();
    }
}
