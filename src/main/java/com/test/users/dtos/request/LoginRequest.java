package com.test.users.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "No puede estar vacío")
    @NotNull(message = "Es requerido este campo")
    private String email;

    @NotBlank(message = "No puede estar vacío")
    @NotNull(message = "Es requerido este campo")
    private String password;
}
