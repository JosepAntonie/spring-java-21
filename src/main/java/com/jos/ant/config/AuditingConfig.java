package com.jos.ant.config;

import lombok.extern.log4j.Log4j2;
import org.jspecify.annotations.NullMarked;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@Log4j2
@Configuration
@EnableJpaAuditing( auditorAwareRef = "auditorAware" )
public class AuditingConfig
{
    @Bean
    public AuditorAware<String> auditorAware()
    {
        log.info( "{} -> auditorAware", getClass().getSimpleName() );
        return new AuditorAwareImpl();
    }
}

@Log4j2
class AuditorAwareImpl implements AuditorAware<String>
{
    @Override @NullMarked
    public Optional<String> getCurrentAuditor()
    {
        log.info( "{} -> getCurrentAuditor", getClass().getSimpleName() );
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if ( authentication == null || !authentication.isAuthenticated() || authentication instanceof AnonymousAuthenticationToken ) return Optional.empty();
        return Optional.ofNullable( authentication.getName() );
    }
}