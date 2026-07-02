package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.*;
import com.example.MATRICULA.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository  matriculaRepo;
    private final CuotaRepository      cuotaRepo;
    private final AlumnoService        alumnoService;
    private final AulaService          aulaService;
    private final AnioAcademicoService anioService;
    private final ConceptoService      conceptoService;

    @Transactional(rollbackFor = Exception.class)
    public Matricula matricular(Integer idAlumno, Integer idAula,
                                Integer idAnio, String usuario) {
        try {
            // 1. Validar que el alumno no tenga matrícula en ese año
            if (matriculaRepo.existsByAlumnoIdAlumnoAndAnioAcademicoIdAnioAndEstado(
                    idAlumno, idAnio, 'A'))
                throw new RuntimeException(
                        "El alumno ya tiene matrícula registrada en este año académico");

            // 2. Validar capacidad máxima del aula
            int capacidad = aulaService.obtenerCapacidadMaxima();
            int actuales  = aulaService.contarAlumnos(idAula);
            if (actuales >= capacidad)
                throw new RuntimeException(
                        "El aula ya alcanzó el máximo de " + capacidad + " alumnos");

            // 3. Validar que existan conceptos activos
            List<Concepto> conceptos = conceptoService.listarPorAnio(idAnio);
            if (conceptos.isEmpty())
                throw new RuntimeException(
                        "No existen conceptos activos para el año académico seleccionado. " +
                                "Registre los conceptos antes de matricular.");

            // 4. Crear matrícula
            Matricula matricula = Matricula.builder()
                    .alumno(alumnoService.obtener(idAlumno))
                    .aula(aulaService.obtener(idAula))
                    .anioAcademico(anioService.obtener(idAnio))
                    .estado('A')
                    .usuInsert(usuario)
                    .build();
            matricula = matriculaRepo.save(matricula);

            // 5. Generar cuotas automáticamente
            Matricula finalMatricula = matricula;
            List<Cuota> cuotas = conceptos.stream().map(c -> Cuota.builder()
                    .matricula(finalMatricula)
                    .concepto(c)
                    .monto(c.getMonto())
                    .estadoPago('P')
                    .usuInsert(usuario)
                    .build()
            ).toList();
            cuotaRepo.saveAll(cuotas);

            return matricula;

        } catch (RuntimeException e) {
            // El @Transactional hace rollback automático
            throw e;
        }
    }

    public List<Matricula> listarPorAula(Integer idAula) {
        return matriculaRepo.findByAulaIdAulaAndEstado(idAula, 'A');
    }

    public Matricula obtenerPorAlumnoYAnio(Integer idAlumno, Integer idAnio) {
        return matriculaRepo
                .findByAlumnoIdAlumnoAndAnioAcademicoIdAnio(idAlumno, idAnio)
                .orElseThrow(() -> new RuntimeException(
                        "El alumno no tiene matrícula en el año seleccionado"));
    }
}