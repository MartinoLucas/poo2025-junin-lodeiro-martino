package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.role.CreateRoleDTO;
import com.poo.proyecto.dto.role.RoleResponseDTO;
import com.poo.proyecto.dto.role.UpdateRoleDTO;
import com.poo.proyecto.entity.Role;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T18:12:06-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class RoleMapperImpl implements RoleMapper {

    @Override
    public RoleResponseDTO toResponse(Role entity) {
        if ( entity == null ) {
            return null;
        }

        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();

        roleResponseDTO.setId( entity.getId() );
        roleResponseDTO.setName( entity.getName() );
        roleResponseDTO.setDescription( entity.getDescription() );

        return roleResponseDTO;
    }

    @Override
    public Role toEntity(CreateRoleDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Role role = new Role();

        role.setName( dto.getName() );
        role.setDescription( dto.getDescription() );

        return role;
    }

    @Override
    public void updateEntity(UpdateRoleDTO dto, Role entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getName() != null ) {
            entity.setName( dto.getName() );
        }
        if ( dto.getDescription() != null ) {
            entity.setDescription( dto.getDescription() );
        }
    }
}
