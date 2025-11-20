package com.poo.proyecto.controller;

import com.poo.proyecto.dto.authentication.AuthenticationRequestDTO;
import com.poo.proyecto.service.AuthenticationService;
import org.apache.catalina.Authenticator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/admin/auth")
    public ResponseEntity<?> authenticateAdmin(@RequestBody AuthenticationRequestDTO dto) {
        String token = authenticationService.authenticateAdmin(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }

    @PostMapping("/auth")
    public ResponseEntity<?> authenticateParticipant(@RequestBody AuthenticationRequestDTO dto) {
        String token = authenticationService.authenticateParticipant(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }

}
