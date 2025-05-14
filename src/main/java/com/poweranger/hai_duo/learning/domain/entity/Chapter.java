package com.poweranger.hai_duo.learning.domain.entity;

import com.poweranger.hai_duo.user.domain.entity.mysql.GameCharacter;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class Chapter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long chapterId;

    @OneToOne
    @JoinColumn(name = "character_id")
    private GameCharacter character;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt = LocalDateTime.now();

}
