package com.poweranger.hai_duo.user.api.resolver;

import com.poweranger.hai_duo.user.api.dto.UserDto;
import com.poweranger.hai_duo.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserQueryResolver {

    private final UserService userService;

    @QueryMapping
    public UserDto getUserById(@Argument Long userId) {
        return userService.getUserById(userId);
    }
}
