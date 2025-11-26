package com.poo.proyecto.service;

import com.poo.proyecto.dto.authentication.AuthenticationRequestDTO;
import com.poo.proyecto.entity.Role;
import com.poo.proyecto.entity.UserAccount;
import com.poo.proyecto.repository.UserAccountRepository;
import com.poo.proyecto.util.JwtTokenUtil;
import com.poo.proyecto.util.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl  extends BaseServiceSupport implements AuthenticationService {

    private final UserAccountRepository userRepo;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthenticationServiceImpl(UserAccountRepository userRepo, PasswordEncoder passwordEncoder, JwtTokenUtil jwtTokenUtil) {
        this.userRepo = userRepo;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public String authenticateAdmin(AuthenticationRequestDTO dto)  {
        UserAccount user = orNotFound(userRepo.findByEmail_ValueIgnoreCase(dto.getEmail()) , "Email No Registrado");

        check(user.hasRole("ROLE_ADMIN"), "Acceso Denegado");

        check(passwordEncoder.verify(dto.getPassword(), user.getPasswordHash()), "Credenciales Invalidas" );

        return jwtTokenUtil.generateToken(user.getEmail().value(), user.getRoles().stream().map(Role::getName).toList());
    }

    @Override
    public String authenticateParticipant(AuthenticationRequestDTO dto)  {
        UserAccount user = orNotFound(userRepo.findByEmail_ValueIgnoreCase(dto.getEmail()) , "Email No Registrado");

        check((user.hasRole("ROLE_ADMIN") || user.hasRole("ROLE_PARTICIPANT")), "Acceso Denegado");

        check(passwordEncoder.verify(dto.getPassword(), user.getPasswordHash()), "Credenciales Invalidas" );

        return jwtTokenUtil.generateToken(user.getEmail().value(), user.getRoles().stream().map(Role::getName).toList());
    }
}
