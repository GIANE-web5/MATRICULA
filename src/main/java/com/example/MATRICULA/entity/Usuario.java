package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "usuario")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idUsuario;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @Column(nullable = false)
    private String nombreCompleto;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

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