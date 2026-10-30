package com.example.server.service;

import com.example.server.dto.AuthResponseDTO;
import com.example.server.dto.LoginRequestDTO;
import com.example.server.dto.RegisterRequestDTO;
import com.example.server.entity.User;
import com.example.server.repository.UserRepository;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Service layer for user authentication.
 *
 * Handles:
 *  1. register() — validate input, check for duplicates, hash password, save user
 *  2. login()    — find user by username, verify password with BCrypt
 */
@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    // Spring injects both beans via constructor
    public AuthService(UserRepository userRepository,
                       BCryptPasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ──────────────────────────────────────────────────────────────────
    // REGISTER
    // ──────────────────────────────────────────────────────────────────

    /**
     * Registers a new user.
     *
     * Steps:
     *  a) Check passwords match
     *  b) Check username is not already taken
     *  c) Check email is not already taken
     *  d) Hash the plain-text password with BCrypt
     *  e) Save the new User to the database
     *  f) Return a safe AuthResponseDTO (no password)
     */
    public AuthResponseDTO register(RegisterRequestDTO request) {

        // a) Passwords must match
        if (!request.getPassword().equals(request.getConfirmPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, "Passwords do not match");
        }

        // b) Username must be unique
        if (userRepository.existsByUsername(request.getUsername())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "Username '" + request.getUsername() + "' is already taken");
        }

        // c) Email must be unique
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT, "An account with email '" + request.getEmail() + "' already exists");
        }

        // d) Hash the password — NEVER store plain text!
        String hashedPassword = passwordEncoder.encode(request.getPassword());

        // e) Create and save the User entity
        User newUser = new User(
                request.getFullName(),
                request.getUsername(),
                request.getEmail(),
                hashedPassword
        );
        User savedUser = userRepository.save(newUser);

        // f) Return safe user info
        return toResponseDTO(savedUser, "Registration successful! Welcome, " + savedUser.getFullName() + "!");
    }

    // ──────────────────────────────────────────────────────────────────
    // LOGIN
    // ──────────────────────────────────────────────────────────────────

    /**
     * Logs in an existing user.
     *
     * Steps:
     *  a) Find user by username (throws 404 if not found)
     *  b) Compare submitted password with the stored BCrypt hash
     *  c) Return a safe AuthResponseDTO on success
     */
    public AuthResponseDTO login(LoginRequestDTO request) {

        // a) Find user — use a vague error message for security
        //    (don't reveal whether the username or password was wrong)
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "Invalid username or password"));

        // b) Verify password against the BCrypt hash
        boolean passwordMatches = passwordEncoder.matches(request.getPassword(), user.getPassword());
        if (!passwordMatches) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        // c) Return safe user info
        return toResponseDTO(user, "Welcome back, " + user.getFullName() + "!");
    }

    // ──────────────────────────────────────────────────────────────────
    // Helper: Convert User entity → AuthResponseDTO (no password)
    // ──────────────────────────────────────────────────────────────────

    private AuthResponseDTO toResponseDTO(User user, String message) {
        return new AuthResponseDTO(
                user.getId(),
                user.getFullName(),
                user.getUsername(),
                user.getEmail(),
                user.getCreatedAt() != null ? user.getCreatedAt().toString() : null,
                message
        );
    }
}
