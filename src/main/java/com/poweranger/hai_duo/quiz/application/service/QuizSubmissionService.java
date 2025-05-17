package com.poweranger.hai_duo.quiz.application.service;

import com.poweranger.hai_duo.global.response.code.SuccessStatus;
import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import com.poweranger.hai_duo.quiz.domain.repository.QuizLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class QuizSubmissionService {

    private final QuizLogRepository quizLogRepository;

    public SuccessStatus submitQuiz(SubmitQuizInputDto input) {
        quizLogRepository.save(input);
        return SuccessStatus._OK;
    }
}
