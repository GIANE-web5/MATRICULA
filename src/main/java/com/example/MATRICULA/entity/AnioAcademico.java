package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "anio_academico")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class AnioAcademico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAnio;

    @Column(nullable = false, unique = true)
    private Integer anio;

    private String descripcion;

    @Column(length = 1, nullable = false)
    private Character estado = 'A';

    private String usuInsert;
    private LocalDateTime fecInsert;
    private String usuUpdate;
    private LocalDateTime fecUpdate;

    @PrePersist
    public void prePersist() {
        this.fecInsert = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.fecUpdate = LocalDateTime.now();
    }
}