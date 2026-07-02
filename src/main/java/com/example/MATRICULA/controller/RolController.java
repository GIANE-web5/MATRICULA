package com.example.MATRICULA.controller;

import com.example.MATRICULA.entity.Rol;
import com.example.MATRICULA.repository.RolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/roles")
@RequiredArgsConstructor
public class RolController {

    private final RolRepository repo;

    @GetMapping
    public List<Rol> listar() {
        return repo.findAll();
    }
}