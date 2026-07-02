package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Usuario;
import com.example.MATRICULA.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService service;

    @GetMapping
    public List<Usuario> listar() {
        return service.listar();
    }

    @PostMapping("/login")
    public ResponseEntity<Usuario> login(@RequestBody Map<String, String> body) {
        return ResponseEntity.ok(
                service.login(body.get("username"), body.get("password")));
    }

    @PostMapping
    public ResponseEntity<Usuario> guardar(@RequestBody Map<String, Object> body) {
        Usuario u = new Usuario();
        u.setUsername((String) body.get("username"));
        u.setPasswordHash((String) body.get("password"));
        u.setNombreCompleto((String) body.get("nombreCompleto"));
        Integer idRol = (Integer) body.get("idRol");
        return ResponseEntity.ok(service.guardar(u, idRol, "admin"));
    }

    @PutMapping("/{id}/cambiar-password")
    public ResponseEntity<?> cambiarPassword(
            @PathVariable Integer id,
            @RequestBody Map<String, String> body) {
        try {
            service.cambiarPassword(
                    id,
                    body.get("passwordActual"),
                    body.get("passwordNueva"));
            return ResponseEntity.ok(Map.of("message", "Contraseña actualizada correctamente"));
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest()
                    .body(Map.of("message", e.getMessage()));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        service.eliminar(id);
        return ResponseEntity.noContent().build();
    }
}