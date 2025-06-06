package com.poweranger.hai_duo.user.api.dto;

import java.time.LocalDateTime;
import com.poweranger.hai_duo.user.domain.entity.mysql.User;

public record UserDto(
        Long userId,
        String tempUserToken,
        int exp,
        int goldAmount,
        Long levelId,
        Long characterId,
        LocalDateTime createdAt,
        LocalDateTime lastAccessedAt
) {
    public static UserDto from(User user) {
        return new UserDto(
                user.getUserId(),
                user.getTempUserToken(),
                user.getExp(),
                user.getGoldAmount(),
                user.getLevel().getLevelId(),
                user.getGameCharacter().getCharacterId(),
                user.getCreatedAt(),
                user.getLastAccessedAt()
        );
    }
}
