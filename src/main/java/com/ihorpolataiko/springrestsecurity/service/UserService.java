package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.transfer.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> findAll();

    UserDto findById(String id);

    UserDto save(UserDto userDto);

    void deleteById(String id);
}
