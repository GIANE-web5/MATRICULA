package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Nivel;
import com.example.MATRICULA.service.NivelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/niveles")
@RequiredArgsConstructor
public class NivelController {

    private final NivelService service;

    @GetMapping
    public List<Nivel> listar() {
        return service.listarActivos();
    }

    @PostMapping
    public ResponseEntity<Nivel> guardar(
            @RequestBody Nivel nivel) {
        return ResponseEntity.ok(service.guardar(nivel, "admin"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Nivel> actualizar(
            @PathVariable Integer id,
            @RequestBody Nivel nivel) {
        return ResponseEntity.ok(service.actualizar(id, nivel, "admin"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {
        service.eliminar(id, "admin");
        return ResponseEntity.noContent().build();
    }
}