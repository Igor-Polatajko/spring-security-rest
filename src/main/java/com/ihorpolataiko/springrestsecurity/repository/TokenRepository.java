package com.ihorpolataiko.springrestsecurity.repository;

import com.ihorpolataiko.springrestsecurity.domain.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.Optional;

public interface TokenRepository extends JpaRepository<Token, String> {

    Optional<Token> findByValue(String value);

    void deleteByValue(String value);

    void deleteByLastActivityTimeIsBefore(LocalDateTime lastUpdatedTime);

    void deleteByUserId(String userId);

}
