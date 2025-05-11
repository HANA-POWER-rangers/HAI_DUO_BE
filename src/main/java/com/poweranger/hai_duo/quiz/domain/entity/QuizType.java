package com.poweranger.hai_duo.quiz.domain.entity;

public enum QuizType {

    MEAN(QuizMeaning.class),
    CARD(QuizCard.class),
    OX(QuizOX.class),
    BLANK(QuizBlank.class);

    private final Class<?> quizDetailClass;

    QuizType(Class<?> quizDetailClass) {
        this.quizDetailClass = quizDetailClass;
    }

    public Class<?> getDetailClass() {
        return this.quizDetailClass;
    }
}
