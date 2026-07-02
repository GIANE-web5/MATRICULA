package com.example.MATRICULA.repository;

import com.example.MATRICULA.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {
    Optional<Usuario> findByUsernameAndEstado(String username, Character estado);
    boolean existsByUsername(String username);
}