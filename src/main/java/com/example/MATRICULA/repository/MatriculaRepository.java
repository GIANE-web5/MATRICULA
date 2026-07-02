package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Matricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Integer> {

    boolean existsByAlumnoIdAlumnoAndAnioAcademicoIdAnioAndEstado(
            Integer idAlumno, Integer idAnio, Character estado);

    Optional<Matricula> findByAlumnoIdAlumnoAndAnioAcademicoIdAnio(
            Integer idAlumno, Integer idAnio);

    List<Matricula> findByAulaIdAulaAndEstado(Integer idAula, Character estado);

    List<Matricula> findByAnioAcademicoIdAnioAndEstado(Integer idAnio, Character estado);
}