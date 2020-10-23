package com.dev.cinema.model.dto;

import com.dev.cinema.model.Movie;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MovieSessionResponseDto {
    private Long id;
    private Movie movie;
    private LocalDateTime showTime;
}
