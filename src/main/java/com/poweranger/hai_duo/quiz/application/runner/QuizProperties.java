package com.poweranger.hai_duo.quiz.application.runner;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "quiz")
public class QuizProperties {
    private String path;
}
