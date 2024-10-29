package com.solid.auth.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class LoginRequest {
    @NotBlank(message = "Password cannot be empty", groups = NotBlank.class)
    private String username;
    @Email(message = "Not Valid Email Format", groups = Email.class)
    private String email;
    @NotNull(message = "Password cannot be empty", groups = NotNull.class)
    @NotBlank(message = "Password cannot be empty", groups = NotBlank.class)
    private String password;
}
