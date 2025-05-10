package com.poweranger.hai_duo.user.application.processor;

import com.poweranger.hai_duo.user.domain.entity.Level;
import com.poweranger.hai_duo.user.domain.entity.User;
import com.poweranger.hai_duo.user.domain.repository.LevelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class LevelUpProcessor {

    private final LevelRepository levelRepository;

    public int applyLevelUp(User user) {
        List<Level> levels = getAllLevels();
        Level newLevel = findLevelByExp(levels, user.getExp());
        int diff = getLevelDifference(user.getLevel(), newLevel);

        setUserLevel(user, newLevel);
        return diff;
    }

    private List<Level> getAllLevels() {
        return levelRepository.findAll();
    }

    private Level findLevelByExp(List<Level> levels, int exp) {
        Level result = levels.get(0);

        for (int i = 1; i < levels.size(); i++) {
            Level candidate = levels.get(i);
            result = getUpdatedLevel(result, candidate, exp);
        }

        return result;
    }

    private Level getUpdatedLevel(Level current, Level candidate, int exp) {
        if (isCandidateLevelValid(candidate, exp)) {
            return candidate;
        }
        return current;
    }

    private boolean isCandidateLevelValid(Level level, int exp) {
        return level.getRequiredExp() <= exp;
    }

    private int getLevelDifference(Level current, Level next) {
        return (int)(next.getLevelId() - current.getLevelId());
    }

    private void setUserLevel(User user, Level level) {
        user.setLevel(level);
    }

}
