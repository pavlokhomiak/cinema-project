package com.dev.cinema.model.dto;

import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MovieSessionRequestDto {
    @NotNull
    private Long movieId;
    @NotNull
    private Long cinemaHallId;
    @NotNull
    private String showTime;
}
