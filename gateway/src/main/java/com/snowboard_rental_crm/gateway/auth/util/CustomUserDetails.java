package com.snowboard_rental_crm.gateway.auth.util;

import com.snowboard_rental_crm.shared_data.model.UserDataResponseModel;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class CustomUserDetails implements UserDetails {

    private final Long userId;
    private final String login;
    private String password;
    private List<GrantedAuthority> authorities;

    public CustomUserDetails(Long userId, String login) {
        this.userId = userId;
        this.login = login;
    }

    public CustomUserDetails(UserDataResponseModel user) {
        this.password = user.getPassword();
        this.userId = user.getId();
        this.login = user.getLogin();
        this.authorities = Arrays.stream(user.getRole().split(","))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
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

    public Long getUserId() {
        return userId;
    }
}
