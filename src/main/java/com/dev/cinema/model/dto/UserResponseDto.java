package com.dev.cinema.model.dto;

import lombok.Data;

@Data
public class UserResponseDto {
    private Long id;
    private String email;
    private String password;
}
