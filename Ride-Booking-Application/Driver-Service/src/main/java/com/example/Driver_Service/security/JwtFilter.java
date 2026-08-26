package com.example.Driver_Service.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
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
        try{
            String type = jwtService.getType(token);
            String username = jwtService.extractUsername(token);
            if(type != null && username!=null ){
                if("SERVICE".equalsIgnoreCase(type) && "AUTH-SERVICE".equalsIgnoreCase(username) && SecurityContextHolder.getContext().getAuthentication()==null){
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    List<String> upcomingAuthorities = jwtService.getAuthorities(token);
                    upcomingAuthorities.forEach(authority-> authorities.add(new SimpleGrantedAuthority(authority)));
                    System.out.println(authorities);

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            authorities
                    );

                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
            else if( username != null && SecurityContextHolder.getContext().getAuthentication() == null){
                if(jwtService.validateToken(token, username)){
                    List<GrantedAuthority> authorities = new ArrayList<>();
                    String role = jwtService.role(token);
                    authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
                    List<String> upcomingAuthorities = jwtService.getAuthorities(token);
                    upcomingAuthorities.forEach(authority-> authorities.add(new SimpleGrantedAuthority(authority)));
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            username, null, authorities);
                    authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails((request)));
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }catch(Exception ex){
            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request,response);

    }
}
