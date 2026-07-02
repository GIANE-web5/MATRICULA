package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Aula;
import com.example.MATRICULA.service.AulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/aulas")
@RequiredArgsConstructor
public class AulaController {

    private final AulaService service;

    @GetMapping
    public List<Aula> listar(@RequestParam Integer idAnio) {
        return service.listarPorAnio(idAnio);
    }

    @PostMapping
    public ResponseEntity<Aula> guardar(
            @RequestBody Map<String, Object> body) {
        Integer idAnio   = (Integer) body.get("idAnio");
        Integer idNivel  = (Integer) body.get("idNivel");
        Integer grado    = (Integer) body.get("grado");
        String  seccion  = (String)  body.get("seccion");
        return ResponseEntity.ok(
                service.guardar(idAnio, idNivel, grado, seccion, "admin"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {
        service.eliminar(id, "admin");
        return ResponseEntity.noContent().build();
    }
}