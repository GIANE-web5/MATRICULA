package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "aula")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Aula {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAula;

    @ManyToOne
    @JoinColumn(name = "id_anio", nullable = false)
    private AnioAcademico anioAcademico;

    @ManyToOne
    @JoinColumn(name = "id_nivel", nullable = false)
    private Nivel nivel;

    @Column(nullable = false)
    private Integer grado;

    @Column(nullable = false, length = 5)
    private String seccion;

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