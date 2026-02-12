package com.poo.proyecto.mapper.common;

import com.poo.proyecto.dto.common.MoneyDTO;
import com.poo.proyecto.entity.base.Money;
import org.mapstruct.Mapper;
import java.math.BigDecimal;

@Mapper(componentModel = "spring")
public interface MoneyMapper {

    MoneyDTO toDto(Money money);
    Money toEntity(MoneyDTO dto);

    // AGREGÁ ESTE MÉTODO:
    // MapStruct lo usará cuando vea un BigDecimal/Number en un DTO
    // y necesite un objeto Money en la Entidad.
    default Money map(BigDecimal amount) {
        if (amount == null) return null;
        Money money = new Money();
        money.setAmount(amount);
        money.setCurrency("ARS"); // O tu moneda por defecto
        return money;
    }
}