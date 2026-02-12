package com.poo.proyecto.service;

import com.poo.proyecto.dto.inscripcion.CreateInscripcionDTO;
import com.poo.proyecto.dto.inscripcion.InscripcionResponseDTO;
import com.poo.proyecto.entity.Inscripcion;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;

public interface InscripcionService {

    InscripcionResponseDTO create(CreateInscripcionDTO dto);

    InscripcionResponseDTO get(Long id);

    Page<InscripcionResponseDTO> list(Pageable pageable);

    Page<InscripcionResponseDTO> listByParticipanteId(Long participanteId, Pageable pageable);

    Page<InscripcionResponseDTO> listByCompetitionId(Long competitionId, Pageable pageable);
}