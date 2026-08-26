package com.example.Driver_Service.security;

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

    private final String secret;

    public  JwtService(@Value("${token.secret.key}") String secret) {
        this.secret = secret;
    }

    public String extractUsername(String token){
        return extractClaimByToken(token, Claims::getSubject);
    }

    public String role(String token){
        return extractClaimsJwt(token).get("role", String.class);
    }

    public String getType(String token){
        return extractClaimsJwt(token).get("type", String.class);
    }

    public List<String> getAuthorities(String token){
        return  extractClaimsJwt(token).get("authorities", List.class);
    }

    public Date extractExpiration(String token){
        return extractClaimByToken(token,Claims::getExpiration);
    }

    public boolean isTokenExpired(String token){
        return !extractExpiration(token).before(new Date());
    }

    public boolean validateToken(String token , String username){
        return isTokenExpired(token) && extractUsername(token).equals(username);
    }

    public <T> T extractClaimByToken(String token, Function<Claims,T> claimsTFunction){
        Claims claims = extractClaimsJwt(token);
        return claimsTFunction.apply(claims);
    }

    public Claims extractClaimsJwt(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
    }
}
