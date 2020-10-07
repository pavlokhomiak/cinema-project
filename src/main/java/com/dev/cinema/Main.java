package com.dev.cinema;

import com.dev.cinema.lib.Injector;
import com.dev.cinema.model.CinemaHall;
import com.dev.cinema.model.Movie;
import com.dev.cinema.model.MovieSession;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.service.MovieService;
import com.dev.cinema.service.MovieSessionService;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class Main {
    private static Injector injector = Injector.getInstance("com.dev.cinema");

    public static void main(String[] args) {
        MovieService movieService = (MovieService) injector.getInstance(MovieService.class);
        CinemaHallService cinemaHallService = (CinemaHallService) injector
                .getInstance(CinemaHallService.class);
        final MovieSessionService movieSessionService = (MovieSessionService) injector
                .getInstance(MovieSessionService.class);
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
    }
}
