package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Cuota;
import com.example.MATRICULA.entity.Recibo;
import com.example.MATRICULA.service.PagoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/pagos")
@RequiredArgsConstructor
public class PagoController {

    private final PagoService service;

    @GetMapping("/deudas")
    public List<Cuota> listarDeudas(
            @RequestParam Integer idAlumno,
            @RequestParam Integer idAnio) {
        return service.listarDeudas(idAlumno, idAnio);
    }

    @PostMapping("/pagar")
    public ResponseEntity<Recibo> pagar(
            @RequestBody Map<String, Object> body) { // ← Cambiado a Object
        Integer idAlumno = (Integer) body.get("idAlumno");
        Integer idAnio   = (Integer) body.get("idAnio");
        Integer idCuota  = (Integer) body.get("idCuota");
        String usuario   = (String) body.get("usuarioLogin"); // ← Agregado

        return ResponseEntity.ok(service.pagar(
                idAlumno,
                idAnio,
                idCuota,
                usuario)); // ← Cambiado "admin" por la variable
    }
}