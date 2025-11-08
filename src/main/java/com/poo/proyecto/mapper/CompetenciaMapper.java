package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.competencia.CreateCompetenciaDTO;
import com.poo.proyecto.dto.competencia.UpdateCompetenciaDTO;
import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.entity.Torneo;
import com.poo.proyecto.mapper.common.MoneyMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { MoneyMapper.class, TorneoMapper.class })
public interface CompetenciaMapper {

    Competencia toEntity(CreateCompetenciaDTO dto);

    @AfterMapping
    default void linkTorneo(CreateCompetenciaDTO dto, @MappingTarget Competencia entity) {
        if (dto.getTorneoId() != null) {
            var torneo = new com.poo.proyecto.entity.Torneo();
            try {
                java.lang.reflect.Field idField = torneo.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(torneo, dto.getTorneoId());
            } catch (Exception e) {
                throw new RuntimeException("Error setting Torneo ID manually", e);
            }
            entity.setTorneo(torneo);
        }
    }

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateCompetenciaDTO dto, @MappingTarget Competencia entity);

    @Mapping(source = "torneo", target = "torneo")
    CompetenciaResponseDTO toResponse(Competencia entity);
}
