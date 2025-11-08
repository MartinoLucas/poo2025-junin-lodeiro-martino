package com.poo.proyecto.service;

import com.poo.proyecto.dto.participante.CreateParticipanteDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.participante.UpdateParticipanteDTO;
import com.poo.proyecto.dto.role.UpdateRoleDTO;
import com.poo.proyecto.entity.Participante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ParticipanteService {

    ParticipanteResponseDTO create(CreateParticipanteDTO dto);

    ParticipanteResponseDTO update(Long id, UpdateParticipanteDTO dto);

    ParticipanteResponseDTO get(Long id);

    Page<ParticipanteResponseDTO> list(Pageable pageable);

    ParticipanteResponseDTO findByEmail(String email);
}
