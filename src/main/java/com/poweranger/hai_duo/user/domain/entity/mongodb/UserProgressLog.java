package com.poweranger.hai_duo.user.domain.entity.mongodb;

import com.poweranger.hai_duo.quiz.domain.entity.QuizType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Document(collection = "user_progress_logs")
public class UserProgressLog {

    @Id
    private ObjectId id;

    private Long userId;
    private Long levelId;
    private Long stageId;

    private QuizType quizType;

    private boolean isCorrect;
    private String selectedOption;
    private String answer;

    private Float responseTime;

    @CreatedDate
    private LocalDateTime answeredAt;

    public UserProgressLog(
            Long userId,
            Long levelId,
            Long stageId,
            QuizType quizType,
            boolean isCorrect,
            String selectedOption,
            String answer,
            float responseTime,
            LocalDateTime answeredAt
    ) {
        this.userId = userId;
        this.levelId = levelId;
        this.stageId = stageId;
        this.quizType = quizType;
        this.isCorrect = isCorrect;
        this.selectedOption = selectedOption;
        this.answer = answer;
        this.responseTime = responseTime;
        this.answeredAt = answeredAt;
    }
}
