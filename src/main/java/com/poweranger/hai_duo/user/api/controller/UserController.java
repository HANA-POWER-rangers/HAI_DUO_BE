package com.poweranger.hai_duo.user.api.controller;

import com.poweranger.hai_duo.global.response.ApiResponse;
import com.poweranger.hai_duo.user.api.dto.UserDto;
import com.poweranger.hai_duo.user.api.dto.UserStreakDto;
import com.poweranger.hai_duo.user.application.service.UserService;
import com.poweranger.hai_duo.user.application.service.UserStreakService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserStreakService userStreakService;

    @PostMapping("/temp")
    public UserDto createTempUser() {
        return userService.createTempUser();
    }

    @GetMapping("/{userId}/streak")
    public ApiResponse<UserStreakDto> getUserStreak(@PathVariable Long userId) {
        return userStreakService.getUserStreak(userId);
    }
}
