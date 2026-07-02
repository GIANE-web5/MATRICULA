package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Concepto;
import com.example.MATRICULA.service.ConceptoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/conceptos")
@RequiredArgsConstructor
public class ConceptoController {

    private final ConceptoService service;

    @GetMapping
    public List<Concepto> listar(@RequestParam Integer idAnio) {
        return service.listarPorAnio(idAnio);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Concepto> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<Concepto> guardar(
            @RequestBody Map<String, Object> body) {
        Concepto c = new Concepto();
        c.setDescripcion((String) body.get("descripcion"));
        c.setMonto(new java.math.BigDecimal(body.get("monto").toString()));
        c.setOrden((Integer) body.get("orden"));
        c.setEsObligatorio(((String) body.get("esObligatorio")).charAt(0));
        Integer idAnio     = (Integer) body.get("idAnio");
        Integer idTipoConc = (Integer) body.get("idTipoConc");
        return ResponseEntity.ok(service.guardar(c, idAnio, idTipoConc, "admin"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Concepto> actualizar(
            @PathVariable Integer id,
            @RequestBody Concepto concepto) {
        return ResponseEntity.ok(service.actualizar(id, concepto, "admin"));
    }

    @PostMapping("/clonar")
    public ResponseEntity<List<Concepto>> clonar(
            @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(service.clonarPorAnio(
                body.get("idAnioOrigen"), body.get("idAnioDestino"), "admin"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {
        service.eliminar(id, "admin");
        return ResponseEntity.noContent().build();
    }

}