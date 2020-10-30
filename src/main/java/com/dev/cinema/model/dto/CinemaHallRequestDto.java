package com.dev.cinema.model.dto;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CinemaHallRequestDto {
    @Min(10)
    private int capacity;
    @NotNull
    private String description;
}
