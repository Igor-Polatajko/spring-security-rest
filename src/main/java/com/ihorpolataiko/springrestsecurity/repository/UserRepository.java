package com.ihorpolataiko.springrestsecurity.repository;

import com.ihorpolataiko.springrestsecurity.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, String> {
}
