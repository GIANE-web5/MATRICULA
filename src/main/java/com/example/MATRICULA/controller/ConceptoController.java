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
        String usuario     = (String) body.get("usuarioLogin"); // ← Agregado

        return ResponseEntity.ok(service.guardar(c, idAnio, idTipoConc, usuario)); // ← Cambiado
    }

    @PutMapping("/{id}")
    public ResponseEntity<Concepto> actualizar(
            @PathVariable Integer id,
            @RequestBody Concepto concepto,
            @RequestParam String usuarioLogin) { // ← Pasado como parámetro en la URL
        return ResponseEntity.ok(service.actualizar(id, concepto, usuarioLogin)); // ← Cambiado
    }

    @PostMapping("/clonar")
    public ResponseEntity<List<Concepto>> clonar(
            @RequestBody Map<String, Object> body) { // ← Cambiado de Integer a Object para soportar el String del usuario
        Integer idAnioOrigen = (Integer) body.get("idAnioOrigen");
        Integer idAnioDestino = (Integer) body.get("idAnioDestino");
        String usuario = (String) body.get("usuarioLogin"); // ← Agregado

        return ResponseEntity.ok(service.clonarPorAnio(idAnioOrigen, idAnioDestino, usuario)); // ← Cambiado
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam String usuarioLogin) { // ← Agregado
        service.eliminar(id, usuarioLogin); // ← Cambiado
        return ResponseEntity.noContent().build();
    }
}