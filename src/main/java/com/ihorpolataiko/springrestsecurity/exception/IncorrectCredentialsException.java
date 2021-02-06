package com.ihorpolataiko.springrestsecurity.exception;

import org.springframework.security.authentication.BadCredentialsException;

public class IncorrectCredentialsException extends BadCredentialsException {

    public IncorrectCredentialsException() {
        super("Provided credentials are incorrect");
    }

}
