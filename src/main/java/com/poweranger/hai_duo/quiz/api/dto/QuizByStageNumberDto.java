package com.poweranger.hai_duo.quiz.api.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record QuizByStageNumberDto(
        Long stageId,
        String stageName,
        Integer stageNumber,
        Integer quizzesCount,
        List<QuizSetDto> quizzes
) {}
