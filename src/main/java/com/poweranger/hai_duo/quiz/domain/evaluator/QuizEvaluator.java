package com.poweranger.hai_duo.quiz.domain.evaluator;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.progress.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import com.poweranger.hai_duo.quiz.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QuizEvaluator {

    private final StageRepository stageRepository;
    private final QuizMeaningRepository quizMeaningRepository;
    private final QuizOXRepository quizOXRepository;
    private final QuizCardRepository quizCardRepository;
    private final QuizBlankRepository quizBlankRepository;

    public boolean evaluate(SubmitQuizInputDto input) {
        String answer = getCorrectAnswer(input.stageId(), input.quizType());
        return input.selectedOption().equals(answer);
    }

    public String getCorrectAnswer(Long stageId, String quizType) {
        return switch (quizType) {
            case "MEANING" -> quizMeaningRepository.findByStage_StageId(stageId)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.QUIZ_MEANING_NOT_FOUND))
                    .getMeaning();
            case "OX" -> String.valueOf(
                    quizOXRepository.findByStage_StageId(stageId)
                            .orElseThrow(() -> new GeneralException(ErrorStatus.QUIZ_OX_NOT_FOUND))
                            .isCorrect());
            case "CARD" -> quizCardRepository.findByStage_StageId(stageId)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.QUIZ_CARD_NOT_FOUND))
                    .getCorrectWord();
            case "BLANK" -> quizBlankRepository.findByStage_StageId(stageId)
                    .orElseThrow(() -> new GeneralException(ErrorStatus.QUIZ_BLANK_NOT_FOUND))
                    .getCorrectWord();
            default -> throw new GeneralException(ErrorStatus.QUIZ_TYPE_NOT_FOUND);
        };
    }



}
