package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.domain.security.Token;
import com.ihorpolataiko.springrestsecurity.repository.TokenRepository;
import com.ihorpolataiko.springrestsecurity.repository.UserRepository;
import com.ihorpolataiko.springrestsecurity.transfer.LoginDto;
import com.ihorpolataiko.springrestsecurity.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static com.ihorpolataiko.springrestsecurity.transfer.TokenDto.from;

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
        if (loginDto != null) {
            User user = userRepository.findByUsername(loginDto.getUsername())
                    .orElseThrow(() -> new IllegalArgumentException("Invalid login"));

            if (bCryptPasswordEncoder.matches(loginDto.getPassword(), user.getPasswordHash())) {
                Token token = Token.builder()
                        .id(UUID.randomUUID().toString())
                        .value(UUID.randomUUID().toString())
                        .user(user)
                        .build();
                return from((tokenRepository.save(token)));
            }
            throw new IllegalArgumentException("Password is not correct");
        }
        throw new IllegalArgumentException("Credentials are not provided");
    }
}