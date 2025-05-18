package com.poweranger.hai_duo.quiz.api.dto;

public record QuizOXDto(
        String word,
        String meaning,
        boolean isCorrect
) {}
