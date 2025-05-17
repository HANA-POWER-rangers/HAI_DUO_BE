package com.poweranger.hai_duo.quiz.api.dto;

public record SubmitQuizPayloadDto(
        boolean isCorrect,
        String correctAnswer
) {
    public static SubmitQuizPayloadDto of(boolean isCorrect, String correctAnswer) {
        return new SubmitQuizPayloadDto(isCorrect, correctAnswer);
    }
}
