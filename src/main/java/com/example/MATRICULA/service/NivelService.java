package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.Nivel;
import com.example.MATRICULA.repository.NivelRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NivelService {

    private final NivelRepository repo;

    public List<Nivel> listarActivos() {
        return repo.findByEstado('A');
    }

    public Nivel obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Nivel no encontrado"));
    }

    public Nivel guardar(Nivel nivel, String usuario) {
        nivel.setEstado('A');
        nivel.setUsuInsert(usuario);
        return repo.save(nivel);
    }

    public Nivel actualizar(Integer id, Nivel datos, String usuario) {
        Nivel existe = obtener(id);
        existe.setNombre(datos.getNombre());
        existe.setUsuUpdate(usuario);
        return repo.save(existe);
    }

    public void eliminar(Integer id, String usuario) {
        Nivel existe = obtener(id);
        existe.setEstado('I');
        existe.setUsuUpdate(usuario);
        repo.save(existe);
    }
}