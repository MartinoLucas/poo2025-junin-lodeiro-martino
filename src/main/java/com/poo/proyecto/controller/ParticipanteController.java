package com.poo.proyecto.controller;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.inscripcion.InscripcionResponseDTO;
import com.poo.proyecto.dto.participante.*;
import com.poo.proyecto.dto.torneo.TorneoResponseDTO;
import com.poo.proyecto.service.InscripcionService;
import com.poo.proyecto.service.ParticipanteService;
import com.poo.proyecto.service.TorneoService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping
public class ParticipanteController {

    private final ParticipanteService service;
    private final TorneoService torneoService;
    private final InscripcionService inscripcionService;

    public ParticipanteController(ParticipanteService service, TorneoService torneoService, InscripcionService inscripcionService) {
        this.service = service;
        this.torneoService = torneoService;
        this.inscripcionService = inscripcionService;
    }

    @PostMapping("/accounts")
    public ResponseEntity<ParticipanteResponseDTO> create(@RequestBody @Valid CreateParticipanteDTO dto) {
        return ResponseEntity.status(201).body(service.create(dto));
    }

    @GetMapping("/accounts")
    public ResponseEntity<Page<ParticipanteResponseDTO>> list(Pageable pageable) {
        return ResponseEntity.ok(service.list(pageable));
    }

    @GetMapping("/accounts/{id}")
    public ResponseEntity<ParticipanteResponseDTO> get(@PathVariable Long id) {
        return ResponseEntity.ok(service.get(id));
    }

    @PutMapping("/accounts/{id}")
    public ResponseEntity<ParticipanteResponseDTO> update(@PathVariable Long id, @RequestBody @Valid UpdateParticipanteDTO dto) {
        return ResponseEntity.ok(service.update(id, dto));
    }

    @GetMapping("/tournaments")
    public ResponseEntity<Page<TorneoResponseDTO>> listTorneos(Pageable pageable) {
        return ResponseEntity.ok(torneoService.listPublished(pageable));
    }

    @GetMapping("/tournaments/{id}")
    public ResponseEntity<TorneoResponseDTO> getTorneo(@PathVariable Long id) {
        return ResponseEntity.ok(torneoService.get(id));
    }

    @GetMapping("/tournaments/{torunamentId}/competitions")
    public ResponseEntity<Page<CompetenciaResponseDTO>> listCompetenciasByTorneo(@PathVariable Long torunamentId, Pageable pageable) {
        return ResponseEntity.ok(torneoService.findAllByTorneoId(torunamentId, pageable));
    }

    @GetMapping("/tournaments/{torunamentId}/competitions/{id}")
    public ResponseEntity<CompetenciaResponseDTO> getCompetenica(@PathVariable Long torunamentId, @PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(torneoService.getByTorneoIdByCompetitionId(torunamentId, id));
    }

    @GetMapping("/inscriptions-participant/{participantId}")
    public ResponseEntity<Page<InscripcionResponseDTO>> listInscripcionesByParticipante(@PathVariable Long participantId, Pageable pageable) {
        return ResponseEntity.ok(inscripcionService.listByParticipanteId(participantId, pageable));
    }


}
