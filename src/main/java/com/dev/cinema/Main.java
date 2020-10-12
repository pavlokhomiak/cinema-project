package com.dev.cinema;

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

public class Main {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

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
        final ShoppingCartService shoppingCartService = (ShoppingCartService) injector
                .getInstance(ShoppingCartService.class);
        final OrderService orderService = (OrderService) injector.getInstance(OrderService.class);

        movieService.getAll().forEach(System.out::println);
        cinemaHallService.getAll().forEach(System.out::println);

        Movie movie = new Movie();
        movie.setTitle("Fast and Furious");
        movieService.add(movie);

        Movie movie1 = new Movie();
        movie1.setTitle("Avengers");
        movieService.add(movie1);
        movieService.getAll().forEach(System.out::println);

        CinemaHall cinemaHall = new CinemaHall();
        cinemaHall.setCapacity(50);
        cinemaHall.setDescription("Best cinemaHall");
        cinemaHallService.add(cinemaHall);
        cinemaHallService.getAll().forEach(System.out::println);

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
        movieSessionService.findAvailableSessions(movieId, localDate).forEach(System.out::println);

        String userOneEmail = "pawa@gmail.com";
        String userOnePassword = "1234";
        authenticationService.register(userOneEmail, userOnePassword);

        String userTwoEmail = "pawawa@gmail.com";
        String userTwoPassword = "1234";
        authenticationService.register(userTwoEmail, userTwoPassword);

        User user = userService.findByEmail("pawa@gmail.com").get();
        System.out.println(user);

        ShoppingCart shoppingCart = shoppingCartService.getByUser(user);
        System.out.println("FIRST " + shoppingCart);
        shoppingCartService.addSession(movieSession, user);

        shoppingCart = shoppingCartService.getByUser(user);
        System.out.println("SECOND " + shoppingCart);

//        shoppingCartService.clear(shoppingCart);
//        System.out.println(shoppingCart);

        Order order = orderService.completeOrder(shoppingCart.getTickets(), user);
        System.out.println(order);
        shoppingCart = shoppingCartService.getByUser(user);
        System.out.println("AFTER COMPLETE ORDER " + shoppingCart);

        shoppingCartService.addSession(movieSession1, user);
        shoppingCartService.addSession(movieSession2, user);
        shoppingCart = shoppingCartService.getByUser(user);
        System.out.println(shoppingCart);

        order = orderService.completeOrder(shoppingCart.getTickets(), user);
        System.out.println("" + order);

        List<Order> orderList = orderService.getOrderHistory(user);
        System.out.println("ORDER HISTORY" + orderList);
    }
}
