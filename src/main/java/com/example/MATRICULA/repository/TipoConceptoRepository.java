package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.TipoConcepto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TipoConceptoRepository extends JpaRepository<TipoConcepto, Integer> {
    List<TipoConcepto> findByEstado(Character estado);
}