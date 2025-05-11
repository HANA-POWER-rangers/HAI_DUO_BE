package com.poweranger.hai_duo.learning.domain.entity;

import com.poweranger.hai_duo.user.domain.entity.Level;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chapterId;

    @ManyToOne
    @JoinColumn(name = "level_id")
    private Level level;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
