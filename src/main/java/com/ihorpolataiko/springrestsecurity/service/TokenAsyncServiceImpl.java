package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.domain.security.Token;
import com.ihorpolataiko.springrestsecurity.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class TokenAsyncServiceImpl implements TokenAsyncService {

    private TokenRepository tokenRepository;

    @Autowired
    public TokenAsyncServiceImpl(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    @Override
    @Async
    public void updateLastActivityTime(Token token) {

        Token updatedToken = token.toBuilder()
                .lastActivityTime(LocalDateTime.now())
                .build();

        tokenRepository.save(updatedToken);
    }

}
