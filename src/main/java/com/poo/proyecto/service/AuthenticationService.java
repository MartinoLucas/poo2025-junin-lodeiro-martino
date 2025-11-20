package com.poo.proyecto.service;

import com.poo.proyecto.dto.authentication.AuthenticationRequestDTO;

public interface AuthenticationService {
    String authenticateAdmin(AuthenticationRequestDTO dto);
    String authenticateParticipant(AuthenticationRequestDTO dto);
}
