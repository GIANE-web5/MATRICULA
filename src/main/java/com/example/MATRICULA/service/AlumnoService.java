package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.Alumno;
import com.example.MATRICULA.entity.TipoDocumento;
import com.example.MATRICULA.repository.AlumnoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AlumnoService {

    private final AlumnoRepository repo;
    private final TipoDocumentoService tipoDocService;

    public List<Alumno> buscarPorNombre(String texto) {
        return repo.buscarPorNombreCompleto(texto);
    }

    public List<Alumno> buscarPorDocumento(String doc) {
        return repo.buscarPorDocumento(doc);
    }

    public Alumno obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Alumno no encontrado"));
    }

    public Alumno guardar(Alumno alumno, Integer idTipoDoc, String usuario) {
        if (repo.existsByNroDocumento(alumno.getNroDocumento()))
            throw new RuntimeException("Ya existe un alumno con ese documento");

        TipoDocumento tipo = tipoDocService.obtener(idTipoDoc);
        alumno.setTipoDocumento(tipo);
        alumno.setEstado('A');
        alumno.setUsuInsert(usuario);
        return repo.save(alumno);
    }

    public Alumno actualizar(Integer id, Alumno datos, Integer idTipoDoc, String usuario) {
        Alumno existe = obtener(id);
        existe.setApPaterno(datos.getApPaterno());
        existe.setApMaterno(datos.getApMaterno());
        existe.setNombre(datos.getNombre());
        existe.setFecNacimiento(datos.getFecNacimiento());
        existe.setSexo(datos.getSexo());
        existe.setTipoDocumento(tipoDocService.obtener(idTipoDoc));
        existe.setNroDocumento(datos.getNroDocumento());
        existe.setUsuUpdate(usuario);
        return repo.save(existe);
    }

    public void eliminar(Integer id, String usuario) {
        Alumno existe = obtener(id);
        existe.setEstado('I');
        existe.setUsuUpdate(usuario);
        repo.save(existe);
    }
}