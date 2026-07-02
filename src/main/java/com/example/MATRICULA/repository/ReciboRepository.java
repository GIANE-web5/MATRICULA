package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Recibo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReciboRepository extends JpaRepository<Recibo, Integer> {

    Optional<Recibo> findByNroBoleta(String nroBoleta);

    @Query("SELECT COALESCE(MAX(r.idRecibo), 0) FROM Recibo r")
    Integer obtenerUltimoId();

    @Query("SELECT r FROM Recibo r WHERE " +
            "r.cuota.matricula.anioAcademico.idAnio = :idAnio " +
            "ORDER BY r.idRecibo ASC")
    List<Recibo> findByAnio(@Param("idAnio") Integer idAnio);
}