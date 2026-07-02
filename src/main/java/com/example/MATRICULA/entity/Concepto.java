package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "concepto")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Concepto {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idConcepto;

    @ManyToOne
    @JoinColumn(name = "id_anio", nullable = false)
    private AnioAcademico anioAcademico;

    @ManyToOne
    @JoinColumn(name = "id_tipo_conc", nullable = false)
    private TipoConcepto tipoConcepto;

    @Column(nullable = false)
    private String descripcion;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    @Column(nullable = false)
    private Integer orden;

    @Column(length = 1)
    private Character esObligatorio = 'S';

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