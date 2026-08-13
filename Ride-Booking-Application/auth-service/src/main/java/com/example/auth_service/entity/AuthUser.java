package com.example.auth_service.entity;

import com.example.auth_service.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "auth_users")
@Builder
@Data @AllArgsConstructor @NoArgsConstructor
public class AuthUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder.Default
    private boolean enable = true;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    @PrePersist
    public void createAt(){
        createdDate = LocalDateTime.now();
        updatedDate = LocalDateTime.now();
        enable = true;
    }

    @PreUpdate
    public void updateAt(){
        updatedDate = LocalDateTime.now();
    }

}
