package com.ihorpolataiko.springrestsecurity.controller;

import com.ihorpolataiko.springrestsecurity.service.UserService;
import com.ihorpolataiko.springrestsecurity.transfer.UserDto;
import com.ihorpolataiko.springrestsecurity.transfer.validation.ExistingRecord;
import com.ihorpolataiko.springrestsecurity.transfer.validation.NewRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public List<UserDto> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto findById(@PathVariable("id") String id) {
        return userService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserDto onboard(@Validated(NewRecord.class) @RequestBody UserDto userDto) {
        return userService.save(userDto);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public UserDto update(@Validated(ExistingRecord.class) @PathVariable("id") String id, @RequestBody UserDto userDto) {
        userDto.setId(id);
        return userService.save(userDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") String id) {
        userService.deleteById(id);
    }
}
