package com.example.server.controller;

import com.example.server.dto.*;
import com.example.server.entity.Question;
import com.example.server.service.QuizService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for the Online Quiz application.
 *
 * Base URL: http://localhost:8080/api/questions
 *
 * Endpoints:
 *   GET    /api/questions          - Get all questions (answers hidden)
 *   GET    /api/questions/{id}     - Get one question by ID (answer hidden)
 *   POST   /api/questions          - Create a new question (admin)
 *   PUT    /api/questions/{id}     - Update a question (admin)
 *   DELETE /api/questions/{id}     - Delete a question (admin)
 *   POST   /api/questions/submit   - Submit quiz and get score + correct answers
 */
@RestController
@RequestMapping("/api/questions")
@CrossOrigin(origins = "http://localhost:5173")  // Allow requests from React (Vite dev server)
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    // ──────────────────────────────────────────────────────────────────
    // GET /api/questions
    // Returns all quiz questions WITHOUT the correct answer.
    // ──────────────────────────────────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<QuestionResponseDTO>> getAllQuestions() {
        List<QuestionResponseDTO> questions = quizService.getAllQuestions();
        return ResponseEntity.ok(questions);  // 200 OK
    }

    // ──────────────────────────────────────────────────────────────────
    // GET /api/questions/{id}
    // Returns one question by its database ID (answer hidden).
    // ──────────────────────────────────────────────────────────────────

    @GetMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> getQuestionById(@PathVariable Long id) {
        QuestionResponseDTO question = quizService.getQuestionById(id);
        return ResponseEntity.ok(question);  // 200 OK
    }

    // ──────────────────────────────────────────────────────────────────
    // POST /api/questions
    // Creates a new question. Body must include all fields including
    // correctAnswer (only admins should call this).
    // ──────────────────────────────────────────────────────────────────

    @PostMapping
    public ResponseEntity<QuestionResponseDTO> createQuestion(
            @Valid @RequestBody Question question) {
        QuestionResponseDTO created = quizService.createQuestion(question);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);  // 201 Created
    }

    // ──────────────────────────────────────────────────────────────────
    // PUT /api/questions/{id}
    // Updates all fields of an existing question.
    // ──────────────────────────────────────────────────────────────────

    @PutMapping("/{id}")
    public ResponseEntity<QuestionResponseDTO> updateQuestion(
            @PathVariable Long id,
            @Valid @RequestBody Question question) {
        QuestionResponseDTO updated = quizService.updateQuestion(id, question);
        return ResponseEntity.ok(updated);  // 200 OK
    }

    // ──────────────────────────────────────────────────────────────────
    // DELETE /api/questions/{id}
    // Deletes a question by ID. Returns 204 No Content on success.
    // ──────────────────────────────────────────────────────────────────

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteQuestion(@PathVariable Long id) {
        quizService.deleteQuestion(id);
        return ResponseEntity.noContent().build();  // 204 No Content
    }

    // ──────────────────────────────────────────────────────────────────
    // POST /api/questions/submit
    // Accepts the student's full quiz submission, grades it, and
    // returns the score + correct answers (revealed after submission).
    // ──────────────────────────────────────────────────────────────────

    @PostMapping("/submit")
    public ResponseEntity<QuizResultDTO> submitQuiz(
            @Valid @RequestBody QuizSubmissionDTO submission) {
        QuizResultDTO result = quizService.gradeQuiz(submission);
        return ResponseEntity.ok(result);  // 200 OK
    }
}
