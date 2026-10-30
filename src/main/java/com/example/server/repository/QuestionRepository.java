package com.example.server.repository;

import com.example.server.entity.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for Question entity.
 *
 * Spring Data JPA automatically provides implementations for:
 *   findAll(), findById(), save(), deleteById(), count(), etc.
 *
 * No custom queries are needed for this application.
 */
@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    // All standard CRUD operations are inherited from JpaRepository
}
