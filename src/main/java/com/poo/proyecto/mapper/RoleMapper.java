package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.role.*;
import com.poo.proyecto.entity.Role;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponseDTO toResponse(Role entity);

    Role toEntity(CreateRoleDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateRoleDTO dto, @MappingTarget Role entity);
}
