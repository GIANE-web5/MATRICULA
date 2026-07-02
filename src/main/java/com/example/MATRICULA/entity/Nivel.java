package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "nivel")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Nivel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idNivel;

    @Column(nullable = false)
    private String nombre;

    @Column(length = 1)
    private Character estado = 'A';

    // Campos de auditoría para Inserción
    private String usuInsert;
    private LocalDateTime fecInsert;

    // Campos de auditoría para Actualización (¡Estos faltaban!)
    private String usuUpdate;
    private LocalDateTime fecUpdate;

    @PrePersist
    public void prePersist() {
        this.fecInsert = LocalDateTime.now();
    }

    // Agrega esto para que la fecha de actualización se asigne sola
    @PreUpdate
    public void preUpdate() {
        this.fecUpdate = LocalDateTime.now();
    }
}