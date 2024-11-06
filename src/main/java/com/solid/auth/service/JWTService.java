package com.solid.auth.service;


import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.KeyUse;
import com.nimbusds.jose.jwk.RSAKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.KeyFactory;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Date;
import java.util.Map;

import com.auth0.jwt.JWT;


@Service
public class JWTService {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.expiration-time}")
    private int expiration;
    private final RSAPrivateKey privateKey;
    private final RSAPublicKey publicKey;

    @Autowired
    public JWTService(RSAPrivateKey privateKey, RSAPublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    public Map<String, Object> getJwkCert() {
        RSAKey.Builder builder = new RSAKey.Builder(publicKey)
                .keyUse(KeyUse.SIGNATURE)
                .algorithm(JWSAlgorithm.RS256)
                .keyID("12345");

//        JWK jwk = new RSAKey.Builder(publicKey)
//                .keyUse(KeyUse.SIGNATURE)
//                .algorithm(JWSAlgorithm.RS256)
//                .keyID("12345")
//                .build();

        return new JWKSet(builder.build()).toJSONObject();
    }

    public String generateToken(String username,Long userId,Long sessionId) {
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);

        return JWT.create()
                .withSubject(username)
                .withClaim("userid",userId)
                .withClaim("sid", sessionId)
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(algorithm);
    }

    public String generateRefreshToken(String username,Long userId,Long sessionId,Date expSession){
        Algorithm algorithm = Algorithm.RSA256(publicKey, privateKey);
        return JWT.create()
                .withSubject(username)
                .withClaim("userid",userId)
                .withClaim("sid", sessionId)
                .withIssuedAt(new Date())
                .withExpiresAt(expSession)
                .sign(algorithm);
    }

}
