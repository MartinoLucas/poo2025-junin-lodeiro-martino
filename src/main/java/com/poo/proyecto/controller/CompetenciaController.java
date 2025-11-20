package com.poo.proyecto.controller;

import com.poo.proyecto.dto.competencia.*;
import com.poo.proyecto.service.CompetenciaService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/competencias")
public class CompetenciaController {

    private final CompetenciaService service;

    public CompetenciaController(CompetenciaService service) {
        this.service = service;
    }

//    @PostMapping
//    public ResponseEntity<CompetenciaResponseDTO> create(@RequestBody @Valid CreateCompetenciaDTO dto) {
//        return ResponseEntity.status(201).body(service.create(dto));
//    }
//
//    @GetMapping("/torneo/{torneoId}")
//    public ResponseEntity<Page<CompetenciaResponseDTO>> list(@PathVariable Long torneoId, Pageable pageable) {
//        return ResponseEntity.ok(service.listByTorneo(torneoId,pageable));
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<CompetenciaResponseDTO> get(@PathVariable Long id) {
//        return ResponseEntity.ok(service.get(id));
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<CompetenciaResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateCompetenciaDTO dto) {
//        return ResponseEntity.ok(service.update(id, dto));
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> delete(@PathVariable Long id) {
//        service.delete(id);
//        return ResponseEntity.noContent().build();
//    }
}