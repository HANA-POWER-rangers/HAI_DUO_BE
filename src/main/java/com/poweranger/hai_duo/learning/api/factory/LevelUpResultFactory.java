package com.poweranger.hai_duo.learning.api.factory;

import com.poweranger.hai_duo.user.api.dto.CharacterDto;
import com.poweranger.hai_duo.learning.api.dto.LevelUpResultDto;
import com.poweranger.hai_duo.user.domain.entity.mysql.GameCharacter;
import com.poweranger.hai_duo.user.domain.entity.mysql.User;

public class LevelUpResultFactory {

    public static LevelUpResultDto from(User user, boolean leveledUp, GameCharacter upgradedCharacter) {
        return LevelUpResultDto.builder()
                .userId(user.getUserId())
                .exp(user.getExp())
                .levelId(user.getLevel().getLevelId())
                .leveledUp(leveledUp)
                .upgradedCharacter(CharacterDto.from(upgradedCharacter))
                .build();
    }
}
