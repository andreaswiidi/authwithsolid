package com.solid.auth.service;


import com.auth0.jwt.algorithms.Algorithm;
import com.solid.auth.dto.JWTDto;
import com.solid.auth.models.Session;
import com.solid.auth.models.Users;
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
    private int expiration;

    public String generateToken(String username,Long sessionId){
        return JWT.create()
                .withSubject("username")
                .withClaim("sid", sessionId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC256(secret));
    }

    public String generateRefreshToken(String username,Long sessionId,Date expSession){
        return JWT.create()
                .withSubject("username")
                .withClaim("sid", sessionId)
                .withIssuedAt(new Date())
                .withExpiresAt(expSession)
                .sign(Algorithm.HMAC256(secret));
    }

    public JWTDto generateJWT(Users users, Session session){
        return new JWTDto(generateToken(users.getUsername(), session.getId()),
                generateRefreshToken(users.getUsername(), session.getId(), session.getExpSession()));
    }

}
