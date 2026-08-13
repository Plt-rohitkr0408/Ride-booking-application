package com.example.User_service.repository;

import com.example.User_service.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    User findByAuthId(Long authId);
    User findByEmail(String email);
    boolean existsByAuthId(Long authId);

}
