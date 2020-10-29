package com.dev.cinema.model.dto;

import com.dev.cinema.annotations.EmailConstraint;
import javax.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OrderRequestDto {
    @NotNull
    @EmailConstraint
    private String userEmail;
}
