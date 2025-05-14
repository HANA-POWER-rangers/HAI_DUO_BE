package com.poweranger.hai_duo.user.api.resolver;

import com.poweranger.hai_duo.progress.api.dto.LevelUpResultDto;
import com.poweranger.hai_duo.user.application.service.UserService;
import com.poweranger.hai_duo.user.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserMutationResolver {

    private final UserService userService;

    @MutationMapping
    public UserDto createTempUser() {
        return userService.createTempUser();
    }

    @MutationMapping
    public LevelUpResultDto applyExpAndUpgradeStatus(@Argument Long userId, @Argument int amount) {
        return userService.applyExpAndUpgradeStatus(userId, amount);
    }

}
