package com.example.server.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * Global exception handler — catches errors from any controller and
 * returns a clean, structured JSON error response instead of a stack trace.
 *
 * Handled exceptions:
 *  1. ResponseStatusException  → for 404 Not Found errors thrown in the service
 *  2. MethodArgumentNotValidException → for @Valid validation failures
 *  3. Exception (generic)      → for any unexpected server error
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // ──────────────────────────────────────────────────────────────────
    // Handle 404 Not Found and other ResponseStatusExceptions
    // ──────────────────────────────────────────────────────────────────

    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
            ResponseStatusException ex) {

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status",  ex.getStatusCode().value());
        error.put("error",   ex.getStatusCode().toString());
        error.put("message", ex.getReason());

        return ResponseEntity.status(ex.getStatusCode()).body(error);
    }

    // ──────────────────────────────────────────────────────────────────
    // Handle validation errors (from @Valid on request body)
    // ──────────────────────────────────────────────────────────────────

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        // Collect all field-level validation errors into a map
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError ->
                fieldErrors.put(fieldError.getField(), fieldError.getDefaultMessage())
        );

        Map<String, Object> error = new HashMap<>();
        error.put("timestamp",   LocalDateTime.now().toString());
        error.put("status",      HttpStatus.BAD_REQUEST.value());
        error.put("error",       "Validation Failed");
        error.put("fieldErrors", fieldErrors);

        return ResponseEntity.badRequest().body(error);  // 400 Bad Request
    }

    // ──────────────────────────────────────────────────────────────────
    // Handle any unexpected server-side error
    // ──────────────────────────────────────────────────────────────────

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGenericException(Exception ex) {
        Map<String, Object> error = new HashMap<>();
        error.put("timestamp", LocalDateTime.now().toString());
        error.put("status",    HttpStatus.INTERNAL_SERVER_ERROR.value());
        error.put("error",     "Internal Server Error");
        error.put("message",   ex.getMessage());

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
