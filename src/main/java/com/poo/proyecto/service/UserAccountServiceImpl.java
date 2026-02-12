package com.poo.proyecto.service;

import com.poo.proyecto.dto.user.CreateUserDTO;
import com.poo.proyecto.dto.user.UpdateUserDTO;
import com.poo.proyecto.dto.user.UserResponseDTO;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.Role;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.entity.base.Email;
import com.poo.proyecto.mapper.ParticipanteMapper;
import com.poo.proyecto.mapper.UserAccountMapper;
import com.poo.proyecto.mapper.common.EmailMapper;
import com.poo.proyecto.repository.ParticipanteRepository;
import com.poo.proyecto.repository.RoleRepository;
import com.poo.proyecto.repository.UserAccountRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import com.poo.proyecto.util.PasswordEncoder;

@Service
public class UserAccountServiceImpl extends BaseServiceSupport implements UserAccountService {

    private final UserAccountRepository repo;
    private final UserAccountMapper userMapper;
    private final EmailMapper emailMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepo;
    private final ParticipanteRepository participanteRepo;

    public UserAccountServiceImpl(UserAccountRepository repo, UserAccountMapper userMapper, EmailMapper emailMapper, PasswordEncoder passwordEncoder, RoleRepository roleRepo, ParticipanteRepository participanteRepo) {
        this.repo = repo;
        this.userMapper = userMapper;
        this.emailMapper = emailMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepo = roleRepo;
        this.participanteRepo = participanteRepo;
    }

    @Override
    @Transactional
    public UserResponseDTO create(CreateUserDTO dto, String defaultRoleName) {
        check(!repo.existsByEmailValueAndDeletedAtIsNull(dto.getEmail()), "Ya existe una cuenta con ese email");

        UserAccount u = userMapper.toEntity(dto);

        if (dto.getEmail() != null) {
            com.poo.proyecto.entity.base.Email emailEntity = new com.poo.proyecto.entity.base.Email();
            emailEntity.setValue(dto.getEmail());
            u.setEmail(emailEntity);
        }

        // Cargar roles desde la BD
        Set<Role> roles = new HashSet<>();
        if (dto.getRoleIds() != null && !dto.getRoleIds().isEmpty()) {
            roles.addAll(roleRepo.findAllById(dto.getRoleIds()));
        }

        Role defaultRole = orNotFound(roleRepo.findByNameIgnoreCase(defaultRoleName), "Rol por defecto no encontrado");
        roles.add(defaultRole);

        u.setRoles(roles);

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

//        if (dto.getEmail() != null) {
//            check(!repo.existsByEmailValueAndDeletedAtIsNull(dto.getEmail().getValue()), "Ya existe una cuenta con ese email" );
//        }
        if (dto.getPassword() != null) {
            check(!dto.getPassword().isEmpty(), "La contraseña no puede ser nula o vacia");
        }

        u.setPasswordHash(passwordEncoder.encode(dto.getPassword()));

        return userMapper.toResponse(u);
    }

    @Override
    @Transactional
    public void deactivate(Long id) {
        UserAccount u = orNotFound(repo.findById(id), "Usuario no encontrado");
        check(u.getDeletedAt() == null, "El usuario ya se encuentra inactivo");
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
    public Page<UserResponseDTO> listAdmin(Pageable pageable) {

        return repo.findByRole("ROLE_ADMIN", pageable).map(userMapper::toResponse);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Participante> getParticipant(Long userId){
        if(participanteRepo.existsByUserAccount_Id(userId)){
            return participanteRepo.findByUserAccount_Id(userId);
        }
        return Optional.empty();
    }
}
