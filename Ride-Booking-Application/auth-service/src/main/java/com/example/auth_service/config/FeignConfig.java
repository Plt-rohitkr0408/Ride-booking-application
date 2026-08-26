package com.example.auth_service.config;

import com.example.auth_service.security.JwtService;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.stereotype.Component;

@Component
class FeignConfig implements RequestInterceptor {
    private   final JwtService jwtService;
    public FeignConfig(JwtService jwtService) {
        this.jwtService = jwtService;
    }
    @Override
    public void apply(RequestTemplate requestTemplate) {
        String token =  jwtService.servicetoken();

        requestTemplate.header("Authorization","Bearer "+token);
    }
}