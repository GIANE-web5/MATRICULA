package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Funcionalidad;
import com.example.MATRICULA.service.FuncionalidadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/funcionalidades")
@RequiredArgsConstructor
public class FuncionalidadController {

    private final FuncionalidadService service;

    @GetMapping
    public List<Funcionalidad> listar() {
        return service.listarTodas();
    }
}