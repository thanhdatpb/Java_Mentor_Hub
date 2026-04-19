package com.thanhdatpb.javamentorhub.entity;

import com.thanhdatpb.javamentorhub.enums.BookLevel;
import jakarta.persistence.*;
import lombok.*;

/**
 * Book entity - recommended books for Java learning.
 * Data seeded from ThanhDat's Notion collection.
 */
@Entity
@Table(name = "books")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    private Topic topic;

    @Column(nullable = false, length = 500)
    private String name;

    @Column(length = 200)
    private String author;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "cover_url", length = 500)
    private String coverUrl;

    @Column(name = "download_link", length = 500)
    private String downloadLink;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private BookLevel level;

    @Column(name = "display_order")
    @Builder.Default
    private Integer displayOrder = 0;
}
