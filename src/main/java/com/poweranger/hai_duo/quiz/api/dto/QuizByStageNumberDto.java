package com.poweranger.hai_duo.quiz.api.dto;

import lombok.Builder;
import java.util.List;

@Builder
public record QuizByStageNumberDto(
    Long stageId,
    String stageName,
    Integer stageNumber,
    List<QuizMeaningDto> quizMeaning,
    List<QuizCardDto> quizCard,
    List<QuizOXDto> quizOX,
    List<QuizBlankDto> quizBlank
){}
