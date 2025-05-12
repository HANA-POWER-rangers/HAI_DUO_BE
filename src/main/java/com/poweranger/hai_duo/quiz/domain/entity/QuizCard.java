package com.poweranger.hai_duo.quiz.domain.entity;

import com.poweranger.hai_duo.learning.domain.entity.Stage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class QuizCard {

    @Id
    private Long stageId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private String meaning;
    private String choices; // json string 형식
    private String correctWord;

}
