package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Alumno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface AlumnoRepository extends JpaRepository<Alumno, Integer> {

    Optional<Alumno> findByNroDocumento(String nroDocumento);

    boolean existsByNroDocumento(String nroDocumento);

    @Query("SELECT a FROM Alumno a WHERE " +
            "LOWER(CONCAT(a.apPaterno, ' ', a.apMaterno, ' ', a.nombre)) LIKE LOWER(CONCAT('%', :texto, '%')) " +
            "AND a.estado = 'A'")
    List<Alumno> buscarPorNombreCompleto(@Param("texto") String texto);

    @Query("SELECT a FROM Alumno a WHERE a.nroDocumento LIKE CONCAT('%', :doc, '%') AND a.estado = 'A'")
    List<Alumno> buscarPorDocumento(@Param("doc") String doc);
}