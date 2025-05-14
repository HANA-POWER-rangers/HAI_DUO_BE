package com.poweranger.hai_duo.learning.api.dto;

import com.poweranger.hai_duo.user.api.dto.CharacterDto;
import lombok.Builder;

public record LevelUpResultDto(
        Long userId,
        int exp,
        Long levelId,
        boolean leveledUp,
        CharacterDto upgradedCharacter
) {
    @Builder
    public LevelUpResultDto {}
}
