package com.dev.cinema.model.dto;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class MovieSessionResponseDto {
    private Long id;
    private Long movieId;
    private Long cinemaHallId;
    private LocalDateTime showTime;
}
