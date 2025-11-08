package com.poo.proyecto.mapper.common;

import com.poo.proyecto.dto.common.MoneyDTO;
import com.poo.proyecto.entity.base.Money;
import java.math.BigDecimal;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-11-08T11:58:48-0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.8 (Microsoft)"
)
@Component
public class MoneyMapperImpl implements MoneyMapper {

    @Override
    public MoneyDTO toDto(Money money) {
        if ( money == null ) {
            return null;
        }

        MoneyDTO moneyDTO = new MoneyDTO();

        moneyDTO.setAmount( money.getAmount() );
        moneyDTO.setCurrency( money.getCurrency() );

        return moneyDTO;
    }

    @Override
    public Money toEntity(MoneyDTO dto) {
        if ( dto == null ) {
            return null;
        }

        BigDecimal amount = null;

        amount = dto.getAmount();

        Money money = new Money( amount );

        return money;
    }
}
