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
public class quizService {

    private final StageRepository stageRepository;
    private final QuizDtoFactory quizDtoFactory;
    private final QuizReader quizReader;

    public QuizByStageIdDto getQuizzes(Long chapterId, Long stageId) {
        Stage stage = stageRepository.findByIdAndChapterId(stageId, chapterId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.STAGE_NOT_FOUND));

        return quizDtoFactory.from(
                stage,
                quizDtoFactory.mapToDto(quizReader.getMeaningQuiz(stage), quizDtoFactory::toDto),
                quizDtoFactory.mapToDto(quizReader.getCardQuiz(stage), quizDtoFactory::toDto),
                quizDtoFactory.mapToDto(quizReader.getOXQuiz(stage), quizDtoFactory::toDto),
                quizDtoFactory.mapToDto(quizReader.getBlankQuiz(stage), quizDtoFactory::toDto)
        );
    }
}
