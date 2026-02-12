package com.poo.proyecto.service;

import com.poo.proyecto.dto.participante.CreateParticipanteDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.participante.UpdateParticipanteDTO;
import com.poo.proyecto.dto.user.CreateUserDTO;
import com.poo.proyecto.dto.user.UserResponseDTO;
import com.poo.proyecto.entity.Inscripcion;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.Role;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.entity.base.Documento;
import com.poo.proyecto.entity.base.Email;
import com.poo.proyecto.mapper.ParticipanteMapper;
import com.poo.proyecto.mapper.UserAccountMapper;
import com.poo.proyecto.mapper.common.EmailMapper;
import com.poo.proyecto.repository.InscripcionRepository;
import com.poo.proyecto.repository.ParticipanteRepository;
import com.poo.proyecto.repository.RoleRepository;
import com.poo.proyecto.repository.UserAccountRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
public class ParticipanteServiceImpl extends BaseServiceSupport implements ParticipanteService {

    private final ParticipanteRepository repo;
    private final UserAccountRepository userRepo;
    private final ParticipanteMapper mapper;
    private final UserAccountMapper userMapper;
    private final UserAccountService userAccountService;
    private final InscripcionRepository inscripcionRepository;
    private final RoleRepository roleRepo;

    public ParticipanteServiceImpl(ParticipanteRepository repo, UserAccountRepository userRepo, ParticipanteMapper mapper, UserAccountMapper userMapper, UserAccountService userAccountService, InscripcionRepository inscripcionRepository, RoleRepository roleRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.userAccountService = userAccountService;
        this.inscripcionRepository = inscripcionRepository;
        this.roleRepo = roleRepo;
    }

    @Override
    @Transactional
    public ParticipanteResponseDTO create(CreateParticipanteDTO dto) {
        // 1. Validaciones
        check(!repo.existsByDocumento_TipoAndDocumento_Numero(dto.getDocumento().getTipo(), dto.getDocumento().getNumero()),
                "Ya existe un participante con ese documento");
        check(!userRepo.existsByEmail_Value(dto.getEmail()), "Ya existe un participante con ese email");

        // 2. Crear UserAccount (El userAccountService usará el mapper con el nuevo EmailMapper)
        CreateUserDTO dtoUser = new CreateUserDTO();
        dtoUser.setEmail(dto.getEmail());
        dtoUser.setPassword(dto.getPassword());

        Long newUserId = this.userAccountService.create(dtoUser, "ROLE_PARTICIPANT").getId();
        UserAccount ua = orNotFound(userRepo.findById(newUserId), "UserAccount no encontrado");

        // 3. Crear Participante (Ahora el mapper SI mapea el email correctamente)
        Participante p = mapper.toEntity(dto);
        p.setUserAccount(ua);

        // 4. Guardar
        return mapper.toResponse(repo.save(p));
    }

    @Override
    @Transactional
    public ParticipanteResponseDTO update(Long id, UpdateParticipanteDTO dto) {
        Participante p = orNotFound(repo.findById(id), "Participante no encontrado");

        if (dto.getDocumento().getTipo() != null || dto.getDocumento().getNumero() != null) {
            check(!repo.existsByDocumento_TipoAndDocumento_Numero(dto.getDocumento().getTipo(), dto.getDocumento().getNumero()),
                    "Ya existe un participante con ese documento");
        }
        if (dto.getEmail().getValue() != null) {
            check(!repo.existsByEmail_Value(dto.getEmail().getValue()), "Ya existe un participante con ese email");
        }

        mapper.updateEntity(dto, p);

        return mapper.toResponse(p);
    }

    @Override
    @Transactional(readOnly = true)
    public ParticipanteResponseDTO get(Long id) {
        Participante p = orNotFound(repo.findById(id), "Participante no encontrado");

        return mapper.toResponse(p);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParticipanteResponseDTO> list(Pageable pageable) {
        return repo.findAll(pageable).map(mapper::toResponse);
    }

    @Override
    public ParticipanteResponseDTO findByEmail(String email) {
        Participante p = orNotFound(repo.findByEmail_Value(email), "Participante no encontrado");

        return mapper.toResponse(p);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParticipanteResponseDTO> listByCompetitionId(Long competitionId, Pageable pageable) {
        Page<Inscripcion> inscripcions = inscripcionRepository.findByCompetencia_Id(competitionId, pageable);
        return inscripcions.map(i -> mapper.toResponse(i.getParticipante()));
    }
}
