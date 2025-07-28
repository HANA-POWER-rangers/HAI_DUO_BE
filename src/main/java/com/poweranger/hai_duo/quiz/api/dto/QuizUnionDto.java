package com.poweranger.hai_duo.quiz.api.dto;

public sealed interface QuizUnionDto permits QuizMeaningDto, QuizCardDto, QuizOXDto, QuizBlankDto {}
