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
            @RequestBody Map<String, Integer> body) {
        return ResponseEntity.ok(service.pagar(
                body.get("idAlumno"),
                body.get("idAnio"),
                body.get("idCuota"),
                "admin"));
    }
}