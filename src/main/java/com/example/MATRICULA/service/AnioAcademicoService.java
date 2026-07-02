package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.AnioAcademico;
import com.example.MATRICULA.repository.AnioAcademicoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AnioAcademicoService {

    private final AnioAcademicoRepository repo;

    public List<AnioAcademico> listar() {
        return repo.findAll();
    }

    public AnioAcademico obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Año académico no encontrado"));
    }

    public AnioAcademico guardar(AnioAcademico anio, String usuario) {
        if (repo.existsByAnio(anio.getAnio()))
            throw new RuntimeException("Ya existe el año académico " + anio.getAnio());
        anio.setUsuInsert(usuario);
        anio.setEstado('A');
        return repo.save(anio);
    }

    public AnioAcademico actualizar(Integer id, AnioAcademico datos, String usuario) {
        AnioAcademico existe = obtener(id);
        existe.setDescripcion(datos.getDescripcion());
        existe.setUsuUpdate(usuario);
        return repo.save(existe);
    }

    public void eliminar(Integer id, String usuario) {
        AnioAcademico existe = obtener(id);
        existe.setEstado('I');
        existe.setUsuUpdate(usuario);
        repo.save(existe);
    }
}