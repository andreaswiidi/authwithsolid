package com.solid.auth.service;


import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.solid.auth.dto.JWTDto;
import com.solid.auth.models.Session;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import com.auth0.jwt.JWT;


@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration-time}")
    private int expiration;

    private RSAPrivateKey loadPrivateKey() throws Exception {
        byte[] keyBytes = Files.readAllBytes(Paths.get("src/main/resources/private_key.pem"));
        PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        return (RSAPrivateKey) keyFactory.generatePrivate(spec);
    }

    public String generateToken(String username,Long sessionId) throws Exception{
        RSAPrivateKey privateKey = loadPrivateKey();
        Algorithm algorithm = Algorithm.RSA256(null, privateKey);

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

    public boolean isTokenValid(String token,String username){
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(secret))
                    .withSubject("username")
                    .build();
            verifier.verify(token);
        }catch (Exception ex){
            return false;
        }

        return  true;
    }

}
