package com.ihorpolataiko.springrestsecurity.security;

import com.ihorpolataiko.springrestsecurity.domain.security.Token;
import com.ihorpolataiko.springrestsecurity.exception.IncorrectCredentialsException;
import com.ihorpolataiko.springrestsecurity.repository.TokenRepository;
import com.ihorpolataiko.springrestsecurity.service.TokenAsyncService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

@Component
public class TokenProvider implements AuthenticationProvider {

    private TokenRepository tokenRepository;

    private TokenAsyncService tokenAsyncService;

    @Autowired
    public TokenProvider(TokenRepository tokenRepository, TokenAsyncService tokenAsyncService) {
        this.tokenRepository = tokenRepository;
        this.tokenAsyncService = tokenAsyncService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        TokenAuthentication tokenAuthentication = (TokenAuthentication) authentication;

        Token token = tokenRepository.findByValue((String) tokenAuthentication.getCredentials())
                .orElseThrow(IncorrectCredentialsException::new);

        tokenAsyncService.updateLastActivityTime(token);

        tokenAuthentication.setAuthenticated(true);
        tokenAuthentication.setUserDetails(new UserDetailsImpl(token.getUser()));

        return tokenAuthentication;
    }

    @Override
    public boolean supports(Class<?> authenticationClass) {
        return TokenAuthentication.class.equals(authenticationClass);
    }

}
