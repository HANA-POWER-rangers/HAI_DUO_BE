package com.poweranger.hai_duo.quiz.api.resolver;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.quiz.domain.repository.QuizBlankRepository;
import com.poweranger.hai_duo.quiz.domain.repository.QuizCardRepository;
import com.poweranger.hai_duo.quiz.domain.repository.QuizMeaningRepository;
import com.poweranger.hai_duo.quiz.domain.repository.QuizOXRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizAnswerResolver {

    private final QuizMeaningRepository quizMeaningRepository;
    private final QuizCardRepository quizCardRepository;
    private final QuizOXRepository quizOXRepository;
    private final QuizBlankRepository quizBlankRepository;

    public String resolveCorrectAnswer(Long stageId, String quizType) {
        return switch (quizType) {
            case "MEAN" -> quizMeaningRepository.findByStage_StageId(stageId)
                    .orElseThrow().getWord();
            case "CARD" -> quizCardRepository.findByStage_StageId(stageId)
                    .orElseThrow().getCorrectWord();
            case "OX" -> String.valueOf(quizOXRepository.findByStage_StageId(stageId)
                    .orElseThrow().getWord());
            case "BLANK" -> quizBlankRepository.findByStage_StageId(stageId)
                    .orElseThrow().getCorrectWord();
            default -> throw new GeneralException(ErrorStatus.QUIZ_TYPE_NOT_FOUND);
        };
    }
}
