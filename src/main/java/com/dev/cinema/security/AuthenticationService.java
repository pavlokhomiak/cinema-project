package com.dev.cinema.security;

import com.dev.cinema.model.User;
import javax.naming.AuthenticationException;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;

    User register(String email, String password);
}
