package com.poweranger.hai_duo.quiz.application.service;

import com.poweranger.hai_duo.global.exception.GeneralException;
import com.poweranger.hai_duo.global.response.code.ErrorStatus;
import com.poweranger.hai_duo.quiz.api.dto.SubmitQuizInputDto;
import com.poweranger.hai_duo.quiz.api.resolver.QuizAnswerResolver;
import com.poweranger.hai_duo.quiz.domain.entity.QuizType;
import com.poweranger.hai_duo.quiz.domain.repository.*;
import com.poweranger.hai_duo.user.domain.entity.mongodb.UserProgressLog;
import com.poweranger.hai_duo.user.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class QuizSubmissionService {

    private final QuizLogRepository quizLogRepository;
    private final QuizAnswerResolver quizAnswerResolver;
    private final UserRepository userRepository;

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
        Long levelId = getLevelIdByUser(input.userId());

        UserProgressLog log = new UserProgressLog(
                input.userId(),
                levelId,
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

    private Long getLevelIdByUser(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new GeneralException(ErrorStatus.USER_NOT_FOUND))
                .getLevel()
                .getLevelId();
    }
}
