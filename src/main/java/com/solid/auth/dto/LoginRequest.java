package com.solid.auth.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

/**
 * The purpose of this class is to store request for login
 * S -> responsible for managing the LoginRequest
 * why not cek the req in controller because it will be repetitive if this class used more than 1 controller
 */

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
