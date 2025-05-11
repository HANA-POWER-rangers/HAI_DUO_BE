package com.poweranger.hai_duo.quiz.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class QuizCard {

    @Id
    private Long quizId;

    @OneToOne
    @MapsId
    @JoinColumn(name = "quiz_id")
    private Quiz quiz;

    private String meaning;
    private String choices; // jsoin string 형식
    private String correctWord;
}
