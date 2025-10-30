package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.role.RoleResponseDTO;
import com.poo.proyecto.entity.Role;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    RoleResponseDTO toResponse(Role role);
}
