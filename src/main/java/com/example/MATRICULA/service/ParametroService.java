package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.Parametro;
import com.example.MATRICULA.repository.ParametroRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ParametroService {

    private final ParametroRepository repo;

    public String obtenerValor(String clave) {
        return repo.findByClave(clave)
                .map(Parametro::getValor)
                .orElseThrow(() -> new RuntimeException("Parámetro no encontrado: " + clave));
    }

    public Parametro actualizar(String clave, String nuevoValor, String usuario) {
        Parametro p = repo.findByClave(clave)
                .orElseThrow(() -> new RuntimeException("Parámetro no encontrado: " + clave));
        p.setValor(nuevoValor);
        p.setUsuUpdate(usuario);
        return repo.save(p);
    }
}