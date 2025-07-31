package com.poweranger.hai_duo.quiz.application.reader;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.domain.entity.QuizBlank;
import com.poweranger.hai_duo.quiz.domain.entity.QuizCard;
import com.poweranger.hai_duo.quiz.domain.entity.QuizMeaning;
import com.poweranger.hai_duo.quiz.domain.entity.QuizOX;
import com.poweranger.hai_duo.quiz.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class QuizReader {

    private final QuizMeaningRepository quizMeaningRepository;
    private final QuizCardRepository quizCardRepository;
    private final QuizOXRepository quizOXRepository;
    private final QuizBlankRepository quizBlankRepository;

    public List<QuizMeaning> getMeaningQuizzesByStage(Stage stage) {
        return quizMeaningRepository.findAllByStage(stage);
    }

    public List<QuizCard> getCardQuizzesByStage(Stage stage) {
        return quizCardRepository.findAllByStage(stage);
    }

    public List<QuizOX> getOXQuizzesByStage(Stage stage) {
        return quizOXRepository.findAllByStage(stage);
    }

    public List<QuizBlank> getBlankQuizzesByStage(Stage stage) {
        return quizBlankRepository.findAllByStage(stage);
    }
}
