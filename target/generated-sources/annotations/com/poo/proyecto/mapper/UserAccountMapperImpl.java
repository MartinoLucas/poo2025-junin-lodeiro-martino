package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.role.RoleResponseDTO;
import com.poo.proyecto.dto.user.CreateUserDTO;
import com.poo.proyecto.dto.user.UpdateUserDTO;
import com.poo.proyecto.dto.user.UserResponseDTO;
import com.poo.proyecto.entity.Role;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.mapper.common.EmailMapper;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-20T18:47:00-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class UserAccountMapperImpl implements UserAccountMapper {

    @Autowired
    private EmailMapper emailMapper;
    @Autowired
    private RoleMapper roleMapper;

    @Override
    public UserAccount toEntity(CreateUserDTO dto) {
        if ( dto == null ) {
            return null;
        }

        UserAccount userAccount = new UserAccount();

        userAccount.setEmail( emailMapper.toEntity( dto.getEmail() ) );

        return userAccount;
    }

    @Override
    public void updateEntity(UpdateUserDTO dto, UserAccount entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getEmail() != null ) {
            entity.setEmail( emailMapper.toEntity( dto.getEmail() ) );
        }
    }

    @Override
    public UserResponseDTO toResponse(UserAccount entity) {
        if ( entity == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setRoles( roleSetToRoleResponseDTOSet( entity.getRoles() ) );
        userResponseDTO.setId( entity.getId() );
        userResponseDTO.setEmail( emailMapper.toDto( entity.getEmail() ) );
        userResponseDTO.setCreatedAt( entity.getCreatedAt() );
        userResponseDTO.setUpdatedAt( entity.getUpdatedAt() );

        return userResponseDTO;
    }

    protected Set<RoleResponseDTO> roleSetToRoleResponseDTOSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponseDTO> set1 = new LinkedHashSet<RoleResponseDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleMapper.toResponse( role ) );
        }

        return set1;
    }
}
