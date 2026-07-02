package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "tipo_documento")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class TipoDocumento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idTipoDoc;

    @Column(nullable = false)
    private String descripcion;

    @Column(length = 1)
    private Character estado = 'A';
}