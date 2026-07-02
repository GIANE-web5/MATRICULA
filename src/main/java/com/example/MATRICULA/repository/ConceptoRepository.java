package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Concepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ConceptoRepository extends JpaRepository<Concepto, Integer> {
    List<Concepto> findByAnioAcademicoIdAnioAndEstadoOrderByOrden(Integer idAnio, Character estado);
    boolean existsByAnioAcademicoIdAnioAndDescripcionAndEstado(Integer idAnio, String descripcion, Character estado);
}