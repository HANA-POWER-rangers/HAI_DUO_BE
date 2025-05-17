package com.poweranger.hai_duo.user.api.dto;

public record UserAccuracyDto(
        Long userId,
        int totalCount,
        int correctCount,
        float accuracyRate
) {}
