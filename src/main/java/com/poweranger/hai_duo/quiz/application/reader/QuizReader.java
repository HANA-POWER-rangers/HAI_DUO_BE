package com.poweranger.hai_duo.quiz.application.reader;

import com.poweranger.hai_duo.learning.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.domain.entity.*;
import com.poweranger.hai_duo.quiz.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class QuizReader {

    private final QuizMeaningRepository quizMeaningRepository;
    private final QuizCardRepository quizCardRepository;
    private final QuizOXRepository quizOXRepository;
    private final QuizBlankRepository quizBlankRepository;

    public Optional<QuizMeaning> getMeaningQuiz(Stage stage) {
        return quizMeaningRepository.findByStage(stage);
    }

    public Optional<QuizCard> getCardQuiz(Stage stage) {
        return quizCardRepository.findByStage(stage);
    }

    public Optional<QuizOX> getOXQuiz(Stage stage) {
        return quizOXRepository.findByStage(stage);
    }

    public Optional<QuizBlank> getBlankQuiz(Stage stage) {
        return quizBlankRepository.findByStage(stage);
    }
}
