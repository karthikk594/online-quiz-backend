package com.example.server.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Represents a single answer submitted by a student for one question.
 *
 * Example JSON:
 * {
 *   "questionId": 1,
 *   "selectedOption": "B"
 * }
 */
public class AnswerDTO {

    @NotNull(message = "Question ID must not be null")
    private Long questionId;

    @NotBlank(message = "Selected option must not be blank")
    private String selectedOption;  // Expected values: "A", "B", "C", or "D"

    // ──────────────────────────────────────────────
    // Constructors
    // ──────────────────────────────────────────────

    public AnswerDTO() {}

    public AnswerDTO(Long questionId, String selectedOption) {
        this.questionId     = questionId;
        this.selectedOption = selectedOption;
    }

    // ──────────────────────────────────────────────
    // Getters & Setters
    // ──────────────────────────────────────────────

    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }

    public String getSelectedOption() { return selectedOption; }
    public void setSelectedOption(String selectedOption) { this.selectedOption = selectedOption; }
}
