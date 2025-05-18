package com.poweranger.hai_duo.quiz.domain.entity;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class QuizMeaning {

    @Id
    private Long stageId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private String word;
    private String meaning;
    private String exampleSentence;
}
