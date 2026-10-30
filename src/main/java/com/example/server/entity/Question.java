package com.example.server.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

/**
 * Represents a single multiple-choice quiz question stored in the database.
 *
 * Each question has:
 *  - A question text
 *  - Four answer options (A, B, C, D)
 *  - A correct answer key ("A", "B", "C", or "D")
 *
 * NOTE: The correctAnswer field is NEVER sent to the frontend before submission.
 *       It is only used internally by the service when grading.
 */
@Entity
@Table(name = "questions")
public class Question {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Question text must not be blank")
    @Column(nullable = false)
    private String questionText;

    @NotBlank(message = "Option A must not be blank")
    @Column(nullable = false)
    private String optionA;

    @NotBlank(message = "Option B must not be blank")
    @Column(nullable = false)
    private String optionB;

    @NotBlank(message = "Option C must not be blank")
    @Column(nullable = false)
    private String optionC;

    @NotBlank(message = "Option D must not be blank")
    @Column(nullable = false)
    private String optionD;

    /**
     * Correct answer: must be exactly one of "A", "B", "C", or "D".
     * This field is hidden from the frontend via QuestionResponseDTO.
     */
    @NotBlank(message = "Correct answer must not be blank")
    @Pattern(regexp = "^[ABCD]$", message = "Correct answer must be A, B, C, or D")
    @Column(nullable = false)
    private String correctAnswer;

    // ──────────────────────────────────────────────
    // Constructors
    // ──────────────────────────────────────────────

    public Question() {
        // Required by JPA
    }

    public Question(String questionText, String optionA, String optionB,
                    String optionC, String optionD, String correctAnswer) {
        this.questionText  = questionText;
        this.optionA       = optionA;
        this.optionB       = optionB;
        this.optionC       = optionC;
        this.optionD       = optionD;
        this.correctAnswer = correctAnswer;
    }

    // ──────────────────────────────────────────────
    // Getters & Setters
    // ──────────────────────────────────────────────

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }

    public String getOptionA() { return optionA; }
    public void setOptionA(String optionA) { this.optionA = optionA; }

    public String getOptionB() { return optionB; }
    public void setOptionB(String optionB) { this.optionB = optionB; }

    public String getOptionC() { return optionC; }
    public void setOptionC(String optionC) { this.optionC = optionC; }

    public String getOptionD() { return optionD; }
    public void setOptionD(String optionD) { this.optionD = optionD; }

    public String getCorrectAnswer() { return correctAnswer; }
    public void setCorrectAnswer(String correctAnswer) { this.correctAnswer = correctAnswer; }
}
