package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Matricula;
import com.example.MATRICULA.service.MatriculaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
public class MatriculaController {

    private final MatriculaService service;

    @GetMapping("/aula/{idAula}")
    public List<Matricula> listarPorAula(@PathVariable Integer idAula) {
        return service.listarPorAula(idAula);
    }

    @GetMapping("/alumno/{idAlumno}/anio/{idAnio}")
    public ResponseEntity<Matricula> obtener(
            @PathVariable Integer idAlumno,
            @PathVariable Integer idAnio) {
        return ResponseEntity.ok(service.obtenerPorAlumnoYAnio(idAlumno, idAnio));
    }

    @PostMapping
    public ResponseEntity<Matricula> matricular(@RequestBody Map<String, Object> body) { // ← Cambiado a Object
        Integer idAlumno = (Integer) body.get("idAlumno");
        Integer idAula   = (Integer) body.get("idAula");
        Integer idAnio   = (Integer) body.get("idAnio");
        String usuario   = (String) body.get("usuarioLogin"); // ← Agregado

        return ResponseEntity.ok(service.matricular(
                idAlumno,
                idAula,
                idAnio,
                usuario)); // ← Cambiado "admin" por la variable usuario
    }
}