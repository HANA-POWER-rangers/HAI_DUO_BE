package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.user.api.dto.CharacterDto;
import com.poweranger.hai_duo.user.api.dto.LevelUpResultDto;
import com.poweranger.hai_duo.user.api.dto.UserDto;
import com.poweranger.hai_duo.user.application.processor.CharacterUpgradeProcessor;
import com.poweranger.hai_duo.user.application.processor.LevelUpProcessor;
import com.poweranger.hai_duo.user.domain.entity.GameCharacter;
import com.poweranger.hai_duo.user.domain.entity.Level;
import com.poweranger.hai_duo.user.domain.entity.User;
import com.poweranger.hai_duo.user.domain.repository.CharacterRepository;
import com.poweranger.hai_duo.user.domain.repository.LevelRepository;
import com.poweranger.hai_duo.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final LevelRepository levelRepository;
    private final CharacterRepository characterRepository;
    private final LevelUpProcessor levelUpProcessor;
    private final CharacterUpgradeProcessor characterUpgradeProcessor;
    private final UserFactory userFactory;

    public UserDto createTempUser() {
        Level defaultLevel = levelRepository.findById(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LEVEL_NOT_FOUND));
        GameCharacter defaultGameCharacter = characterRepository.findById(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHARACTER_NOT_FOUND));

        User newUser = userFactory.createTempUser(defaultLevel, defaultGameCharacter);
        return UserDto.from(userRepository.save(newUser));
    }

    public UserDto getUserDtoById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::from)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    public LevelUpResultDto applyExpAndUpgradeStatus(Long userId, int amount) {
        User user = getUser(userId);
        user.addExp(amount);

        boolean leveledUp = levelUpProcessor.tryLevelUp(user);
        GameCharacter upgradedCharacter = characterUpgradeProcessor.tryUpgrade(user);

        return LevelUpResultDto.builder()
                .userId(user.getUserId())
                .exp(user.getExp())
                .levelId(user.getLevel().getLevelId())
                .leveledUp(leveledUp)
                .upgradedCharacter(CharacterDto.from(upgradedCharacter))
                .build();
    }

    private User getUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

}
