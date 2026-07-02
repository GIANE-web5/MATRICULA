package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Parametro;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface ParametroRepository extends JpaRepository<Parametro, Integer> {
    Optional<Parametro> findByClave(String clave);
}