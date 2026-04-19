package com.thanhdatpb.javamentorhub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Topic entity - categorizes questions, assignments, and books.
 * Examples: Java Core, OOP, Spring Framework, Design Patterns...
 */
@Entity
@Table(name = "topics")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Topic {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(length = 10)
    private String icon;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;
}
