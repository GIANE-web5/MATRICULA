package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "matricula")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Matricula {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idMatricula;

    @ManyToOne
    @JoinColumn(name = "id_anio", nullable = false)
    private AnioAcademico anioAcademico;

    @ManyToOne
    @JoinColumn(name = "id_aula", nullable = false)
    private Aula aula;

    @ManyToOne
    @JoinColumn(name = "id_alumno", nullable = false)
    private Alumno alumno;

    private LocalDate fecMatricula;

    @Column(length = 1)
    private Character estado = 'A';

    private String usuInsert;
    private LocalDateTime fecInsert;
    private String usuUpdate;
    private LocalDateTime fecUpdate;

    @PrePersist
    public void prePersist() {
        this.fecInsert = LocalDateTime.now();
        if (this.fecMatricula == null) this.fecMatricula = LocalDate.now();
    }

    @PreUpdate
    public void preUpdate() { this.fecUpdate = LocalDateTime.now(); }

}