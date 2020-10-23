package com.dev.cinema.model.dto;

import lombok.Data;
import java.time.LocalDateTime;

@Data
public class MovieSessionResponseDto {
    private Long id;
    private Long movieId;
    private Long cinemaHallId;
    private LocalDateTime showTime;
}
