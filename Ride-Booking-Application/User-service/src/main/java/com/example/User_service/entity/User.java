package com.example.User_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    private Long authId;
    private String name;
    private String email;
    private String phone;
    private String password;
    private String homeAddress;
    private String profileImage;
    private String officeAddress;
    private LocalDateTime createAt;
    private LocalDateTime updateAt;

    @PrePersist
    public void create(){
        this.createAt = LocalDateTime.now();
        this.updateAt = LocalDateTime.now();
    }

    @PostPersist
    public void update(){
        this.updateAt = LocalDateTime.now();
    }
}
