package com.solid.auth.dto;

import jakarta.validation.GroupSequence;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

@Getter
@Setter
@ToString
@GroupSequence({
        NotNull.class,
        NotBlank.class,
        Email.class,
        RegisterRequest.class
})
public class RegisterRequest implements Serializable {
    @NotNull(message = "Username is required", groups = NotNull.class)
    @NotBlank(message = "Username is required", groups = NotBlank.class)
    private String username;
    @NotNull(message = "Email is required", groups = NotNull.class)
    @NotBlank(message = "Email is required", groups = NotBlank.class)
    @Email(message = "Not Valid Email Format", groups = Email.class)
    private String email;
    @NotNull(message = "Password cannot be empty", groups = NotNull.class)
    @NotBlank(message = "Password cannot be empty", groups = NotBlank.class)
    private String password;
}
