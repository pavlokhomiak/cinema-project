package com.dev.cinema.service;

import com.dev.cinema.model.User;
import java.util.Optional;

public interface UserService {
    User add(User user);

    Optional<User> findByEmail(String email);
}
