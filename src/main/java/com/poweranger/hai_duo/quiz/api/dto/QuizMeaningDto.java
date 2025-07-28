package com.poweranger.hai_duo.quiz.api.dto;

public record QuizMeaningDto(
        String word,
        String meaning,
        String exampleSentence
) implements QuizUnionDto {}
