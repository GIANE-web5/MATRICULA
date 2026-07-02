package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.TipoDocumento;
import com.example.MATRICULA.repository.TipoDocumentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TipoDocumentoService {

    private final TipoDocumentoRepository repo;

    public List<TipoDocumento> listarActivos() {
        return repo.findByEstado('A');
    }

    public TipoDocumento obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Tipo de documento no encontrado"));
    }

    public TipoDocumento guardar(TipoDocumento tipo) {
        tipo.setEstado('A');
        return repo.save(tipo);
    }

    public void eliminar(Integer id) {
        TipoDocumento existe = obtener(id);
        existe.setEstado('I');
        repo.save(existe);
    }
}