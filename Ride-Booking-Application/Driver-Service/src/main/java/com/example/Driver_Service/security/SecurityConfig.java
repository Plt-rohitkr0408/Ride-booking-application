package com.example.Driver_Service.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {
    private final JwtFilter jwtFilter;
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }
    @Bean
    public SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth->
                        auth.requestMatchers("/api/v1/driver/internal").permitAll()
                                .requestMatchers("/api/v1/driver/update/status/*").permitAll()
                                .requestMatchers( "/api/v1/driver/status").permitAll()
                                .requestMatchers("/api/v1/driver/**").authenticated()
                                .anyRequest().authenticated()
                        )
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .httpBasic(h->{});
        return http.build();
    }
}
