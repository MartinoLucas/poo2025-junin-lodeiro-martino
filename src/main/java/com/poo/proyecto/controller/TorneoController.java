package com.poo.proyecto.controller;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.competencia.CreateCompetenciaDTO;
import com.poo.proyecto.dto.competencia.UpdateCompetenciaDTO;
import com.poo.proyecto.dto.inscripcion.InscripcionResponseDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
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
@RequestMapping("/admin/tournaments")
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
    @PutMapping("/{id}/publish")
    public ResponseEntity<TorneoResponseDTO> publish(@PathVariable Long id) {
        return ResponseEntity.ok(service.publish(id));
    }

    // Finalizar un torneo
    @PutMapping("/{id}/finalize")
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

    @GetMapping
    public ResponseEntity<Page<TorneoResponseDTO>> listAll(Pageable pageable) {
        return ResponseEntity.ok(service.listAll(pageable));
    }

    @GetMapping("/{id}/competitions")
    public ResponseEntity<Page<CompetenciaResponseDTO>> listCompetitionsByTournament(@PathVariable Long id, Pageable pageable) {
        return ResponseEntity.ok(this.service.findAllByTorneoId(id, pageable));
    }

    @GetMapping("/{tournamentId}/competitions/{id}")
    public ResponseEntity<CompetenciaResponseDTO> getCompetitionByTournamentByCompetitionId(@PathVariable Long tournamentId, @PathVariable Long id) {
        return ResponseEntity.ok(this.service.getByTorneoIdByCompetitionId(tournamentId, id));
    }

    @PostMapping("/{tournamentId}")
    public ResponseEntity<CompetenciaResponseDTO> createCompetitionInTournament(@PathVariable Long tournamentId, @RequestBody @Valid CreateCompetenciaDTO dto) {
        dto.setTorneoId(tournamentId);
        return ResponseEntity.status(201).body(service.createCompetitionInTournament(tournamentId, dto));
    }

    @PutMapping("/{tournamentId}/competitions/{id}")
    public ResponseEntity<CompetenciaResponseDTO> updateCompetitionInTournament(@PathVariable Long tournamentId, @PathVariable Long id, @RequestBody @Valid UpdateCompetenciaDTO dto) {
        CompetenciaResponseDTO updatedCompetition = service.updateCompetitionInTournament(tournamentId, id, dto);
        return ResponseEntity.ok(updatedCompetition);
    }

    @GetMapping("/{tournamentId}/competitions/{id}/inscriptions")
    public ResponseEntity<Page<InscripcionResponseDTO>> listInscriptionsByCompetition(
            @PathVariable Long tournamentId,
            @PathVariable Long id,
            Pageable pageable) {
        Page<InscripcionResponseDTO> inscriptions = service.listInscriptionsByCompetition(tournamentId, id, pageable);
        return ResponseEntity.ok(inscriptions);
    }
}
