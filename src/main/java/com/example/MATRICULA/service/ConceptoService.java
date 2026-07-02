package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.AnioAcademico;
import com.example.MATRICULA.entity.Concepto;
import com.example.MATRICULA.repository.ConceptoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConceptoService {

    private final ConceptoRepository repo;
    private final AnioAcademicoService anioService;
    private final TipoConceptoService tipoConceptoService;

    public List<Concepto> listarPorAnio(Integer idAnio) {
        return repo.findByAnioAcademicoIdAnioAndEstadoOrderByOrden(idAnio, 'A');
    }

    public Concepto obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Concepto no encontrado"));
    }

    public Concepto guardar(Concepto concepto, Integer idAnio,
                            Integer idTipoConc, String usuario) {
        AnioAcademico anio = anioService.obtener(idAnio);

        if (repo.existsByAnioAcademicoIdAnioAndDescripcionAndEstado(
                idAnio, concepto.getDescripcion(), 'A'))
            throw new RuntimeException("Ya existe ese concepto para el año " + anio.getAnio());

        concepto.setAnioAcademico(anio);
        concepto.setTipoConcepto(tipoConceptoService.obtener(idTipoConc));
        concepto.setEstado('A');
        concepto.setUsuInsert(usuario);
        return repo.save(concepto);
    }

    public Concepto actualizar(Integer id, Concepto datos, String usuario) {
        Concepto existe = obtener(id);
        existe.setDescripcion(datos.getDescripcion());
        existe.setMonto(datos.getMonto());
        existe.setOrden(datos.getOrden());
        existe.setEsObligatorio(datos.getEsObligatorio());
        existe.setUsuUpdate(usuario);
        return repo.save(existe);
    }

    // Clona todos los conceptos de un año a otro
    public List<Concepto> clonarPorAnio(Integer idAnioOrigen, Integer idAnioDestino,
                                        String usuario) {
        AnioAcademico anioDestino = anioService.obtener(idAnioDestino);
        List<Concepto> origen = listarPorAnio(idAnioOrigen);

        if (origen.isEmpty())
            throw new RuntimeException("No hay conceptos en el año origen");

        List<Concepto> clonados = origen.stream().map(c -> Concepto.builder()
                .anioAcademico(anioDestino)
                .tipoConcepto(c.getTipoConcepto())
                .descripcion(c.getDescripcion())
                .monto(c.getMonto())
                .orden(c.getOrden())
                .esObligatorio(c.getEsObligatorio())
                .estado('A')
                .usuInsert(usuario)
                .build()
        ).toList();

        return repo.saveAll(clonados);
    }

    public void eliminar(Integer id, String usuario) {
        Concepto existe = obtener(id);
        existe.setEstado('I');
        existe.setUsuUpdate(usuario);
        repo.save(existe);
    }
}