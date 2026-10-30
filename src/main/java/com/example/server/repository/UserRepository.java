package com.example.server.repository;

import com.example.server.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for User entity.
 * Spring Data JPA automatically implements these methods.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Find a user by their username (used during login).
     * Returns Optional.empty() if no user has that username.
     */
    Optional<User> findByUsername(String username);

    /**
     * Find a user by their email (used to check for duplicate emails at registration).
     */
    Optional<User> findByEmail(String email);

    /**
     * Check if a username already exists (faster than findByUsername — no object creation).
     */
    boolean existsByUsername(String username);

    /**
     * Check if an email already exists.
     */
    boolean existsByEmail(String email);
}
