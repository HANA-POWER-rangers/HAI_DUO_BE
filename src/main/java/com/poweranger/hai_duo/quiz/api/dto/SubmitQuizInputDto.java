package com.poweranger.hai_duo.quiz.api.dto;

public record SubmitQuizInputDto (
        Long userId,
        Long stageId,
        String quizType,
        String selectedOption,
        String elapsedTime
){}
