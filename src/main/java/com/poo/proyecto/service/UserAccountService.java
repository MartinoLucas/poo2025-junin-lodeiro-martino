package com.poo.proyecto.service;

import com.poo.proyecto.dto.user.CreateUserDTO;
import com.poo.proyecto.dto.user.UpdateUserDTO;
import com.poo.proyecto.dto.user.UserResponseDTO;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.entity.base.Email;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserAccountService {
    UserResponseDTO create(CreateUserDTO dto);
    UserResponseDTO get(Long id);
    UserResponseDTO update(Long id, UpdateUserDTO dto);
    void deactivate(Long id);
    void activate(Long id);
    Page<UserResponseDTO> list(Pageable pageable);
}

