package com.thanhdatpb.javamentorhub.entity;

import com.thanhdatpb.javamentorhub.enums.AssignmentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

/**
 * AssignmentProgress entity - tracks each user's progress on an assignment.
 * Unique constraint on (user_id, assignment_id) ensures one record per user per assignment.
 */
@Entity
@Table(name = "assignment_progress",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "assignment_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssignmentProgress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id", nullable = false)
    private Assignment assignment;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @Builder.Default
    private AssignmentStatus status = AssignmentStatus.NOT_STARTED;

    @Column(columnDefinition = "TEXT")
    private String submission;

    @Column(name = "started_at")
    private LocalDateTime startedAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;
}
