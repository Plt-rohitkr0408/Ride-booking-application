package com.example.User_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.List;
import java.util.function.Function;

@Service
public class JwtService {
    private final String secretKey;
    public JwtService(@Value("${token.secret.key}") String secretKey){
        this.secretKey = secretKey;
    }

    public String extractUsername(String token){
        return extractClaimByToken(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractClaimByToken(token, Claims::getExpiration);
    }

    public String role(String token){
        return extractClaimsJws(token).get("role",String.class);
    }

    public List<String> authorities(String token){
       return extractClaimsJws(token).get("authorities",List.class);
    }

    public String type(String token){
        return extractClaimsJws(token).get("type",String.class);
    }

    public boolean isTokenValid(String token){
        return !extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token, String username){
        return isTokenValid(token) && extractUsername(token).equals(username);
    }

    public <T> T extractClaimByToken(String token, Function<Claims, T> function){
        Claims claims= extractClaimsJws(token);
        return function.apply(claims);
    }

    public Claims extractClaimsJws(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretKey.getBytes(StandardCharsets.UTF_8));
    }
}
