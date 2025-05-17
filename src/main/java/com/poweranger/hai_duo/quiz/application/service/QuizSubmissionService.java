package com.poweranger.hai_duo.quiz.application.service;

import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import com.poweranger.hai_duo.quiz.api.resolver.QuizAnswerResolver;
import com.poweranger.hai_duo.quiz.domain.entity.QuizType;
import com.poweranger.hai_duo.quiz.domain.repository.*;
import com.poweranger.hai_duo.user.domain.entity.mongodb.UserProgressLog;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuizSubmissionService {

    private final QuizLogRepository quizLogRepository;
    private final QuizAnswerResolver quizAnswerResolver;

    public boolean submitQuiz(SubmitQuizInputDto input) {
        String correctAnswer = getCorrectAnswer(input);
        boolean isCorrect = checkAnswerCorrectness(correctAnswer, input.selectedOption());

        saveUserProgressLog(input, correctAnswer, isCorrect);
        return isCorrect;
    }

    private String getCorrectAnswer(SubmitQuizInputDto input) {
        return quizAnswerResolver.resolveCorrectAnswer(input.stageId(), input.quizType());
    }

    private boolean checkAnswerCorrectness(String correctAnswer, String selectedOption) {
        return correctAnswer.equals(selectedOption);
    }

    private void saveUserProgressLog(SubmitQuizInputDto input, String correctAnswer, boolean isCorrect) {
        UserProgressLog log = new UserProgressLog(
                input.userId(),
                input.stageId(),
                QuizType.valueOf(input.quizType()),
                isCorrect,
                input.selectedOption(),
                correctAnswer,
                input.responseTime(),
                LocalDateTime.now()
        );
        quizLogRepository.save(log);
    }
}
