package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.competencia.CompetenciaResponseDTO;
import com.poo.proyecto.dto.competencia.CreateCompetenciaDTO;
import com.poo.proyecto.dto.competencia.UpdateCompetenciaDTO;
import com.poo.proyecto.entity.Competencia;
import com.poo.proyecto.mapper.common.MoneyMapper;
import javax.annotation.processing.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T18:12:06-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class CompetenciaMapperImpl implements CompetenciaMapper {

    @Autowired
    private MoneyMapper moneyMapper;
    @Autowired
    private TorneoMapper torneoMapper;

    @Override
    public Competencia toEntity(CreateCompetenciaDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Competencia competencia = new Competencia();

        competencia.setNombre( dto.getNombre() );
        competencia.setPrecioBase( moneyMapper.toEntity( dto.getPrecioBase() ) );
        competencia.setCupo( dto.getCupo() );

        linkTorneo( dto, competencia );

        return competencia;
    }

    @Override
    public void updateEntity(UpdateCompetenciaDTO dto, Competencia entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getNombre() != null ) {
            entity.setNombre( dto.getNombre() );
        }
        if ( dto.getPrecioBase() != null ) {
            entity.setPrecioBase( moneyMapper.toEntity( dto.getPrecioBase() ) );
        }
        entity.setCupo( dto.getCupo() );
    }

    @Override
    public CompetenciaResponseDTO toResponse(Competencia entity) {
        if ( entity == null ) {
            return null;
        }

        CompetenciaResponseDTO competenciaResponseDTO = new CompetenciaResponseDTO();

        competenciaResponseDTO.setTorneo( torneoMapper.toResponse( entity.getTorneo() ) );
        competenciaResponseDTO.setId( entity.getId() );
        competenciaResponseDTO.setNombre( entity.getNombre() );
        competenciaResponseDTO.setPrecioBase( moneyMapper.toDto( entity.getPrecioBase() ) );
        competenciaResponseDTO.setCupo( entity.getCupo() );
        competenciaResponseDTO.setInscriptosActuales( entity.getInscriptosActuales() );
        competenciaResponseDTO.setCreatedAt( entity.getCreatedAt() );
        competenciaResponseDTO.setUpdatedAt( entity.getUpdatedAt() );

        return competenciaResponseDTO;
    }
}
