package com.ihorpolataiko.springrestsecurity.security;

import com.ihorpolataiko.springrestsecurity.domain.security.Token;
import com.ihorpolataiko.springrestsecurity.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider implements AuthenticationProvider {

    private TokenRepository tokenRepository;

    @Autowired
    public TokenProvider(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;

        Token token = tokenRepository.findByValue((String) tokenAuthentication.getCredentials())
                .orElseThrow(() -> new IllegalArgumentException("Incorrect auth token provided"));

        tokenAuthentication.setAuthenticated(true);
        tokenAuthentication.setUserDetails(new UserDetailsImpl(token.getUser()));

        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return TokenAuthentication.class.equals(authenticationClass);
    }

}
