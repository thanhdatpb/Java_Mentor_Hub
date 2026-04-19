package com.thanhdatpb.javamentorhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * PracticeSession entity - records each practice attempt by a user.
 * Stores user's answer, correctness, and score for each question attempted.
 */
@Entity
@Table(name = "practice_sessions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PracticeSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "user_answer", columnDefinition = "TEXT")
    private String userAnswer;

    @Column(name = "is_correct")
    private Boolean isCorrect;

    @Column
    @Builder.Default
    private Integer score = 0;

    @Column(name = "practiced_at", updatable = false)
    private LocalDateTime practicedAt;

    @PrePersist
    protected void onCreate() {
        practicedAt = LocalDateTime.now();
    }
}
