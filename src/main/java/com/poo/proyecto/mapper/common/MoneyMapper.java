package com.poo.proyecto.mapper.common;

import com.poo.proyecto.dto.common.MoneyDTO;
import com.poo.proyecto.entity.base.Money;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MoneyMapper {
    MoneyDTO toDto(Money money);
    Money toEntity(MoneyDTO dto);
}
