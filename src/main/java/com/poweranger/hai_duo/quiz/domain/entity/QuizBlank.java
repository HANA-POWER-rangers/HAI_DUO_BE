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
public class QuizBlank {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quizBlankId;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String sentenceWithBlank;

    private String correctWord;
}
