package com.poweranger.hai_duo.quiz.domain.entity;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class QuizMeaning {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizMeaningId;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private String word;
    private String meaning;
    private String exampleSentence;
}
