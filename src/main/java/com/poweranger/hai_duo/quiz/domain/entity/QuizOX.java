package com.poweranger.hai_duo.quiz.domain.entity;

import com.poweranger.hai_duo.progress.domain.entity.Stage;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
@Table(name = "quiz_ox")
public class QuizOX {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizOXId;

    @OneToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    private String word;
    private String meaning;
    private boolean isCorrect;
}
