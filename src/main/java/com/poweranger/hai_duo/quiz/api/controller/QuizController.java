package com.poweranger.hai_duo.quiz.api.controller;

import com.poweranger.hai_duo.global.response.ApiResponse;
import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizResultDto;
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
    public ApiResponse<SubmitQuizResultDto> submitQuiz(@RequestBody SubmitQuizInputDto input) {
        boolean isCorrect = quizSubmissionService.isCorrect(input.stageId(), input.quizType(), input.selectedOption());
        quizSubmissionService.submitQuiz(input);
        return ApiResponse.onSuccess(new SubmitQuizResultDto(isCorrect));
    }
}
