package com.poo.proyecto.service;

import com.poo.proyecto.dto.user.CreateUserDTO;
import com.poo.proyecto.dto.user.UpdateUserDTO;
import com.poo.proyecto.dto.user.UserResponseDTO;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.entity.base.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface UserAccountService {
    UserResponseDTO create(CreateUserDTO dto, String role);
    UserResponseDTO get(Long id);
    UserResponseDTO update(Long id, UpdateUserDTO dto);
    void deactivate(Long id);
    void activate(Long id);
    Page<UserResponseDTO> listAdmin(Pageable pageable);
    public Optional<Participante> getParticipant(Long userId);
}

