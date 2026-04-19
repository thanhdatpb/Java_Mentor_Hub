package com.thanhdatpb.javamentorhub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Answer entity - possible answers for a question.
 * For MULTIPLE_CHOICE: multiple answers, one or more marked is_correct.
 * For OPEN_ENDED/CODING: one sample answer with explanation.
 */
@Entity
@Table(name = "answers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Answer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id", nullable = false)
    private Question question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "is_correct")
    @Builder.Default
    private Boolean isCorrect = false;

    @Column(columnDefinition = "TEXT")
    private String explanation;
}
