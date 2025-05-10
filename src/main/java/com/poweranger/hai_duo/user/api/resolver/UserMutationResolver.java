package com.poweranger.hai_duo.user.api.resolver;

import com.poweranger.hai_duo.user.application.service.UserService;
import com.poweranger.hai_duo.user.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
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

}
