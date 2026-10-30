package com.example.server.controller;

import com.example.server.dto.AuthResponseDTO;
import com.example.server.dto.LoginRequestDTO;
import com.example.server.dto.RegisterRequestDTO;
import com.example.server.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * REST Controller for user authentication.
 *
 * Base URL: http://localhost:8080/api/auth
 *
 * Endpoints:
 *   POST /api/auth/register  — create a new user account
 *   POST /api/auth/login     — authenticate an existing user
 */
@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173")  // allow React Vite dev server
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    // ──────────────────────────────────────────────────────────────────
    // POST /api/auth/register
    // Registers a new user. Returns 201 Created with user info.
    // ──────────────────────────────────────────────────────────────────

    @PostMapping("/register")
    public ResponseEntity<AuthResponseDTO> register(
            @Valid @RequestBody RegisterRequestDTO request) {

        AuthResponseDTO response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);  // 201 Created
    }

    // ──────────────────────────────────────────────────────────────────
    // POST /api/auth/login
    // Authenticates a user. Returns 200 OK with user info on success,
    // or 401 Unauthorized if credentials are wrong.
    // ──────────────────────────────────────────────────────────────────

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(
            @Valid @RequestBody LoginRequestDTO request) {

        AuthResponseDTO response = authService.login(request);
        return ResponseEntity.ok(response);  // 200 OK
    }
}
