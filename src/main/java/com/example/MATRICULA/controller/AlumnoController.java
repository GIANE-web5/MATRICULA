package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Alumno;
import com.example.MATRICULA.service.AlumnoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/alumnos")
@RequiredArgsConstructor
public class AlumnoController {

    private final AlumnoService service;

    @GetMapping("/buscar/nombre")
    public List<Alumno> buscarNombre(@RequestParam String texto) {
        return service.buscarPorNombre(texto);
    }

    @GetMapping("/buscar/documento")
    public List<Alumno> buscarDocumento(@RequestParam String doc) {
        return service.buscarPorDocumento(doc);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alumno> obtener(@PathVariable Integer id) {
        return ResponseEntity.ok(service.obtener(id));
    }

    @PostMapping
    public ResponseEntity<Alumno> guardar(
            @RequestBody Map<String, Object> body) {
        Alumno alumno = new Alumno();
        alumno.setApPaterno((String) body.get("apPaterno"));
        alumno.setApMaterno((String) body.get("apMaterno"));
        alumno.setNombre((String) body.get("nombre"));
        alumno.setNroDocumento((String) body.get("nroDocumento"));
        Integer idTipoDoc = (Integer) body.get("idTipoDoc");
        String  usuario   = (String)  body.get("usuarioLogin");
        return ResponseEntity.ok(service.guardar(alumno, idTipoDoc, usuario));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Alumno> actualizar(
            @PathVariable Integer id,
            @RequestBody Map<String, Object> body) {
        Alumno datos = new Alumno();
        datos.setApPaterno((String) body.get("apPaterno"));
        datos.setApMaterno((String) body.get("apMaterno"));
        datos.setNombre((String) body.get("nombre"));
        datos.setNroDocumento((String) body.get("nroDocumento"));
        Integer idTipoDoc = (Integer) body.get("idTipoDoc");
        String  usuario   = (String)  body.get("usuarioLogin");
        return ResponseEntity.ok(service.actualizar(id, datos, idTipoDoc, usuario));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id,
            @RequestParam String usuarioLogin) {
        service.eliminar(id, usuarioLogin);
        return ResponseEntity.noContent().build();
    }
}