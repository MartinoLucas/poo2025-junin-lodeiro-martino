package com.poo.proyecto.service;

import com.poo.proyecto.entity.Participante;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ParticipanteService {

    Participante create(String nombre, String apellido, String tipoDocumento, String numeroDocumento, String email, Long userId);

    Participante update(Long id, String nombre, String apellido, String email);

    Participante get(Long id);

    Page<Participante> list(Pageable pageable);
}
