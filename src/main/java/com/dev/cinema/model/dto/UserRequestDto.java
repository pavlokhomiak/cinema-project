package com.dev.cinema.model.dto;

import com.dev.cinema.annotations.EmailConstraint;
import com.dev.cinema.annotations.FieldsValueMatch;
import lombok.Data;

@Data
@FieldsValueMatch(
        field = "password",
        fieldMatch = "repeatPassword",
        message = "Passwords do not match")
public class UserRequestDto {
    @EmailConstraint
    private String userEmail;
    private String password;
    private String repeatPassword;
}
