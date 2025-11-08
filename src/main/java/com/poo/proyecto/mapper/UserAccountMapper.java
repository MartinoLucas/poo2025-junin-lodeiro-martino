package com.poo.proyecto.mapper;

import com.poo.proyecto.dto.user.CreateUserDTO;
import com.poo.proyecto.dto.user.UpdateUserDTO;
import com.poo.proyecto.dto.user.UserResponseDTO;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.mapper.common.EmailMapper;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = { EmailMapper.class, RoleMapper.class })
public interface UserAccountMapper {

    @Mapping(target = "passwordHash", ignore = true)
    UserAccount toEntity(CreateUserDTO dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntity(UpdateUserDTO dto, @MappingTarget UserAccount entity);

    @Mapping(source = "roles", target = "roles")
    UserResponseDTO toResponse(UserAccount entity);
}
