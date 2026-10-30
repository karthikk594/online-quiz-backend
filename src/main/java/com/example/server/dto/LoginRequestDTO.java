package com.example.server.dto;

import jakarta.validation.constraints.NotBlank;

/**
 * DTO for user login.
 *
 * Sent from the frontend when a user submits the Login form.
 *
 * Example JSON:
 * {
 *   "username": "johndoe",
 *   "password": "secret123"
 * }
 */
public class LoginRequestDTO {

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required")
    private String password;

    // ── Getters & Setters ──────────────────────────────────────────────

    public String getUsername()        { return username; }
    public void setUsername(String v)  { this.username = v; }

    public String getPassword()        { return password; }
    public void setPassword(String v)  { this.password = v; }
}
