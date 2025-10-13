package com.poo.proyecto.service;

import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.entity.base.Documento;
import com.poo.proyecto.entity.base.Email;
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

    public ParticipanteServiceImpl(ParticipanteRepository repo, UserAccountRepository userRepo) {
        this.repo = repo;
        this.userRepo = userRepo;
    }

    @Override
    @Transactional
    public Participante create(String nombre, String apellido, String tipoDocumento, String numeroDocumento, String email, Long userId) {
        if (repo.findByDocumento_TipoAndDocumento_Numero(tipoDocumento, numeroDocumento).isPresent())
            throw new RuntimeException("Documento ya registrado");

        var user = require(() -> userRepo.findById(userId).orElse(null), "User no encontrado");

        Participante p = new Participante();
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setDocumento(new Documento(Enum.valueOf(com.poo.proyecto.entity.TipoDocumento.class, tipoDocumento), numeroDocumento));
        p.setEmail(new Email(email));
        p.setUserAccount(user);
        return repo.save(p);
    }

    @Override
    @Transactional
    public Participante update(Long id, String nombre, String apellido, String email) {
        Participante p = orNotFound(repo.findById(id), "Participante no encontrado");
        p.setNombre(nombre);
        p.setApellido(apellido);
        p.setEmail(new Email(email));
        return p;
    }

    @Override
    @Transactional(readOnly = true)
    public Participante get(Long id) {
        return orNotFound(repo.findById(id), "Participante no encontrado");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Participante> list(Pageable pageable) {
        return repo.findAll(pageable);
    }
}
