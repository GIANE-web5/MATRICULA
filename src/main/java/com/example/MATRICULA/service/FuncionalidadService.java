package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.Funcionalidad;
import com.example.MATRICULA.repository.FuncionalidadRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FuncionalidadService {

    private final FuncionalidadRepository repo;

    public List<Funcionalidad> listarTodas() {
        return repo.findByEstadoOrderByOrden('A');
    }

    public List<Funcionalidad> listarPadres() {
        return repo.findByPadreIsNullAndEstadoOrderByOrden('A');
    }

    public Funcionalidad obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionalidad no encontrada"));
    }
}