package com.poo.proyecto.mapper.common;

import com.poo.proyecto.dto.common.EmailDTO;
import com.poo.proyecto.entity.base.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    EmailDTO toDto(Email email);
    Email toEntity(EmailDTO dto);
}
