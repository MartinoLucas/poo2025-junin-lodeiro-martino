package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.inscripcion.CreateInscripcionDTO;
import com.poo.proyecto.dto.inscripcion.InscripcionResponseDTO;
import com.poo.proyecto.entity.Inscripcion;
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
public class InscripcionMapperImpl implements InscripcionMapper {

    @Autowired
    private MoneyMapper moneyMapper;
    @Autowired
    private ParticipanteMapper participanteMapper;
    @Autowired
    private CompetenciaMapper competenciaMapper;

    @Override
    public Inscripcion toEntity(CreateInscripcionDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Inscripcion inscripcion = new Inscripcion();

        inscripcion.setPrecioPagado( moneyMapper.toEntity( dto.getPrecioPagado() ) );
        inscripcion.setFechaInscripcion( dto.getFechaInscripcion() );

        return inscripcion;
    }

    @Override
    public InscripcionResponseDTO toResponse(Inscripcion entity) {
        if ( entity == null ) {
            return null;
        }

        InscripcionResponseDTO inscripcionResponseDTO = new InscripcionResponseDTO();

        inscripcionResponseDTO.setParticipante( participanteMapper.toResponse( entity.getParticipante() ) );
        inscripcionResponseDTO.setCompetencia( competenciaMapper.toResponse( entity.getCompetencia() ) );
        inscripcionResponseDTO.setId( entity.getId() );
        inscripcionResponseDTO.setPrecioPagado( moneyMapper.toDto( entity.getPrecioPagado() ) );
        inscripcionResponseDTO.setFechaInscripcion( entity.getFechaInscripcion() );

        return inscripcionResponseDTO;
    }
}
