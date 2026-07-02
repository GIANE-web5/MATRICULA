package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.TipoConcepto;
import com.example.MATRICULA.repository.TipoConceptoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoConceptoService {

    private final TipoConceptoRepository repo;

    public List<TipoConcepto> listarActivos() {
        return repo.findByEstado('A');
    }

    public TipoConcepto obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de concepto no encontrado"));
    }

    public TipoConcepto guardar(TipoConcepto tipo) {
        tipo.setEstado('A');
        return repo.save(tipo);
    }

    public void eliminar(Integer id) {
        TipoConcepto existe = obtener(id);
        existe.setEstado('I');
        repo.save(existe);
    }
}