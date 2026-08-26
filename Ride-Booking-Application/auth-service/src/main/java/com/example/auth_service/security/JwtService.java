package com.example.auth_service.security;

import com.example.auth_service.entity.AuthUser;
import com.example.auth_service.entity.RoleAuthority;
import com.example.auth_service.repository.AuthUserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.function.Function;
import java.util.*;


@Service
public class JwtService {
    private final AuthUserRepository authUserRepository;
    public JwtService(AuthUserRepository authUserRepository) {
        this.authUserRepository = authUserRepository;
    }

    @Value("${token.secret.key}")
    private String secretkey;


    public String servicetoken(){
        Date now = new Date();
        Date exp = new Date(now.getTime() + 3600000);
        String type = "SERVICE";
        List<String> authority = List.of("USER_CREATE","DRIVER_CREATE");
        Map<String,Object> claims = new HashMap<>();
        claims.put("type",type);
        claims.put("authorities",authority);

        return Jwts.builder()
                .signWith(generateKey())
                .subject("auth-service")
                .issuedAt(now)
                .expiration(exp)
                .claims(claims)
                .compact();
    }

    public String accessToken(String email){

        AuthUser authUser = authUserRepository.findByEmail(email);
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600 * 1000);
        List<String> authorities = RoleAuthority.getRoleAuthorities(authUser.getRole()).stream().map(
                Enum::toString
        ).toList();

        System.out.println(authorities);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",authUser.getRole().name());
        claims.put("authorities", authorities );
        return Jwts.builder()
                .signWith(generateKey())
                .issuedAt(now)
                .claims(claims)
                .expiration(expiryDate)
                .subject(email)
                .compact();
    }
    public String refreshToken(String email){
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + 3600 * 1000 * 24);
        AuthUser authUser = authUserRepository.findByEmail(email);
        Map<String, Object> claims = new HashMap<>();
        claims.put("role",authUser.getRole().toString());
        List<String> authorities = RoleAuthority.getRoleAuthorities(authUser.getRole())
                .stream()
                .map(Enum::toString)
                .toList();
        claims.put("authorities", authorities);
        return Jwts.builder()
                .signWith(generateKey())
                .claims(claims)
                .subject(email)
                .issuedAt(now)
                .expiration(expiryDate)
                .compact();
    }

    public String extractUsername(String token){
        return getClaimsByToken(token, Claims::getSubject);
    }

    public Date getExpiration(String token){
        return getClaimsByToken(token, Claims::getExpiration);
    }

    public String getRole(String token){
        String role = extractClaimsToken(token).get("role",String.class);
        return role;
    }

    public Set getAuthorities(String token){
        return extractClaimsToken(token).get("authority",Set.class);
    }

    public boolean isTokenExpired(String token){
        return getExpiration(token).before(new Date());
    }

    public boolean validateToken(String token , UserDetails userDetails){
        return !isTokenExpired(token) && extractUsername(token).equals(userDetails.getUsername());
    }

    public <T> T getClaimsByToken(String token, Function<Claims,T> claimsResolver){
        Claims claims= extractClaimsToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractClaimsToken(String token){
        return Jwts.parser()
                .verifyWith(generateKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


    private SecretKey generateKey(){
        return Keys.hmacShaKeyFor(secretkey.getBytes(StandardCharsets.UTF_8));
    }

}
