package com.poo.proyecto.service;

import com.poo.proyecto.dto.role.CreateRoleDTO;
import com.poo.proyecto.dto.role.RoleResponseDTO;
import com.poo.proyecto.dto.role.UpdateRoleDTO;
import com.poo.proyecto.entity.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {
    RoleResponseDTO create(CreateRoleDTO dto);
    RoleResponseDTO update(Long id, UpdateRoleDTO dto);
    RoleResponseDTO get(Long id);
    Page<RoleResponseDTO> list(Pageable pageable);
    void delete(Long id);
}

