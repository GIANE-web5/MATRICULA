package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "parametro")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Parametro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idParametro;

    @Column(nullable = false, unique = true)
    private String clave;

    @Column(nullable = false)
    private String valor;

    private String descripcion;

    @Column(length = 1)
    private Character estado = 'A';

    private String usuInsert;
    private LocalDateTime fecInsert;
    private String usuUpdate;
    private LocalDateTime fecUpdate;

    @PrePersist
    public void prePersist() { this.fecInsert = LocalDateTime.now(); }

    @PreUpdate
    public void preUpdate() { this.fecUpdate = LocalDateTime.now(); }
}