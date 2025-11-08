package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.participante.CreateParticipanteDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.participante.UpdateParticipanteDTO;
import com.poo.proyecto.dto.role.RoleResponseDTO;
import com.poo.proyecto.dto.user.UserResponseDTO;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.Role;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.mapper.common.DocumentoMapper;
import com.poo.proyecto.mapper.common.EmailMapper;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-08T11:58:49-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class ParticipanteMapperImpl implements ParticipanteMapper {

    @Autowired
    private DocumentoMapper documentoMapper;
    @Autowired
    private EmailMapper emailMapper;

    @Override
    public Participante toEntity(CreateParticipanteDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Participante participante = new Participante();

        participante.setNombre( dto.getNombre() );
        participante.setApellido( dto.getApellido() );
        participante.setDocumento( documentoMapper.toEntity( dto.getDocumento() ) );
        participante.setEmail( emailMapper.toEntity( dto.getEmail() ) );

        return participante;
    }

    @Override
    public void updateEntity(UpdateParticipanteDTO dto, Participante entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getNombre() != null ) {
            entity.setNombre( dto.getNombre() );
        }
        if ( dto.getApellido() != null ) {
            entity.setApellido( dto.getApellido() );
        }
        if ( dto.getDocumento() != null ) {
            entity.setDocumento( documentoMapper.toEntity( dto.getDocumento() ) );
        }
        if ( dto.getEmail() != null ) {
            entity.setEmail( emailMapper.toEntity( dto.getEmail() ) );
        }
    }

    @Override
    public ParticipanteResponseDTO toResponse(Participante entity) {
        if ( entity == null ) {
            return null;
        }

        ParticipanteResponseDTO participanteResponseDTO = new ParticipanteResponseDTO();

        participanteResponseDTO.setUser( userAccountToUserResponseDTO( entity.getUserAccount() ) );
        participanteResponseDTO.setId( entity.getId() );
        participanteResponseDTO.setNombre( entity.getNombre() );
        participanteResponseDTO.setApellido( entity.getApellido() );
        participanteResponseDTO.setDocumento( documentoMapper.toDto( entity.getDocumento() ) );
        participanteResponseDTO.setEmail( emailMapper.toDto( entity.getEmail() ) );

        return participanteResponseDTO;
    }

    protected RoleResponseDTO roleToRoleResponseDTO(Role role) {
        if ( role == null ) {
            return null;
        }

        RoleResponseDTO roleResponseDTO = new RoleResponseDTO();

        roleResponseDTO.setId( role.getId() );
        roleResponseDTO.setName( role.getName() );
        roleResponseDTO.setDescription( role.getDescription() );

        return roleResponseDTO;
    }

    protected Set<RoleResponseDTO> roleSetToRoleResponseDTOSet(Set<Role> set) {
        if ( set == null ) {
            return null;
        }

        Set<RoleResponseDTO> set1 = new LinkedHashSet<RoleResponseDTO>( Math.max( (int) ( set.size() / .75f ) + 1, 16 ) );
        for ( Role role : set ) {
            set1.add( roleToRoleResponseDTO( role ) );
        }

        return set1;
    }

    protected UserResponseDTO userAccountToUserResponseDTO(UserAccount userAccount) {
        if ( userAccount == null ) {
            return null;
        }

        UserResponseDTO userResponseDTO = new UserResponseDTO();

        userResponseDTO.setId( userAccount.getId() );
        userResponseDTO.setEmail( emailMapper.toDto( userAccount.getEmail() ) );
        userResponseDTO.setRoles( roleSetToRoleResponseDTOSet( userAccount.getRoles() ) );
        userResponseDTO.setCreatedAt( userAccount.getCreatedAt() );
        userResponseDTO.setUpdatedAt( userAccount.getUpdatedAt() );

        return userResponseDTO;
    }
}
