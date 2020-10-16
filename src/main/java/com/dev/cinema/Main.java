package com.dev.cinema;

import com.dev.cinema.exceptions.AuthenticationException;
import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.model.Order;
import com.dev.cinema.model.ShoppingCart;
import com.dev.cinema.model.User;
import com.dev.cinema.security.AuthenticationService;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.service.OrderService;
import com.dev.cinema.service.ShoppingCartService;
import com.dev.cinema.service.UserService;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import org.apache.log4j.Logger;

public class Main {
    private static Injector injector = Injector.getInstance("com.dev.cinema");
    private static final Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
        final UserService userService = (UserService) injector
                .getInstance(UserService.class);
        final AuthenticationService authenticationService = (AuthenticationService) injector
                .getInstance(AuthenticationService.class);
        final OrderService orderService = (OrderService) injector.getInstance(OrderService.class);
        final ShoppingCartService shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);

        movieService.getAll().forEach(logger::info);
        cinemaHallService.getAll().forEach(logger::info);

        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        movieService.add(movie);

        Movie movie1 = new Movie();
        movie1.setTitle("Avengers");
        movieService.add(movie1);
        movieService.getAll().forEach(logger::info);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("Best cinemaHall");
        cinemaHallService.add(cinemaHall);
        cinemaHallService.getAll().forEach(logger::info);

        MovieSession movieSession = new MovieSession();
        movieSession.setCinemaHall(cinemaHall);
        movieSession.setMovie(movie);
        movieSession.setShowTime(LocalDateTime.of(2020, 10, 6, 13, 0));
        movieSessionService.add(movieSession);

        MovieSession movieSession1 = new MovieSession();
        movieSession1.setCinemaHall(cinemaHall);
        movieSession1.setMovie(movie1);
        movieSession1.setShowTime(LocalDateTime.of(2020, 10, 6, 18, 0));
        movieSessionService.add(movieSession1);

        MovieSession movieSession2 = new MovieSession();
        movieSession2.setCinemaHall(cinemaHall);
        movieSession2.setMovie(movie);
        movieSession2.setShowTime(LocalDateTime.of(2020, 10, 6, 22, 0));
        movieSessionService.add(movieSession2);

        MovieSession movieSession3 = new MovieSession();
        movieSession3.setCinemaHall(cinemaHall);
        movieSession3.setMovie(movie);
        movieSession3.setShowTime(LocalDateTime.of(2020, 10, 7, 13, 0));
        movieSessionService.add(movieSession3);

        LocalDate localDate = LocalDate.of(2020, 10, 6);
        Long movieId = movie.getId();
        movieSessionService.findAvailableSessions(movieId, localDate).forEach(logger::info);

        String userOneEmail = "pawa@gmail.com";
        String userOnePassword = "1234";
        User userOne = authenticationService.register(userOneEmail, userOnePassword);

        String userTwoEmail = "pawawa@gmail.com";
        String userTwoPassword = "1234";
        User userTwo = authenticationService.register(userTwoEmail, userTwoPassword);

        try {
            authenticationService.login(userOneEmail, userOnePassword);
        } catch (AuthenticationException e) {
            logger.warn("User: " + userOne + " access denied ", e);
        }

        String userTwoIncorrectPassword = "4321";
        try {
            authenticationService.login(userTwoEmail, userTwoIncorrectPassword);
        } catch (AuthenticationException e) {
            logger.warn("User: " + userTwo + " access denied ", e);
        }

        User user = userService.findByEmail("pawa@gmail.com").get();
        logger.info(user);

        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        logger.info("FIRST\n" + shoppingCart);

        shoppingCartService.addSession(movieSession, user);
        shoppingCart = shoppingCartService.getByUser(user);
        logger.info("CART AFTER ADD SESSION\n" + shoppingCart);

        Order order = orderService.completeOrder(shoppingCart.getTickets(), user);
        logger.info("ORDER AFTER COMPLETE ORDER\n" + order);
        shoppingCart = shoppingCartService.getByUser(user);
        logger.info("CART AFTER COMPLETE ORDER\n" + shoppingCart);

        shoppingCartService.addSession(movieSession1, user);
        shoppingCartService.addSession(movieSession2, user);
        shoppingCart = shoppingCartService.getByUser(user);
        logger.info("CART AFTER ADD SESSION\n" + shoppingCart);

        order = orderService.completeOrder(shoppingCart.getTickets(), user);
        logger.info("ORDER AFTER COMPLETE ORDER\n" + order);

        List<Order> orderList = orderService.getOrderHistory(user);
        orderList.forEach(logger::info);
    }
}
