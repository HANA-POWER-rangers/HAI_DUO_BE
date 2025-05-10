package com.poweranger.hai_duo.user.domain.entity;

import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Getter
public class Level {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long levelId;

    @Column(nullable = false)
    private int requiredExp;
}
