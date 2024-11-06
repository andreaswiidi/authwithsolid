package com.solid.auth.controller;

import com.solid.auth.dto.JWTReq;
import com.solid.auth.dto.JWTResponse;
import com.solid.auth.dto.ResponseWrapper;
import com.solid.auth.models.Session;
import com.solid.auth.service.JWTService;
import com.solid.auth.service.SessionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class AuthController {
    private final JWTService jwtService;
    private final SessionService sessionService;

    public AuthController(JWTService jwtService, SessionService sessionService) {
        this.jwtService = jwtService;
        this.sessionService = sessionService;
    }

    @GetMapping("/jwk")
    public Map<String, Object> keys() {
        return jwtService.getJwkCert();
    }

    @PostMapping("/get-jwt")
    public ResponseWrapper<JWTResponse> getJWT(@RequestBody JWTReq jwtReq){
        Session session = sessionService.createSessionForUser(jwtReq.getUserId(), jwtReq.getUsername());
        return new ResponseWrapper<JWTResponse>().success(new JWTResponse(jwtService.generateToken(jwtReq.getUsername(),jwtReq.getUserId(),session.getId()),
                jwtService.generateRefreshToken(jwtReq.getUsername(),jwtReq.getUserId() ,session.getId(),session.getExpSession())));
    }

}
