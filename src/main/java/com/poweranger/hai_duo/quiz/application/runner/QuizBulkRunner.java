package com.poweranger.hai_duo.quiz.application.runner;

import com.poweranger.hai_duo.quiz.application.importer.QuizBulkImporter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
@RequiredArgsConstructor
public class QuizBulkRunner implements CommandLineRunner {

    private final QuizBulkImporter quizBulkImporter;
    private final QuizProperties quizProperties;

    @Override
    public void run(String... args) throws Exception {
        Path base = Paths.get(quizProperties.getPath());
        System.out.println(">>> Quiz Path: " + base);
        quizBulkImporter.importAllQuizzes(base);
    }
}
