package com.poweranger.hai_duo.quiz.api.dto;

import lombok.Builder;

@Builder
public record QuizByStageNumberDto(
    Long stageId,
    String stageName,
    Integer stageNumber,
    QuizMeaningDto quizMeaning,
    QuizCardDto quizCard,
    QuizOXDto quizOX,
    QuizBlankDto quizBlank
){}
