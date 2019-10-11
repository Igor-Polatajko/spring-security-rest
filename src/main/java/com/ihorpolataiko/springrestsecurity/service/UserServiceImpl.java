package com.ihorpolataiko.springrestsecurity.service;

import com.ihorpolataiko.springrestsecurity.domain.User;
import com.ihorpolataiko.springrestsecurity.repository.UserRepository;
import com.ihorpolataiko.springrestsecurity.transfer.UserDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.ihorpolataiko.springrestsecurity.transfer.UserDto.from;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
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
    public UserDto save(User user) {
        if (user.getId() == null) {
            user.setActive(true);
        }
        else {
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
