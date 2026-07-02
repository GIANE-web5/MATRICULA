package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.*;
import com.example.MATRICULA.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/api/reportes")
@RequiredArgsConstructor
public class ReporteController {

    private final MatriculaRepository matriculaRepo;
    private final ReciboRepository    reciboRepo;
    private final CuotaRepository     cuotaRepo;
    private final AlumnoRepository    alumnoRepo;

    // Reporte 1: Alumnos matriculados por aula en un año
    @GetMapping("/matriculados/{idAnio}")
    public List<Map<String, Object>> matriculadosPorAnio(
            @PathVariable Integer idAnio) {
        List<Matricula> matriculas = matriculaRepo
                .findByAnioAcademicoIdAnioAndEstado(idAnio, 'A');

        return matriculas.stream().map(m -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("alumno", m.getAlumno().getApPaterno() + " " +
                    m.getAlumno().getApMaterno() + ", " +
                    m.getAlumno().getNombre());
            row.put("documento", m.getAlumno().getTipoDocumento().getDescripcion()
                    + ": " + m.getAlumno().getNroDocumento());
            row.put("nivel",   m.getAula().getNivel().getNombre());
            row.put("grado",   m.getAula().getGrado() + "°");
            row.put("seccion", m.getAula().getSeccion());
            row.put("fecha",   m.getFecMatricula().toString());
            return row;
        }).toList();
    }

    // Reporte 2: Pagos realizados en un año
    @GetMapping("/pagos/{idAnio}")
    public List<Map<String, Object>> pagosPorAnio(
            @PathVariable Integer idAnio) {
        List<Recibo> recibos = reciboRepo
                .findByAnio(idAnio);

        return recibos.stream().map(r -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("boleta",   r.getNroBoleta());
            row.put("alumno",
                    r.getCuota().getMatricula().getAlumno().getApPaterno() + " " +
                            r.getCuota().getMatricula().getAlumno().getApMaterno() + ", " +
                            r.getCuota().getMatricula().getAlumno().getNombre());
            row.put("documento",
                    r.getCuota().getMatricula().getAlumno().getNroDocumento());
            row.put("concepto", r.getCuota().getConcepto().getDescripcion());
            row.put("monto",    r.getMontoPagado());
            row.put("fecha",    r.getFecPago().toString());
            return row;
        }).toList();
    }

    // Reporte 3: Deudas pendientes por año
    @GetMapping("/deudas/{idAnio}")
    public List<Map<String, Object>> deudasPorAnio(
            @PathVariable Integer idAnio) {
        List<Cuota> pendientes = cuotaRepo
                .findPendientesPorAnio(idAnio);

        return pendientes.stream().map(c -> {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("alumno",
                    c.getMatricula().getAlumno().getApPaterno() + " " +
                            c.getMatricula().getAlumno().getApMaterno() + ", " +
                            c.getMatricula().getAlumno().getNombre());
            row.put("documento",
                    c.getMatricula().getAlumno().getNroDocumento());
            row.put("concepto", c.getConcepto().getDescripcion());
            row.put("monto",    c.getMonto());
            row.put("orden",    c.getConcepto().getOrden());
            return row;
        }).toList();
    }
}