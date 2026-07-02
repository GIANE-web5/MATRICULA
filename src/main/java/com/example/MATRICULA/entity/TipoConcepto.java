package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_concepto")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoConcepto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoConc;

    @Column(nullable = false)
    private String descripcion;

    @Column(length = 1)
    private Character estado = 'A';
}