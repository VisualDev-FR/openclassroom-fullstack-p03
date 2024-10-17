package com.rentals.api.dto;

import lombok.Data;

import com.rentals.api.model.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {

    @Email(message = "Invalid email")
    @NotNull(message = "An email must be provided")
    @NotEmpty(message = "Email must not be empty")
    @Size(max = User.EMAIL_MAX_SIZE)
    protected String email;

    @NotNull(message = "A password must be provided")
    @NotEmpty(message = "Password must not be empty")
    @Size(max = User.PASSWORD_MAX_SIZE)
    protected String password;
}
