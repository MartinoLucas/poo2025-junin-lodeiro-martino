package com.poo.proyecto.service;

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
}
