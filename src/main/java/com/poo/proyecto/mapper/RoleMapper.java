package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.role.*;
import com.poo.proyecto.entity.Role;
import org.mapstruct.*;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface RoleMapper {

    RoleResponseDTO toResponse(Role entity);

    Role toEntity(CreateRoleDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateRoleDTO dto, @MappingTarget Role entity);

    default String toString(Role role) {
        return role != null ? role.getName() : null;
    }

    default Set<String> toStringSet(Set<Role> roles) {
        return roles != null
                ? roles.stream().map(this::toString).collect(Collectors.toSet())
                : null;
    }
}
