package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Nivel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NivelRepository extends JpaRepository<Nivel, Integer> {
    List<Nivel> findByEstado(Character estado);
}