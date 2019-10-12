package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.repository.UserRepository;
import com.ihorpolataiko.springrestsecurity.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.ihorpolataiko.springrestsecurity.transfer.UserDto.from;
import static com.ihorpolataiko.springrestsecurity.transfer.UserDto.toUser;

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
        List<User> users = userRepository.findAll();
        return users.stream().map(UserDto::from).collect(Collectors.toList());
    }

    @Override
    public UserDto findById(String id) {
        return from(findByIdOrThrowException(id));
    }

    @Override
    public UserDto save(UserDto userDto) {
        User user = toUser(userDto);
        if (user.getId() == null) {
            userRepository.findByUsername(user.getUsername())
                    .ifPresent(userWithUsername -> {
                        throw new IllegalArgumentException("Username already in use");
                    });

            user.setId(UUID.randomUUID().toString());
            user.setActive(true);
            user.setPasswordHash(bCryptPasswordEncoder.encode(user.getPasswordHash()));
        } else {
            findByIdOrThrowException(user.getId());
        }

        return from(userRepository.save(user));
    }

    @Override
    public void deleteById(String id) {
        findByIdOrThrowException(id);
        userRepository.deleteById(id);
    }

    private User findByIdOrThrowException(String id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found!"));
    }
}
