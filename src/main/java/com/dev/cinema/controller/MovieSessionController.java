package com.dev.cinema.controller;

import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.util.mapper.MovieSessionMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/movie-session")
public class MovieSessionController {
    private MovieSessionService movieSessionService;
    private MovieSessionMapper movieSessionMapper;

    public MovieSessionController(MovieSessionService movieSessionService, MovieSessionMapper movieSessionMapper) {
        this.movieSessionService = movieSessionService;
        this.movieSessionMapper = movieSessionMapper;
    }

    @PostMapping("/add")
    public void addMovieSession(@RequestBody MovieSessionRequestDto dto) {
        movieSessionService.add(movieSessionMapper.toMovieSession(dto));
    }

    @GetMapping("/available")
    public List<MovieSessionResponseDto> getMovieSessionDto(
            @RequestParam(name = "movieId") Long id,
            @RequestParam(name = "date") String date) {
        LocalDate localDate = LocalDate.parse(date,
                DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        return movieSessionService.findAvailableSessions(id, localDate).stream()
                .map(movieSessionMapper::toMovieSessionResponseDto)
                .collect(Collectors.toList());
    }
}
