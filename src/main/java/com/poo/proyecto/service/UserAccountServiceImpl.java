package com.poo.proyecto.service;

import com.poo.proyecto.dto.user.CreateUserDTO;
import com.poo.proyecto.dto.user.UpdateUserDTO;
import com.poo.proyecto.dto.user.UserResponseDTO;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.entity.base.Email;
import com.poo.proyecto.mapper.ParticipanteMapper;
import com.poo.proyecto.mapper.UserAccountMapper;
import com.poo.proyecto.mapper.common.EmailMapper;
import com.poo.proyecto.repository.UserAccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import com.poo.proyecto.util.PasswordEncoder;

@Service
public class UserAccountServiceImpl extends BaseServiceSupport implements UserAccountService {

    private final UserAccountRepository repo;
    private final UserAccountMapper userMapper;
    private final EmailMapper emailMapper;
    private final PasswordEncoder passwordEncoder;

    public UserAccountServiceImpl(UserAccountRepository repo, UserAccountMapper userMapper, EmailMapper emailMapper, PasswordEncoder passwordEncoder) {
        this.repo = repo;
        this.userMapper = userMapper;
        this.emailMapper = emailMapper;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public UserResponseDTO create(CreateUserDTO dto) {
        check(!repo.existsByEmailValueAndDeletedAtIsNull(dto.getEmail().getValue()), "Ya existe una cuenta con ese email");

        UserAccount u = userMapper.toEntity(dto);

        //Como se ignora la password al mapear, se setea aqui luego de ser encodeada
        u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        repo.save(u);

        return userMapper.toResponse(u);
    }

    @Override
    @Transactional(readOnly = true)
    public UserResponseDTO get(Long id) {
        UserAccount u = orNotFound(repo.findById(id), "Usuario no encontrado");
        return userMapper.toResponse(u);
    }

    @Override
    @Transactional
    public UserResponseDTO update(Long id, UpdateUserDTO dto) {
        UserAccount u = orNotFound(repo.findById(id), "Usuario no encontrado");

        if (dto.getEmail() != null) {
            check(!repo.existsByEmailValueAndDeletedAtIsNull(dto.getEmail().getValue()), "Ya existe una cuenta con ese email" );
        }
        if (dto.getPassword() != null) {
            check(!dto.getPassword().isEmpty(), "La contraseña no puede ser nula o vacia");
        }

        userMapper.updateEntity(dto,u);

        return userMapper.toResponse(u);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        UserAccount u = orNotFound(repo.findById(id), "Usuario no encontrado");
        u.setDeletedAt(LocalDateTime.now());
    }

    @Override
    @Transactional
    public void activate(Long id) {
        UserAccount u = orNotFound(repo.findById(id), "Usuario no encontrado");

        check(!repo.existsByEmailValueAndDeletedAtIsNull(u.getEmail().value()), "Ya existe una cuenta con ese email" );

        u.setDeletedAt(null);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UserResponseDTO> list(Pageable pageable) {

        return repo.findAll(pageable).map(userMapper::toResponse);
    }
}
