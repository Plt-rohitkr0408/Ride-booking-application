package com.example.Ride_Service.config;

import feign.RequestTemplate;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
public class RequestInterceptor implements feign.RequestInterceptor {
    @Override
    public void apply(RequestTemplate requestTemplate) {
       ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if(servletRequestAttributes != null) {
            HttpServletRequest request = servletRequestAttributes.getRequest();


            String token = request.getHeader("Authorization");

            if(token != null) {
                requestTemplate.header("Authorization", token);
            }
        }
    }
}
