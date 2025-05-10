package com.poweranger.hai_duo.user.api.controller;

import com.poweranger.hai_duo.user.api.resolver.UserMutationResolver;
import com.poweranger.hai_duo.user.api.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserMutationResolver userMutationResolver;

    @PostMapping("/temp")
    public UserDto createTempUser() {
        return userMutationResolver.createTempUser();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        return userMutationResolver.getUserById(id);
    }
}
