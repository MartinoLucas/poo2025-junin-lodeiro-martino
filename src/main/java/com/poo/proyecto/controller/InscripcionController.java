package com.poo.proyecto.controller;

import com.poo.proyecto.dto.inscripcion.*;
import com.poo.proyecto.service.InscripcionService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/inscripciones")
public class InscripcionController {

    private final InscripcionService service;

    public InscripcionController(InscripcionService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<InscripcionResponseDTO> create(@RequestBody @Valid CreateInscripcionDTO dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<List<InscripcionResponseDTO>> list() {
        return ResponseEntity.ok(service.listAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<InscripcionResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }


}