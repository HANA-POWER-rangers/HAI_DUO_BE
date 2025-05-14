package com.poweranger.hai_duo.quiz.application.reader;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
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

    public Optional<QuizMeaning> getMeaningQuizByStage(Stage stage) {
        return quizMeaningRepository.findByStage(stage);
    }

    public Optional<QuizCard> getCardQuizByStage(Stage stage) {
        return quizCardRepository.findByStage(stage);
    }

    public Optional<QuizOX> getOXQuizByStage(Stage stage) {
        return quizOXRepository.findByStage(stage);
    }

    public Optional<QuizBlank> getBlankQuizByStage(Stage stage) {
        return quizBlankRepository.findByStage(stage);
    }

    public Optional<QuizMeaning> getMeaningQuizByStageId(Long stageId) {
        return quizMeaningRepository.findByStage_StageId(stageId);
    }

    public Optional<QuizCard> getCardQuizByStageId(Long stageId) {
        return quizCardRepository.findByStage_StageId(stageId);
    }

    public Optional<QuizOX> getOXQuizByStageId(Long stageId) {
        return quizOXRepository.findByStage_StageId(stageId);
    }

    public Optional<QuizBlank> getBlankQuizByStageId(Long stageId) {
        return quizBlankRepository.findByStage_StageId(stageId);
    }
}
