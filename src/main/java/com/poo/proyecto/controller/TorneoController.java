package com.poo.proyecto.controller;

import com.poo.proyecto.dto.torneo.CreateTorneoDTO;
import com.poo.proyecto.dto.torneo.TorneoResponseDTO;
import com.poo.proyecto.dto.torneo.UpdateTorneoDTO;
import com.poo.proyecto.service.TorneoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/torneos")
public class TorneoController {

    private final TorneoService service;

    public TorneoController(TorneoService service) {
        this.service = service;
    }

    // Crear un torneo (solo en estado BORRADOR)
    @PostMapping
    public ResponseEntity<TorneoResponseDTO> create(@RequestBody @Valid CreateTorneoDTO dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    // Actualizar un torneo (solo si está en BORRADOR)
    @PutMapping("/{id}")
    public ResponseEntity<TorneoResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateTorneoDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    // Publicar un torneo
    @PutMapping("/{id}/publicar")
    public ResponseEntity<TorneoResponseDTO> publish(@PathVariable Long id) {
        return ResponseEntity.ok(service.publish(id));
    }

    // Finalizar un torneo
    @PutMapping("/{id}/finalizar")
    public ResponseEntity<TorneoResponseDTO> finalizeTournament(@PathVariable Long id) {
        return ResponseEntity.ok(service.finalizeTournament(id));
    }

    // Eliminar un torneo (solo si está en BORRADOR)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDraft(@PathVariable Long id) {
        service.deleteDraft(id);
        return ResponseEntity.noContent().build();
    }

    // Obtener un torneo por id
    @GetMapping("/{id}")
    public ResponseEntity<TorneoResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    // Listar torneos publicados (paginado)
    @GetMapping("/publicados")
    public ResponseEntity<Page<TorneoResponseDTO>> listPublished(Pageable pageable) {
        return ResponseEntity.ok(service.listPublished(pageable));
    }
}
