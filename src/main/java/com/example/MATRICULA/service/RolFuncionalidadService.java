package com.example.MATRICULA.service;

import com.example.MATRICULA.entity.*;
import com.example.MATRICULA.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RolFuncionalidadService {

    private final RolFuncionalidadRepository rolFuncRepo;
    private final RolRepository              rolRepo;
    private final FuncionalidadRepository    funcRepo;

    public List<RolFuncionalidad> listarPorRol(Integer idRol) {
        return rolFuncRepo.findByRolIdRol(idRol);
    }

    @Transactional
    public void aplicarPermisos(Integer idRol,
                                List<Map<String, Object>> permisos,
                                String usuario) {

        Rol rol = rolRepo.findById(idRol)
                .orElseThrow(() -> new RuntimeException("Rol no encontrado"));

        // Eliminar permisos anteriores
        rolFuncRepo.deleteByRol(idRol);
        rolFuncRepo.flush(); // forzar el DELETE antes del INSERT

        // Guardar nuevos permisos
        for (Map<String, Object> p : permisos) {
            Integer idFunc = (Integer) p.get("idFuncionalidad");

            Funcionalidad func = funcRepo.findById(idFunc)
                    .orElseThrow(() -> new RuntimeException(
                            "Funcionalidad no encontrada: " + idFunc));

            RolFuncionalidad rf = RolFuncionalidad.builder()
                    .rol(rol)
                    .funcionalidad(func)
                    .puedeVer(charOf(p, "puedeVer"))
                    .puedeInsertar(charOf(p, "puedeInsertar"))
                    .puedeEditar(charOf(p, "puedeEditar"))
                    .puedeEliminar(charOf(p, "puedeEliminar"))
                    .usuInsert(usuario)
                    .build();

            rolFuncRepo.save(rf);
        }
    }

    private Character charOf(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) return 'N';
        return (val.toString().equals("true") ||
                val.toString().equals("S")) ? 'S' : 'N';
    }
}