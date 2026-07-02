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
            @RequestBody AnioAcademico anio) {
        return ResponseEntity.ok(service.guardar(anio, "admin"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnioAcademico> actualizar(
            @PathVariable Integer id,
            @RequestBody AnioAcademico anio) {
        return ResponseEntity.ok(service.actualizar(id, anio, "admin"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {
        service.eliminar(id, "admin");
        return ResponseEntity.noContent().build();
    }
}