package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.RolFuncionalidad;
import com.example.MATRICULA.service.RolFuncionalidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/permisos")
@RequiredArgsConstructor
public class RolFuncionalidadController {

    private final RolFuncionalidadService service;

    @GetMapping("/rol/{idRol}")
    public ResponseEntity<?> listarPorRol(@PathVariable Integer idRol) {
        try {
            return ResponseEntity.ok(service.listarPorRol(idRol));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @PostMapping("/aplicar/{idRol}")
    public ResponseEntity<?> aplicar(
            @PathVariable Integer idRol,
            @RequestBody List<Map<String, Object>> permisos) {
        try {
            System.out.println("=== APLICAR PERMISOS ===");
            System.out.println("idRol: " + idRol);
            System.out.println("permisos recibidos: " + permisos.size());
            permisos.forEach(p -> System.out.println("  >> " + p));

            service.aplicarPermisos(idRol, permisos, "admin");

            return ResponseEntity.ok(
                    Map.of("message", "Permisos aplicados correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}