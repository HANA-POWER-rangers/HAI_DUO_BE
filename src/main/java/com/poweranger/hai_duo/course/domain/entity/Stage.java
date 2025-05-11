package com.poweranger.hai_duo.course.domain.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long stageId;

    private String stageName;

    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    private int stageNumber; // 챕터 안에서 속하는 스테이지 순서 번호

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
