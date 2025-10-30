package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.competencia.CreateCompetenciaDTO;
import com.poo.proyecto.dto.competencia.UpdateCompetenciaDTO;
import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.mapper.common.MoneyMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { MoneyMapper.class })
public interface CompetenciaMapper {

    Competencia toEntity(CreateCompetenciaDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateCompetenciaDTO dto, @MappingTarget Competencia entity);

    @Mapping(source = "torneo.id", target = "torneoId")
    CompetenciaResponseDTO toResponse(Competencia entity);
}
