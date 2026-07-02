package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Aula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AulaRepository extends JpaRepository<Aula, Integer> {

    List<Aula> findByAnioAcademicoIdAnioAndEstado(Integer idAnio, Character estado);

    @Query("SELECT COUNT(m) FROM Matricula m WHERE m.aula.idAula = :idAula AND m.estado = 'A'")
    int contarAlumnosEnAula(@Param("idAula") Integer idAula);

    boolean existsByAnioAcademicoIdAnioAndNivelIdNivelAndGradoAndSeccionAndEstado(
            Integer idAnio, Integer idNivel, Integer grado, String seccion, Character estado);
}