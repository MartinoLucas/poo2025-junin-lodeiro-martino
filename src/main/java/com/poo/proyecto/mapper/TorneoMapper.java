package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.torneo.CreateTorneoDTO;
import com.poo.proyecto.dto.torneo.TorneoResponseDTO;
import com.poo.proyecto.dto.torneo.UpdateTorneoDTO;
import com.poo.proyecto.entity.Torneo;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface TorneoMapper {

    Torneo toEntity(CreateTorneoDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateTorneoDTO dto, @MappingTarget Torneo entity);

    TorneoResponseDTO toResponse(Torneo torneo);
}
