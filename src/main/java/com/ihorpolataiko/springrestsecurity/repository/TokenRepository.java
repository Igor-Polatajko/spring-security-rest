package com.ihorpolataiko.springrestsecurity.repository;

import com.ihorpolataiko.springrestsecurity.domain.security.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, String> {

}
