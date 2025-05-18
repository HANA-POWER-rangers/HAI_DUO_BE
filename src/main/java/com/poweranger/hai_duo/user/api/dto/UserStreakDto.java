package com.poweranger.hai_duo.user.api.dto;

public record UserStreakDto(
        Long userId,
        int streakCount
) {
    public static UserStreakDto from(Long userId, int streakCount) {
        return new UserStreakDto(
                userId,
                streakCount
        );
    }
}
