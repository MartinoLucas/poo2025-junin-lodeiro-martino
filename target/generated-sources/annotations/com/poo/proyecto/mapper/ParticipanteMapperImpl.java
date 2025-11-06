package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.participante.CreateParticipanteDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.participante.UpdateParticipanteDTO;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.mapper.common.DocumentoMapper;
import com.poo.proyecto.mapper.common.EmailMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-06T12:05:06-0300",
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

        linkUser( dto, participante );

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

        participanteResponseDTO.setUserId( entityUserAccountId( entity ) );
        participanteResponseDTO.setId( entity.getId() );
        participanteResponseDTO.setNombre( entity.getNombre() );
        participanteResponseDTO.setApellido( entity.getApellido() );
        participanteResponseDTO.setDocumento( documentoMapper.toDto( entity.getDocumento() ) );
        participanteResponseDTO.setEmail( emailMapper.toDto( entity.getEmail() ) );

        return participanteResponseDTO;
    }

    private Long entityUserAccountId(Participante participante) {
        if ( participante == null ) {
            return null;
        }
        UserAccount userAccount = participante.getUserAccount();
        if ( userAccount == null ) {
            return null;
        }
        Long id = userAccount.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }
}
