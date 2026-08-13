package com.example.auth_service.repository;

import com.example.auth_service.entity.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    RefreshToken findByToken(String token);
    RefreshToken findByUserId(Long userId);
}
