package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Cuota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface CuotaRepository extends JpaRepository<Cuota, Integer> {

    List<Cuota> findByMatriculaIdMatriculaOrderByConceptoOrden(Integer idMatricula);

    @Query("SELECT c FROM Cuota c WHERE c.matricula.idMatricula = :idMatricula " +
            "AND c.estadoPago = 'P' ORDER BY c.concepto.orden ASC")
    List<Cuota> findPendientesByMatricula(@Param("idMatricula") Integer idMatricula);


    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END FROM Cuota c " +
            "WHERE c.matricula.idMatricula = :idMatricula " +
            "AND c.concepto.orden < :orden AND c.estadoPago = 'P'")
    boolean existeDeudaAnterior(@Param("idMatricula") Integer idMatricula,
                                @Param("orden") Integer orden);
    @Query("SELECT c FROM Cuota c WHERE " +
            "c.matricula.anioAcademico.idAnio = :idAnio " +
            "AND c.estadoPago = 'P' " +
            "ORDER BY c.matricula.alumno.apPaterno ASC")
    List<Cuota> findPendientesPorAnio(@Param("idAnio") Integer idAnio);

    Optional<Cuota> findByMatriculaIdMatriculaAndConceptoIdConcepto(
            Integer idMatricula, Integer idConcepto);
}