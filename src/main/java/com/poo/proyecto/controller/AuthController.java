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
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    public AuthController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping
    public ResponseEntity<?> authenticate(@RequestBody AuthenticationRequestDTO dto) {
        String token = authenticationService.authenticate(dto);
        return ResponseEntity.ok(Map.of("token", token));
    }

}
