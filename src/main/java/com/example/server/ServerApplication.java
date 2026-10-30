package com.example.server;

import com.example.server.entity.Question;
import com.example.server.repository.QuestionRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

/**
 * Main entry point for the Online Quiz Spring Boot application.
 * On startup, it seeds the database with 5 sample multiple-choice questions.
 */
@SpringBootApplication
public class ServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ServerApplication.class, args);
    }

    /**
     * Seeds the H2 database with sample questions when the application starts.
     * Only inserts data if the table is empty (avoids duplicates on restart).
     */
    @Bean
    public CommandLineRunner seedData(QuestionRepository questionRepository) {
        return args -> {
            if (questionRepository.count() == 0) {
                List<Question> questions = List.of(
                    new Question(
                        "What does HTML stand for?",
                        "Hyper Text Markup Language",
                        "High Tech Modern Language",
                        "Hyper Transfer Markup Language",
                        "Home Tool Markup Language",
                        "A"
                    ),
                    new Question(
                        "Which keyword is used to declare a variable in Java that cannot be reassigned?",
                        "static",
                        "final",
                        "const",
                        "immutable",
                        "B"
                    ),
                    new Question(
                        "Which HTTP status code means 'Not Found'?",
                        "200",
                        "301",
                        "404",
                        "500",
                        "C"
                    ),
                    new Question(
                        "What does CSS stand for?",
                        "Computer Style Sheets",
                        "Creative Style Sheets",
                        "Cascading Style Sheets",
                        "Colorful Style Sheets",
                        "C"
                    ),
                    new Question(
                        "Which of the following is a JavaScript framework?",
                        "Django",
                        "Laravel",
                        "Spring",
                        "React",
                        "D"
                    ),
                    new Question(
                        "In REST APIs, which HTTP method is used to create a new resource?",
                        "GET",
                        "POST",
                        "PUT",
                        "DELETE",
                        "B"
                    ),
                    new Question(
                        "Which SQL clause is used to filter rows?",
                        "ORDER BY",
                        "GROUP BY",
                        "WHERE",
                        "HAVING",
                        "C"
                    )
                );
                questionRepository.saveAll(questions);
                System.out.println("✅ Database seeded with " + questions.size() + " quiz questions.");
            }
        };
    }
}
