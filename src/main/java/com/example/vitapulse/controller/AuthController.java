package com.example.vitapulse.controller;

import com.example.vitapulse.dto.auth.LoginRequest;
import com.example.vitapulse.dto.auth.LoginResponse;
import com.example.vitapulse.dto.auth.RegisterRequest;
import com.example.vitapulse.dto.auth.RegisterResponse;
import com.example.vitapulse.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public LoginResponse login(@Valid @RequestBody LoginRequest request) {
        return authService.login(request);
    }

}
