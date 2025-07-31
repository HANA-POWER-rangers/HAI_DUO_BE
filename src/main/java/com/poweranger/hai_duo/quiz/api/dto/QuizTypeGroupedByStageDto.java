package com.poweranger.hai_duo.quiz.api.dto;

import com.poweranger.hai_duo.quiz.domain.entity.QuizType;

import java.util.List;

public record QuizTypeGroupedByStageDto(
        Long stageId,
        String stageName,
        Integer stageNumber,
        Integer quizzesCount,
        QuizType quizType,
        List<QuizUnionDto> quizzes
) {}
