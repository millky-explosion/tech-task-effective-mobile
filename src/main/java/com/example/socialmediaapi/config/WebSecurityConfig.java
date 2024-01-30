package com.example.socialmediaapi.config;


import com.example.socialmediaapi.security.AuthenticationManager;
import com.example.socialmediaapi.security.BearerTokenServerAuthenticationConverter;
import com.example.socialmediaapi.security.JWTHandler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.WebSecurityConfigurer;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity.IgnoredRequestConfigurer;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.ServerWebExchangeMatchers;
import reactor.core.publisher.Mono;


@Slf4j
@Configuration
@EnableReactiveMethodSecurity
public class WebSecurityConfig  {


    @Value("${jwt.secret}")
    private String secret;

    private final String [] whiteList =
                     {
                     "/api/v1/auth/**", "/api/v1/auth/register",
                     "/api/v1/auth/login", "/swagger-ui/**",
                     "/swagger-ui.html","/swagger-ui/", "/swagger-ui/index.html/",
                             "/webjars/**", "/v3/api-docs/**", "/api/v1/users/**", "/api/v1/post/**"
                     };

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http, AuthenticationManager authenticationManager) {
        return http.csrf(csrf->csrf.disable()
                        .authorizeExchange(authorizeExchange -> authorizeExchange.pathMatchers(whiteList)
                                .permitAll()))
                        .exceptionHandling(exceptionHandling-> exceptionHandling
                                .authenticationEntryPoint((swe , e) -> {
                    log.error("IN securityWebFilterChain - unauthorized error: {}", e.getMessage());
                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED));})
                                .accessDeniedHandler((swe, e) -> {
                    log.error("IN securityWebFilterChain - access denied: {}", e.getMessage());
                    return Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN));}))
                .addFilterAt(bearerAuthenticationFilter(authenticationManager), SecurityWebFiltersOrder.AUTHENTICATION)
                .build();
    }
    private AuthenticationWebFilter bearerAuthenticationFilter(AuthenticationManager authenticationManager) {
        AuthenticationWebFilter bearerAuthenticationFilter = new AuthenticationWebFilter(authenticationManager);
        bearerAuthenticationFilter.setServerAuthenticationConverter(new BearerTokenServerAuthenticationConverter(new JWTHandler(secret)));

        return bearerAuthenticationFilter;
    }


}