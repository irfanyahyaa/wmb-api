package com.enigma.wmb_api.security;

import jakarta.servlet.DispatcherType;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfiguration {
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
        /*return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(config -> {
                    config.accessDeniedHandler(accessDeniedHandler);
                    config.authenticationEntryPoint(authenticationEntryPoint);
                })
                .sessionManagement(cfg -> cfg.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(req ->
                        req.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                                .requestMatchers("/api/auth/**").permitAll()
                                .requestMatchers("/api/images/**").permitAll()
                                .requestMatchers(HttpMethod.POST, "/api/bills/status").permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .build();*/

        return httpSecurity
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable) // pembatasan pengiriman http request
                .authorizeHttpRequests(req -> {
                    req.dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
                            .requestMatchers("/api/auth/**").permitAll()
                            .anyRequest().authenticated();
                })
                .build();
    }
}
