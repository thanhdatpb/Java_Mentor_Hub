package com.thanhdatpb.javamentorhub.entity;

import jakarta.persistence.*;
import lombok.*;

/**
 * Role entity for authorization.
 * Two default roles: ROLE_ADMIN, ROLE_USER
 */
@Entity
@Table(name = "roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 50)
    private String name;
}
