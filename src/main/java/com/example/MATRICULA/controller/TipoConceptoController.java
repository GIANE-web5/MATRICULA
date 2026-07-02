package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.TipoConcepto;
import com.example.MATRICULA.service.TipoConceptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-concepto")
@RequiredArgsConstructor
public class TipoConceptoController {

    private final TipoConceptoService service;

    @GetMapping
    public List<TipoConcepto> listar() {
        return service.listarActivos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoConcepto> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<TipoConcepto> guardar(@RequestBody TipoConcepto tipo) {
        return ResponseEntity.ok(service.guardar(tipo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}