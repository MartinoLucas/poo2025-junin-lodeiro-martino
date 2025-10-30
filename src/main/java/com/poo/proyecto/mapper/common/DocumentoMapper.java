package com.poo.proyecto.mapper.common;

import com.poo.proyecto.dto.common.DocumentoDTO;
import com.poo.proyecto.entity.base.Documento;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface DocumentoMapper {
    DocumentoDTO toDto(Documento documento);
    Documento toEntity(DocumentoDTO dto);
}
