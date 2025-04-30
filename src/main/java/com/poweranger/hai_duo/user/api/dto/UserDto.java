package com.poweranger.hai_duo.user.api.dto;

import java.time.LocalDateTime;
import com.poweranger.hai_duo.user.domain.entity.User;

public record UserDto(
        Long userId,
        String tempUserId,
        int exp,
        Long levelId,
        Long characterId,
        int goldAmount,
        LocalDateTime createdAt,
        LocalDateTime lastAccessedAt
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getUserId(),
                user.getTempUserId(),
                user.getExp(),
                user.getLevelId(),
                user.getCharacterId(),
                user.getGoldAmount(),
                user.getCreatedAt(),
                user.getLastAccessedAt()
        );
    }
}
