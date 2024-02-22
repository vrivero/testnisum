package com.test.users.dtos.request;

import com.test.users.utils.validations.Email;
import com.test.users.utils.validations.Password;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Validated
public class UserRequest {

    @NotBlank(message = "No puede estar vacío")
    @NotNull(message = "Es requerido este campo")
    private String name;

    @NotBlank(message = "No puede estar vacío")
    @NotNull(message = "Es requerido este campo")
    @Email
    private String email;

    @NotBlank(message = "No puede estar vacío")
    @NotNull(message = "Es requerido este campo")
    @Password
    private String password;
    private List<PhoneRequest> phones;

    private Boolean isactive;
}
