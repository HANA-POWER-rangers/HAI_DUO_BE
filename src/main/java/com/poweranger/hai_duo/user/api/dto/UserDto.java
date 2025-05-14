package com.poweranger.hai_duo.user.api.dto;

import java.time.LocalDateTime;

import com.poweranger.hai_duo.learning.domain.entity.GameCharacter;
import com.poweranger.hai_duo.learning.domain.entity.Level;
import com.poweranger.hai_duo.user.domain.entity.mysql.User;

public record UserDto(
        Long userId,
        String tempUserToken,
        int exp,
        int goldAmount,
        Level levelId,
        GameCharacter characterId,
        LocalDateTime createdAt,
        LocalDateTime lastAccessedAt
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getUserId(),
                user.getTempUserToken(),
                user.getExp(),
                user.getGoldAmount(),
                user.getLevel(),
                user.getGameCharacter(),
                user.getCreatedAt(),
                user.getLastAccessedAt()
        );
    }
}
