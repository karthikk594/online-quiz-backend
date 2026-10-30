package com.example.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

/**
 * Represents a registered user of the quiz application.
 *
 * Fields stored in the USERS table:
 *   id          – auto-generated primary key
 *   fullName    – user's display name
 *   username    – unique login name
 *   email       – unique email address
 *   password    – BCrypt-hashed password (NEVER stored as plain text)
 *   createdAt   – timestamp when the account was created
 */
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Full name is required")
    @Column(nullable = false)
    private String fullName;

    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 30, message = "Username must be between 3 and 30 characters")
    @Column(nullable = false, unique = true)
    private String username;

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be a valid email address")
    @Column(nullable = false, unique = true)
    private String email;

    /**
     * Stores the BCrypt hash of the password.
     * This value is NEVER returned to the frontend.
     */
    @NotBlank(message = "Password is required")
    @Column(nullable = false)
    private String password;

    /**
     * Automatically set to the current timestamp when a user registers.
     * updatable = false means it cannot be changed after creation.
     */
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    // ──────────────────────────────────────────────
    // Lifecycle: set createdAt before first save
    // ──────────────────────────────────────────────

    @PrePersist
    public void prePersist() {
        this.createdAt = LocalDateTime.now();
    }

    // ──────────────────────────────────────────────
    // Constructors
    // ──────────────────────────────────────────────

    public User() {}

    public User(String fullName, String username, String email, String password) {
        this.fullName = fullName;
        this.username = username;
        this.email    = email;
        this.password = password;
    }

    // ──────────────────────────────────────────────
    // Getters & Setters
    // ──────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
