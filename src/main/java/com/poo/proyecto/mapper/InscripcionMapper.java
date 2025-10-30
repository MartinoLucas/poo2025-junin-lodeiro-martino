package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.inscripcion.CreateInscripcionDTO;
import com.poo.proyecto.dto.inscripcion.InscripcionResponseDTO;
import com.poo.proyecto.entity.Inscripcion;
import com.poo.proyecto.mapper.common.MoneyMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { MoneyMapper.class })
public interface InscripcionMapper {

    Inscripcion toEntity(CreateInscripcionDTO dto);

    InscripcionResponseDTO toResponse(Inscripcion entity);
}
