package com.poweranger.hai_duo.progress.application.processor;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.progress.domain.entity.GameCharacter;
import com.poweranger.hai_duo.progress.domain.entity.Level;
import com.poweranger.hai_duo.progress.domain.repository.CharacterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CharacterUpgradeProcessor {

    private final CharacterRepository characterRepository;

    public GameCharacter processCharacterUpgrade(Long userId, Level currentLevel) {
        GameCharacter currentCharacter = getCurrentCharacter(userId);
        boolean shouldUpgrade = shouldUpgradeCharacter(currentLevel, currentCharacter);

        if (shouldUpgrade) {
            return getNextCharacter(currentCharacter);
        }

        return currentCharacter;
    }

    private GameCharacter getCurrentCharacter(Long userId) {
        return characterRepository.findGameCharacterByUserId(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHARACTER_NOT_FOUND));
    }

    private boolean shouldUpgradeCharacter(Level level, GameCharacter character) {
        return level.getLevelId() >= character.getUnlockLevel();
    }

    private GameCharacter getNextCharacter(GameCharacter currentCharacter) {
        return characterRepository.findById(currentCharacter.getCharacterId() + 1)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHARACTER_NOT_FOUND));
    }
}
