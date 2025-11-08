package com.poo.proyecto.service;

import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.entity.Participante;

public interface AuthorizationService {
    ParticipanteResponseDTO authorize(String token) throws Exception;
}
