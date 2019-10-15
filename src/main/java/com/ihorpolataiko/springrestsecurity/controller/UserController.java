package com.ihorpolataiko.springrestsecurity.controller;

import com.ihorpolataiko.springrestsecurity.domain.Role;
import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.service.LoginService;
import com.ihorpolataiko.springrestsecurity.service.UserService;
import com.ihorpolataiko.springrestsecurity.transfer.ResetPasswordDto;
import com.ihorpolataiko.springrestsecurity.transfer.UserDto;
import com.ihorpolataiko.springrestsecurity.transfer.validation.ExistingRecord;
import com.ihorpolataiko.springrestsecurity.transfer.validation.NewRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;
    private LoginService loginService;

    @Autowired
    public UserController(UserService userService, LoginService loginService) {
        this.userService = userService;
        this.loginService = loginService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto onboard(@Validated(NewRecord.class) @RequestBody UserDto userDto) {
        return userService.onboard(userDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@AuthenticationPrincipal User user,
                          @Validated(ExistingRecord.class) @RequestBody UserDto userDto) {
        userDto.setId(user.getId());
        userDto.setPassword(user.getPasswordHash());
        userDto.setRoles(user.getRoles());
        return userService.update(userDto);
    }

    @PostMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void setRoles(@PathVariable("id") String id, @RequestBody List<Role> roles) {
        userService.setRoles(id, roles);
    }

    @GetMapping("/current")
    public UserDto getCurrent(@AuthenticationPrincipal User user) {
        return UserDto.from(user);
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@AuthenticationPrincipal User user,
                              @Validated @RequestBody @NotNull ResetPasswordDto resetPasswordDto) {
        loginService.resetPassword(user, resetPasswordDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable("id") String id) {
        userService.deleteById(id);
    }
}
