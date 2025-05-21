package com.poweranger.hai_duo.quiz.api.dto;

import lombok.Builder;

@Builder
public record QuizByStageIdDto(
        Long stageId,
        String stageName,
        QuizMeaningDto quizMeaning,
        QuizCardDto quizCard,
        QuizOXDto quizOX,
        QuizBlankDto quizBlank
) {}

