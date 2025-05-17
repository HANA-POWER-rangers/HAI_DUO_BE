package com.poweranger.hai_duo.quiz.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import com.poweranger.hai_duo.quiz.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QuizSubmissionService {

    private final QuizLogRepository quizLogRepository;
    private final QuizMeaningRepository quizMeaningRepository;
    private final QuizCardRepository quizCardRepository;
    private final QuizOXRepository quizOXRepository;
    private final QuizBlankRepository quizBlankRepository;

    public void submitQuiz(SubmitQuizInputDto input) {
        quizLogRepository.save(input);
    }

    public boolean isCorrect(Long stageId, String quizType, String selectedOption) {
        String correctAnswer = switch (quizType) {
            case "MEAN" -> quizMeaningRepository.findByStage_StageId(stageId).orElseThrow().getWord();
            case "CARD" -> quizCardRepository.findByStage_StageId(stageId).orElseThrow().getCorrectWord();
            case "OX" -> String.valueOf(quizOXRepository.findByStage_StageId(stageId).orElseThrow().getWord());
            case "BLANK" -> quizBlankRepository.findByStage_StageId(stageId).orElseThrow().getCorrectWord();
            default -> throw new GeneralException(ErrorStatus.QUIZ_TYPE_NOT_FOUND);
        };
        return correctAnswer.equals(selectedOption);
    }
}
