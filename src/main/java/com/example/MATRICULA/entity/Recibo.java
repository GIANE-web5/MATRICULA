package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "recibo")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Recibo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRecibo;

    @OneToOne
    @JoinColumn(name = "id_cuota", nullable = false)
    private Cuota cuota;

    @Column(nullable = false, unique = true)
    private String nroBoleta;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal montoPagado;

    private LocalDate fecPago;

    private String observacion;

    @Column(length = 1)
    private Character estado = 'A';

    private String usuInsert;
    private LocalDateTime fecInsert;

    @PrePersist
    public void prePersist() {
        this.fecInsert = LocalDateTime.now();
        if (this.fecPago == null) this.fecPago = LocalDate.now();
    }
}