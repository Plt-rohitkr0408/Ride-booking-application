package com.example.oder_service.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    private final String secretkey;

    public JwtService(@Value("${token.secret.key}") String secretkey) {
        this.secretkey = secretkey;
    }

    public String extractUsername(String token){
        return extractInfo(token, Claims::getSubject);
    }

    public Date extractExpiration(String token){
        return extractInfo(token, Claims::getExpiration);
    }

    public boolean isExpired(String authToken){
        return !extractExpiration(authToken).before(new Date());
    }

    public boolean validateToken(String authToken, String username){
        return isExpired(authToken) && username.equals(extractUsername(authToken));
    }

   public <T> T extractInfo(String token, Function<Claims, T> claimsTFunction){
        Claims claims = extractClaimsJws(token);
        return claimsTFunction.apply(claims);
   }

    public Claims extractClaimsJws(String token){
        return Jwts.parser()
                .verifyWith(getSecretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private SecretKey getSecretKey(){
        return Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
    }


}
