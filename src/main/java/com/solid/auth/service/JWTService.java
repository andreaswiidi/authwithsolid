package com.solid.auth.service;


import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import com.auth0.jwt.JWT;


@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-time}")
    private Long expiration;

    public String generateToken(String username){
        String token = JWT.create()
                .withIssuer("your-app-name")
                .withSubject("username")
                .withClaim("role", "user")
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secret));

        return token;
    }
}
