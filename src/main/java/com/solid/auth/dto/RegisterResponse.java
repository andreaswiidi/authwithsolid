package com.solid.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterResponse {
    private Long id;
    private String username;
    private String email;
    private String jwtToken;
    private String refreshToken;
}
