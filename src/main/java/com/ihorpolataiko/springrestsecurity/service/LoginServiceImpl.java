package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.domain.security.Token;
import com.ihorpolataiko.springrestsecurity.exception.IncorrectCredentialsException;
import com.ihorpolataiko.springrestsecurity.repository.TokenRepository;
import com.ihorpolataiko.springrestsecurity.repository.UserRepository;
import com.ihorpolataiko.springrestsecurity.transfer.LoginDto;
import com.ihorpolataiko.springrestsecurity.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
public class LoginServiceImpl implements LoginService {

    private UserRepository userRepository;

    private TokenRepository tokenRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public LoginServiceImpl(UserRepository userRepository, TokenRepository tokenRepository,
                            BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.tokenRepository = tokenRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public TokenDto login(LoginDto loginDto) {

        if (isNull(loginDto)) {
            throw new IncorrectCredentialsException();
        }

        User user = userRepository.findByUsername(loginDto.getUsername())
                .orElseThrow(IncorrectCredentialsException::new);

        if (bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {

            LocalDateTime now = LocalDateTime.now();

            Token token = Token.builder()
                    .id(UUID.randomUUID().toString())
                    .value(UUID.randomUUID().toString())
                    .user(user)
                    .lastActivityTime(now)
                    .createdTime(now)
                    .build();

            return tokenRepository.save(token).toDto();
        }

        throw new IncorrectCredentialsException();
    }

    @Override
    @Transactional
    public void logout() {
        String tokenValue = (String) SecurityContextHolder.getContext().getAuthentication().getCredentials();
        SecurityContextHolder.clearContext();
        tokenRepository.deleteByValue(tokenValue);
    }

    @Override
    @Transactional
    public void logoutGlobal(User loggedInUser) {
        tokenRepository.deleteByUserId(loggedInUser.getId());
    }

}
