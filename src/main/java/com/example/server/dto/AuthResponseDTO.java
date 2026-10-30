package com.example.server.dto;

/**
 * DTO returned to the frontend after a successful login or registration.
 *
 * NOTE: The password is intentionally NOT included here.
 *       We only send safe, non-sensitive user information.
 *
 * Example response:
 * {
 *   "id": 1,
 *   "fullName": "John Doe",
 *   "username": "johndoe",
 *   "email": "john@example.com",
 *   "createdAt": "2026-10-30T12:00:00",
 *   "message": "Login successful"
 * }
 */
public class AuthResponseDTO {

    private Long id;
    private String fullName;
    private String username;
    private String email;
    private String createdAt;   // formatted as a readable string
    private String message;     // success message for the frontend to display

    // ── Constructors ──────────────────────────────────────────────────

    public AuthResponseDTO() {}

    public AuthResponseDTO(Long id, String fullName, String username,
                           String email, String createdAt, String message) {
        this.id        = id;
        this.fullName  = fullName;
        this.username  = username;
        this.email     = email;
        this.createdAt = createdAt;
        this.message   = message;
    }

    // ── Getters & Setters ─────────────────────────────────────────────

    public Long getId()               { return id; }
    public void setId(Long id)        { this.id = id; }

    public String getFullName()              { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUsername()              { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail()             { return email; }
    public void setEmail(String email)   { this.email = email; }

    public String getCreatedAt()               { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getMessage()             { return message; }
    public void setMessage(String message) { this.message = message; }
}
