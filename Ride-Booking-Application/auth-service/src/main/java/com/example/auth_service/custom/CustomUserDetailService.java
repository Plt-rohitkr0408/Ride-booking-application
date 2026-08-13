package com.example.auth_service.custom;

import com.example.auth_service.entity.AuthUser;
import com.example.auth_service.repository.AuthUserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    private final AuthUserRepository userRepository;

    public CustomUserDetailService(AuthUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser user = userRepository.findByEmail(username);
        return new CustomUser(user);
    }
}
