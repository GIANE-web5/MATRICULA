package com.example.MATRICULA.config;

import com.example.MATRICULA.entity.*;
import com.example.MATRICULA.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final RolRepository rolRepo;
    private final UsuarioRepository usuarioRepo;
    private final NivelRepository nivelRepo;
    private final TipoDocumentoRepository tipoDocRepo;
    private final ParametroRepository parametroRepo;
    private final FuncionalidadRepository funcionalidadRepo;
    private final RolFuncionalidadRepository rolFuncRepo;

    @Override
    public void run(String... args) {

        if (rolRepo.count() == 0) {
            rolRepo.save(Rol.builder().nombre("SUPERUSUARIO").estado('A').build());
            rolRepo.save(Rol.builder().nombre("SECRETARIA").estado('A').build());
            rolRepo.save(Rol.builder().nombre("ADMINISTRADOR").estado('A').build());
        }

        if (usuarioRepo.count() == 0) {
            Rol su = rolRepo.findByNombre("SUPERUSUARIO").orElseThrow();
            usuarioRepo.save(Usuario.builder()
                    .username("admin")
                    .passwordHash("admin123")
                    .nombreCompleto("Administrador del Sistema")
                    .rol(su)
                    .estado('A')
                    .usuInsert("SYSTEM")
                    .build());
        }

        if (nivelRepo.count() == 0) {
            nivelRepo.save(Nivel.builder().nombre("INICIAL").estado('A').usuInsert("SYSTEM").build());
            nivelRepo.save(Nivel.builder().nombre("PRIMARIA").estado('A').usuInsert("SYSTEM").build());
            nivelRepo.save(Nivel.builder().nombre("SECUNDARIA").estado('A').usuInsert("SYSTEM").build());
        }

        if (tipoDocRepo.count() == 0) {
            tipoDocRepo.save(TipoDocumento.builder().descripcion("DNI").estado('A').build());
            tipoDocRepo.save(TipoDocumento.builder().descripcion("PASAPORTE").estado('A').build());
            tipoDocRepo.save(TipoDocumento.builder().descripcion("CARNET DE EXTRANJERIA").estado('A').build());
        }

        if (parametroRepo.count() == 0) {
            parametroRepo.save(Parametro.builder()
                    .clave("MAX_ALUMNOS_AULA")
                    .valor("35")
                    .descripcion("Máximo de alumnos por aula")
                    .estado('A')
                    .usuInsert("SYSTEM")
                    .build());
        }

        // Funcionalidades
        if (funcionalidadRepo.count() == 0) {

            // ── Padres ──
            Funcionalidad fMantenimiento = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Mantenimiento")
                            .icono("📁")
                            .orden(1)
                            .estado('A')
                            .build());

            Funcionalidad fOperaciones = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Operaciones")
                            .icono("📁")
                            .orden(2)
                            .estado('A')
                            .build());

            Funcionalidad fSeguridad = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Seguridad")
                            .icono("📁")
                            .orden(3)
                            .estado('A')
                            .build());

            // ── Hijos Mantenimiento ──
            Funcionalidad fAnios = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Años Académicos")
                            .url("anios.html")
                            .icono("📅")
                            .padre(fMantenimiento)
                            .orden(1)
                            .estado('A')
                            .build());

            Funcionalidad fAlumnos = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Alumnos")
                            .url("alumnos.html")
                            .icono("👤")
                            .padre(fMantenimiento)
                            .orden(2)
                            .estado('A')
                            .build());

            Funcionalidad fAulas = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Aulas")
                            .url("aulas.html")
                            .icono("🏫")
                            .padre(fMantenimiento)
                            .orden(3)
                            .estado('A')
                            .build());

            Funcionalidad fConceptos = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Conceptos")
                            .url("conceptos.html")
                            .icono("📋")
                            .padre(fMantenimiento)
                            .orden(4)
                            .estado('A')
                            .build());

            // ── Hijos Operaciones ──
            Funcionalidad fMatricula = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Matrícula")
                            .url("matricula.html")
                            .icono("📝")
                            .padre(fOperaciones)
                            .orden(1)
                            .estado('A')
                            .build());

            Funcionalidad fPagos = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Pagos")
                            .url("pagos.html")
                            .icono("💰")
                            .padre(fOperaciones)
                            .orden(2)
                            .estado('A')
                            .build());

            // ── Hijos Seguridad ──
            Funcionalidad fUsuarios = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Usuarios")
                            .url("usuarios.html")
                            .icono("👥")
                            .padre(fSeguridad)
                            .orden(1)
                            .estado('A')
                            .build());

            Funcionalidad fPermisos = funcionalidadRepo.save(
                    Funcionalidad.builder()
                            .nombre("Permisos")
                            .url("permisos.html")
                            .icono("🔐")
                            .padre(fSeguridad)
                            .orden(2)
                            .estado('A')
                            .build());

            // ── Permisos del SUPERUSUARIO ──
            Rol su = rolRepo.findByNombre("SUPERUSUARIO").orElseThrow();

            List<Funcionalidad> todas = funcionalidadRepo.findAll();

            for (Funcionalidad f : todas) {
                if (f.getUrl() == null) continue;

                rolFuncRepo.save(
                        RolFuncionalidad.builder()
                                .rol(su)
                                .funcionalidad(f)
                                .puedeVer('S')
                                .puedeInsertar('S')
                                .puedeEditar('S')
                                .puedeEliminar('S')
                                .usuInsert("SYSTEM")
                                .build()
                );
            }

            // ── Permisos SECRETARIA ──
            Rol sec = rolRepo.findByNombre("SECRETARIA").orElseThrow();

            List<Funcionalidad> sinPermisos = List.of(
                    fAnios,
                    fAlumnos,
                    fAulas,
                    fConceptos,
                    fMatricula,
                    fPagos,
                    fUsuarios
            );

            for (Funcionalidad f : sinPermisos) {
                rolFuncRepo.save(
                        RolFuncionalidad.builder()
                                .rol(sec)
                                .funcionalidad(f)
                                .puedeVer('S')
                                .puedeInsertar('S')
                                .puedeEditar('S')
                                .puedeEliminar('S')
                                .usuInsert("SYSTEM")
                                .build()
                );
            }

            // ── Permisos ADMINISTRADOR ──
            Rol adm = rolRepo.findByNombre("ADMINISTRADOR").orElseThrow();

            for (Funcionalidad f : todas) {
                if (f.getUrl() == null) continue;

                rolFuncRepo.save(
                        RolFuncionalidad.builder()
                                .rol(adm)
                                .funcionalidad(f)
                                .puedeVer('S')
                                .puedeInsertar('N')
                                .puedeEditar('N')
                                .puedeEliminar('N')
                                .usuInsert("SYSTEM")
                                .build()
                );
            }
        }

    }
}