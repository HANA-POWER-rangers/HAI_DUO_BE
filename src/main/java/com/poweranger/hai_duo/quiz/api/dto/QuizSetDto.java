package com.poweranger.hai_duo.quiz.api.dto;

public record QuizSetDto(
        QuizMeaningDto quizMeaning,
        QuizCardDto quizCard,
        QuizOXDto quizOX,
        QuizBlankDto quizBlank
) {}
