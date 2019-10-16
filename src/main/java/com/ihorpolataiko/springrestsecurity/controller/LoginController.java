package com.ihorpolataiko.springrestsecurity.controller;

import com.ihorpolataiko.springrestsecurity.service.LoginService;
import com.ihorpolataiko.springrestsecurity.transfer.LoginDto;
import com.ihorpolataiko.springrestsecurity.transfer.TokenDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
public class LoginController {

    private LoginService loginService;

    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    public TokenDto login(@Validated @RequestBody LoginDto loginDto) {
        return loginService.login(loginDto);
    }

    @PostMapping("/logout")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void logout() {
        loginService.logout();
    }
}
