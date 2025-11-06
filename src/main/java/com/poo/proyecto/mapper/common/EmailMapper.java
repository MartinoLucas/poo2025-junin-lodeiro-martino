package com.poo.proyecto.mapper.common;

import com.poo.proyecto.dto.common.EmailDTO;
import com.poo.proyecto.entity.base.Email;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EmailMapper {
    default EmailDTO toDto(Email email) {
        if (email == null) return null;
        return new EmailDTO(email.value());
    }

    default Email toEntity(EmailDTO dto) {
        if (dto == null) return null;
        return new Email(dto.getValue());
    }
}
