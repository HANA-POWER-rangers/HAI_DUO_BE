package com.poweranger.hai_duo.user.domain.entity.mongodb;

import com.poweranger.hai_duo.quiz.domain.entity.QuizType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collection = "user_quiz_logs")
public class UserQuizLog {

    @Id
    private String id;

    private String userId;
    private Long stageId;

    private QuizType quizType;

    private boolean isCorrect;
    private String selectedOption;
    private String answer;

    private LocalDateTime answeredAt;

    public UserQuizLog(
            String userId,
            Long stageId,
            QuizType quizType,
            boolean isCorrect,
            String selectedOption,
            String answer,
            LocalDateTime answeredAt
    ) {
        this.userId = userId;
        this.stageId = stageId;
        this.quizType = quizType;
        this.isCorrect = isCorrect;
        this.selectedOption = selectedOption;
        this.answer = answer;
        this.answeredAt = answeredAt;
    }
}
