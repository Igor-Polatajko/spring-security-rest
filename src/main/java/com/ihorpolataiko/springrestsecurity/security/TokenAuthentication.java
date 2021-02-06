package com.ihorpolataiko.springrestsecurity.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.Objects.isNull;

public class TokenAuthentication implements Authentication {

    private String tokenValue;

    private UserDetails userDetails;

    private boolean isAuthenticated;

    public TokenAuthentication(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public void setUserDetails(UserDetails userDetails) {
        this.userDetails = userDetails;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return userDetails.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return tokenValue;
    }

    @Override
    public Object getDetails() {
        return userDetails;
    }

    @Override
    public Object getPrincipal() {
        if (isNull(userDetails)) {
            return null;
        }
        return ((UserDetailsImpl) userDetails).getUser();
    }

    @Override
    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) {
        this.isAuthenticated = isAuthenticated;
    }

    @Override
    public String getName() {
        if (isNull(userDetails)) {
            return null;
        }
        return userDetails.getUsername();
    }
}
