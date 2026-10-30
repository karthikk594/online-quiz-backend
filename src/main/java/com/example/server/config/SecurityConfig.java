package com.example.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Security configuration for the quiz application.
 *
 * Goals:
 *  1. Disable CSRF (not needed for REST APIs called from React)
 *  2. Allow ALL HTTP requests without authentication (we handle auth manually)
 *  3. Provide a BCryptPasswordEncoder bean for hashing passwords
 *
 * NOTE: In a production app you would protect endpoints with JWT tokens.
 *       For this student project, all routes are open and we trust the frontend.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * Configure which HTTP requests are allowed.
     * We permit everything so our existing quiz endpoints keep working.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // Disable CSRF protection (not needed for REST APIs)
            .csrf(csrf -> csrf.disable())
            // Allow every request without authentication
            .authorizeHttpRequests(auth -> auth.anyRequest().permitAll());

        return http.build();
    }

    /**
     * BCryptPasswordEncoder bean used by AuthService to:
     *  - Hash passwords before storing them in the database
     *  - Verify passwords at login time
     */
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
