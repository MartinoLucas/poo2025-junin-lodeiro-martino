package com.poo.proyecto.mapper.common;

import com.poo.proyecto.dto.common.DocumentoDTO;
import com.poo.proyecto.entity.TipoDocumento;
import com.poo.proyecto.entity.base.Documento;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-08T11:58:48-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class DocumentoMapperImpl implements DocumentoMapper {

    @Override
    public DocumentoDTO toDto(Documento documento) {
        if ( documento == null ) {
            return null;
        }

        DocumentoDTO documentoDTO = new DocumentoDTO();

        documentoDTO.setTipo( documento.getTipo() );
        documentoDTO.setNumero( documento.getNumero() );

        return documentoDTO;
    }

    @Override
    public Documento toEntity(DocumentoDTO dto) {
        if ( dto == null ) {
            return null;
        }

        TipoDocumento tipo = null;
        String numero = null;

        tipo = dto.getTipo();
        numero = dto.getNumero();

        Documento documento = new Documento( tipo, numero );

        return documento;
    }
}
