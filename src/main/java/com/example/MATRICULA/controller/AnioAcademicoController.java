package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.AnioAcademico;
import com.example.MATRICULA.service.AnioAcademicoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/anios")
@CrossOrigin(origins = "*")
@RequiredArgsConstructor
public class AnioAcademicoController {

    private final AnioAcademicoService service;

    @GetMapping
    public List<AnioAcademico> listar() {
        return service.listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnioAcademico> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<AnioAcademico> guardar(
            @RequestBody AnioAcademico anio,
            @RequestParam String usuarioLogin) { // ← Agregado como parámetro en la URL
        return ResponseEntity.ok(service.guardar(anio, usuarioLogin)); // ← Cambiado
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnioAcademico> actualizar(
            @PathVariable Integer id,
            @RequestBody AnioAcademico anio,
            @RequestParam String usuarioLogin) { // ← Agregado como parámetro en la URL
        return ResponseEntity.ok(service.actualizar(id, anio, usuarioLogin)); // ← Cambiado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam String usuarioLogin) { // ← Agregado como parámetro en la URL
        service.eliminar(id, usuarioLogin); // ← Cambiado
        return ResponseEntity.noContent().build();
    }
}