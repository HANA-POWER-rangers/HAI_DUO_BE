package com.poweranger.hai_duo.user.api.resolver;

import com.poweranger.hai_duo.user.application.service.UserService;
import com.poweranger.hai_duo.user.api.dto.UserDto;
import graphql.kickstart.tools.GraphQLMutationResolver;
import lombok.RequiredArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMutationResolver implements GraphQLMutationResolver {

    private final UserService userService;

    public UserDto getUserById(@Argument("userId") Long id) {
        return userService.getUserById(id);
    }
}
