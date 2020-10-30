package com.dev.cinema.controller;

import com.dev.cinema.model.dto.CinemaHallRequestDto;
import com.dev.cinema.model.dto.CinemaHallResponseDto;
import com.dev.cinema.service.CinemaHallService;
import com.dev.cinema.util.mapper.CinemaHallMapper;
import java.util.List;
import java.util.stream.Collectors;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cinema-halls")
public class CinemaHallController {
    private CinemaHallService cinemaHallService;
    private CinemaHallMapper cinemaHallMapper;

    public CinemaHallController(CinemaHallService cinemaHallService,
                                CinemaHallMapper cinemaHallMapper) {
        this.cinemaHallService = cinemaHallService;
        this.cinemaHallMapper = cinemaHallMapper;
    }

    @PostMapping
    public void addCinemaHall(@RequestBody @Valid CinemaHallRequestDto cinemaHallRequestDto) {
        cinemaHallService.add(cinemaHallMapper.toCinemaHall(cinemaHallRequestDto));
    }

    @GetMapping
    public List<CinemaHallResponseDto> getAllCinemaHall() {
        return cinemaHallService.getAll().stream()
                .map(cinemaHallMapper::toCinemaHallResponseDto)
                .collect(Collectors.toList());
    }
}
