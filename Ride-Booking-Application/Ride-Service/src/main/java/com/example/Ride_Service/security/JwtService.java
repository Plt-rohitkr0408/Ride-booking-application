package com.example.Ride_Service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {
    private final String secretKey;
    public JwtService(@Value("${token.secret.key}") String secretKey) {
        this.secretKey = secretKey;
    }

    public String extractUsername(String token) {
        return extractClaimByToken(token, Claims::getSubject);
    }
    public Date extractExpiration(String token) {
        return extractClaimByToken(token, Claims::getExpiration);
    }

    public String getRole(String token) {
        return extractClaims(token).get("role", String.class);
    }

    public List<String> getAuthorities(String token) {
        return extractClaims(token).get("authorities", List.class);
    }
    public boolean isTokenExpired(String token) {
        return !extractExpiration(token).before(new Date());
    }
    public boolean validateToken(String token , String username) {
        return isTokenExpired(token) && extractUsername(token).equals(username);
    }

    private <T> T extractClaimByToken(java.lang.String token, Function<Claims, T> claimsTFunction) {
        Claims claims = extractClaims(token);
        return claimsTFunction.apply(claims);
    }

    private Claims extractClaims(java.lang.String token) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8)))
                .build()
                .parseSignedClaims(token).getPayload();
    }
}
