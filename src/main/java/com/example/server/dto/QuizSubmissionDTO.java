package com.example.server.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

/**
 * The full quiz submission from a student.
 * Contains a list of answers (one per question).
 *
 * Example JSON:
 * {
 *   "answers": [
 *     { "questionId": 1, "selectedOption": "A" },
 *     { "questionId": 2, "selectedOption": "B" },
 *     { "questionId": 3, "selectedOption": "C" }
 *   ]
 * }
 */
public class QuizSubmissionDTO {

    @NotEmpty(message = "Answers list must not be empty")
    @Valid  // triggers validation on each AnswerDTO inside the list
    private List<AnswerDTO> answers;

    // ──────────────────────────────────────────────
    // Constructors
    // ──────────────────────────────────────────────

    public QuizSubmissionDTO() {}

    public QuizSubmissionDTO(List<AnswerDTO> answers) {
        this.answers = answers;
    }

    // ──────────────────────────────────────────────
    // Getters & Setters
    // ──────────────────────────────────────────────

    public List<AnswerDTO> getAnswers() { return answers; }
    public void setAnswers(List<AnswerDTO> answers) { this.answers = answers; }
}
