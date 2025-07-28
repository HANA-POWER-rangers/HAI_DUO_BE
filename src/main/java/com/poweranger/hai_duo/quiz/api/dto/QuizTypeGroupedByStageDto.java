package com.poweranger.hai_duo.quiz.api.dto;

import java.util.List;

public record QuizTypeGroupedByStageDto(
        Long stageId,
        String stageName,
        int stageNumber,
        List<QuizUnionDto> quizzes
) {}
