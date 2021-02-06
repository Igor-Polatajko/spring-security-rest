package com.ihorpolataiko.springrestsecurity.controller;

import com.ihorpolataiko.springrestsecurity.domain.Role;
import com.ihorpolataiko.springrestsecurity.domain.User;
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
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
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
    public UserDto create(@Validated(NewRecord.class) @RequestBody UserDto userDto) {
        return userService.create(userDto);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@AuthenticationPrincipal User loggedInUser,
                          @Validated(ExistingRecord.class) @RequestBody UserDto userDto) {

        return userService.update(loggedInUser, userDto);
    }

    @PostMapping("/{id}/roles")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto setRoles(@PathVariable("id") String id, @RequestBody List<Role> roles) {
        return userService.setRoles(id, roles);
    }

    @PostMapping("/{id}/activate")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto activate(@PathVariable("id") String userId) {
        return userService.activate(userId);
    }

    @PostMapping("/{id}/deactivate")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public UserDto deactivate(@PathVariable("id") String userId) {
        return userService.deactivate(userId);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public void delete(@PathVariable("id") String id) {
        userService.deleteById(id);
    }

    @GetMapping("/self")
    public UserDto getSelf(@AuthenticationPrincipal User user) {
        return user.toDto();
    }

    @PostMapping("/reset-password")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void resetPassword(@AuthenticationPrincipal User user,
                              @Validated @RequestBody @NotNull ResetPasswordDto resetPasswordDto) {
        userService.resetPassword(user, resetPasswordDto);
    }

}
