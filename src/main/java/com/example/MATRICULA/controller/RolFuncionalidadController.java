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
    public List<RolFuncionalidad> listarPorRol(@PathVariable Integer idRol) {
        return service.listarPorRol(idRol);
    }

    @PostMapping("/aplicar/{idRol}")
    public ResponseEntity<?> aplicar(
            @PathVariable Integer idRol,
            @RequestBody List<Map<String, Object>> permisos,
            @RequestParam String usuarioLogin) { // ← Agregado aquí como parámetro de URL
        try {
            service.aplicarPermisos(idRol, permisos, usuarioLogin); // ← Cambiado "admin" por la variable
            return ResponseEntity.ok(Map.of("message", "Permisos aplicados correctamente"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }
}