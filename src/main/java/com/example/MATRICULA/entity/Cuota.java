package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "cuota")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Cuota {

    @Version
    private Long version;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idCuota;

    @ManyToOne
    @JoinColumn(name = "id_matricula", nullable = false)
    private Matricula matricula;

    @ManyToOne
    @JoinColumn(name = "id_concepto", nullable = false)
    private Concepto concepto;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal monto;

    // P=Pendiente, C=Cancelado, A=Anulado
    @Column(length = 1)
    private Character estadoPago = 'P';

    private String usuInsert;
    private LocalDateTime fecInsert;
    private String usuUpdate;
    private LocalDateTime fecUpdate;

    @PrePersist
    public void prePersist() { this.fecInsert = LocalDateTime.now(); }

    @PreUpdate
    public void preUpdate() { this.fecUpdate = LocalDateTime.now(); }
}