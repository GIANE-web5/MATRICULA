package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.AnioAcademico;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AnioAcademicoRepository extends JpaRepository<AnioAcademico, Integer> {
    Optional<AnioAcademico> findByAnio(Integer anio);
    boolean existsByAnio(Integer anio);
}