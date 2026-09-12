package com.expensetracker.expense_tracker_api.controller;

import com.expensetracker.expense_tracker_api.dtorequest.LoginRequest;
import com.expensetracker.expense_tracker_api.dtorequest.RegisterRequest;
import com.expensetracker.expense_tracker_api.dtoresponse.AuthResponse;
import com.expensetracker.expense_tracker_api.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}