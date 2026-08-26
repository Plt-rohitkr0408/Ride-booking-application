package com.example.auth_service.custom;

import com.example.auth_service.entity.AuthUser;
import com.example.auth_service.entity.RoleAuthority;
import com.example.auth_service.enums.Authority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class CustomUser implements UserDetails {

    private final AuthUser user;

    public CustomUser(AuthUser user) {
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_"+user.getRole().toString()));

        Set<Authority> roleAuth = RoleAuthority.getRoleAuthorities(user.getRole());

        roleAuth.forEach(authority -> authorities.add(new SimpleGrantedAuthority(authority.toString())));

        return authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }


    @Override
    public String getUsername() {
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
