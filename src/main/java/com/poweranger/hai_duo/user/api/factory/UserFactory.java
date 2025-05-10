package com.poweranger.hai_duo.user.api.factory;

import com.poweranger.hai_duo.user.domain.entity.GameCharacter;
import com.poweranger.hai_duo.user.domain.entity.Level;
import com.poweranger.hai_duo.user.domain.entity.User;
import org.springframework.stereotype.Component;
import java.time.LocalDateTime;
import java.util.UUID;

@Component
public class UserFactory {

    public User createTempUser(Level level, GameCharacter gameCharacter) {
        return User.builder()
                .tempUserToken(UUID.randomUUID().toString())
                .exp(0)
                .goldAmount(0)
                .level(level)
                .gameCharacter(gameCharacter)
                .createdAt(LocalDateTime.now())
                .lastAccessedAt(LocalDateTime.now())
                .build();
    }
}
