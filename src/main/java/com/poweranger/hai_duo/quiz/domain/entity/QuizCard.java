package com.poweranger.hai_duo.quiz.domain.entity;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class QuizCard {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizCardId;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private String meaning;
    private String choices;
    private String correctWord;
}
