package com.example.User_service.security;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtFilter jwtFilter;
    public SecurityConfig(JwtFilter jwtFilter) {
        this.jwtFilter = jwtFilter;
    }

    @Bean
    SecurityFilterChain configure(HttpSecurity http) throws Exception {
        http
                .csrf(c-> c.disable())
                .authorizeHttpRequests(auth->
                        auth.requestMatchers(HttpMethod.POST,"/users/create").hasAuthority("USER_CREATE")
                                .requestMatchers(HttpMethod.GET, "/users/email" , "/users/{userId}").hasAuthority("RIDE_CREATE")

                                .anyRequest().authenticated()
                ).addFilterBefore(jwtFilter,  UsernamePasswordAuthenticationFilter.class)
                .sessionManagement(session->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .httpBasic(h-> {});

      return  http.build();
    }
}
