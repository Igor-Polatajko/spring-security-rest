package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.transfer.LoginDto;
import com.ihorpolataiko.springrestsecurity.transfer.ResetPasswordDto;
import com.ihorpolataiko.springrestsecurity.transfer.TokenDto;

public interface LoginService {
    TokenDto login(LoginDto loginDto);

    void logout();

    void resetPassword(User user, ResetPasswordDto resetPasswordDto);
}
