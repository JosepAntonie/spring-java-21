package com.jos.ant.config;

import com.jos.ant.security.JwtAccessDeniedHandler;
import com.jos.ant.security.JwtAuthenticationEntryPoint;
import com.jos.ant.security.JwtFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class WebSecurityConfig
{
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain( HttpSecurity httpSecurity )
    {
        httpSecurity.csrf( AbstractHttpConfigurer::disable )
                .cors( cors -> cors.configurationSource( corsConfigurationSource() ) )
                .addFilterBefore( jwtFilter, UsernamePasswordAuthenticationFilter.class )
                .exceptionHandling( exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint( new JwtAuthenticationEntryPoint() )
                        .accessDeniedHandler( new JwtAccessDeniedHandler() ) )
                .sessionManagement( sessionManagement -> sessionManagement
                        .sessionCreationPolicy( SessionCreationPolicy.STATELESS ) )
                .authorizeHttpRequests( authorizeRequests -> authorizeRequests
                        .requestMatchers( HttpMethod.GET, "/api/auth/authorize", "/api/auth/token" ).permitAll()
                        .requestMatchers( "/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html" ).permitAll()
                        .anyRequest().fullyAuthenticated() );
        return httpSecurity.build();
    }

    @Bean
    public CsrfTokenRepository csrfTokenRepository()
    {
        CookieCsrfTokenRepository cookieCsrfTokenRepository = new CookieCsrfTokenRepository();
        cookieCsrfTokenRepository.setCookieCustomizer( customizer -> customizer.maxAge(Duration.ofMinutes( 30 ) ) );
        return cookieCsrfTokenRepository;
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource()
    {
        CorsConfiguration  corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedOrigins( List.of( "http://localhost:3001", "http://localhost:8080" ) );
        corsConfiguration.setAllowedMethods( List.of( HttpMethod.GET.toString(), HttpMethod.POST.toString(), HttpMethod.PUT.toString(), HttpMethod.DELETE.toString(), HttpMethod.OPTIONS.toString() ) );
        corsConfiguration.setAllowedHeaders( List.of( "X-CSRF-TOKEN", HttpHeaders.AUTHORIZATION, HttpHeaders.CONTENT_TYPE ) );
        corsConfiguration.setAllowCredentials( Boolean.TRUE );

        UrlBasedCorsConfigurationSource urlBasedCorsConfigurationSource = new UrlBasedCorsConfigurationSource();
        urlBasedCorsConfigurationSource.registerCorsConfiguration( "/**", corsConfiguration );
        return urlBasedCorsConfigurationSource;
    }
}
