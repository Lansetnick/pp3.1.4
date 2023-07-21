package ru.kata.spring.boot_security.demo.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService extends UserDetailsService {
    User saveUser(User user);

    void deleteUserById(Long userId);

    User changeUser(User user);

    List<User> findAll();

    User findByUsername(String username);

    User findByEmail(String email);

    User findUserById(Long id);
}
