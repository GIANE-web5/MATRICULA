package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.Rol;
import com.example.MATRICULA.entity.Usuario;
import com.example.MATRICULA.repository.RolRepository;
import com.example.MATRICULA.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repo;
    private final RolRepository rolRepo;

    public List<Usuario> listar() {
        return repo.findAll();
    }

    public Usuario obtener(Integer id) {
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

    public Usuario guardar(Usuario usuario, Integer idRol, String usuLogin) {
        if (repo.existsByUsername(usuario.getUsername()))
            throw new RuntimeException("El username ya existe");

        Rol rol = rolRepo.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        usuario.setRol(rol);
        usuario.setEstado('A');
        usuario.setUsuInsert(usuLogin);
        return repo.save(usuario);
    }

    public void cambiarPassword(Integer id, String passwordActual, String passwordNueva) {
        Usuario usuario = obtener(id);

        if (!usuario.getPasswordHash().equals(passwordActual))
            throw new RuntimeException("La contraseña actual es incorrecta");

        usuario.setPasswordHash(passwordNueva);
        repo.save(usuario);
    }

    public void eliminar(Integer id) {
        Usuario existe = obtener(id);

        // Validar que no sea superusuario
        if ("SUPERUSUARIO".equalsIgnoreCase(existe.getRol().getNombre())) {
            throw new RuntimeException(
                    "El Superusuario no puede ser eliminado del sistema");
        }

        existe.setEstado('I');
        repo.save(existe);
    }

    public Usuario login(String username, String password) {
        return repo.findByUsernameAndEstado(username, 'A')
                .filter(u -> u.getPasswordHash().equals(password))
                .orElseThrow(() -> new RuntimeException("Usuario o contraseña incorrectos"));
    }
}