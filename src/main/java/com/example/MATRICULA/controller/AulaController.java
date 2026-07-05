package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Aula;
import com.example.MATRICULA.entity.Matricula;
import com.example.MATRICULA.repository.MatriculaRepository;
import com.example.MATRICULA.repository.ParametroRepository;
import com.example.MATRICULA.service.AulaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/aulas")
@RequiredArgsConstructor
public class AulaController {

    private final AulaService service;
    private final MatriculaRepository matriculaRepo;
    private final ParametroRepository parametroRepo;

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
        String  usuario  = (String)  body.get("usuarioLogin");
        return ResponseEntity.ok(
                service.guardar(idAnio, idNivel, grado, seccion, usuario));
    }
    @GetMapping("/{id}/alumnos")
    public ResponseEntity<List<Map<String, Object>>> alumnosPorAula(
            @PathVariable Integer id) {
        List<Matricula> matriculas = matriculaRepo.findByAulaIdAulaAndEstado(id, 'A');
        int capacidad = Integer.parseInt(parametroRepo
                .findByClave("MAX_ALUMNOS_AULA")
                .map(p -> p.getValor()).orElse("35"));

        List<Map<String, Object>> resultado = matriculas.stream().map(m -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("idAlumno",   m.getAlumno().getIdAlumno());
            row.put("nombre",     m.getAlumno().getApPaterno() + " " +
                    m.getAlumno().getApMaterno() + ", " +
                    m.getAlumno().getNombre());
            row.put("documento",  m.getAlumno().getTipoDocumento().getDescripcion()
                    + ": " + m.getAlumno().getNroDocumento());
            row.put("fecMatricula", m.getFecMatricula().toString());
            return row;
        }).toList();

        return ResponseEntity.ok(resultado);
    }

    @GetMapping("/cupos")
    public ResponseEntity<List<Map<String, Object>>> cuposPorAnio(
            @RequestParam Integer idAnio) {
        List<Aula> aulas = service.listarPorAnio(idAnio);
        int capacidad = Integer.parseInt(parametroRepo
                .findByClave("MAX_ALUMNOS_AULA")
                .map(p -> p.getValor()).orElse("35"));

        List<Map<String, Object>> resultado = aulas.stream().map(a -> {
            int ocupados = matriculaRepo
                    .findByAulaIdAulaAndEstado(a.getIdAula(), 'A').size();
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("idAula",    a.getIdAula());
            row.put("ocupados",  ocupados);
            row.put("capacidad", capacidad);
            row.put("disponible", ocupados < capacidad);
            return row;
        }).toList();

        return ResponseEntity.ok(resultado);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam String usuarioLogin) { // ← agrega esto
        service.eliminar(id, usuarioLogin); // ← cambia "admin"
        return ResponseEntity.noContent().build();
    }
}