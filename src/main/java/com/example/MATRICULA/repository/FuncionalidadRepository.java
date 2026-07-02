package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Funcionalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface FuncionalidadRepository extends JpaRepository<Funcionalidad, Integer> {
    List<Funcionalidad> findByEstadoOrderByOrden(Character estado);
    List<Funcionalidad> findByPadreIsNullAndEstadoOrderByOrden(Character estado);
}