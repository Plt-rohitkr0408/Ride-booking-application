package com.example.auth_service.repository;

import com.example.auth_service.entity.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthUserRepository extends JpaRepository<AuthUser,Long> {
    AuthUser findByEmail(String email);
}
