package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.participante.CreateParticipanteDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.participante.UpdateParticipanteDTO;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.mapper.common.DocumentoMapper;
import com.poo.proyecto.mapper.common.EmailMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { DocumentoMapper.class, EmailMapper.class })
public interface ParticipanteMapper {

    Participante toEntity(CreateParticipanteDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateParticipanteDTO dto, @MappingTarget Participante entity);

    @Mapping(source = "userAccount.id", target = "userId")
    ParticipanteResponseDTO toResponse(Participante entity);
}
