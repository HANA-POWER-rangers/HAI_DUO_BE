package com.poweranger.hai_duo.user.api.controller;

import com.poweranger.hai_duo.user.api.dto.UserDto;
import com.poweranger.hai_duo.user.application.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/temp")
    public UserDto createTempUser() {
        return userService.createTempUser();
    }
}
