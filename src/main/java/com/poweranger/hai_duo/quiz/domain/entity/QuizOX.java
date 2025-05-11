package com.poweranger.hai_duo.quiz.domain.entity;

import com.poweranger.hai_duo.course.domain.entity.Stage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class QuizOX {

    @Id
    private Long stageId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private String word;
    private String meaning;
    private boolean isCorrect;

}
