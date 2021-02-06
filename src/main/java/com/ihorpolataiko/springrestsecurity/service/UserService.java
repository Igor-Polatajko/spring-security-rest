package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.domain.Role;
import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.transfer.ResetPasswordDto;
import com.ihorpolataiko.springrestsecurity.transfer.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(String id);

    UserDto create(UserDto userDto);

    UserDto update(User loggedInUser, UserDto userDto);

    void setRoles(String userId, List<Role> roles);

    void deleteById(String id);

    void resetPassword(User user, ResetPasswordDto resetPasswordDto);

}
