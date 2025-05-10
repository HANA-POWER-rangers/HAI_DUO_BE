package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.user.api.dto.UserDto;
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
    private final UserFactory userFactory;

    public UserDto createTempUser() {
        Level defaultLevel = levelRepository.findById(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.LEVEL_NOT_FOUND));
        GameCharacter defaultGameCharacter = characterRepository.findById(1L)
                .orElseThrow(() -> new GeneralException(ErrorStatus.CHARACTER_NOT_FOUND));

        User newUser = userFactory.createTempUser(defaultLevel, defaultGameCharacter);
        return UserDto.from(userRepository.save(newUser));
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::from)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }
}
