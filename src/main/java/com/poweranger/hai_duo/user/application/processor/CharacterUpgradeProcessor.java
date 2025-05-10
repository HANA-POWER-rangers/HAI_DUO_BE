package com.poweranger.hai_duo.user.application.processor;

import com.poweranger.hai_duo.user.domain.entity.GameCharacter;
import com.poweranger.hai_duo.user.domain.entity.Level;
import com.poweranger.hai_duo.user.domain.entity.User;
import com.poweranger.hai_duo.user.domain.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterUpgradeProcessor {

    private final CharacterRepository characterRepository;

    public GameCharacter tryUpgrade(User user) {
        GameCharacter currentCharacter = user.getGameCharacter();
        Level currentLevel = user.getLevel();

        if (currentLevel.getLevelId() == currentCharacter.getUnlockLevel()) {
            return characterRepository.findById(currentCharacter.getCharacterId() + 1)
                    .orElse(currentCharacter);
        }

        return currentCharacter;
    }
}
