package com.poo.proyecto.service;

import com.poo.proyecto.dto.participante.CreateParticipanteDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.participante.UpdateParticipanteDTO;
import com.poo.proyecto.dto.user.CreateUserDTO;
import com.poo.proyecto.dto.user.UserResponseDTO;
import com.poo.proyecto.entity.Inscripcion;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.entity.base.Documento;
import com.poo.proyecto.entity.base.Email;
import com.poo.proyecto.mapper.ParticipanteMapper;
import com.poo.proyecto.mapper.UserAccountMapper;
import com.poo.proyecto.repository.InscripcionRepository;
import com.poo.proyecto.repository.ParticipanteRepository;
import com.poo.proyecto.repository.UserAccountRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ParticipanteServiceImpl extends BaseServiceSupport implements ParticipanteService {

    private final ParticipanteRepository repo;
    private final UserAccountRepository userRepo;
    private final ParticipanteMapper mapper;
    private final UserAccountMapper userMapper;
    private final UserAccountService userAccountService;
    private final InscripcionRepository inscripcionRepository;

    public ParticipanteServiceImpl(ParticipanteRepository repo, UserAccountRepository userRepo, ParticipanteMapper mapper, UserAccountMapper userMapper, UserAccountService userAccountService, InscripcionRepository inscripcionRepository) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.mapper = mapper;
        this.userMapper = userMapper;
        this.userAccountService = userAccountService;
        this.inscripcionRepository = inscripcionRepository;
    }

    @Override
    @Transactional
    public ParticipanteResponseDTO create(CreateParticipanteDTO dto) {
        check(!repo.existsByDocumento_TipoAndDocumento_Numero(dto.getDocumento().getTipo(), dto.getDocumento().getNumero()),
                "Ya existe un participante con ese documento");

        check(!userRepo.existsByEmail_Value(dto.getEmail().getValue()), "Ya existe un participante con ese email");

        CreateUserDTO dtoUser = new CreateUserDTO();
        dtoUser.setEmail(dto.getEmail());
        dtoUser.setPassword(dto.getPassword());
        UserResponseDTO newUser = this.userAccountService.create(dtoUser, "ROLE_PARTICIPANT");

        Participante p = mapper.toEntity(dto);
        p.setUserAccount(userMapper.toEntity(dtoUser));

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
