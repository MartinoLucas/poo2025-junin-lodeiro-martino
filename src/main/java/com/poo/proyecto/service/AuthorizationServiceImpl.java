package com.poo.proyecto.service;

import com.poo.proyecto.dto.participante.ParticipanteResponseDTO;
import com.poo.proyecto.entity.Participante;
import com.poo.proyecto.util.JwtTokenUtil;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationServiceImpl extends BaseServiceSupport implements AuthorizationService {

    private final JwtTokenUtil jwtTokenUtil;
    private final ParticipanteService participanteService;

    public AuthorizationServiceImpl(JwtTokenUtil jwtTokenUtil, ParticipanteService participanteService) {
        this.jwtTokenUtil = jwtTokenUtil;
        this.participanteService = participanteService;
    }

    @Override
    public ParticipanteResponseDTO authorize(String token) throws Exception {
        check(jwtTokenUtil.verify(token)," no valido") ;

        String email = jwtTokenUtil.getSubject(token);

        return participanteService.findByEmail(email);
    }
}
