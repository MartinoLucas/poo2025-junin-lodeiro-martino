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

    // MapStruct usará esto para: String -> Email
    public default Email toEntity(String value) {
        if (value == null) return null;
        Email email = new Email();
        email.setValue(value);
        return email;
    }

    // MapStruct usará esto para: Email -> String
    public default String toString(Email email) {
        return email != null ? email.getValue() : null;
    }
}
