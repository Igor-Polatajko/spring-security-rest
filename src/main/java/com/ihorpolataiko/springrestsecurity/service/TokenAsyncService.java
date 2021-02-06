package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.domain.security.Token;

public interface TokenAsyncService {

    void updateLastActivityTime(Token token);

}
