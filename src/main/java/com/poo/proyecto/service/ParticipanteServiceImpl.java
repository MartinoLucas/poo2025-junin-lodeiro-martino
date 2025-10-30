package com.poo.proyecto.service;

import com.poo.proyecto.dto.participante.CreateParticipanteDTO;
import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.dto.participante.UpdateParticipanteDTO;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.entity.base.Documento;
import com.poo.proyecto.entity.base.Email;
import com.poo.proyecto.mapper.ParticipanteMapper;
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

    public ParticipanteServiceImpl(ParticipanteRepository repo, UserAccountRepository userRepo, ParticipanteMapper mapper) {
        this.repo = repo;
        this.userRepo = userRepo;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public ParticipanteResponseDTO create(CreateParticipanteDTO dto) {
        check(repo.findByDocumento_TipoAndDocumento_Numero(dto.getDocumento().getTipo(), dto.getDocumento().getNumero()).isEmpty(),
                "Ya existe un participante con ese documento");

        UserAccount user = require(() -> userRepo.findById(dto.getUserId()).orElse(null), "User no encontrado");

        Participante p = mapper.toEntity(dto);

        return mapper.toResponse(repo.save(p));
    }

    @Override
    @Transactional
    public ParticipanteResponseDTO update(Long id, UpdateParticipanteDTO dto) {
        Participante p = orNotFound(repo.findById(id), "Participante no encontrado");
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
}
