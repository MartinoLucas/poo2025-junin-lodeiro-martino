package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.participante.CreateParticipanteDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.participante.UpdateParticipanteDTO;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.mapper.common.DocumentoMapper;
import com.poo.proyecto.mapper.common.EmailMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { DocumentoMapper.class, EmailMapper.class })
public interface ParticipanteMapper {

    @Mapping(target = "userAccount", ignore = true)
    Participante toEntity(CreateParticipanteDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateParticipanteDTO dto, @MappingTarget Participante entity);

    @Mapping(source = "userAccount.id", target = "userId")
    ParticipanteResponseDTO toResponse(Participante entity);

    // 👇 Este método se ejecuta después del mapeo
    @AfterMapping
    default void linkUser(CreateParticipanteDTO dto, @MappingTarget Participante entity) {
        if (dto.getUserId() != null) {
            var user = new com.poo.proyecto.entity.UserAccount();
            try {
                java.lang.reflect.Field idField = user.getClass().getDeclaredField("id");
                idField.setAccessible(true);
                idField.set(user, dto.getUserId());
            } catch (Exception e) {
                throw new RuntimeException("Error setting UserAccount ID manually", e);
            }
            entity.setUserAccount(user);
        }
    }
}
