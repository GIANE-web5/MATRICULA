package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "funcionalidad")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Funcionalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idFuncionalidad;

    @Column(nullable = false)
    private String nombre;

    // URL del módulo, ej: /alumnos.html
    private String url;

    // Ícono para el Tree
    private String icono;

    // Si tiene padre, es un submenú
    @ManyToOne
    @JoinColumn(name = "id_padre")
    private Funcionalidad padre;

    // Orden visual en el árbol
    private Integer orden;

    @Column(length = 1)
    private Character estado = 'A';
}