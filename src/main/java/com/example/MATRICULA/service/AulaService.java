package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.Aula;
import com.example.MATRICULA.entity.AnioAcademico;
import com.example.MATRICULA.entity.Nivel;
import com.example.MATRICULA.repository.AulaRepository;
import com.example.MATRICULA.repository.ParametroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AulaService {

    private final AulaRepository aulaRepo;
    private final AnioAcademicoService anioService;
    private final NivelService nivelService;
    private final ParametroService parametroService;

    public List<Aula> listarPorAnio(Integer idAnio) {
        return aulaRepo.findByAnioAcademicoIdAnioAndEstado(idAnio, 'A');
    }

    public Aula obtener(Integer id) {
        return aulaRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Aula no encontrada"));
    }

    public Aula guardar(Integer idAnio, Integer idNivel, Integer grado,
                        String seccion, String usuario) {
        AnioAcademico anio = anioService.obtener(idAnio);
        Nivel nivel = nivelService.obtener(idNivel);

        boolean existe = aulaRepo
                .existsByAnioAcademicoIdAnioAndNivelIdNivelAndGradoAndSeccionAndEstado(
                        idAnio, idNivel, grado, seccion.toUpperCase(), 'A');
        if (existe)
            throw new RuntimeException("Ya existe el aula para ese nivel, grado y sección");

        Aula aula = Aula.builder()
                .anioAcademico(anio)
                .nivel(nivel)
                .grado(grado)
                .seccion(seccion.toUpperCase())
                .estado('A')
                .usuInsert(usuario)
                .build();

        return aulaRepo.save(aula);
    }

    public int obtenerCapacidadMaxima() {
        return Integer.parseInt(parametroService.obtenerValor("MAX_ALUMNOS_AULA"));
    }

    public int contarAlumnos(Integer idAula) {
        return aulaRepo.contarAlumnosEnAula(idAula);
    }

    public void eliminar(Integer id, String usuario) {
        Aula existe = obtener(id);
        existe.setEstado('I');
        existe.setUsuUpdate(usuario);
        aulaRepo.save(existe);
    }
}