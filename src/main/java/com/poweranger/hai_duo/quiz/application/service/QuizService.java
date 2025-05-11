package com.poweranger.hai_duo.quiz.application.service;

import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.learning.domain.entity.Stage;
import com.poweranger.hai_duo.quiz.api.dto.*;
import com.poweranger.hai_duo.quiz.api.factory.QuizDtoFactory;
import com.poweranger.hai_duo.quiz.application.reader.QuizReader;
import com.poweranger.hai_duo.quiz.domain.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class QuizService {

    private final StageRepository stageRepository;
    private final QuizDtoFactory quizDtoFactory;
    private final QuizReader quizReader;

    public QuizByStageIdDto getQuizzesByStageId(Long stageId) {
        Stage stage = stageRepository.findById(stageId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STAGE_NOT_FOUND));

        return quizDtoFactory.from(
                stage,
                quizDtoFactory.mapToDto(quizReader.getMeaningQuizByStage(stage), quizDtoFactory::toDto),
                quizDtoFactory.mapToDto(quizReader.getCardQuizByStage(stage), quizDtoFactory::toDto),
                quizDtoFactory.mapToDto(quizReader.getOXQuizByStage(stage), quizDtoFactory::toDto),
                quizDtoFactory.mapToDto(quizReader.getBlankQuizByStage(stage), quizDtoFactory::toDto)
        );
    }

}
