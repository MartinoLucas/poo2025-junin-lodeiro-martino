package com.poo.proyecto.service;

import com.poo.proyecto.dto.authentication.AuthenticationRequestDTO;

public interface AuthenticationService {
    String authenticate(AuthenticationRequestDTO dto) ;
}
