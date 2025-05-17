package com.poweranger.hai_duo.quiz.api.controller;

import com.poweranger.hai_duo.global.response.code.SuccessStatus;
import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import com.poweranger.hai_duo.quiz.application.service.QuizSubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/quiz")
@RequiredArgsConstructor
public class QuizController {

    private final QuizSubmissionService quizSubmissionService;

    @PostMapping("/submit")
    public SuccessStatus submitQuiz(@RequestBody SubmitQuizInputDto input) {
        return quizSubmissionService.submitQuiz(input);
    }
}
