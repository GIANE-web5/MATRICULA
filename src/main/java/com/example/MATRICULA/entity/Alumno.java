package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "alumno")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Alumno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idAlumno;

    @ManyToOne
    @JoinColumn(name = "id_tipo_doc", nullable = false)
    private TipoDocumento tipoDocumento;

    @Column(nullable = false, length = 20)
    private String nroDocumento;

    @Column(nullable = false)
    private String apPaterno;

    @Column(nullable = false)
    private String apMaterno;

    @Column(nullable = false)
    private String nombre;

    private LocalDate fecNacimiento;

    @Column(length = 1)
    private Character sexo;

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