package com.solid.auth.controller;

import com.solid.auth.dto.RegisterRequest;
import com.solid.auth.dto.RegisterResponse;
import com.solid.auth.dto.ResponseWrapper;
import com.solid.auth.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    @PostMapping("/register")
    public ResponseWrapper<RegisterResponse> registerUser(@RequestBody RegisterRequest request) {
        return new ResponseWrapper<RegisterResponse>()
                .success(userService.registerUser(request));

    }
}
