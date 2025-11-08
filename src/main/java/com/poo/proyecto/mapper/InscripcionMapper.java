package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.inscripcion.CreateInscripcionDTO;
import com.poo.proyecto.dto.inscripcion.InscripcionResponseDTO;
import com.poo.proyecto.entity.Inscripcion;
import com.poo.proyecto.mapper.common.MoneyMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { MoneyMapper.class, ParticipanteMapper.class, CompetenciaMapper.class })
public interface InscripcionMapper {

    Inscripcion toEntity(CreateInscripcionDTO dto);

    @Mappings({
        @Mapping(source = "participante", target = "participante"),
        @Mapping(source = "competencia", target = "competencia")
    })
    InscripcionResponseDTO toResponse(Inscripcion entity);
}
