package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.TipoDocumento;
import com.example.MATRICULA.service.TipoDocumentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tipos-documento")
@CrossOrigin(origins = "*") // ¡Crucial para conectar con tu alumnos.html!
@RequiredArgsConstructor
public class TipoDocumentoController {

    private final TipoDocumentoService service;

    // GET: http://localhost:8080/api/tipos-documento
    @GetMapping
    public ResponseEntity<List<TipoDocumento>> listar() {
        return ResponseEntity.ok(service.listarActivos());
    }

    // GET: http://localhost:8080/api/tipos-documento/{id}
    @GetMapping("/{id}")
    public ResponseEntity<TipoDocumento> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    // POST: http://localhost:8080/api/tipos-documento
    @PostMapping
    public ResponseEntity<TipoDocumento> guardar(@RequestBody TipoDocumento tipoDocumento) {
        return ResponseEntity.ok(service.guardar(tipoDocumento));
    }

    // DELETE: http://localhost:8080/api/tipos-documento/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}