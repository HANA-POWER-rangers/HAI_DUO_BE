package com.poweranger.hai_duo.user.api.dto;

public record UserAccuracyDto(
        Long userId,
        Integer totalCount,
        Integer correctCount,
        Float accuracyRate
) {}
