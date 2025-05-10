package com.poweranger.hai_duo.user.application.processor;

import com.poweranger.hai_duo.user.domain.entity.Level;
import com.poweranger.hai_duo.user.domain.entity.User;
import com.poweranger.hai_duo.user.domain.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class LevelUpProcessor {

    private final LevelRepository levelRepository;

    public boolean tryLevelUp(User user) {
        Level currentLevel = user.getLevel();
        Optional<Level> nextLevelOpt = currentLevel.findNextLevel(levelRepository);

        if (nextLevelOpt.isEmpty()) return false;

        Level nextLevel = nextLevelOpt.get();
        if (user.getExp() >= nextLevel.getRequiredExp()) {
            user.updateLevel(nextLevel);
            return true;
        }
        return false;
    }
}
