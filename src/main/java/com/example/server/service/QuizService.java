package com.example.server.service;

import com.example.server.dto.*;
import com.example.server.entity.Question;
import com.example.server.repository.QuestionRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Service layer for quiz operations.
 *
 * Responsibilities:
 *  1. CRUD operations on questions (create, read, update, delete)
 *  2. Grade a quiz submission and return the result
 *
 * The controller delegates all business logic to this service.
 */
@Service
public class QuizService {

    // Spring automatically injects the repository via the constructor
    private final QuestionRepository questionRepository;

    public QuizService(QuestionRepository questionRepository) {
        this.questionRepository = questionRepository;
    }

    // ──────────────────────────────────────────────────────────────────
    // Helper: convert a Question entity → QuestionResponseDTO (no answer)
    // ──────────────────────────────────────────────────────────────────

    private QuestionResponseDTO toResponseDTO(Question q) {
        return new QuestionResponseDTO(
            q.getId(),
            q.getQuestionText(),
            q.getOptionA(),
            q.getOptionB(),
            q.getOptionC(),
            q.getOptionD()
        );
    }

    // ──────────────────────────────────────────────────────────────────
    // 1. Get all questions (correct answer hidden)
    // ──────────────────────────────────────────────────────────────────

    public List<QuestionResponseDTO> getAllQuestions() {
        return questionRepository.findAll()
                .stream()
                .map(this::toResponseDTO)
                .toList();
    }

    // ──────────────────────────────────────────────────────────────────
    // 2. Get a single question by ID (correct answer hidden)
    // ──────────────────────────────────────────────────────────────────

    public QuestionResponseDTO getQuestionById(Long id) {
        Question question = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question with id=" + id + " not found"));
        return toResponseDTO(question);
    }

    // ──────────────────────────────────────────────────────────────────
    // 3. Create a new question
    // ──────────────────────────────────────────────────────────────────

    public QuestionResponseDTO createQuestion(Question question) {
        Question saved = questionRepository.save(question);
        return toResponseDTO(saved);
    }

    // ──────────────────────────────────────────────────────────────────
    // 4. Update an existing question
    // ──────────────────────────────────────────────────────────────────

    public QuestionResponseDTO updateQuestion(Long id, Question updatedData) {
        Question existing = questionRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "Question with id=" + id + " not found"));

        // Update each field
        existing.setQuestionText(updatedData.getQuestionText());
        existing.setOptionA(updatedData.getOptionA());
        existing.setOptionB(updatedData.getOptionB());
        existing.setOptionC(updatedData.getOptionC());
        existing.setOptionD(updatedData.getOptionD());
        existing.setCorrectAnswer(updatedData.getCorrectAnswer());

        Question saved = questionRepository.save(existing);
        return toResponseDTO(saved);
    }

    // ──────────────────────────────────────────────────────────────────
    // 5. Delete a question by ID
    // ──────────────────────────────────────────────────────────────────

    public void deleteQuestion(Long id) {
        if (!questionRepository.existsById(id)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, "Question with id=" + id + " not found");
        }
        questionRepository.deleteById(id);
    }

    // ──────────────────────────────────────────────────────────────────
    // 6. Grade a quiz submission
    // ──────────────────────────────────────────────────────────────────

    /**
     * Grades the student's submitted answers.
     *
     * Steps:
     *  a) For each submitted answer, look up the question in the DB.
     *  b) Compare the student's selected option with the stored correct answer.
     *  c) Count correct answers and build the result map.
     *  d) Return score + correct answers (revealed now that submission is done).
     */
    public QuizResultDTO gradeQuiz(QuizSubmissionDTO submission) {
        int score = 0;
        Map<Long, String> correctAnswers = new HashMap<>();

        for (AnswerDTO answer : submission.getAnswers()) {
            Long qId = answer.getQuestionId();

            // Fetch the question (throws 404 if ID doesn't exist)
            Question question = questionRepository.findById(qId)
                    .orElseThrow(() -> new ResponseStatusException(
                            HttpStatus.NOT_FOUND, "Question with id=" + qId + " not found"));

            String correct = question.getCorrectAnswer();
            correctAnswers.put(qId, correct);

            // Case-insensitive comparison (handles "a" == "A")
            if (correct.equalsIgnoreCase(answer.getSelectedOption())) {
                score++;
            }
        }

        int total = submission.getAnswers().size();
        return new QuizResultDTO(score, total, correctAnswers);
    }
}
