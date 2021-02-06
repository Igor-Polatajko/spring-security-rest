package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.domain.Role;
import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.repository.UserRepository;
import com.ihorpolataiko.springrestsecurity.transfer.ResetPasswordDto;
import com.ihorpolataiko.springrestsecurity.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public List<UserDto> findAll() {

        return userRepository.findAll().stream()
                .map(User::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public UserDto findById(String id) {

        return findByIdOrThrowException(id).toDto();
    }

    @Override
    public UserDto create(UserDto userDto) {

        userRepository.findByUsername(userDto.getUsername())
                .ifPresent(userWithUsername -> {
                    throw new IllegalArgumentException("Username already in use");
                });

        User user = User.builder()
                .id(UUID.randomUUID().toString())
                .username(userDto.getUsername())
                .passwordHash(bCryptPasswordEncoder.encode(userDto.getPassword()))
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .roles(Collections.singletonList(Role.ROLE_USER))
                .active(true)
                .build();

        return userRepository.save(user).toDto();
    }

    @Override
    public UserDto update(User loggedInUser, UserDto userDto) {

        User updatedUser = loggedInUser.toBuilder()
                .username(userDto.getUsername())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();

        return userRepository.save(updatedUser).toDto();
    }

    @Override
    public void setRoles(String userId, List<Role> roles) {

        User user = findByIdOrThrowException(userId);

        User updatedUser = user.toBuilder()
                .roles(roles)
                .build();

        userRepository.save(updatedUser);
    }

    @Override
    public void deleteById(String id) {

        findByIdOrThrowException(id);
        userRepository.deleteById(id);
    }

    @Override
    public void resetPassword(User user, ResetPasswordDto resetPasswordDto) {

        if (!bCryptPasswordEncoder.matches(resetPasswordDto.getOldPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Incorrect old password provided");
        }

        User updatedUser = user.toBuilder()
                .passwordHash(bCryptPasswordEncoder.encode(resetPasswordDto.getNewPassword()))
                .build();

        userRepository.save(updatedUser);
    }

    private User findByIdOrThrowException(String id) {

        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }
}
