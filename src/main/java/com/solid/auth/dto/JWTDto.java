package com.solid.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTDto {
    private String jwtToken;
    private String refreshToken;

    public JWTDto(String jwtToken, String refreshToken) {
        this.jwtToken = jwtToken;
        this.refreshToken = refreshToken;
    }
}
