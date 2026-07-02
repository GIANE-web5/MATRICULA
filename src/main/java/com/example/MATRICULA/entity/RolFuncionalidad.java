package com.example.MATRICULA.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "rol_funcionalidad")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class RolFuncionalidad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idRolFuncionalidad;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private Rol rol;

    @ManyToOne
    @JoinColumn(name = "id_funcionalidad", nullable = false)
    private Funcionalidad funcionalidad;

    // Permisos
    @Column(length = 1)
    private Character puedeVer     = 'N';
    @Column(length = 1)
    private Character puedeInsertar = 'N';
    @Column(length = 1)
    private Character puedeEditar  = 'N';
    @Column(length = 1)
    private Character puedeEliminar = 'N';

    private String usuInsert;
    private LocalDateTime fecInsert;
    private String usuUpdate;
    private LocalDateTime fecUpdate;

    @PrePersist
    public void prePersist() { this.fecInsert = LocalDateTime.now(); }
    @PreUpdate
    public void preUpdate()  { this.fecUpdate = LocalDateTime.now(); }
}