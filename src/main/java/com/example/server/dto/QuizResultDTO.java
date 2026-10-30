package com.example.server.dto;

import java.util.Map;

/**
 * The response sent back to the student after quiz submission.
 *
 * Contains:
 *  - score: number of correct answers
 *  - totalQuestions: total number of questions answered
 *  - correctAnswers: a map of questionId -> correct answer letter (e.g., "A", "B")
 *                    This is revealed ONLY after submission.
 *
 * Example response:
 * {
 *   "score": 3,
 *   "totalQuestions": 5,
 *   "correctAnswers": {
 *     "1": "A",
 *     "2": "B",
 *     "3": "C",
 *     "4": "C",
 *     "5": "D"
 *   }
 * }
 */
public class QuizResultDTO {

    private int score;
    private int totalQuestions;
    private Map<Long, String> correctAnswers;  // revealed after submission

    // ──────────────────────────────────────────────
    // Constructors
    // ──────────────────────────────────────────────

    public QuizResultDTO() {}

    public QuizResultDTO(int score, int totalQuestions, Map<Long, String> correctAnswers) {
        this.score          = score;
        this.totalQuestions = totalQuestions;
        this.correctAnswers = correctAnswers;
    }

    // ──────────────────────────────────────────────
    // Getters & Setters
    // ──────────────────────────────────────────────

    public int getScore() { return score; }
    public void setScore(int score) { this.score = score; }

    public int getTotalQuestions() { return totalQuestions; }
    public void setTotalQuestions(int totalQuestions) { this.totalQuestions = totalQuestions; }

    public Map<Long, String> getCorrectAnswers() { return correctAnswers; }
    public void setCorrectAnswers(Map<Long, String> correctAnswers) { this.correctAnswers = correctAnswers; }
}
