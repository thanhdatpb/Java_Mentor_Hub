package com.thanhdatpb.javamentorhub.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * Progress entity - aggregated learning progress per user per topic.
 * Unique constraint on (user_id, topic_id) ensures one progress record per combination.
 * Updated whenever user practices questions or completes assignments.
 */
@Entity
@Table(name = "progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "topic_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Progress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id", nullable = false)
    private Topic topic;

    @Column(name = "questions_answered")
    @Builder.Default
    private Integer questionsAnswered = 0;

    @Column(name = "questions_correct")
    @Builder.Default
    private Integer questionsCorrect = 0;

    @Column(name = "assignments_completed")
    @Builder.Default
    private Integer assignmentsCompleted = 0;

    @Column(name = "completion_percentage")
    @Builder.Default
    private Float completionPercentage = 0.0f;

    @Column(name = "last_activity")
    private LocalDateTime lastActivity;

    @PrePersist
    protected void onCreate() {
        lastActivity = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastActivity = LocalDateTime.now();
    }
}
