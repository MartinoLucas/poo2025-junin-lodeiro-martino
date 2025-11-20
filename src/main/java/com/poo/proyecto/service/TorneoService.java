package com.poo.proyecto.service;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.competencia.CreateCompetenciaDTO;
import com.poo.proyecto.dto.competencia.UpdateCompetenciaDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.torneo.CreateTorneoDTO;
import com.poo.proyecto.dto.torneo.TorneoResponseDTO;
import com.poo.proyecto.dto.torneo.UpdateTorneoDTO;
import com.poo.proyecto.entity.Torneo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

public interface TorneoService {

    TorneoResponseDTO create(CreateTorneoDTO dto);
    TorneoResponseDTO update(Long id, UpdateTorneoDTO dto);
    TorneoResponseDTO publish(Long id);
    TorneoResponseDTO finalizeTournament(Long id);
    void deleteDraft(Long id);
    TorneoResponseDTO get(Long id);
    Page<TorneoResponseDTO> listPublished(Pageable pageable);
    Page<TorneoResponseDTO> listAll(Pageable pageable);
    Page<CompetenciaResponseDTO> findAllByTorneoId(Long torneoId, Pageable pageable);
    CompetenciaResponseDTO getByTorneoIdByCompetitionId(Long torunamentId, Long id);
    CompetenciaResponseDTO createCompetitionInTournament(Long tournamentId, CreateCompetenciaDTO dto);
    CompetenciaResponseDTO updateCompetitionInTournament(Long tournamentId, Long competitionId, UpdateCompetenciaDTO dto);
    Page<ParticipanteResponseDTO> listInscriptionsByCompetition(Long tournamentId, Long competitionId, Pageable pageable);
}
