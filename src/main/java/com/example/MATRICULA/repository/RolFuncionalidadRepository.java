package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.RolFuncionalidad;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Repository
public interface RolFuncionalidadRepository extends JpaRepository<RolFuncionalidad, Integer> {

    List<RolFuncionalidad> findByRolIdRol(Integer idRol);

    @Query("SELECT rf FROM RolFuncionalidad rf " +
            "WHERE rf.rol.idRol = :idRol " +
            "AND rf.funcionalidad.idFuncionalidad = :idFunc")
    RolFuncionalidad findByRolAndFuncionalidad(
            @Param("idRol") Integer idRol,
            @Param("idFunc") Integer idFunc);

    @Modifying
    @Transactional
    @Query("DELETE FROM RolFuncionalidad rf WHERE rf.rol.idRol = :idRol")
    void deleteByRol(@Param("idRol") Integer idRol);
}