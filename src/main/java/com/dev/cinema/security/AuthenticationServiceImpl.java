package com.dev.cinema.security;

import com.dev.cinema.Main;
import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import com.dev.cinema.util.HashUtil;
import java.util.Optional;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger = Logger.getLogger(Main.class.getName());

    private UserService userService;

    private ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(UserService userService, ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
    }

    @Override
    public User login(String email, String password)
            throws AuthenticationException {
        Optional<User> userFromDB = userService.findByEmail(email);
        if (userFromDB.isPresent() && isPasswordValid(password, userFromDB.get())) {
            logger.info("User: " + userFromDB + " has logged in ");
            return userFromDB.get();
        }
        throw new AuthenticationException("Incorrect username or password");
    }

    @Override
    public User register(String email, String password) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        User detachedUser = userService.add(user);
        shoppingCartService.registerNewShoppingCart(detachedUser);
        logger.info("User: " + detachedUser + " has registered");
        return detachedUser;
    }

    private boolean isPasswordValid(String password, User user) {
        byte[] salt = user.getSalt();
        String hashedPassword = user.getPassword();
        String hashedPasswordNew = HashUtil.hashPassword(password, salt);
        return hashedPasswordNew.equals(hashedPassword);
    }
}
