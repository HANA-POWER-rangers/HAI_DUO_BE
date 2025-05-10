package com.poweranger.hai_duo.user.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.user.api.dto.UserDto;
import com.poweranger.hai_duo.user.domain.repository.UserRepository;
import com.poweranger.hai_duo.user.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserDto createTempUser() {
        User user = User.builder()
                .tempUserToken(UUID.randomUUID().toString())
                .exp(0)
                .goldAmount(0)
                .levelId(1L)
                .characterId(1L)
                .createdAt(LocalDateTime.now())
                .lastAccessedAt(LocalDateTime.now())
                .build();

        return UserDto.from(userRepository.save(user));
    }

    public UserDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserDto::from)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND));
    }
}
