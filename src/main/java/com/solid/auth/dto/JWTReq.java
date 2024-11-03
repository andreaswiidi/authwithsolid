package com.solid.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JWTReq {
    private Long userId;
    private String username;
}
