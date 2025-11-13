package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.torneo.CreateTorneoDTO;
import com.poo.proyecto.dto.torneo.TorneoResponseDTO;
import com.poo.proyecto.dto.torneo.UpdateTorneoDTO;
import com.poo.proyecto.entity.Torneo;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-13T18:12:06-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class TorneoMapperImpl implements TorneoMapper {

    @Override
    public Torneo toEntity(CreateTorneoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        Torneo torneo = new Torneo();

        torneo.setNombre( dto.getNombre() );
        torneo.setDescripcion( dto.getDescripcion() );
        torneo.setFechaInicio( dto.getFechaInicio() );
        torneo.setFechaFin( dto.getFechaFin() );
        torneo.setEstado( dto.getEstado() );

        return torneo;
    }

    @Override
    public void updateEntity(UpdateTorneoDTO dto, Torneo entity) {
        if ( dto == null ) {
            return;
        }

        if ( dto.getNombre() != null ) {
            entity.setNombre( dto.getNombre() );
        }
        if ( dto.getDescripcion() != null ) {
            entity.setDescripcion( dto.getDescripcion() );
        }
        if ( dto.getFechaInicio() != null ) {
            entity.setFechaInicio( dto.getFechaInicio() );
        }
        if ( dto.getFechaFin() != null ) {
            entity.setFechaFin( dto.getFechaFin() );
        }
    }

    @Override
    public TorneoResponseDTO toResponse(Torneo torneo) {
        if ( torneo == null ) {
            return null;
        }

        TorneoResponseDTO torneoResponseDTO = new TorneoResponseDTO();

        torneoResponseDTO.setId( torneo.getId() );
        torneoResponseDTO.setNombre( torneo.getNombre() );
        torneoResponseDTO.setDescripcion( torneo.getDescripcion() );
        torneoResponseDTO.setFechaInicio( torneo.getFechaInicio() );
        torneoResponseDTO.setFechaFin( torneo.getFechaFin() );
        torneoResponseDTO.setEstado( torneo.getEstado() );
        torneoResponseDTO.setCreatedAt( torneo.getCreatedAt() );
        torneoResponseDTO.setUpdatedAt( torneo.getUpdatedAt() );

        return torneoResponseDTO;
    }
}
