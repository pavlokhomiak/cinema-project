package com.dev.cinema.controller;

import com.dev.cinema.model.dto.MovieSessionRequestDto;
import com.dev.cinema.model.dto.MovieSessionResponseDto;
import com.dev.cinema.service.MovieSessionService;
import com.dev.cinema.util.mapper.MovieSessionMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.time.LocalDate;
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

    @PostMapping
    public void addMovieSession(@RequestBody MovieSessionRequestDto dto) {
        movieSessionService.add(movieSessionMapper.toMovieSession(dto));
    }

    @GetMapping
    public List<MovieSessionResponseDto> getMovieSessionDto(
            @RequestParam(name = "movieId") Long id,
            @RequestParam @DateTimeFormat(pattern = "dd.MM.yyyy") LocalDate date) {
        return movieSessionService.findAvailableSessions(id, date).stream()
                .map(movieSessionMapper::toMovieSessionResponseDto)
                .collect(Collectors.toList());
    }
}
