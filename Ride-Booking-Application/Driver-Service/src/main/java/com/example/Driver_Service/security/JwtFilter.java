package com.example.Driver_Service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private final JwtService jwtService;

    public JwtFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        System.out.println("header: "+ header);
        if(header == null || !header.startsWith("Bearer ")){
            filterChain.doFilter(request,response);
            return;
        }
        String token = header.substring(7);
        System.out.println("token: "+ token);
        String username = jwtService.extractUsername(token);
        System.out.println("username: "+ username);
        if( username != null && SecurityContextHolder.getContext().getAuthentication() == null){
            if(jwtService.validateToken(token, username)){
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(username, null, List.of());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((request)));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request,response);

    }
}
