package com.dev.cinema.security;

import com.dev.cinema.model.User;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final Logger logger =
            Logger.getLogger(AuthenticationServiceImpl.class.getName());

    private UserService userService;
    private ShoppingCartService shoppingCartService;

    public AuthenticationServiceImpl(UserService userService,
                                     ShoppingCartService shoppingCartService) {
        this.userService = userService;
        this.shoppingCartService = shoppingCartService;
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
}
