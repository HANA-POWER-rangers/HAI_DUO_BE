package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.CustomGraphQLException;
import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.progress.api.dto.LevelUpResultDto;
import com.poweranger.hai_duo.user.api.dto.UserDto;
import com.poweranger.hai_duo.progress.api.factory.LevelUpResultFactory;
import com.poweranger.hai_duo.user.api.factory.UserFactory;
import com.poweranger.hai_duo.progress.application.processor.CharacterUpgradeProcessor;
import com.poweranger.hai_duo.progress.application.processor.LevelUpProcessor;
import com.poweranger.hai_duo.progress.domain.entity.GameCharacter;
import com.poweranger.hai_duo.progress.domain.entity.Level;
import com.poweranger.hai_duo.user.domain.entity.mysql.User;
import com.poweranger.hai_duo.progress.domain.repository.CharacterRepository;
import com.poweranger.hai_duo.progress.domain.repository.LevelRepository;
import com.poweranger.hai_duo.user.domain.repository.UserRepository;
import graphql.ErrorType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
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
        newUser.setExp(100);
        return UserDto.from(userRepository.save(newUser));
    }

    public UserDto getUserDtoById(Long id) {
        return userRepository.findByIdWithLevelAndCharacter(id)
                .map(UserDto::from)
                .orElseThrow(() ->  new CustomGraphQLException(
                ErrorStatus.USER_NOT_FOUND,
                ErrorType.DataFetchingException,
                "UserService.getUserDtoById(): 사용자 ID " + id + "는 존재하지 않음"
        ));
    }

    public LevelUpResultDto applyExpAndUpgradeStatus(Long userId, int amount) {
        User user = loadUser(userId);
        increaseExp(user, amount);

        int levelDiff = levelUpProcessor.applyLevelUp(user);
        boolean leveledUp = isLeveledUp(levelDiff);

        GameCharacter upgradedCharacter = characterUpgradeProcessor.processCharacterUpgrade(userId, user.getLevel());
        user.setGameCharacter(upgradedCharacter);

        userRepository.save(user);
        return LevelUpResultFactory.from(user, leveledUp, upgradedCharacter);
    }

    private User loadUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }

    private void increaseExp(User user, int amount) {
        user.addExp(amount);
    }

    private boolean isLeveledUp(int diff) {
        return diff > 0;
    }
}
