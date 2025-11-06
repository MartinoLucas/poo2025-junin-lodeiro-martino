package com.poo.proyecto.controller;

import com.poo.proyecto.dto.participante.*;
import com.poo.proyecto.service.ParticipanteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/participantes")
public class ParticipanteController {

    private final ParticipanteService service;

    public ParticipanteController(ParticipanteService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ParticipanteResponseDTO> create(@RequestBody @Valid CreateParticipanteDTO dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @GetMapping
    public ResponseEntity<Page<ParticipanteResponseDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ParticipanteResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateParticipanteDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }


}
