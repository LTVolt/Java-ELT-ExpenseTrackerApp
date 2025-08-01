package com.cptvolt.expensetracker.controller;

import com.cptvolt.expensetracker.dto.AuthRequest;
import com.cptvolt.expensetracker.dto.AuthResponse;
import org.springframework.web.bind.annotation.*;
import com.cptvolt.expensetracker.service.AuthService;

@CrossOrigin
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public AuthResponse register(@RequestBody AuthRequest request) {
        return authService.register(request);
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        return authService.login(request);
    }
}
